package cz.schutzpetr.stock.core.utils;

/**
 * Created by Petr Schutz on 27.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class StoppableThread extends Thread {
    /**
     * True if thread are running
     */
    private volatile boolean running = false;

    /**
     * Causes this thread to begin execution; the Java Virtual Machine
     * calls the <code>run</code> method of this thread.
     * <p>
     * The result is that two threads are running concurrently: the
     * current thread (which returns from the call to the
     * <code>start</code> method) and the other thread (which executes its
     * <code>run</code> method).
     * <p>
     * It is never legal to start a thread more than once.
     * In particular, a thread may not be restarted once it has completed
     * execution.
     *
     * @throws IllegalThreadStateException if the thread was already
     *                                     started.
     * @see #run()
     * @see #stop()
     */
    @Override
    public void start(){
        this.setDaemon(true);
        super.start();
        this.running = true;
        ThreadManager.addThread(this);
    }


    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see     #start()
     * @see     #stop()
     */
    @Override
    abstract public void run();

    /**
     * This method stops a running thread, securely
     */
    public void terminate(){
        this.interrupt();
        this.running = false;
        ThreadManager.removeThread(this);
    }

    /**
     *
     * @return true if thread are running
     */
    public boolean isRunning() {
        return running;
    }
}
