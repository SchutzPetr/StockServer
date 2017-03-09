package cz.schutzpetr.stock.core.utils;

/**
 * Created by Petr Schutz on 27.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class StoppableThread extends Thread {
    private volatile boolean running = false;

    @Override
    public void start(){
        this.setDaemon(true);
        super.start();
        this.running = true;
        ThreadManager.addTask(this);
    }


    @Override
    abstract public void run();

    public void terminate(){
        this.running = false;
        this.interrupt();
        ThreadManager.removeTask(this);
    }

    public boolean isRunning() {
        return running;
    }
}
