package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import utils.PrototypeReader;
import utils.exceptions.InvalidProtocolException;
import utils.LinkedStack;

public class PrototypeWriter implements WritablePrototype {
  private URI loc;
  private CharSequence content;
  private File prototype;
  public static final String RESET = "\033[0m"; 
  public static final String PURPLE = "\033[1;35m"; 
  public static final String[] FIELDS = { 
    "type", 
    "title", 
    "status", 
    "context",
    "core decision",
    "prospects",
    "decision parameters",
    "technical debt",
    "decision process",
    "results and conflicts",
    "experience report",
  };
  private int position = -1;

  @Override
  public URI getPrototype() {
    try {
      PrototypeReader pr = new PrototypeReader();
      URI prototype = pr.getPrototype();
      if (prototype == null) {
        throw new InvalidProtocolException("URI Protocol could not be parsed.");
      } else {
        // this.loc = prototype;
        this.content = pr.parse(prototype);
        return prototype;
      }
    } catch (InvalidProtocolException exc) {
      System.err.println(exc);
    } 
    return null;
  }

  @Override
  public URI getLocation() {
    return this.loc;
  }

  @Override
  public void setLocation(URI loc) {
    if (loc == null) throw new NullPointerException("Null Location");
    this.loc = loc;
    this.prototype = new File(this.loc);
  }

  /**
   * Offers method overloading for case of string parameter which resolves it to of type URI
   *
   * Note: Do not conflate with dynamic dispatch, which is a runtime resolution in the context of polymorphism where actual types are prioritized in method calling; method overloading is a compile-time resolution that allows multiple method signatures with shared identifiers but with different parameter types which are resolved by their argument types
   *
   * @param loc a string 
   */
  public void setLocation(String loc) {
    // Static approach of resolving to basepath
    URI protoURI = getPrototype();
    if (protoURI.getScheme().equals("jar")) {
      try {
        protoURI = new URI(protoURI.toString().replaceAll("(^jar\\:)([\\S\\s]+)((?<=adk/)[\\S\\s]+jar!/)(?:dist/)?([\\S\\s]+)", "$2$4"));
      } catch (URISyntaxException x) {
        System.err.println(x);
      }
    }
    System.out.println("RESULTANT: " + protoURI);
    File traverser = new File(protoURI).getParentFile();
    while (!traverser.getName().equals("assets")) {
      traverser = new File(traverser.getParent());
    }
    URI baseURI = new File(traverser.getParent()).toURI();
    this.setLocation(baseURI.resolve(loc));
  }

  // TODO: Handle closing brace on new line exception
  @Override
  public String getField(String fieldName) {
    boolean hasField = false;
    for (String field : PrototypeWriter.FIELDS) {
      if (field.equals(fieldName)) {
        hasField = true;
      }
    }
    if (hasField == true) {
      Stack<Character> curlStack = new LinkedStack<>();
      Stream<String> content = this.content.toString().lines();
      content.reduce("", (String prev, String next) -> {
        if (!prev.contains(fieldName)) {
          return prev = next;
        } else if (prev.contains(fieldName) && this.position != Integer.MIN_VALUE) {
          StringBuilder line = new StringBuilder(next);
          for (int i = 0; i < line.length(); i++) {
            char targetChar = line.charAt(i);
            if (targetChar == '{' || targetChar == '}') {
              if (targetChar == '{') { 
                curlStack.push(targetChar);
              } else {
                Character popped = curlStack.pop();
                if (popped == null) throw new NullPointerException("Missing field opener.");
                if (curlStack.size() == 0) this.position++;
              }
            }
          }
          if (PrototypeWriter.FIELDS[this.position].equals(fieldName)) {
            this.position = Integer.MIN_VALUE;
            return line.toString();
          }
          return prev = next;
        } else {
          return prev;
        }
      });
      return content.toString();
    } else {
      System.err.println("Invalid field name.");
      return "Invalid field name..";
    }
  }

  // TODO: Handle closing brace on new line exception
  @Override
  public void setField(String fieldName, String input) {
    this.position = -1;
    Stack<Character> curlStack = new LinkedStack<>();
    Stream<String> content = this.content.toString().lines();
    content.reduce("", (String prev, String next) -> {
      if (!prev.contains(fieldName)) {
        return prev + next + '\n';
      } else if (prev.contains(fieldName) && this.position != Integer.MIN_VALUE) {
        StringBuilder line = new StringBuilder(next);
        for (int i = 0; i < line.length(); i++) {
          char targetChar = line.charAt(i);
          if (targetChar == '{' || targetChar == '}') {
            if (targetChar == '{') {
              curlStack.push(targetChar);
            } else {
              Character popped = curlStack.pop();
              if (popped == null) throw new NullPointerException("Missing field opener.");
              if (curlStack.size() == 0) this.position++;
            }
          }
        }
        if (PrototypeWriter.FIELDS[this.position].equals(fieldName)) {
          // Here
          this.position = Integer.MIN_VALUE;
          line.delete(1, line.length() - 1);
          return line.insert(1, input).toString();
        }
        return prev + next + '\n';
      } else {
        return prev + next + '\n';
      }
    });
    this.content = content.toString();
  }

  public void initialize() {
    if (this.prototype == null) {
      System.err.println("Prototype URI failed to construct.");
    } 

    try {
      BufferedWriter bpWriter = Files.newBufferedWriter(new File(this.loc).toPath());
      bpWriter.write(this.content.toString());
      bpWriter.close();
    } catch (IOException x) {
      System.err.println(x);
    }
  }
}
