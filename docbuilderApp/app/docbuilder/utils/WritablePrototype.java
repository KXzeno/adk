package docbuilder.utils;

import java.net.URI;

import docbuilder.utils.exceptions.UnknownURIException;

/**
 * A WritablePrototype is an open buffer object that the caller may use to fulfill existing parameter fields within the parsed prototype, by default, it returns a prototype file with empty fields located in the current working directory instantiated via initialize method.
 *
 * The term Prototype is used outside programming context; they are instances of a LaTeX document template.
 *
 * @author Kx
 */
public interface WritablePrototype extends ReadablePrototype {
  /**
   * Gets the current location for where the prototype will be instantiated
   * @return path to instantiate
   */
  URI getLocation();

  /**
   * Changes the current location for where the prototype will be instantiated to another
   * @param loc the URI for relocation
   */
  void setLocation(URI loc) throws UnknownURIException;

  /**
   * Gets the line of a particular field
   * @param fieldName the name to search within traversal of prototype text
   * @return respective line of target field, null otherwise
   */
  String getField(String fieldName);

  /**
   * Defines or change the value of a particular prototype field
   * @param fieldName the prototype field to [re]assign value to
   * @param input the new field value
   */
  void setField(String fieldName, String input);

  /**
   * Initializes the prototype in the class's location, otherwise current working directory 
   */
  void initialize();
}
