package utils.resolvers;

import java.nio.file.Path;
import java.io.File;

public class BinaryResolver implements Resolver {
  private StringBuilder newPath;
  private String[] splittedPath;
  private byte depth;

  public BinaryResolver() {}

  @Override
  public Path resolve(Path path) {
    /** @remarks
     * For personal use if need to parse System props
     *
     * String SYS_KEYS = System.getProperties().stringPropertyNames().toString();
     * System.out.println(SYS_KEYS); 
     * */
    this.newPath = new StringBuilder("");
    this.splittedPath = path.normalize().toAbsolutePath().toString().split("\\.\\.\\\\");

    // Non-lossy explicit widen cast
    this.depth = (byte) (this.splittedPath.length - 1);
    if (this.depth > 0) {
      this.newPath.append(this.splittedPath[0]);
      for (int i = 0; i <= depth; ++i) {
        // System.out.println("BEFORE: " + newPath.toString());
        this.newPath.delete(this.newPath.lastIndexOf("\\"), this.newPath.length());
        // System.out.println("AFTER: " + newPath.toString());
      }
      return new File(this.newPath.append("\\").append(this.splittedPath[depth]).toString()).toPath();
    }
    return new File(this.splittedPath[0]).toPath();
  }
}



