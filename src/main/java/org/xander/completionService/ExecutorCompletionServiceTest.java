package org.xander.completionService;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class ExecutorCompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Executor ex = Executors.newCachedThreadPool();
        CompletionService<Long> cs = new ExecutorCompletionService<>(ex);
        cs.submit(new Worker());
        cs.submit(new Worker());
        cs.submit(new Worker());
        for (int i = 0; i < 3; i++) {

//            Retrieves and removes the Future representing the next
//            completed task, waiting if none are yet present.
            long l = cs.take().get();
//            Retrieves and removes the Future representing the next
//            completed task or <tt>null</tt> if none are present.
//            long l = cs.poll().get();
            //utilize the result
            System.out.println(l);
        }
    }
}

class Worker implements Callable {
    @Override
    public Long call() throws Exception {
        //do some task and return back
        return System.currentTimeMillis();
    }

}