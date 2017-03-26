package cz.schutzpetr.stock.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Schutz on 27.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class ThreadManager {
    private static final List<StoppableThread> threads = new ArrayList<>();
    private static ThreadManager ourInstance = new ThreadManager();

    private ThreadManager() {
    }

    public static ThreadManager getInstance() {
        return ourInstance;
    }

    protected static void addThread(StoppableThread stoppableThread) {
        threads.add(stoppableThread);
    }

    protected static boolean removeThread(StoppableThread stoppableThread) {
        return threads.remove(stoppableThread);
    }

    public static List<StoppableThread> getTasks() {
        return threads;
    }

    public void stopThreads() {
        threads.forEach(StoppableThread::terminate);
    }
}
