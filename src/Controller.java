import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class Controller {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        ArrayList<Callable<Integer>> callable = new ArrayList<>();
        for (int i = 0; i<100; i++) {
            callable.add(new MyThread((int) (Math.random()*10+1)));
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
