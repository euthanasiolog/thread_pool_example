import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class MyThread implements Callable<Integer> {
    private int inner;
    private ReentrantLock lock = new ReentrantLock();
    private MyPool myPool = MyPool.getMyPool();
    private Semaphore semaphore = myPool.getSemaphore();
    private boolean[] pool = myPool.getPool();

    public MyThread (int i) {
        this.inner = i;
    }

    @Override
    public Integer call() throws Exception {
        int i = 0;
        try {
            semaphore.acquire();
            lock.lock();
            do {
                if (!pool[i]) {
                    pool[i] = true;
                } else i++;
            } while (pool[i]);
            System.out.println("thread "+Thread.currentThread().getName()+" get pool "+i);
            lock.unlock();
            Thread.sleep(500);
            lock.lock();
            pool[i] = false;
            lock.unlock();
            semaphore.release();
            System.out.println("thread "+Thread.currentThread().getName()+" return pool "+i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return inner;
    }
}
