package org.xander.queues.nonBlocking;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinked {
    ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();

    class AddThread implements Runnable {
        @Override
        public void run() {
            //ads the element to the tail of element
            clq.add("A");

            //insert the elements to the tail of element
            clq.offer("B");

            // retrieve and removes the elements from head
            clq.poll();

            Iterator<String> itr = clq.iterator();
            while (itr.hasNext()) {
                System.out.println(itr.next());
            }
        }
    }

    public static void main(String... args) {
        new Thread(new ConcurrentLinked().new AddThread()).start();
        new Thread(new ConcurrentLinked().new AddThread()).start();
    }
}
