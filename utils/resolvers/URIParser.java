package utils.resolvers;

import java.lang.NullPointerException;
import java.net.URISyntaxException;

public class URIParser {
  public static String identifyScheme(ClassLoader cl) {
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
}
