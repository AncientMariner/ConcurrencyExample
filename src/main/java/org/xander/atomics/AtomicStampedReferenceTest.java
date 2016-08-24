package org.xander.atomics;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    AtomicStampedReference<String> asr = new AtomicStampedReference<>("ref", 10);

    class Worker implements Runnable {
        @Override
        public void run() {
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + asr.getReference() + " stamp - " + asr.getStamp());
            //if current reference is "ref" then initial stamp is changed to 20
            asr.attemptStamp("ref", 20);
            System.out.println("current thread - " + Thread.currentThread() + " reference  - " + asr.getReference() + " stamp - " + asr.getStamp());
            //if current value is ref, then it is set as newref
            asr.weakCompareAndSet("ref", "newref", 20, 30);
            System.out.println("current thread - " + Thread.currentThread() + " reference  - " + asr.getReference() + " stamp - " + asr.getStamp());
        }
    }

    public static void main(String... args) {
        new Thread(new AtomicStampedReferenceTest().new Worker()).start();
        new Thread(new AtomicStampedReferenceTest().new Worker()).start();
    }
}