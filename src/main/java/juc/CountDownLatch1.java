package juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatch1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            int final_i = i;
            new Thread(new CountThread(countDownLatch,()->{
                System.out.println("CountDownLatch Test " + final_i);
            })).start();
        }
        countDownLatch.await();
        System.out.println("finished");
    }
}

class CountThread implements Runnable{
    private CountDownLatch count;
    private Runnable function;

    public CountThread(CountDownLatch count,Runnable function) {
        this.count = count;
        this.function = function;
    }

    @Override
    public void run() {
        function.run();
        count.countDown();
    }

}
