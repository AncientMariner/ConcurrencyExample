package org.xander.locks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class LockSupportUsage {
    private AtomicBoolean atLocked = new AtomicBoolean(false);
    private Queue waitingThreads = new ConcurrentLinkedQueue();

    public void lockRes() {
        System.out.println("locking...");
        Thread current = Thread.currentThread();
        waitingThreads.add(current);

        while (waitingThreads.peek() != current || !atLocked.compareAndSet(false, true)) {
            LockSupport.park();
        }
        waitingThreads.remove();
    }

    public void unlockRes() {
        System.out.println("unlocking...");
        atLocked.set(false);
        LockSupport.unpark((Thread) waitingThreads.peek());
    }

    public static void main(String[] args) {
        LockSupportUsage lockSupportUsage = new LockSupportUsage();
        lockSupportUsage.lockRes();
        lockSupportUsage.unlockRes();
    }
}