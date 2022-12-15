package annotation.junit;

import annotation.junit.atunit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class AtUnitExample2 {
    public String methodOne() {
        return "This is methodOne";
    }
    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }
    @Test
    void assertExample() {
        assert methodOne().equals("This is methodOne");
    }
    @Test
    void assertFailureExample() {
        assert 1 == 2: "What a surprise!";
    }
    @Test
    void exceptionExample() throws IOException {
        try(FileInputStream fis =
                    new FileInputStream("nofile.txt")) {} // Throws
    }
    @Test
    boolean assertAndReturn() {
        // Assertion with message:
        assert methodTwo() == 2: "methodTwo must equal 2";
        return methodOne().equals("This is methodOne");
    }
}
/* Output:
annotations.AtUnitExample2
  . assertFailureExample java.lang.AssertionError: What
a surprise!
(failed)
  . assertExample
  . exceptionExample java.io.FileNotFoundException:
nofile.txt (The system cannot find the file specified)
(failed)
  . assertAndReturn This is methodTwo

(4 tests)

>>> 2 FAILURES <<<
  annotations.AtUnitExample2: assertFailureExample
  annotations.AtUnitExample2: exceptionExample
*/