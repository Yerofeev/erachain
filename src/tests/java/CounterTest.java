import main.erachain.ThreadSafeCounter;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CounterTest {

    private ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();

    @BeforeEach
    void initializeCounter() {

        threadSafeCounter.setMaxValue(Integer.MAX_VALUE);
        threadSafeCounter.setCounter(0);
    }

    @Test
    void test1() {

        String input = "y 7";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        threadSafeCounter.initialize();
        assertEquals(7, threadSafeCounter.getMaxValue());
    }

    @Test
    void test2() {

        String input = "N";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        threadSafeCounter.initialize();
        assertEquals(Integer.MAX_VALUE, threadSafeCounter.getMaxValue());
    }

    @Test
    void test3() {

        String input = "Y -1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertThrows(NoSuchElementException.class, () -> threadSafeCounter.initialize());
    }

    @Test
    void test4() {

        threadSafeCounter.incCounter();
        assertEquals(1, threadSafeCounter.getCounterValue());
    }

    @Test
    void test5() throws InterruptedException {

        threadSafeCounter.setMaxValue(3);

        Thread thread = new Thread(() -> {
                for (int j = 0; j < 4; j++)
                    threadSafeCounter.incCounter();
            });
        thread.start();
        thread.join();
        assertFalse(thread.isAlive());
        assertEquals(0, threadSafeCounter.getCounterValue());
    }

    @Test
    void test6() throws InterruptedException {

        threadSafeCounter.setMaxValue(49);

        Thread[] threads = new Thread[10];
        for (Thread thread: threads){
            thread = new Thread(() -> {
                for (int j = 0; j < 5; j++)
                    threadSafeCounter.incCounter();
            });
            thread.start();
            thread.join();
            assertFalse(thread.isAlive());
        }
        assertEquals(0, threadSafeCounter.getCounterValue());
    }

    @Test
    void test7() throws InterruptedException {
        test(10, 1000, threadSafeCounter);
        assertEquals(1000, threadSafeCounter.getCounterValue());
    }

    @Test
    void test8() throws InterruptedException {
        int count = 100 * 1000;
        test(100, count, threadSafeCounter);
        assertEquals(count, threadSafeCounter.getCounterValue());
    }

    @Test
    void test9() throws InterruptedException {
        int count = 100 * 1000;
        test(1000, count, threadSafeCounter);
        assertEquals(count, threadSafeCounter.getCounterValue());
    }

    @Test
    void test10() throws InterruptedException {
        int count = 1000 * 1000;
        test(64, count, threadSafeCounter);
        assertEquals(count, threadSafeCounter.getCounterValue());
    }

    private static void test(int nThreads, int count, ThreadSafeCounter threadSafeCounter) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < nThreads; i++)
            executorService.submit(() -> {
                    for (int j = 0; j < count; j += nThreads)
                        threadSafeCounter.incCounter();
                });
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.MINUTES);
        assert executorService.isTerminated();


    }

}
