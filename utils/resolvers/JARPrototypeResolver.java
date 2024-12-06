package utils.resolvers;

import java.net.URI;
import java.net.URISyntaxException;

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
}
