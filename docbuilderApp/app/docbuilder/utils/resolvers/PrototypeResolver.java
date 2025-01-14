package docbuilder.utils.resolvers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

public class PrototypeResolver implements Resolver {
  private Path binaryPath;
  private Path assetsPath;

  public PrototypeResolver () {}

  @Override
  public Path resolve(Path path) {
    this.binaryPath = new BinaryResolver().resolve(path);
    this.assetsPath = Paths.get(this.binaryPath.toString().replace("\\bin", "\\assets"));
    return this.assetsPath.resolve("boilerplate.tex");
  }

  /**
   * Returns path of the project root of which the prototype resides.
   * @param path the path reference used to resolve to project root
   * @return base path
   */
  public Path resolveToBasePath(Path path) {
    if (this.binaryPath == null) {
      this.binaryPath = new BinaryResolver().resolve(path);
    }
    this.assetsPath = Paths.get(this.binaryPath.toString().replace("\\bin", "\\"));
    return this.assetsPath;
  }

  public Path resolveToBasePath() {
    Path classPath = Path.of(new File(System.getProperty("java.class.path")).toURI());
    if (this.binaryPath == null) {

      this.binaryPath = new BinaryResolver().resolve(classPath);
    }
    this.assetsPath = Paths.get(this.binaryPath.toString().replace("\\bin", "\\"));
    return this.assetsPath;
  }
}
