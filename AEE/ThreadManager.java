package AEE;

import AEE.Timer.TimerType;

import java.util.concurrent.*;

/***
 * ThreadManager.java
 * This class manages timer threads, ensuring that only one can be active at a time per timer.
 * Starts new threads and interrupts existing threads (if necessary)
 *
 * Stephen Fleming 100963909
 */
public class ThreadManager {
    private ConcurrentMap<TimerType, Thread> threadSafeTimerMap;

    private static ThreadManager instance;

    private ThreadManager() {
        threadSafeTimerMap = new ConcurrentHashMap<TimerType, Thread>();
    }

    /**
     * Get singleton instance of ThreadManager
     * @return Instance of ThreadManager
     */
    public synchronized static ThreadManager getInstance() {
        if (instance == null)
            instance = new ThreadManager();

        return instance;
    }

    /**
     * Stop the currently running thread belonging to timer
     * @param timer Type of timer to stop
     */
    public synchronized void stopTimerThread(TimerType timer) {
        threadSafeTimerMap.get(timer).interrupt();
    }

    /***
     * Start a new thread running job
     * @param job   Task implementing runnable interface
     */
    public synchronized void RegisterNewThread(Runnable job, TimerType timer) {
        Thread newThread = new Thread(job);

        if (threadSafeTimerMap.containsKey(timer))
            stopTimerThread(timer);

        threadSafeTimerMap.put(timer, newThread);

        newThread.start();
    }
}
