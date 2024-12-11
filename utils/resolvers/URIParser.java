package utils.resolvers;

import java.net.URISyntaxException;
import java.net.URL;

public class URIParser {
  public static String identifyScheme(ClassLoader cl) {
    String scheme = null;
    try {
      URL currPath = cl.getResource("");
      if (currPath == null) {
        scheme = cl.getResource("META-INF").toURI().getScheme();
      } else {
        scheme = currPath.toURI().getScheme();
      }
    } catch (URISyntaxException exc) {
      exc.printStackTrace();
    }
    return scheme;
  }
}
