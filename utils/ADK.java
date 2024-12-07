package utils;

import java.net.URI;

import utils.PrototypeWriter;
import utils.resolvers.URIParser;

public class ADK {

  public ADK() { }

  public static void main(String[] args) {
    PrototypeWriter pw = new PrototypeWriter();
    pw.getPrototype();
    StringBuilder loc = new StringBuilder();
    loc.append("ADK").append("/test.tex");
    pw.setLocation(loc.toString());
    pw.initialize();
  }
}
