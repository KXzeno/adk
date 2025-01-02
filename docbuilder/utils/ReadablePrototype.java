package utils;

import java.net.URI;
import java.nio.file.NoSuchFileException;

import utils.exceptions.InvalidProtocolException;

/**
 * A ReadablePrototype is a URI source that exposes itself to the caller through a
 * parameterless getPrototype method
 * 
 * The term Prototype is used outside programming context; they are instances of a LaTeX document template.
 *
 * @author Kx
 */
public interface ReadablePrototype {
  /**
   * Returns a URI targetting the prototype path
   * @return the URI of current prototype path
   * @throws InvalidProtocolException if not a jar or a file
   * @throws NoSuchFileException if the prototype path cannot be resolved
   */
  URI getPrototype() throws InvalidProtocolException, NoSuchFileException;
}
