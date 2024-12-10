package utils.resolvers;

import java.nio.file.Path;
import java.net.URI;
import utils.ReadablePrototype;
import utils.exceptions.InvalidProtocolException;

public class PrototypeValidator implements Validator<ReadablePrototype> {
  URI protoURI;
  public PrototypeValidator() {}

  public boolean validate(ReadablePrototype prototype) {
    try {
      this.protoURI = prototype.getPrototype();
      return true;
    } catch (InvalidProtocolException exc) {
      System.err.println(exc);
      return false;
    } finally {
      // Validation is a one-off operation, remove from memory for optimization
      this.protoURI = null;
    }
  }

  public ReadablePrototype rebound() {
    return null;
  }
}
