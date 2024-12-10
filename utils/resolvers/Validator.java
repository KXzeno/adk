package utils.resolvers;

/**
 * Generic validator interface that ensures the exisence of a 
 * required source that is not contained in the JRE or runtime image, 
 * it may also be used to ensure types at runtime.
 * @author Kx
 */
public interface Validator<T> {
  /**
   * Validates the argument if it is an existing instance or source
   * @param T the argument to validate
   */
  boolean validate(T obj);

  /**
   * Undergoes data recreation of source or instance is revalidated, the
   * extent of retrieval is left to implementation.
   */
  T rebound();
}
