package juc;// concurrent/CompletableOperations.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
import java.net.Socket;
import java.util.concurrent.*;

import static juc.CompletableUtilities.showr;
import static juc.CompletableUtilities.voidr;

public class CompletableOperations {
  static CompletableFuture<Integer> cfi(int i) {
    return
      CompletableFuture.completedFuture(
        Integer.valueOf(i));
  }
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    showr(cfi(1)); // Basic test
    voidr(cfi(2).runAsync(() ->
      System.out.println("runAsync")));
    voidr(cfi(3).thenRunAsync(() ->
      System.out.println("thenRunAsync")));
    voidr(CompletableFuture.runAsync(() ->
      System.out.println("runAsync is static")));
    showr(CompletableFuture.supplyAsync(() -> 99));
    voidr(cfi(4).thenAcceptAsync(i ->
      System.out.println("thenAcceptAsync: " + i)));
    showr(cfi(5).thenApplyAsync(i -> i + 42));
    showr(cfi(6).thenComposeAsync(i -> cfi(i + 99)));
    CompletableFuture<Integer> c = cfi(7);
    c.obtrudeValue(111);//强行 替换 结果 7 为 111
    showr(c);
    showr(cfi(8).toCompletableFuture());
    c = new CompletableFuture<>();
    c.complete(9);  //通过complete传入结让一个future完成执行
    showr(c);
    c = new CompletableFuture<>();
    c.cancel(true); //canale后变为已完成
    System.out.println("cancelled: " +
      c.isCancelled());
    System.out.println("completed exceptionally: " +
      c.isCompletedExceptionally());
    System.out.println("done: " + c.isDone());
    System.out.println(c);
    c = new CompletableFuture<>();
    System.out.println(c.getNow(777));//类似于getOrDefault
    c = new CompletableFuture<Integer>();
    c.thenApplyAsync(i -> {
      System.out.println("c.thenApplyAsync: i+42" + (i + 42));
      return i + 42;
    })
            .thenApplyAsync(i -> {
              System.out.println("c.thenApplyAsync: i*12" + (i * 12));
              return i * 12;
            });
    System.out.println("dependents: " +
      c.getNumberOfDependents()); //依赖项 即正在等待该CompletableFuture完成的CompletFuture的预估数量
    System.out.println(c.getNow(0));
     c.thenApplyAsync(i -> {
       System.out.println("c.thenApplyAsync: i/2 "+ (i / 2));
       return i/2;
     });
     c.obtrudeValue(0);
    System.out.println("dependents: " +
      c.getNumberOfDependents());//以上两个thenApplyAsync 即等待c完成后的结果可以fork出两个任务DualCompletableOperations

  }
}

class CompletableUtilities {
  // Get and show value stored in a CF:
  public static void showr(CompletableFuture<?> c) {
    try {
      System.out.println(c.get());
    } catch(InterruptedException
            | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  // For CF operations that have no value:
  public static void voidr(CompletableFuture<Void> c) {
    try {
      c.get(); // Returns void
    } catch(InterruptedException
            | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
/* Output:
1
runAsync
thenRunAsync
runAsync is static
99
thenAcceptAsync: 4
47
105
111
8
9
cancelled: true
completed exceptionally: true
done: true
java.util.concurrent.CompletableFuture@1629346[Complete
d exceptionally]
777
dependents: 1
dependents: 2
*/
