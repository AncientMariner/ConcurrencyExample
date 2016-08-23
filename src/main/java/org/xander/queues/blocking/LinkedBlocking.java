package org.xander.queues.blocking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlocking {
    private final LinkedBlockingQueue<String> lbqueue = new LinkedBlockingQueue<>();

    class ThreadA implements Runnable {
        @Override
        public void run() {
            System.out.println("Producer is waiting to offer...");
            lbqueue.offer("AAAA");
            lbqueue.offer("BBBB");
            lbqueue.offer("CCCC");
            lbqueue.offer("DDDD");
            lbqueue.offer("EEEE");
        }
    }

    class ThreadB implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Consumer is waiting to take element...");
                    System.out.println(lbqueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        LinkedBlocking linkedBlocking = new LinkedBlocking();
        ThreadA threadA = linkedBlocking.new ThreadA();
        ThreadB threadB = linkedBlocking.new ThreadB();
        service.execute(threadA);
        service.execute(threadB);
        service.shutdown();
    }
}
