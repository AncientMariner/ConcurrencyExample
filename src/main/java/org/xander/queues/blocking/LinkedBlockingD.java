package org.xander.queues.blocking;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingD {
    static BlockingDeque<String> bd = new LinkedBlockingDeque<>(1);

    public static void main(String[] args) {
        ExecutorService exService = Executors.newFixedThreadPool(2);
        LinkedBlockingD bdeque = new LinkedBlockingD();
        ElementAdd elementAdd = bdeque.new ElementAdd();
        ElementGet elementGet = bdeque.new ElementGet();
        exService.execute(elementAdd);
        exService.execute(elementGet);
        exService.shutdown();
    }

    class ElementAdd implements Runnable {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    System.out.println("Consumer is waiting to add element...");
                    String s = bd.take();
                    System.out.println("Element received is: " + s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class ElementGet implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Producer is waiting to get...");
                    bd.put("A" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
} 