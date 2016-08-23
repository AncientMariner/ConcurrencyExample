package org.xander.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorUsage {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int corePoolSize = 2;
        ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(corePoolSize);
        stpe.execute(new ScheduledThreadPoolExecutorUsage().new RunnableThread());

        ScheduledFuture<Integer> sf = stpe.schedule(new ScheduledThreadPoolExecutorUsage().new CallableThread(), 2, TimeUnit.SECONDS);
        int res = sf.get();
        System.out.println("value returned by Callable Thread - " + res);

        int activeCount = stpe.getActiveCount();
        System.out.println("active count: " + activeCount);

        stpe.shutdownNow();
        System.out.println("is shutdown " + stpe.isShutdown());
    }

    class RunnableThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("run: " + i);
            }
        }
    }

    class CallableThread implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 5; i++) {
                System.out.println("call: " + i);
            }
            return i;
        }
    }
}