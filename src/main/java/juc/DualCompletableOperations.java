package juc;// concurrent/DualCompletableOperations.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CompletableFuture;

import static juc.CompletableUtilities.showr;
import static juc.CompletableUtilities.voidr;

public class DualCompletableOperations {
    static CompletableFuture<Workable> cfA, cfB;
    static void init() {
        cfA = Workable.make("A", 0.15);
        cfB = Workable.make("B", 0.10); // Always wins
    }
    static void join() {
        cfA.join();
        cfB.join();
        System.out.println("*****************");
    }
    public static void main(String[] args) throws IOException {
        init();
        voidr(cfA.runAfterEitherAsync(cfB, () ->
                System.out.println("runAfterEither")));
        join();

        init();
        voidr(cfA.runAfterBothAsync(cfB, () ->
                System.out.println("runAfterBoth")));
        join();

        init();
        showr(cfA.applyToEitherAsync(cfB, w -> {
            System.out.println("applyToEither: " + w);
            return w;
        }));
        join();

        init();
        voidr(cfA.acceptEitherAsync(cfB, w -> {
            System.out.println("acceptEither: " + w);
        }));
        join();

        init();
        voidr(cfA.thenAcceptBothAsync(cfB, (w1, w2) -> {
            System.out.println("thenAcceptBoth: "
                    + w1 + ", " + w2);
        }));
        join();

        init();
        showr(cfA.thenCombineAsync(cfB, (w1, w2) -> {
            System.out.println("thenCombine: "
                    + w1 + ", " + w2);
            return w1;
        })); //先将等待两个CompletableFuture完成再将两者参数传递给BiFunction
        join();

        init();
        CompletableFuture<Workable>
                cfC = Workable.make("C", 0.08),
                cfD = Workable.make("D", 0.09);
        CompletableFuture.anyOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() ->
                        System.out.println("anyOf"));
        join();

        init();
        cfC = Workable.make("C", 0.08);
        cfD = Workable.make("D", 0.09);
        CompletableFuture.allOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() ->
                        System.out.println("allOf"));
        join();
    }
}

class Workable {
    String id;
    final double duration;
    public Workable(String id, double duration) {
        this.id = id;
        this.duration = duration;
    }
    @Override public String toString() {
        return "Workable[" + id + "]";
    }
    public static Workable work(Workable tt) {
        new Nap(tt.duration); // Seconds
        tt.id = tt.id + "W";
        System.out.println(tt);
        return tt;
    }
    public static CompletableFuture<Workable>
    make(String id, double duration) {
        return
                CompletableFuture.completedFuture(
                                new Workable(id, duration))
                        .thenApplyAsync(Workable::work);
    }
}

/* Output:
Workable[BW]
runAfterEither
Workable[AW]
*****************
Workable[BW]
Workable[AW]
runAfterBoth
*****************
Workable[BW]
applyToEither: Workable[BW]
Workable[BW]
Workable[AW]
*****************
Workable[BW]
acceptEither: Workable[BW]
Workable[AW]
*****************
Workable[BW]
Workable[AW]
thenAcceptBoth: Workable[AW], Workable[BW]
*****************
Workable[BW]
Workable[AW]
thenCombine: Workable[AW], Workable[BW]
Workable[AW]
*****************
Workable[CW]
anyOf
Workable[DW]
Workable[BW]
Workable[AW]
*****************
Workable[CW]
Workable[DW]
Workable[BW]
Workable[AW]
*****************
allOf
*/
