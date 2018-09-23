package com.erachain;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter implements  Runnable {

    private static AtomicInteger counter = new AtomicInteger(0);
    private static int maxValue = Integer.MAX_VALUE;


    public static void setMaxValue(int value) {
        maxValue = value;
    }

    static void incrementCounter() {
        System.out.println(Thread.currentThread().getName() + ": " + counter.getAndIncrement());
    }

    @Override
    public void run() {

        while(counter.get() < maxValue){
            incrementCounter();
        }
        //counter.set(0);
    }
}
