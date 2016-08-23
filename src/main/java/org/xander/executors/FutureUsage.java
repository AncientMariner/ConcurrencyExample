package org.xander.executors;

import java.util.concurrent.*;

public class FutureUsage {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // creating thread pool to execute task which implements Callable
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("submitted callable task to calculate factorial of 10");
        Future<Long> result10 = executorService.submit(new FactorialCalculator(10));

        System.out.println("submitted callable task to calculate factorial of 15");
        Future<Long> result15 = executorService.submit(new FactorialCalculator(15));

        System.out.println("submitted callable task to calculate factorial of 20");
        Future<Long> result20 = executorService.submit(new FactorialCalculator(20));

        System.out.println("Calling get method of FutureUsage to fetch result of factorial 10");
        long factorialOf10 = result10.get();
        System.out.println("factorial of 10 is : " + factorialOf10);

        System.out.println("Calling get method of FutureUsage to get result of factorial 15");
        long factorialOf15 = result15.get();
        System.out.println("factorial of 15 is : " + factorialOf15);

        System.out.println("Calling get method of FutureUsage to get result of factorial 20");
        long factorialOf20 = result20.get();
        System.out.println("factorial of 20 is : " + factorialOf20);
    }

    static class FactorialCalculator implements Callable<Long> {
        private int number;

        public FactorialCalculator(int number) {
            this.number = number;
        }

        @Override
        public Long call() throws Exception {
            return factorial(number);
        }

        private long factorial(int n) throws InterruptedException {
            long result = 1;
            while (n != 0) {
                result = n * result;
                n = n - 1;
                Thread.sleep(100);
            }
            return result;
        }
    }
}
