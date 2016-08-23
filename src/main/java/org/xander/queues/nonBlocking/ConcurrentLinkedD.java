package org.xander.queues.nonBlocking;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentLinkedD {
    static ConcurrentLinkedDeque<String> cld = new ConcurrentLinkedDeque<>();

    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(2);
        ThreadOne elementAdd = new ConcurrentLinkedD().new ThreadOne();
        ThreadTwo elementGet = new ConcurrentLinkedD().new ThreadTwo();
        exService.execute(elementAdd);
        exService.execute(elementGet);
        exService.shutdown();
    }

    class ThreadOne implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                cld.add("A" + i);
            }
        }
    }

    class ThreadTwo implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("Consumer is waiting to take element...");
                String s = cld.poll();
                System.out.println("Element received is: " + s);
            }
        }
    }
}
