package org.xander.queues.blocking;

import java.util.concurrent.*;

public class ArrayBlocking {
    private static BlockingQueue<String> bq = new ArrayBlockingQueue<>(1);

    class PutThread implements Runnable {
        @Override
        public void run() {
            try {
                int i = 0;
                while (true) {
                    System.out.println("Producer is waiting to put...");
                    bq.put("A" + i);
                    System.out.println("Producer has put: A" + i);
                    i++;
                    Thread.sleep(1000);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    class TakeThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Consumer is waiting to take element...");
                    final String data = bq.take();
                    System.out.println("Consumer has consumed.." + data);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(final String... args) {
        final Thread pt = new Thread(new ArrayBlocking().new PutThread());
        pt.start();

        final Thread tt = new Thread(new ArrayBlocking().new TakeThread());
        tt.start();
    }
}
