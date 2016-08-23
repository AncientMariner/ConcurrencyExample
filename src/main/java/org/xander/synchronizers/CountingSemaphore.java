package org.xander.synchronizers;

public class CountingSemaphore {
    private int signals = 0;

    public synchronized void take() {
        System.out.println("taking...");
        this.signals++;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (this.signals == 0) wait();
        System.out.println("releasing...");
        this.signals--;
    }


    public int getSignals() {
        return signals;
    }

    public static void main(String[] args) {
        CountingSemaphore semaphore = new CountingSemaphore();
            semaphore.take();

        try {
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("signals - " + semaphore.getSignals());
    }

}