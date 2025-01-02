package utils.resolvers;

import java.nio.file.Path;

/**
 * A custom path resolver that resolves against the entry-point location,
 * whether from a JAR or Java binary.
 *
 * @author Kx
 */
public interface Resolver {
  /**
   * Finds a path and resolves it against the caller path
   * @param path the path to seek and join the path this is called against
   * @return path constructed if resolved, null otherwise
   */
  Path resolve(Path path);
}
