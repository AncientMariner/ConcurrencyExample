package org.xander.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPoolInvoke {
    public static void main(String[] args) {
        int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        TaskInvoke task = new TaskInvoke();
        pool.invoke(task);
        System.out.println("active thread count - " + pool.getActiveThreadCount());
    }
}

class TaskInvoke extends RecursiveAction {
    private static final long serialVersionUID = 1L;

    @Override
    protected void compute() {
        System.out.println("Inside Compute method");
    }
}