package utils.jtex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import utils.resolvers.JARPrototypeResolver;
import utils.resolvers.PrototypeResolver;
import utils.resolvers.URIParser;

public class TexConfig {
  /**
   * Initializes TEXMF root 'localtexmf' in local MiKTeX portable distribution.
   * @throws IOException
   */
  public static void addCommonRoot() throws IOException {
    String scheme = URIParser.identifyScheme();
    Path basePath;
    if (scheme.equals("jar")) {
      JARPrototypeResolver protoRes = new JARPrototypeResolver();
      basePath = Path.of(protoRes.resolveToBasePath());
    } else if (scheme.equals("file")) {
      PrototypeResolver protoRes = new PrototypeResolver();
      basePath = protoRes.resolveToBasePath();
    } else {
      throw new IOException("FAILED TO FETCH BASE PATH");
    }

    Path config = basePath.resolve("dist/mintex/miktex-portable/texmfs/install/miktex/config/miktexstartup.ini");
    Path localtexmf = basePath.resolve("localtexmf");
    try {
      BufferedReader brv = Files.newBufferedReader(config);
      BufferedReader br = Files.newBufferedReader(config);
      Stream<String> currConfig = brv.lines();
      boolean hasRoots = currConfig.anyMatch(line -> line.contains("CommonRoots"));
      if (hasRoots == true) {
        throw new IOException("Local TEXMF root has already been initialized.");
      }
      String page = br.lines().reduce("", (prev, next) -> {
        if (next.contains("Version")) {
          StringBuilder cmnRts = new StringBuilder(prev);
          cmnRts.append(next).append("\n\n").append("[Paths]\n").append(";;; Common Roots\n").append("CommonRoots=").append(localtexmf);
          return cmnRts.toString();
        } else {
          return prev + next + '\n';
        }
      });
      System.out.println("Config:\n" + page);
      br.close();
      BufferedWriter bw = Files.newBufferedWriter(config);
      bw.write(page);
      bw.close();
    } catch (IOException exc) {
      exc.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   * @param path a TDS-Compliant root to append to local MiKTeX portable distribution's startup config
   */
  public static void addCommonRoot(Path path) throws IOException {
    String scheme = URIParser.identifyScheme();
    Path basePath;
    if (scheme.equals("jar")) {
      JARPrototypeResolver protoRes = new JARPrototypeResolver();
      basePath = Path.of(protoRes.resolveToBasePath());
    } else if (scheme.equals("file")) {
      PrototypeResolver protoRes = new PrototypeResolver();
      basePath = protoRes.resolveToBasePath();
    } else {
      throw new IOException("FAILED TO FETCH BASE PATH");
    }

    Path config = basePath.resolve("dist/mintex/miktex-portable/texmfs/install/miktex/config/miktexstartup.ini");
    try {
      BufferedReader brv = Files.newBufferedReader(config);
      BufferedReader br = Files.newBufferedReader(config);
      Stream<String> currConfig = brv.lines();
      boolean hasRoots = currConfig.anyMatch(line -> line.contains("CommonRoots"));
      if (hasRoots == false) {
        addCommonRoot();
      } else if (hasRoots == true) {
        String page = br.lines().reduce("", (prev, next) -> {
          if (next.contains("CommonRoots")) {
            StringBuilder cmnRts = new StringBuilder(prev);
            cmnRts.append(next).append(";").append(path).append('\n');
            return cmnRts.toString();
          } else {
            return prev + next + '\n';
          }
        });
        System.out.println("Config:\n" + page);
        br.close();
        BufferedWriter bw = Files.newBufferedWriter(config);
        bw.write(page);
        bw.close();
      }
    } catch (IOException exc) {
      exc.printStackTrace();
    }
  }
}
