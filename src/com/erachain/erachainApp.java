package com.erachain;

import java.util.Scanner;

public class erachainApp {

    public static void main(String[] args) {

        setCounterValue();

        ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();

        Thread thread1 = new Thread(threadSafeCounter);
        Thread thread2 = new Thread(threadSafeCounter);
        Thread thread3 = new Thread(threadSafeCounter);

        thread1.start();
        thread2.start();
        thread3.start();
    }

    private static void setCounterValue() {

        System.out.println("Do you want to set MaxCounterNumber? y/n ");

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (true) {

                input = scanner.next();

                if (input.equalsIgnoreCase("n")) {
                    break;
                }
                else if (input.equalsIgnoreCase("y")) {

                    System.out.println("Enter MAX value for counter: ");

                    while (true) {

                        int value;

                        while (!scanner.hasNextInt()) {
                            System.out.println("Enter valid number!");
                            scanner.next();
                        }

                        value = scanner.nextInt();

                        if (value >= 0) {
                            ThreadSafeCounter.setMaxValue(value);
                            return;
                        }

                        System.out.println("Enter positive number!");
                    }
                }

                else {}
            }
        }
    }
}
