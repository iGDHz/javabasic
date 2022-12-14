package stream;// streams/MetalWork2.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.


import java.util.Arrays;
import java.util.function.Function;

import static java.util.Arrays.stream;

public class MetalWork2 {
  public static void main(String[] args) {
    Arrays.stream(new Operation[] {
      () -> Operation.show("Heat"),
      () -> Operation.show("Hammer"),
      () -> Operation.show("Twist"),
      () -> Operation.show("Anneal")
    }).forEach(Operation::execute);

    Operation op = () -> System.out.println("hello world");
  }
}
/* Output:
Heat
Hammer
Twist
Anneal
*/
