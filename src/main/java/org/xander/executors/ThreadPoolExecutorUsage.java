package org.xander.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUsage {
    public static void main(final String[] args) throws Exception {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 100, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        executor.execute(new BookReader("Moby Dick"));
        executor.execute(new BookReader("Island of Treasures"));
        executor.execute(new BookReader("Alice adventures in the Wonderland"));

        System.out.println("Old Max Pool Size:" + executor.getMaximumPoolSize());
        executor.setMaximumPoolSize(4);

        System.out.println("New Max Pool Size:" + executor.getMaximumPoolSize());
        executor.shutdown();
    }

    static class BookReader implements Runnable {
        private String bookName;

        public BookReader(String bookName) {
            this.bookName = bookName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("Reading book: " + bookName);
            }
        }
    }
} 