package utils;

import java.net.URI;
import java.io.BufferedReader;
import java.nio.file.Files;
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
  public URI getPrototype() throws InvalidProtocolException {
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
    this.prototype = URI.create(formattedTargetPath);

    return this.prototype;
  }

  /**
   * Parses the prototype URI and converts it to a modifiable character sequence
   *
   * @param prototype the prototype URI to extract and parse from
   * @return a mutable CharSequence intended for forwarding to an existing or new buffer
   */
  public CharSequence parse(URI prototype) throws NullPointerException {
    try {
      BufferedReader bpReader = Files.newBufferedReader(new File(this.prototype).toPath());
      CharSequence fileText = bpReader.lines().reduce("", PrototypeReader::reducer);
      bpReader.close();
      return fileText;
    } catch (IOException x) {
      System.err.println(x);
    }
    return null;
  }

  /**
   * Lazily integrates and synchronizes the getPrototype and parse methods
   *
   * @throws InvalidProtocolException if the URI scheme is not jar OR file
   * @return a mutable CharSequence intended for forwarding to an existing or new buffer
   */
  public static CharSequence fetch() throws NullPointerException, InvalidProtocolException {
    PrototypeReader pr = new PrototypeReader();
    return pr.parse(pr.getPrototype());
  }
}
