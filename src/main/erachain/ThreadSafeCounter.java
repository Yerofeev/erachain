package main.erachain;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *Потокобезопасный счетчик, содержит методы инкремента, задания максимального значения счетчика, а
 * также получения текущего
 * */
public class ThreadSafeCounter {

    /**
     * Счетчик задаётся посредством переменной AtomicInteger, предоставляющей
     * атомарные операции, которые позволяют
     * безопасно выполнять вычисления в условиях многопоточности
     * и не прибегая к явным блокировкам
     */
    private AtomicInteger counter = new AtomicInteger(0);

    /**
     * Максималое значение счетчика, по умалочению -  максимальное значение Integer.
     */
    private int maxValue = Integer.MAX_VALUE;

    /**
     * {@link ThreadSafeCounter#counter}
     * @param value - устанавливаем значение счетчика
     */
    public void setCounter(int value) {
        counter.set(value);
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int value) {
        maxValue = value;
    }

    /**
     * Функционал инремента: пока значение счетчика меньше установленного положенного
     * максимального значения, то выполняем инкремент первого. Когда значение счетчика
     * превысит - то обнуляем его
     */
    public void incCounter() {
{
            if (counter.get() < maxValue) {
                System.out.println(Thread.currentThread().getName() + ": Shared Counter Value: " + counter.getAndIncrement());
            }
            else {
                counter.set(0);
            }
        }
    }
    /**
     * Получаем значения потокобезопасного счетчика
     * {@link ThreadSafeCounter#counter}
     */
    public int getCounterValue() {
        return counter.get();
    }

    /**
     * Метод для инициализации максимально возможного значения счетчика. Пользователь может отказаться
     * его задавать - тогда будет использоваться значение по умолчанию Integer.MAX_VALUE.
     * Также происходит проверка, что введенное значение -  неотрицательное число.
     */
    public void initialize() {

        System.out.println("Do you want to set MaxCounterNumber? y/n ");

        try (Scanner scanner = new Scanner(System.in)) {

            String input;

            while (true) {

                input = scanner.next();

                if (input.equalsIgnoreCase("n")) {
                    break;
                } else if (input.equalsIgnoreCase("y")) {

                    System.out.println("Enter MAX value for counter: ");

                    while (true) {

                        int value;

                        while (!scanner.hasNextInt()) {
                            System.out.println("Enter valid number!");
                            scanner.next();
                        }

                        value = scanner.nextInt();

                        if (value >= 0) {
                            setMaxValue(value);
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
