package org.xander.queues.blocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class PriorityBlocking {
    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }


    static class Producer implements Runnable {
        BlockingQueue<Integer> queue;

        Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Producer is waiting to put...");
                    queue.put(ThreadLocalRandom.current().nextInt(0, 1000));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        BlockingQueue<Integer> queue;

        Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Consumer is waiting to take element...");
                    System.out.println(queue.take());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
