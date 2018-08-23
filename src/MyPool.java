import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by piatr on 23.08.18.
 */
public class MyPool {
    private static MyPool instance;
    private static final int SIZE = 20;
    private Semaphore semaphore = new Semaphore(SIZE, true);
    private boolean[] pool = new boolean[SIZE];
    private static ReentrantLock lock = new ReentrantLock();
    private MyPool (){
        for (boolean b:pool){
            b = false;
        }
    }

    public static MyPool getMyPool (){
        if (instance==null) {
            try {
                lock.lock();
                if (instance==null)
                return new MyPool();
            } finally {
               lock.unlock();
            }
        }
        return instance;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public boolean[] getPool() {
        return pool;
    }


}