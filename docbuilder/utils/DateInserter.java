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
// import java.util.Calendar;
// import java.util.Date;
// import java.text.DateFormat;
import java.util.Locale;
// import java.util.TimeZone;
import java.time.format.DateTimeFormatter;
// import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.Clock;

public class DateInserter {

  public DateInserter() {
  }

  public boolean start = false;
  private StringBuilder matcher = new StringBuilder();
  private int matched = 0;
  private boolean isDateJustInit = false;

  private static String accumulator(String a, String b) {
    return a + b + '\n';
  }

  private void updateMatcher(String line) {
    String shallowLine = line;
    // Only handles curlies for now
    final String opening = "{";
    final String closing = "}";

    shallowLine = shallowLine.replaceAll("[^\\{|\\}]", "");
    this.matcher.append(shallowLine);

    // TODO: Refactor and make it rely on this.matched for adaptability
    // TODO: Utilize method reference and lambda expressions

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
                /** @Deprecated
                 * Date now = new Date();
                 * DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
                 * dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles")); */
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.US);
                ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC());
                // Parses to UTC, unused
                // ZonedDateTime parsedDate = ZonedDateTime.parse(now.format(DateTimeFormatter.ISO_INSTANT));
                boolean hasDate = line.trim().matches("(\\{(1[0-2]{1}|0[1-9])\\-{1}(3[0-2]{1}|[1-2]{1}[0-9]{1}|0[1-9])\\-{1}\\d{4}\\})");
                if (this.matched == 5 && hasDate == false) {
                  this.isDateJustInit = true;
                  return new StringBuilder("{").append(now.format(formatter)).append("}").toString().replaceAll("/", "-");
                } else if (this.matched == 5 && hasDate == true) {
                  return line;
                }

                if (this.matched == 6 && this.isDateJustInit == true) {
                  System.out.println("Entered");
                  return line;
                } else if (this.matched == 6 && this.isDateJustInit == false) {
                  System.out.println("Entered 2");
                  return new StringBuilder("{").append(now.format(formatter)).append("}").toString().replaceAll("/", "-");
                }
                /** Display Available IDs
                  String[] timezones = TimeZone.getAvailableIDs();
                  for (int i = 0; i < timezones.length; i++) { System.out.println(timezones[i]); } */
              }
              return line;
            } else { return line; }
          }).reduce("", DateInserter::accumulator);

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
