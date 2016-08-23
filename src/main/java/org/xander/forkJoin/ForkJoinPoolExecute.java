package org.xander.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinPoolExecute {
    public static void main(String[] args) {
        int parallelism = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        TaskExecute taskExecute = new TaskExecute();
        pool.execute(taskExecute);
    }
}

class TaskExecute extends ForkJoinTask<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean exec() {
        System.out.println("executing exec method.");
        return true;
    }

    @Override
    public String getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(String value) {
    }
}
 