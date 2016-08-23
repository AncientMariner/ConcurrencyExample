package org.xander.queues.blocking;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Synchronous {
    public static void main(String[] args) {
        final BlockingQueue<String> synchronousQueue = new SynchronousQueue<>(true);

        Thread producer = new Thread("PRODUCER") {
            public void run() {
                String event = "FOUR";
                try {
                    System.out.println("Producer is waiting to put...");
                    synchronousQueue.put(event); // thread will block here
                    System.out.printf("[%s] published event : %s %n", Thread
                            .currentThread().getName(), event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        producer.start(); // starting publisher thread

        Thread consumer = new Thread("CONSUMER") {
            public void run() {
                try {
                    System.out.println("Consumer is waiting to take element...");
                    String event = synchronousQueue.take(); // thread will block here
                    System.out.printf("[%s] consumed event : %s %n", Thread
                            .currentThread().getName(), event);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        consumer.start();
    }
}
