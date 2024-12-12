package utils.resolvers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;

import utils.PrototypeWriter;

public class JARPrototypeResolver {
  public JARPrototypeResolver() {}

  /**
   * @param cl the class loader get extract resource from
   * @return URI converted from resolving given path, otherwise null
   */
  public URI resolve(ClassLoader cl) {
    try {
      return cl.getResource("assets/boilerplate.tex").toURI();
    } catch (URISyntaxException x) {
      System.err.println(x);
    }
    return null;
  }

  /**
   * Returns path of the project root of which the prototype resides.
   * @param path the path reference used to resolve to project root
   * @return base URI
   */
  public URI resolveToBasePath(ClassLoader cl) {
    try {
      URI protoURI = new PrototypeWriter().getPrototype();
      return new URI(protoURI.toString().replaceAll("(^jar\\:)([\\S\\s]*/)((?<!dist/)[\\S\\s]+jar!/)(?:dist/)?([\\S\\s]+)", "$2"));
    } catch (URISyntaxException | NoSuchFileException exc) {
      if (exc instanceof NoSuchFileException) {
        System.out.println("Unable to detect prototype; rebound is expected to undergo...");
      } else {
        System.err.println(exc);
      }
    }
    return null;
  }
}
