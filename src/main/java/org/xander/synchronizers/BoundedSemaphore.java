package org.xander.synchronizers;

public class BoundedSemaphore {
    private int signals = 0;
    private int bound = 0;

    public BoundedSemaphore(int upperBound) {
        this.bound = upperBound;
    }

    public int getSignals() {
        return signals;
    }

    public int getBound() {
        return bound;
    }

    public synchronized void take() throws InterruptedException {
        while (this.signals == bound) wait();
        System.out.println("taking...");
        this.signals++;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (this.signals == 0) wait();
        System.out.println("releasing...");
        this.signals--;
        this.notify();
    }

    public static void main(String[] args) {
        BoundedSemaphore semaphore = new BoundedSemaphore(1);
        try {
            semaphore.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //critical section
        } finally {
            try {
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("bound - " + semaphore.getBound());
        System.out.println("signals - " + semaphore.getSignals());
    }
}