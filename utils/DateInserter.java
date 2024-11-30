package utils;

// import java.util.Scanner;

public class DateInserter {
  public static void main(String[] args) {
    if (args.length > 0) {
      // Otherwise, concatenation runs n^2
      StringBuilder input = new StringBuilder();
      for (String arg : args) {
        input.append(arg).append(" ");
      }
      // TODO: Begin file search
      // Start at likely paths (e.g., ./app/)
    } else {
      System.out.println("No args");
    }
  }
}
