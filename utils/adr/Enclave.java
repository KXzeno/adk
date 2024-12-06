package utils.adr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.nio.file.Path;
import java.net.URI;

import utils.exceptions.InvalidProtocolException;
import utils.resolvers.*;

public class Enclave {
  private static final String RESET = "\033[0m"; 
  private static final String PURPLE = "\033[1;35m"; 

  public Enclave() {}

  private static String reducer(String prev, String next) {
    return prev + next + '\n';
  }

  private static void generateBP(String[] args) {
    // ClassLoader to access resources with respect to jar root, accessible by .class
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    // ClassPath to navigate user's FileSystem with respect to location of initialized JVM 
    URI classPath = new File(System.getProperty("java.class.path")).toURI();
    String targetPath = "";
    try {
      final String PROTOCOL = URIParser.identifyScheme(classLoader);
      switch (PROTOCOL) {
        case "jar": targetPath = new JARPrototypeResolver().resolve(classLoader).toString(); break;
        case "file": targetPath = new PrototypeResolver().resolve(Path.of(classPath)).toString(); break;
        default: throw new InvalidProtocolException("Scheme is not of protocol \\'file\\' or \\'jar\\'");
      }
      String formattedTargetPath = targetPath.replaceAll("\\\\", "/");
      URI targetURI = URI.create(formattedTargetPath);
      for (String arg : args) {
        System.out.println(arg);
      }
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
    generateBP(args);
  }
}
