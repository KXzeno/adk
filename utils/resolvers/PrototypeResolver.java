package utils.resolvers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PrototypeResolver implements Resolver {
  private Path binaryPath;
  private Path assetsPath;

  public PrototypeResolver () {}

  public Path resolve(Path path) {
    this.binaryPath = new BinaryResolver().resolve(path);
    this.assetsPath = Paths.get(binaryPath.toString().replace("\\bin", "\\assets"));
    return assetsPath.resolve("boilerplate.tex");
  }

}
