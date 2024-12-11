package utils.adr;

import utils.exceptions.InvalidProtocolException;

import java.nio.file.NoSuchFileException;

import utils.PrototypeReader;

public class Enclave {
  private static final String RESET = "\033[0m"; 
  private static final String PURPLE = "\033[1;35m"; 

  public Enclave() {}

  private static void generateBP(String[] args) {
    try {
      PrototypeReader protoReader = new PrototypeReader();
      for (String arg : args) {
        System.out.println(arg);
      }
      System.out.println(PURPLE + protoReader.getPrototype() + RESET);
    } catch (InvalidProtocolException | NoSuchFileException exc) {
      exc.printStackTrace();
      System.out.println(exc + "\nCaller: Enclave::generateBP");
    }
  }

  public static void main(String[] args) {
    generateBP(args);
  }
}
