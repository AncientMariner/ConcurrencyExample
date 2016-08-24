package org.xander.atomics.atomicFieldUpdater;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterCounter {

    // AtomicIntegerFieldUpdater is registered with Details.class so that it
    // knows it will later be updating the volatile field called
    // numberTimesInvoked
    private AtomicIntegerFieldUpdater<Details> aifu = AtomicIntegerFieldUpdater.newUpdater(Details.class, "numberTimesInvoked");
    /**
     * Different threads can call this method to update the volatile field of
     * an instance of Details
     *
     * @param details Details object which has the volatile field called
     *                "numberTimesInvoked" in it.
     * @return the value of the counter after it has been incremented by one
     */
    public int addOne(Details details) {
        // performs a "x = x + 1" style atomic operation
        return aifu.addAndGet(details, 1);
    }
    /**
     * See test class for example of using this class with multiple threads,
     * some of which are writing to the volatile field and some which are
     * reading from the volatile field
     */
    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterCounter atomicIntegerFieldUpdaterCounter = new AtomicIntegerFieldUpdaterCounter();
//        This call would ordinarily be made by many other threads
        System.out.println(atomicIntegerFieldUpdaterCounter.addOne(new Details()));
    }
}
