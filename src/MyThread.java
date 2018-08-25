import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class MyThread implements Callable<Integer> {
    private int inner;
    private ReentrantLock lock = new ReentrantLock();
    private Semaphore semaphore;
    private AtomicBoolean[] pool;

    public MyThread (int i, AtomicBoolean[] pool, Semaphore semaphore) {
        this.inner = i;
        this.pool = pool;
        this.semaphore = semaphore;
    }

    @Override
    public Integer call() throws Exception {
        int i;
        lock.lock();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
            for (i = 0;i<29;i++){
                if (!pool[i].get()){
                    pool[i].set(true);
                    System.out.println("thread "+Thread.currentThread().getName()+" get pool "+i);
                    break;
                }
            }
            lock.unlock();
            Thread.sleep(1000);
            lock.lock();
            pool[i].set(false);
            System.out.println("thread "+Thread.currentThread().getName()+" return pool "+i);
            lock.unlock();
            semaphore.release();
        return inner;
    }
}
