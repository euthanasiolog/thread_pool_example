import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class Controller {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static MyPool myPool = MyPool.getMyPool();
    private static AtomicBoolean[] pool = myPool.getPool();
    private static Semaphore semaphore = new Semaphore(MyPool.getSIZE());

    public static void main(String[] args) {

        ArrayList<Callable<Integer>> callable = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            callable.add(new MyThread((int) (Math.random()*10+1), pool, semaphore));
        }
        try {
            List<Future<Integer>> future = executorService.invokeAll(callable);
            executorService.shutdown();
            for (Future<Integer> f:future){
                try {
                    System.out.println(f.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
