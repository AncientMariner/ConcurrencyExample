package org.xander.atomics.atomicFieldUpdater;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

/**
 * This test is a bit complicated as it is testing multiple writer threads
 * each updating a volatile counter held in a a separate Details object for
 * each of the reader threads.
 * <p/>
 * When the reader threads have received all the writer increments they call
 * countDownLatch.countDown().
 * <p/>
 * The test thread running this class waits on the countDownLatch until
 * all the reader threads have called countDown and there are no more tokens
 * to count down anymore. The test thread can then resume its operation and
 * test that no the counter behaved atomically and that no values were skipped
 * or duplicated (i.e. it makes sure that "x = x + 1" was atomic.
 * <p/>
 * It is not important to understand the nitty gritty of this test for all it
 * is doing is testing AtomicIntegerFieldUpdater and we know it works anyway.
 * <p/>
 * The point of this test code is to demonstrate that often the challenge or
 * complexity is  more in writing the test than writing the code.
 *
 */
public class AtomicIntegerFieldUpdaterCounterTest {
    /**
     * This test is a bit complicated as it checks that when
     * atomicIntegerFieldUpdater.addAndGet( details, 1 ) was called on the
     * Details object held in the reader threads by multiple writer threads
     * that no values were skipped, that there were no duplicates and that
     * the end counter values were correct.
     * <p/>
     * Basically the test checks whether
     * atomicIntegerFieldUpdater.addAndGet( details, 1 ) works properly
     *
     * @throws InterruptedException
     */
    @Test
    public void testAddOne() throws InterruptedException {
        int numberThreads = 20;
        int maxNumberCallsPerThread = 1000;

        CountDownLatch countDownLatch = new CountDownLatch(numberThreads);
        ReaderThread[] readerThreads = startReaderThreads(numberThreads, maxNumberCallsPerThread * numberThreads, countDownLatch);
        AtomicIntegerFieldUpdaterCounter atomicIntegerFieldUpdaterCounter = new AtomicIntegerFieldUpdaterCounter();
        WriterThread[] writerThreads = startWriterThreads(numberThreads, maxNumberCallsPerThread, readerThreads, atomicIntegerFieldUpdaterCounter);
        // wait for reader threads to finish reading
        countDownLatch.await();

        for (int i = 0; i < readerThreads.length; i++) {
            System.out.println(readerThreads[i].getName() + " " + readerThreads[i].getDetails().getNumberTimesInvoked());
            // Check that each of the reader threads had their volatile
            // "numberTimesInvoked" incremented to 20000 by the write threads
            assertEquals(numberThreads * maxNumberCallsPerThread, readerThreads[i].getDetails().getNumberTimesInvoked());
        }
        Map<Integer, TreeSet<Integer>> countersByReaderMap = new HashMap<>();
        for (int i = 0; i < readerThreads.length; i++) {
            countersByReaderMap.put(i, new TreeSet<Integer>());
        }

        List<Integer> readerCountList;
        TreeSet<Integer> countSet;
        // Check that that when
        // atomicIntegerFieldUpdater.addAndGet( details, 1 );
        // was called that each worker thread got a unique value. i.e. there
        // were no duplicates.
        //
        // We use a TreeSet for each Reader which basically merges the values
        // each writer updated a particular reader with into one set. During
        // the merge we check for duplicates.
        for (int i = 0; i < writerThreads.length; i++) {
            for (int j = 0; j < readerThreads.length; j++) {
                countSet = countersByReaderMap.get(j);
                readerCountList = writerThreads[i].getReaderCountByReaderMap().get(j);

                for (Integer count : readerCountList) {
                    if (countSet.contains(count)) {
                        Assert.fail("duplicate");
                    } else {
                        countSet.add(count);
                    }
                }
            }
        }
        // Check that there were no values skipped when the counter was incremented
        int numberCounterIncrements = numberThreads * maxNumberCallsPerThread;
        Iterator<Integer> counterReadingsIterator;

        for (int i = 0; i < readerThreads.length; i++) {
            Set<Integer> counterReadings = countersByReaderMap.get(i);
            counterReadingsIterator = counterReadings.iterator();

            for (int j = 0; j < numberCounterIncrements; j++) {
                assertEquals(new Integer(j + 1), counterReadingsIterator.next());
            }
        }
    }
    // Initialises reader threads and starts them
    private ReaderThread[] startReaderThreads(int numberThreads, int maxNumberCalls, CountDownLatch countDownLatch) {
        ReaderThread[] threads = new ReaderThread[numberThreads];

        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new ReaderThread(countDownLatch, maxNumberCalls);
        }
        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }
        return threads;
    }
    // Starts writer threads and starts them
    private WriterThread[] startWriterThreads(int numberThreads, int maxNumberCalls, ReaderThread[] readerThreads, AtomicIntegerFieldUpdaterCounter aifuc) {

        WriterThread[] threads = new WriterThread[numberThreads];

        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new WriterThread(maxNumberCalls, readerThreads, aifuc);
        }
        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }
        return threads;
    }

    public static void main(String args[]) {
        JUnitCore junitCore = new JUnitCore();
        junitCore.addListener(new TextListener(System.out));
        junitCore.run(AtomicIntegerFieldUpdaterCounterTest.class);
    }
}