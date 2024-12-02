package utils;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
// import java.nio.file.FileSystem;
// import java.nio.file.StandardOpenOption;
import java.lang.CharSequence;
import java.nio.file.Path;

public class DateInserter {

  public DateInserter() {
  }

  public boolean start = false;
  private StringBuilder matcher = new StringBuilder();
  private int matched = 0;

  private void updateMatcher(String line) {
    String shallowLine = line;
    // Only handles curlies for now
    final String opening = "{";
    final String closing = "}";

    shallowLine = shallowLine.replaceAll("[^\\{|\\}]", "");
    this.matcher.append(shallowLine);

    for (int i = 0; i < this.matcher.toString().length(); i++) {
      if (this.matcher.indexOf(opening) != -1 && this.matcher.indexOf(closing) != -1) {
        matcher.deleteCharAt(matcher.lastIndexOf(opening));
        matcher.deleteCharAt(matcher.indexOf(closing));
        if (this.matcher.length() == 0) {
          this.matched++;
        }
      } 
    }
  }

  public void modifyDateFields(String[] args) {
    if (args.length > 0) {
      // Otherwise, concatenation runs n^2
      StringBuilder input = new StringBuilder();
      for (String arg : args) {
        input.append(arg).append(" ");
      }

      String inputPath = input.toString().trim();
      File inputFile = new File(new StringBuilder("./").append(inputPath).toString());
      Path target = inputFile.toPath();
      if (inputFile.exists() && inputFile.canRead() && inputFile.canWrite()) {
        try {
          BufferedReader reader = Files.newBufferedReader(target, StandardCharsets.UTF_8);
          CharSequence csq = reader.lines().map(line -> {
            if (line.contains("\\frontmatter") || this.start == true) {
              this.start = true;
              int prevMatched = this.matched;
              updateMatcher(line);
              if (this.matched >= 5 && this.matched <= 6 && prevMatched != this.matched) {
                System.out.println(line);
              }
              return line;
            } else { return line; }
          }).reduce("", (a, b) -> a + b + "\n");

          reader.close();
          BufferedWriter writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8);
          writer.append(csq);
          writer.close();
        } catch (IOException x) {
          x.printStackTrace();
        }
      }
    } else {
      System.out.println("No args");
    }
  }
  public static void main(String[] args) {
    DateInserter augmenter = new DateInserter();
    augmenter.modifyDateFields(args);
  }
}
