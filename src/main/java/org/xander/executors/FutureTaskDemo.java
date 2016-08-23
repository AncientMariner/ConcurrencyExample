package org.xander.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<>(new DemoTask());
        exService.execute(futureTask);
        //checks if task done
        System.out.println(futureTask.isDone());
        //checks if task canceled
        System.out.println(futureTask.isCancelled());
        //fetches result and waits if not ready
        System.out.println(futureTask.get());
    }
}

class DemoTask implements Callable<String> {
    public String call() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        return "Task Done";
    }
}
 