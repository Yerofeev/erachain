package main.erachain;

public class erachainApp {

    public static void main(String[] args) throws InterruptedException {

        ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();
        threadSafeCounter.initialize();

        Thread[] threads = new Thread[10];
        for (Thread thread: threads) {
            thread = new Thread(() -> {
                for (int j = 0; j < 5; j++)
                    threadSafeCounter.incCounter();
            });
            thread.start();
            thread.join();
        }
        System.out.println(threadSafeCounter.getCounterValue());
    }
}
