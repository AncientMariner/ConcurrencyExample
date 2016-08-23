package org.xander.forkJoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
public class ForkJoinPoolSubmit {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int parallelism = Runtime.getRuntime().availableProcessors();
		ForkJoinPool pool = new ForkJoinPool(parallelism);
		TaskSubmit taskSubmit = new TaskSubmit();
		ForkJoinTask<String> output = pool.submit(taskSubmit);
		System.out.println(output.get());
	}
}
class TaskSubmit implements Callable<String>{
	public String call() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		return "TaskSubmit Completed";
	}
}