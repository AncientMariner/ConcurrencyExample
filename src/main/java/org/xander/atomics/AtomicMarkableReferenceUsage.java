package org.xander.atomics;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceUsage {
    AtomicMarkableReference<String> amr = new AtomicMarkableReference<>("ref", false);

    class AddThread implements Runnable {
        @Override
        public void run() {
            //update the value of mark as true if "ref" matches to current reference
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + amr.getReference() + " initial mark - " + amr.isMarked());
            amr.attemptMark("ref", true);
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + amr.getReference() + " initial mark - " + amr.isMarked());

            //if current reference is "ref" and current mark is "true" then it change as newref and false respectively
            amr.compareAndSet("ref", "newref", true, false);
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + amr.getReference() + " initial mark - " + amr.isMarked());
            // sets the new refernce and new mark without any check
            amr.set("reference", true);
            System.out.println("current thread - " + Thread.currentThread() + " reference - " + amr.getReference() + " initial mark - " + amr.isMarked());
        }
    }

    public static void main(String... args) {
        new Thread(new AtomicMarkableReferenceUsage().new AddThread()).start();
        new Thread(new AtomicMarkableReferenceUsage().new AddThread()).start();
    }
}