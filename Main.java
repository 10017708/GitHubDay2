import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main
 */
public class Main {
    private static final int NUMBER_OF_THREADS = 100;

    public static void main(String[] args) {
        Instant instantStart = Instant.now();

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        AtomicInteger count = new AtomicInteger(0);
        ArrayList<Future<Integer>> allFutures = new ArrayList<Future<Integer>>();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int count = 0;
                    for (int j = 0; j < 1000000; j++) {
                        count++;
                    }
                    return count;
                }
            };

            allFutures.add(executorService.submit(callable));
        }

        for (int i = 0; i < allFutures.size(); i++) {
            Future<Integer> future = allFutures.get(i);
            try {
                count.addAndGet(future.get());
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Instant instantEnd = Instant.now();
        long duration = instantEnd.toEpochMilli() - instantStart.toEpochMilli();

        System.out.println("Final count value: " + count.get());
        System.out.println("Duration: " + duration + " milliseconds");
    }
}