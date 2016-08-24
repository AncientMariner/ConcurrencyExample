package org.xander.atomics.atomicFieldUpdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriterThread extends Thread {
    private int maxNumberCalls;
    private ReaderThread[] readerThreads;
    private AtomicIntegerFieldUpdaterCounter atomicIntegerFieldUpdaterCounter;
    private Map<Integer, List<Integer>> readerCountByReaderMap = new HashMap<>();


    public WriterThread(int maxNumberCalls, ReaderThread[] readerThreads, AtomicIntegerFieldUpdaterCounter atomicIntegerFieldUpdaterCounter) {
        this.maxNumberCalls = maxNumberCalls;
        this.readerThreads = readerThreads;
        this.atomicIntegerFieldUpdaterCounter = atomicIntegerFieldUpdaterCounter;

        for (int i = 0; i < readerThreads.length; i++) {
            readerCountByReaderMap.put(i, new ArrayList<Integer>());
        }
    }

    public Map<Integer, List<Integer>> getReaderCountByReaderMap() {
        return readerCountByReaderMap;
    }

    @Override
    public void run() {
        int count;
        List<Integer> counts;

        for (int i = 0; i < maxNumberCalls; i++) {
            for (int j = 0; j < readerThreads.length; j++) {
                counts = readerCountByReaderMap.get(j);
                count = atomicIntegerFieldUpdaterCounter.addOne(readerThreads[j].getDetails());
                counts.add(count);
            }
        }
    }
}