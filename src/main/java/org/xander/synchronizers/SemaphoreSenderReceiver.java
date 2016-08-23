package org.xander.synchronizers;

public class SemaphoreSenderReceiver {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore();

        SendingThread sender = new SendingThread(semaphore);
        ReceivingThread receiver = new ReceivingThread(semaphore);

        receiver.start();
        sender.start();
    }

    static class SendingThread extends Thread {
        Semaphore semaphore = null;

        public SendingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            while (true) {
                //do something, then signal
                System.out.println("sending thread");
                this.semaphore.take();

            }
        }
    }

    static class ReceivingThread extends Thread {
        Semaphore semaphore = null;

        public ReceivingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            while (true) {
                try {
                    System.out.println("receiving thread");
                    this.semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //receive signal, then do something...
            }
        }

    }
}