package utils.exceptions;

// Remember, only checked runtime exceptions

public class UnknownURIException extends Exception {
  public UnknownURIException() {
    super("Cannot resolve URI");
  }
}
