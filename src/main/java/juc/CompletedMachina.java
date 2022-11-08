package juc;// concurrent/CompletedMachina.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
import java.util.concurrent.*;

public class CompletedMachina {
  public static void main(String[] args) {
    CompletableFuture<Machina> cf =
      CompletableFuture.completedFuture(
        new Machina(0));
    try {
      Machina m = cf.get();  // Doesn't block
    } catch(InterruptedException |
            ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}

class Machina {
  public enum State {
    START, ONE, TWO, THREE, END;
    State step() {
      if(equals(END)) return END;
      return values()[ordinal() + 1];
    }
  }
  private State state = State.START;
  private final int id;
  public Machina(int id) { this.id = id; }
  public static Machina work(Machina m) {
    System.out.println(Thread.currentThread().getName());
    if(!m.state.equals(State.END)){
      new Nap(0.1);
      m.state = m.state.step();
    }
    System.out.println(m);
    return m;
  }
  @Override public String toString() {
    return "Machina" + id + ": " +
            (state.equals(State.END)? "complete" : state);
  }
}
