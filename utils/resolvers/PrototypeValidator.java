package utils.resolvers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.net.URI;
import utils.ReadablePrototype;
import utils.PrototypeWriter;
import utils.exceptions.InvalidProtocolException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class PrototypeValidator implements Validator<ReadablePrototype> {
  URI baseURI;
  public PrototypeValidator() {}

  @Override
  public boolean validate(ReadablePrototype prototype) {
    try {
      this.baseURI = prototype.getPrototype();
      return true;
    } catch (InvalidProtocolException exc) {
      System.err.println(exc);
      return false;
    } finally {
      // Validation is a one-off operation, remove from memory for optimization
      this.baseURI = null;
    }
  }

  @Override
  public ReadablePrototype rebound() {
    PrototypeWriter prototype = new PrototypeWriter();
    this.baseURI = prototype.getPrototype();
    String scheme = baseURI.getScheme();
    Path binPath = Path.of(new File(System.getProperty("java.class.path")).toURI());
    String basePath = new PrototypeResolver().resolveToBasePath(binPath).toString();
    URI baseURI = URI.create(basePath.replaceAll("([\\S\\s]+)", String.format("%s$1", "file:\\\\")).replaceAll("\\\\", "/"));
    switch (scheme) {
      case "jar": this.baseURI = new JARPrototypeResolver().resolveToBasePath(ClassLoader.getSystemClassLoader()); break;
      case "file": this.baseURI = baseURI; break;
      default: return null;
    }
    prototype.setLocation(this.baseURI);
    // System.out.println(this.baseURI);
    URI assets = this.baseURI.resolve("assets");
    URI protoURI = this.baseURI.resolve("assets/prototype.tex");
    File assetsDir = new File(assets);
    File prototex = new File(protoURI);

    if (assetsDir.exists() == false) {
      assetsDir.mkdir();
    }

    if (prototex.exists() == false) {
      try {
        BufferedWriter bw = Files.newBufferedWriter(prototex.toPath());
        StringBuilder tex = new StringBuilder();
        tex.append("\\documentclass{article}\n\n");
        tex.append("\\usepackage{preamble}\n");
        tex.append("\\usepackage{adkore}\n\n");
        tex.append("% [ADK // Optional]{Project version encounter and version ending}\n");
        tex.append("% \\version[Optional]{Initial --Final}\n\n");
        tex.append("\\begin{document}\n\n\\frontmatter\n");
        tex.append("% Type\n{}\n% Title\n");
        tex.append("{}\n");
        tex.append("%Status\n");
        tex.append("{}\n");
        tex.append("% Context\n");
        tex.append("{}\n\n");
        tex.append("\\strategy\n");
        tex.append("% Core Decision\n");
        tex.append("{}\n");
        tex.append("% Prospects\n");
        tex.append("{}\n");
        tex.append("% Decision Parameters\n");
        tex.append("{}\n");
        tex.append("% Zero Technical Debt Concerns\n");
        tex.append("{}\n\n");
        tex.append("\\deploy\n");
        tex.append("% Decision Process\n");
        tex.append("{}\n");
        tex.append("% Results and Conflicts\n");
        tex.append("{}\n\n");
        tex.append("\\report\n");
        tex.append("% Experience Report");
        tex.append("{}\n\n");
        tex.append("\\end{document}");

        bw.write(tex.toString());
        bw.close();
        prototex.createNewFile();
      } catch (IOException exc) {
        System.err.println(exc);
      }
    } else {
      System.out.println("Prototype already exists.");
    }

    return (ReadablePrototype) prototype;
  }
}
