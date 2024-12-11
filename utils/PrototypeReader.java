package utils;

import java.net.URI;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.io.File;
import java.nio.file.Path;
import java.io.IOException;

import utils.exceptions.InvalidProtocolException;
import utils.resolvers.URIParser;
import utils.resolvers.JARPrototypeResolver;
import utils.resolvers.PrototypeResolver;

public class PrototypeReader implements ReadablePrototype {
  // ClassLoader to access resources with respect to jar root, accessible by .class
  private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
  private URI prototype = null;
  public String formattedTargetPath = null;

  public PrototypeReader() {}

  private static String reducer(String prev, String next) {
    return prev + next + '\n';
  }

  /**
   * {@inheritDoc}
   *
   * @throws InvalidProtocolException if the URI scheme is not jar OR file
   */
  public URI getPrototype() throws InvalidProtocolException, NoSuchFileException {
    String targetPath = null;
    // ClassPath to navigate user's FileSystem with respect to location of initialized JVM 
    URI classPath = new File(System.getProperty("java.class.path")).toURI();

    final String PROTOCOL = URIParser.identifyScheme(this.classLoader);
    switch (PROTOCOL) {
      case "jar": targetPath = new JARPrototypeResolver().resolve(classLoader).toString(); break;
      case "file": targetPath = new PrototypeResolver().resolve(Path.of(classPath)).toString(); break;
      default: throw new InvalidProtocolException("Scheme is not of protocol \\'file\\' or \\'jar\\'");
    } 

    this.formattedTargetPath = targetPath.replaceAll("\\\\", "/");
    if (PROTOCOL.equals("jar")) {
      this.prototype = URI.create(formattedTargetPath);
    } else {
      StringBuilder ftpURI = new StringBuilder(PROTOCOL).append(":///");
      this.prototype = URI.create(formattedTargetPath.replaceAll("([\\S\\s]+)", String.format("%s$1", ftpURI.toString())));
    }
    return this.prototype;
  }

  /**
   * Parses the prototype URI and converts it to a modifiable character sequence
   *
   * @param prototype the prototype URI to extract and parse from
   * @return a mutable CharSequence intended for forwarding to an existing or new buffer
   */
  public CharSequence parse(URI prototype) throws NoSuchFileException {
    try {
      BufferedReader bpReader;
      if (this.formattedTargetPath.matches("^jar\\:[\\S\\s]+") == true) {
        String fileOfProto = this.prototype.toString().replaceAll("(^jar:file:/)([\\S\\s]+)((?<=adk/)[\\S\\s]+jar!/)(?:dist/)?([\\S\\s]+)", "$2$4");
        bpReader = Files.newBufferedReader(new File(fileOfProto).toPath());
      } else {
        bpReader = Files.newBufferedReader(new File(this.prototype).toPath());
      }
      CharSequence fileText = bpReader.lines().reduce("", PrototypeReader::reducer);
      bpReader.close();
      return fileText;
    } catch (IOException x) {
      if (x instanceof NoSuchFileException) {
        throw new NoSuchFileException("No file found.");
      } else {
        x.printStackTrace();
        System.err.println(x + "\nCaller: PrototypeReader::parse");
      }
      return null;
    }
  }

  /**
   * Lazily integrates and synchronizes the getPrototype and parse methods
   *
   * @throws InvalidProtocolException if the URI scheme is not jar OR file
   * @return a mutable CharSequence intended for forwarding to an existing or new buffer
   */
  public static CharSequence fetch() throws InvalidProtocolException, NoSuchFileException {
    PrototypeReader pr = new PrototypeReader();
    return pr.parse(pr.getPrototype());
  }
}
