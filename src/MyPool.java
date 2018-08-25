import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class MyPool {
    private static MyPool instance;
    private static final int SIZE = 30;
    private AtomicBoolean[] pool = new AtomicBoolean[SIZE];
    private static ReentrantLock lock = new ReentrantLock();
    private MyPool (){
        for (int i = 0; i < SIZE; i++) {
            pool[i] = new AtomicBoolean();
            pool[i].set(false);
        }
    }

    public static MyPool getMyPool (){
        if (instance==null) {
            try {
                lock.lock();
                if (instance==null) {
                    instance = new MyPool();
                    return instance;
                }
            } finally {
               lock.unlock();
            }
        }
        return instance;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public AtomicBoolean[] getPool() {
        return pool;
    }
}
