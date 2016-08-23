package org.xander.executors;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryUsage {
    public static void main(String[] args) {
        DaemonThreadFactory daemontf = new DaemonThreadFactory();
        MaxPriorityThreadFactory mptf = new MaxPriorityThreadFactory();

        Runnable runnable = new SimpleTask("High Priority");
        mptf.newThread(runnable).start();
        runnable = new SimpleTask("Low Priority");
        daemontf.newThread(runnable).start();
    }
}

class SimpleTask implements Runnable {
    String s = null;

    public SimpleTask(String s) {
        this.s = s;
    }

    @Override
    public void run() {
        System.out.println(s + " Simple task done.");
    }
}

class MaxPriorityThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(Thread.MAX_PRIORITY);
        return t;
    }
}

class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}