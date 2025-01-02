package utils;

import java.net.URI;
import java.io.IOException;

import utils.PrototypeWriter;
import utils.resolvers.PrototypeValidator;
import utils.resolvers.URIParser;
import utils.jtex.TexConfig;

public class ADK {
  public ADK() { }

  public static void main(String[] args) {
    // PrototypeWriter pw = new PrototypeWriter();
    // pw.getPrototype();
    // StringBuilder loc = new StringBuilder();
    // StringBuilder userInput = new StringBuilder();
    // for (String arg : args) {
    //   userInput.append(arg).append("-");
    // }
    // String delimitedInput = userInput.toString().replaceFirst("-$", "");
    // System.out.println(PrototypeWriter.PURPLE + delimitedInput + PrototypeWriter.RESET);

    // loc.append("ADK").append("/test.tex");
    // pw.setLocation(loc.toString());
    // pw.initialize();

    // ReadablePrototype proto = new PrototypeReader(); 
    // PrototypeValidator validator = new PrototypeValidator();
    // validator.rebound();

    try {
      TexConfig.addCommonRoot();
    } catch (IOException exc) {
      exc.printStackTrace();
    }
  }
}
