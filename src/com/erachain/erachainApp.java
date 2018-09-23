package com.erachain;

import java.util.Scanner;

public class erachainApp {

    public static void main(String[] args) {

        int maxValue = getInputValue("Enter MAX value for counter: ");
        ThreadSafeCounter.setMaxValue(maxValue);

        ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();
        Thread thread1 = new Thread(threadSafeCounter);
        Thread thread2 = new Thread(threadSafeCounter);
        Thread thread3 = new Thread(threadSafeCounter);

        thread1.start();
        thread2.start();
        thread3.start();


    }

    private static int getInputValue(String message) {

        int value = 0;

        System.out.print(message);

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {

                while (!scanner.hasNextInt()) {
                    System.out.println("Enter valid number!");
                    scanner.next();
                }

                value = scanner.nextInt();

                if (value > 0) {
                    return value;
                }
                else {
                    System.out.println("Enter positive number!");
                }
            }
        }
    }
}
