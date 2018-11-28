package AEE.Timer;

import AEE.Callbacks.TimerLifecycle;
import AEE.ThreadManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *  AsyncTimer.java
 *  This is a timer intended to run in a background thread. It is initialised with a minute and
 *  second value and will repeat until complete.
 *
 *  Callbacks can be registered via registerCallback, which takes an TimerLifecycle object (onUpdate or onFinished).
 *
 *  Stephen Fleming 100963909
 */
public class AsyncTimer implements Runnable {
    private TimerValueContainer values;
    private synchronized TimerValueContainer getValues() { return values; }

    private List<TimerLifecycle> callbackList;

    private TimerType timerType;
    private boolean timerInterrupted;

    /**
     * Asynchronous timer.
     * @param minutes   Number of minutes timer will run for.
     * @param seconds   Number of seconds timer will run for.
     */
    public AsyncTimer(int minutes, int seconds, TimerType type) {
        values = new TimerValueContainer(minutes, seconds);
        values.setActive(false);

        // source: stackoverflow.com/questions/8203864/choosing-the-best-concurrency-list-in-java
        callbackList = Collections.synchronizedList(new ArrayList());
        //end source

        values.setActive(false);
        timerInterrupted = false;

        timerType = type;
    }

    /**
     * Generate a fake time stamp at minute min, used for debugging
     * @param min
     * @return
     */
    public TimerValueContainer genFakeStamp(int min) {
        Random r = new Random();

        return new TimerValueContainer(min, r.nextInt(60));
    }

    /**
     * Create a temporary timer instance and generate a fake time stamp
     * @param min
     * @return TimerValueContainer with the stamp
     */
    public static TimerValueContainer generateFakeStamp(int min){
        AsyncTimer t = new AsyncTimer(min,00, TimerType.NONE);
        return t.genFakeStamp(min);
    }

    public synchronized int getMinutes() {
        return values.minutes();
    }
    public synchronized int getSeconds() {
        return values.seconds();
    }


    /**
     * Set the number of minutes and seconds the timer will run for
     * @param minutes
     * @param seconds
     */
    public synchronized void setTimerValues(int minutes, int seconds) {
        values.setMinutes(minutes);
        values.setSeconds(seconds);
    }

    /**
     * Get a container with the current timer (at the point of call, does not update)
     * @return
     */
    public synchronized TimerValueContainer getTimerValues() {
        return new TimerValueContainer(values.minutes(), values.seconds());
    }

    /**
     * Start the timer with the currently assigned values
     */
    public synchronized void start() {
        ThreadManager.getInstance().RegisterNewThread(this, timerType);
    }

    /**
     * Pause an active timer.
     * Will kill thread.
     */
    public synchronized void pause() {
        values.setActive(false);
    }


    /**
     * Register a new callback for the timer.
     * Probably does not need to be sync, but just to be sure.
     * @param callback Object implementing TimerLifecycle
     */
    public synchronized void registerCallback(TimerLifecycle callback) {
        callbackList.add(callback);
    }

    /**
     * Called on each timer tic.
     * Decrement timer values, run callbacks
     */
    private synchronized void onUpdate() {
        getValues().setSeconds(getValues().seconds() - 1);

        if (getValues().seconds() <= 0) {
            if (getValues().minutes() > 0) {
                getValues().setSeconds(59);
                getValues().setMinutes(getValues().minutes() - 1);
            } else {
                getValues().setActive(false);
            }
        }

        // source : mkyong.com/java8/java-8-foreach-examples/
        callbackList.forEach(cb -> cb.onUpdate(getValues().minutes(), getValues().seconds()) );
        //end source
    }

    /**
     * Called when timer finished.
     * Run callbacks.
     */
    private synchronized void onFinished() {
        callbackList.forEach(cb -> cb.onFinished() );
    }

    /**
     * Start the timer.
     * Intended to be run inside a new thread.
     */
    @Override
    public void run() {
        getValues().setActive(true);

        while (getValues().active()) {
            try {
                Thread.sleep(1000);

                onUpdate();
            } catch(InterruptedException e) {
                timerInterrupted = true;
                break;
            }
        }

        if (!timerInterrupted)
            onFinished();
        else
            timerInterrupted = false;
    }
}
