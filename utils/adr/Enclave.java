package utils.adr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.lang.CharSequence;
import java.security.CodeSource;
import java.net.URI;

// TODO: 

public class Enclave {
  private static final String RESET = "\033[0m"; 
  private static final String PURPLE = "\033[1;35m"; 

  public Enclave() {}

  private static String reducer(String prev, String next) {
    return prev + next + '\n';
  }

  private static Path resolvePrototypePath(Path path) {
    Path binaryPath = resolveBinPath(path);
    Path assetsPath = Paths.get(binaryPath.toString().replace("\\bin", "\\assets"));
    return assetsPath.resolve("boilerplate.tex");
  }

  private static Path resolveBinPath(Path path) {
    /** @remarks
     * For personal use if need to parse System props
     *
     * String SYS_KEYS = System.getProperties().stringPropertyNames().toString();
     * System.out.println(SYS_KEYS); 
     * */

    StringBuilder newPath = new StringBuilder("");
    String[] splittedPath = path.normalize().toAbsolutePath().toString().split("\\.\\.\\\\");
    // Non-lossy explicit widen cast
    byte depth = (byte) (splittedPath.length - 1);
    if (depth > 0) {
      newPath.append(splittedPath[0]);
      for (int i = 0; i <= depth; ++i) {
        // System.out.println("BEFORE: " + newPath.toString());
        newPath.delete(newPath.lastIndexOf("\\"), newPath.length());
        // System.out.println("AFTER: " + newPath.toString());
      }
      return new File(newPath.append("\\").append(splittedPath[depth]).toString()).toPath();
    }
    return new File(splittedPath[0]).toPath();
  }

  private static void generateBP() {
    Path classPath = new File(System.getProperty("java.class.path")).toPath();
    System.out.println((resolvePrototypePath(classPath).toString()));
    /*
    try {
      BufferedReader bpReader = Files.newBufferedReader(new File("assets/boilerplate.tex").toPath());
      CharSequence fileText = bpReader.lines().reduce("", Enclave::reducer);
      // System.out.println(fileText);
    } catch (IOException x) {
      System.err.println(x);
    }
    */
  }

  public static void main(String[] args) {
    generateBP();
  }
}
