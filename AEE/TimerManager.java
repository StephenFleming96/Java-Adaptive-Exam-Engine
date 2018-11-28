package AEE;

import AEE.Callbacks.TimerLifecycle;
import AEE.Timer.AsyncTimer;
import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;

import java.util.HashMap;

/**
 * TimerManager.java
 * Container for Timers, handles interactions
 *
 * Stephen Fleming 100963909
 */
public class TimerManager {
    private HashMap<TimerType, AsyncTimer> timerMap;

    private static TimerManager instance;

    private TimerManager() {
        timerMap = new HashMap<TimerType, AsyncTimer>();

        timerMap.put(TimerType.MAIN, new AsyncTimer(15,00, TimerType.MAIN));
        timerMap.put(TimerType.TESTING, new AsyncTimer(99,00, TimerType.TESTING));
        timerMap.put(TimerType.PIN, new AsyncTimer(00,00, TimerType.PIN));
    }

    public synchronized static TimerManager getInstance() {
        if (instance == null)
            instance = new TimerManager();

        return instance;
    }

    /**
     * Stop the timer, reset with the values
     * @param type
     * @param mins
     * @param secs
     */
    public synchronized void resetTimer(TimerType type, int mins, int secs) {
        pause(type);

        timerMap.get(type).setTimerValues(mins, secs);
    }

    /**
     * Set values for the timer without pausing
     * @param type
     * @param mins
     * @param secs
     */
    public synchronized void setTimerValues(TimerType type, int mins, int secs) {
        timerMap.get(type).setTimerValues(mins, secs);
    }

    /**
     * Set the timer, with new callback
     * @param timer
     * @param mins
     * @param secs
     * @param cb
     */
    public synchronized void setTimer(TimerType timer, int mins, int secs, TimerLifecycle cb) {
        setTimerValues(timer, mins, secs);
        registerCallbackForTimer(timer, cb);
    }

    /**
     * Start the timer
     * @param timer
     */
    public synchronized void startTimer(TimerType timer) {
        timerMap.get(timer).start();
    }

    /**
     * register a new callback for the timer
     * @param timer
     * @param cb
     */
    public synchronized void registerCallbackForTimer(TimerType timer, TimerLifecycle cb) {
        timerMap.get(timer).registerCallback(cb);
    }

    /**
     * return the timer instance of type
     * @param type
     * @return
     */
    public synchronized AsyncTimer getTimer(TimerType type) {
        return timerMap.get(type);
    }

    /**
     * Pause the timer
     * @param type
     */
    public synchronized void pause(TimerType type) {
        ThreadManager.getInstance().stopTimerThread(type);
    }

    /**
     * get the values for the timer
     * @param type
     * @return
     */
    public synchronized TimerValueContainer getTimerValues(TimerType type) {
        return timerMap.get(type).getTimerValues();
    }
}

