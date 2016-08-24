package org.xander.locks;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteL {
    public static void main(String[] args) {
		final int threadCount = 2;
		final ExecutorService exService = Executors.newFixedThreadPool(threadCount);
		final ScoreBoard scoreBoard = new ScoreBoard();
		exService.execute(new ScoreUpdateThread(scoreBoard));
		exService.execute(new ScoreHealthThread(scoreBoard));			
		exService.shutdown();
	}
}

class ScoreBoard {
    private boolean scoreUpdated = false;
    private int score = 0;
    String health = "Not Available";
    final ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
    public String getMatchHealth() {
        rrwl.readLock().lock();
        if (scoreUpdated) {
            rrwl.readLock().unlock();
            rrwl.writeLock().lock();
            try {
                if (scoreUpdated) {
                    score = fetchScore();
                    scoreUpdated = false;
                }
                rrwl.readLock().lock();
            } finally {
                rrwl.writeLock().unlock();
            }
        }
        try {
            if (score % 2 == 0 ){
                health = "Bad Score";
            } else {
                health = "Good Score";
            }
        } finally {
            rrwl.readLock().unlock();
        }
        return health;
    }
    public void updateScore() {
        try {
            rrwl.writeLock().lock();
            //perform more task here
            scoreUpdated = true;
        }finally {
            rrwl.writeLock().unlock();
        }
    }
    private int fetchScore() {
        Calendar calender = Calendar.getInstance();
        return calender.get(Calendar.MILLISECOND);
    }
}

class ScoreHealthThread implements Runnable {
    private ScoreBoard scoreBoard;
    public ScoreHealthThread(ScoreBoard scoreTable) {
        this.scoreBoard = scoreTable;
    }
    @Override
    public void run() {
        for(int i= 0; i< 5; i++) {
            System.out.println("Match Health: "+ scoreBoard.getMatchHealth());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ScoreUpdateThread implements Runnable {
    private ScoreBoard scoreBoard;
    public ScoreUpdateThread(ScoreBoard scoreTable) {
        this.scoreBoard = scoreTable;
    }
    @Override
    public void run() {
        for(int i= 0; i < 5; i++) {
            System.out.println("Score Updated.");
            scoreBoard.updateScore();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}