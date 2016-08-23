package org.xander;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWrite {
    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        CopyOnWrite copyOnWrite = new CopyOnWrite();
        CopyOnWriteArrayList<String> copyOnWriteArrayList = copyOnWrite.copyOnWriteArrayList;
        copyOnWriteArrayList.add("first");
        copyOnWriteArrayList.add("second");
        copyOnWriteArrayList.add("other");

        copyOnWriteArrayList.addIfAbsent("first");

        System.out.println(copyOnWrite.copyOnWriteArrayList);

        new Thread(copyOnWrite.new Parallel()).start();

        copyOnWrite.copyOnWriteArraySet.add("one");
        copyOnWrite.copyOnWriteArraySet.add("two");
        copyOnWrite.copyOnWriteArraySet.add("three");

        System.out.println(copyOnWrite.copyOnWriteArraySet);
    }

    class Parallel implements Runnable {
        @Override
        public void run() {
            Iterator<String> iterator = copyOnWriteArrayList.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                System.out.println(next);
            }
        }
    }
}
