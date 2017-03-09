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
    private static ThreadManager ourInstance = new ThreadManager();

    public static ThreadManager getInstance() {
        return ourInstance;
    }

    private ThreadManager() {
    }

    private static final List<StoppableThread> tasks = new ArrayList<>();


    protected static void addTask(StoppableThread stoppableThread){
        tasks.add(stoppableThread);
    }

    protected static boolean removeTask(StoppableThread stoppableThread){
        return tasks.remove(stoppableThread);
    }

    public static List<StoppableThread> getTasks() {
        return tasks;
    }
}
