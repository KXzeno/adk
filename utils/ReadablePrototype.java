package utils;

import java.net.URI;

import utils.exceptions.InvalidProtocolException;

/**
 * A ReadablePrototype is a URI source that exposes itself to the caller through a
 * parameterless getPrototype method
 * 
 * @author Kx
 */
public interface ReadablePrototype {
  /**
   * Returns a URI targetting the prototype path
   * @return the URI of current prototype path
   */
  URI getPrototype() throws InvalidProtocolException;
}
