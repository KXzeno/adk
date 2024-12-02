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
  DateInserter() {
  }

  public void modifyDateFields(String[] args) {
    if (args.length > 0) {
      // Otherwise, concatenation runs n^2
      StringBuilder input = new StringBuilder();
      for (String arg : args) {
        input.append(arg).append(" ");
      }
      // TODO: Begin file search
      String inputPath = input.toString().trim();
      File inputFile = new File(new StringBuilder("./").append(inputPath).toString());
      Path target = inputFile.toPath();
      if (inputFile.exists() && inputFile.canRead() && inputFile.canWrite()) {
        try {
          BufferedReader reader = Files.newBufferedReader(target, StandardCharsets.UTF_8);
          CharSequence csq = reader.lines().map(line -> {
            if (line.contains("C Date")) {
              return "{Pending Creation Date}";
            } else if (line.contains("E Date")) {
              return "{Pending Edited Date}";
            } else {
              return line;
            }
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
