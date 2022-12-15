package exception;// exceptions/StreamsAreAutoCloseable.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
//import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class StreamsAreAutoCloseable implements AutoCloseable{
  public static void
  main(String[] args) throws IOException{
    assert false: "ERROR EXCEPTIOM";
    try(
      Stream<String> in = Files.lines(
        Paths.get("./src/main/java/exception/StreamsAreAutoCloseable.java"));
      PrintWriter outfile = new PrintWriter(
        "Results.txt");                         // [1]
      StreamsAreAutoCloseable autoclose = new StreamsAreAutoCloseable(); //需要继承autoclose接口才能使用
    ) {
      in.skip(5)
        .limit(1)
        .map(String::toLowerCase)
        .forEachOrdered(outfile::println);
    }                                           // [2]
  }

  @Override
  public void close()  {
    //释放持有的资源
  }
}
