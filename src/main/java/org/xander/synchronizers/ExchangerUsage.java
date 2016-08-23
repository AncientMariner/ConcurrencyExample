package org.xander.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerUsage {
    Exchanger<List<String>> exchanger = new Exchanger<>();
    List<String> exchnagerList = new ArrayList<>();

    class AddList implements Runnable {
        public void run() {
            try {
                while (true) {
                    System.out.println("adding...");
                    exchnagerList.add("1");
                    if (exchnagerList.size() == 1) {
                        exchnagerList = exchanger.exchange(exchnagerList);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    class SubtractList implements Runnable {
        public void run() {
            try {
                while (true) {
                    System.out.println("substracting...");
                    exchnagerList.remove("1");
                    if (exchnagerList.size() == 0) {
                        exchnagerList = exchanger.exchange(exchnagerList);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread((new ExchangerUsage()).new SubtractList()).start();
        new Thread((new ExchangerUsage()).new AddList()).start();
    }
}
