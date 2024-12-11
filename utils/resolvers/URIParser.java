package utils.resolvers;

import java.net.URISyntaxException;

public class URIParser {
  public static String identifyScheme(ClassLoader cl) {
    String scheme = null;
    try {
      scheme = cl.getResource("").toURI().getScheme();
    } catch (URISyntaxException x1) {
      try {
        scheme = cl.getResource("META-INF").toURI().getScheme();
      } catch (URISyntaxException x2) {
        System.err.println(x1);
        x1.printStackTrace();
        System.err.println(x2);
        x2.printStackTrace();
      }
    }
    return scheme;
  }
}
