package org.xander.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserUsage {
    public static void main(String[] args) {
        PhaserUsage phaser = new PhaserUsage();

        RunnableTask task1 = new RunnableTask("Task one");
        RunnableTask task2 = new RunnableTask("Task two");
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        phaser.doTasks(tasks);
        phaser.initializeTasks(tasks, 2);
    }

    static class RunnableTask implements Runnable {
        String msg = null;

        public RunnableTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(msg + " done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doTasks(List<Runnable> tasks) {
        final Phaser phaser = new Phaser();
        for (final Runnable task : tasks) {
            phaser.register();
            new Thread() {
                public void run() {
                    phaser.arriveAndAwaitAdvance();
                    task.run();
                }
            }.start();
        }
        phaser.arriveAndDeregister();
    }

    public void initializeTasks(List<Runnable> tasks, final int iterations) {
        final Phaser phaser = new Phaser() {
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase >= iterations || registeredParties == 0;
            }
        };
        phaser.register();
        for (final Runnable task : tasks) {
            phaser.register();
            new Thread() {
                public void run() {
                    do {
                        task.run();
                        phaser.arriveAndAwaitAdvance();
                    } while (!phaser.isTerminated());
                }
            }.start();
        }
        phaser.arriveAndDeregister();
    }
}