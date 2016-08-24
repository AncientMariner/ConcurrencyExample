package org.xander.atomics;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayTest {
    AtomicReferenceArray<String> ara = new AtomicReferenceArray<>(10);

    class AddThread implements Runnable {
        @Override
        public void run() {
            //sets value at the index 1
            System.out.println("current thread - " + Thread.currentThread() + " element - " + ara.get(0) + " length - " + ara.length());
            ara.set(0, "ref");
            System.out.println("current thread - " + Thread.currentThread() + " element - " + ara.get(0) + " length - " + ara.length());
            //at index 0, if current reference is "ref" then it changes as newref
            ara.compareAndSet(0, "ref", "newref");
            System.out.println("current thread - " + Thread.currentThread() + " element - " + ara.get(0) + " length - " + ara.length());
            //at index 0, if current value is newref, then it is sets as finalref
            ara.weakCompareAndSet(0, "newref", "finalref");
            System.out.println("current thread - " + Thread.currentThread() + " element - " + ara.get(0) + " length - " + ara.length());
        }
    }

    public static void main(String... args) {
        new Thread(new AtomicReferenceArrayTest().new AddThread()).start();
        new Thread(new AtomicReferenceArrayTest().new AddThread()).start();
    }
}