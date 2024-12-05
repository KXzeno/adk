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
import java.net.URISyntaxException;

import utils.exceptions.InvalidProtocolException;

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

  private static URI resolveJARPrototypePath(ClassLoader cl) {
    try {
      return cl.getResource("assets/boilerplate.tex").toURI();
    } catch (URISyntaxException x) {
      System.err.println(x);
    }
    return null;
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

  private static String identifyScheme(ClassLoader cl) {
    String scheme = null;
    try {
      scheme = cl.getResource("").toURI().getScheme();
    } catch (NullPointerException | URISyntaxException x1) {
      try {
        scheme = cl.getResource("META-INF").toURI().getScheme();
      } catch (NullPointerException | URISyntaxException x2) {
        System.err.println(x1);
        System.err.println(x2);
      }
    }
    return scheme;
  }

  private static void generateBP() {
    // ClassLoader to access resources with respect to jar root, accessible by .class
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    // ClassPath to navigate user's FileSystem with respect to location of initialized JVM 
    URI classPath = new File(System.getProperty("java.class.path")).toURI();
    String targetPath = "";
    try {
      final String PROTOCOL = identifyScheme(classLoader);
      switch (PROTOCOL) {
        case "jar": targetPath = resolveJARPrototypePath(classLoader).toString(); break;
        case "file": targetPath = resolvePrototypePath(Path.of(classPath)).toString(); break;
        default: throw new InvalidProtocolException("Scheme is not of protocol \\'file\\' or \\'jar\\'");
      }
      URI targetURI = URI.create(targetPath.replaceAll("\\\\", "/"));
      System.out.println(targetURI);

      // try {
      //   BufferedReader bpReader = Files.newBufferedReader(new File(targetURI).toPath());
      //   CharSequence fileText = bpReader.lines().reduce("", Enclave::reducer);
      //   bpReader.close();
      //   BufferedWriter bpWriter = Files.newBufferedWriter(newFile(""))
      // } catch (IOException x) {
      //   System.err.println(x);
      // }
    } catch (InvalidProtocolException x) {
      System.err.println(x);
    }
  }

  public static void main(String[] args) {
    generateBP();
  }
}
