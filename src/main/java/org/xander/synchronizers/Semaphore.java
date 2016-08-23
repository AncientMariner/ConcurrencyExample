package org.xander.synchronizers;

public class Semaphore {
    private boolean signal = false;

    public synchronized void take() {
        System.out.println("taking...");
        this.signal = true;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        System.out.println("releasing...");
        while (!this.signal) wait();
        this.signal = false;
    }

    public boolean isSignal() {
        return signal;
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();
            semaphore.take();
        try {
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("signal - " + semaphore.isSignal());
    }
}