package java.com.erachain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Tests {
    //for (int nThreads = 1; nThreads <= 64; nThreads *= 2)            doThreadSafeTest(nThreads);

    private static void doThreadSafeTest(final int nThreads) throws InterruptedException {
        final int count = 1000 * 1000;
        ExecutorService es = Executors.newFixedThreadPool(nThreads);
        final VolatileInt vi = new VolatileInt();
        System.out.printf("--- Testing with Volatile --- ");
        for (int i = 0; i < nThreads; i++)
            es.submit(new Runnable() {
                public void run() {
                    for (int j = 0; j < count; j += nThreads)
                        vi.num++;
                }
            });
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        assert es.isTerminated();
        System.out.printf("With %,d threads should total %,d but was %,d%n", nThreads, count, vi.num /*num.longValue()*/);

        System.out.printf("--- Testing AtomicInteger --- ");
        es = Executors.newFixedThreadPool(nThreads);
        final AtomicInteger num = new AtomicInteger();
        for (int i = 0; i < nThreads; i++)
            es.submit(new Runnable() {
                public void run() {
                    for (int j = 0; j < count; j += nThreads)
                        num.incrementAndGet();
                }
            });
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        assert es.isTerminated();
        System.out.printf("With %,d threads should total %,d but was %,d%n", nThreads, count, num.longValue() );
    }

    static class VolatileInt {
        volatile int num = 0;
    }
}
