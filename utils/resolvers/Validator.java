package utils.resolvers;

import utils.ReadablePrototype;

/**
 * @author Kx
 */
public interface Validator<T> extends ReadablePrototype {
/**
 * @
 */
  boolean validate(T path);
}
