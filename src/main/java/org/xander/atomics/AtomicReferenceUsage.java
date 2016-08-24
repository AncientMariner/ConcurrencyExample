package org.xander.atomics;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceUsage {
    AtomicReference<String> ar = new AtomicReference<>("ref");

    class AddThread implements Runnable {
        @Override
        public void run() {
            //if current reference is "ref" then it changes as newref
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + ar.get());
            ar.compareAndSet("ref", "newref");

            // sets the new reference without any check
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + ar.get());
            ar.set("reference");

            //sets new value and return the old value
            String s = ar.getAndSet("reference1");

            System.out.println("current thread - " + Thread.currentThread() + " reference old value - " + s);
            System.out.println("current thread - " + Thread.currentThread() + " reference new value - " + ar.get());
        }
    }

    public static void main(String... args) {
        new Thread(new AtomicReferenceUsage().new AddThread()).start();
        new Thread(new AtomicReferenceUsage().new AddThread()).start();
    }
}