package stream;// streams/Reduce.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

class Frobnitz {
  int size;
  Frobnitz(int sz) { size = sz; }
  @Override
  public String toString() {
    return "Frobnitz(" + size + ")";
  }
  // Generator:
  static Random rand = new Random(47);
  static final int BOUND = 100;
  static Frobnitz supply() {
    return new Frobnitz(rand.nextInt(BOUND));
  }
}

public class Reduce {
  public static void main(String[] args) {
    Stream.generate(Frobnitz::supply)
      .limit(5)
      .peek(System.out::println)
      .reduce((fr0, fr1) -> (fr0.size < 50) ? fr0 : null)
      .ifPresent((x) -> {
        System.out.println("Present:"+x);
      });
  }
}
/* Output:
Frobnitz(58)
Frobnitz(55)
Frobnitz(93)
Frobnitz(61)
Frobnitz(61)
Frobnitz(29)
Frobnitz(68)
Frobnitz(0)
Frobnitz(22)
Frobnitz(7)
Frobnitz(29)
*/
