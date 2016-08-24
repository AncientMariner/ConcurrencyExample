package org.xander.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantL {
    private ReentrantLock lock = new ReentrantLock();

    public void transfer(Account from, Account to, Integer amount) {
        boolean transfer = false;
        try {
            if (lock.tryLock()) {
                System.out.println(Thread.currentThread().getName() + " says acquire lock");
                boolean flag = from.debit(amount);
                if (flag) {
                    to.credit(amount);
                }
                System.out.println(Thread.currentThread().getName() + " :: " + from.getName() + " says :: now balance is " + from.getBalance());
                System.out.println(Thread.currentThread().getName() + " :: " + to.getName() + " says :: now balance is " + to.getBalance());
                transfer = true;
            } else {
                System.out.println(Thread.currentThread().getName() + " says fail to acquire both lock Try again");
                transfer(from, to, amount);//try again
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (transfer) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);

        final Account from = new Account();
        from.setBalance(20000);
        from.setName("Alex Jones");

        final Account to = new Account();
        to.setName("John Doe");
        final ReentrantL transfer = new ReentrantL();

        Runnable a = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transfer.transfer(from, to, 200);
                System.out.println(Thread.currentThread().getName() + " says :: Transfer successful");
            }
        };

        Runnable b = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transfer.transfer(to, from, 1000);
                System.out.println(Thread.currentThread().getName() + " says :: Transfer successful");
            }
        };

        for (int i = 0; i < 4; i++) {
            service.submit(a);
            service.submit(b);
        }
    }


    static class Account {
        private ReentrantLock implicitLock = new ReentrantLock();
        private String name;
        private Integer balance = 10000;

        public ReentrantLock getImplicitLock() {
            return implicitLock;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public boolean debit(Integer amount) {
            if (amount > balance) {
                System.out.println(Thread.currentThread().getName() + " :: " + name + " says ::" + amount + " grater than current balance");
                return false;
            }

            balance = balance - amount;
            System.out.println(Thread.currentThread().getName() + " :: " + name + " says ::" + amount + " Debited Success Fully");
            return true;
        }

        public void credit(Integer amount) {
            balance = balance + amount;
            System.out.println(Thread.currentThread().getName() + " :: " + name + " says ::" + amount + " Credited Success Fully");
        }
    }
}