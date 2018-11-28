package AEE.Callbacks;

/**
 * Callback interface for AsyncTimer.
 * Usage:
 *  timer.registerCallback(new TimerLifecycle() {...})
 *
 *  Stephen Fleming 100963909
 */
public interface TimerLifecycle {
    /**
     *  Called on every timer tic
     * @param minutes   Timers current minutes
     * @param seconds   Timers current seconds
     */
    void onUpdate(int minutes, int seconds);

    /**
     *  Called when timer finishes
     */
    void onFinished();
}
