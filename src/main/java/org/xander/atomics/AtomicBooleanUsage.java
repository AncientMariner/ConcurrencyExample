package org.xander.atomics;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanUsage {
    AtomicBoolean ab = new AtomicBoolean(true);

    class A implements Runnable {
        @Override
        public void run() {
            System.out.println("before   current thread - " + Thread.currentThread() + ab.get());
            ab.compareAndSet(false, true);
            System.out.println("after    current thread - " + Thread.currentThread() + ab.get());
        }
    }

    class B implements Runnable {
        @Override
        public void run() {
            System.out.println("before   current thread - " + Thread.currentThread() + ab.get());
            ab.compareAndSet(true, false);
            System.out.println("after    current thread - " + Thread.currentThread() + ab.get());
        }
    }

    public static void main(String... args) {
        new Thread(new AtomicBooleanUsage().new A()).start();
        new Thread(new AtomicBooleanUsage().new B()).start();
    }
}