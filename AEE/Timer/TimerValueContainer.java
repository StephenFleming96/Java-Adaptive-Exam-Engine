package AEE.Timer;

/**
 * TimerValueContainer.java
 * Container for time values
 *
 * Stephen Fleming 100963909
 */
public class TimerValueContainer {
    private int minutes;
    private int seconds;

    private int secondsTotal;

    private boolean active;

    public TimerValueContainer(int mins, int secs) {
        minutes = mins;
        seconds = secs;

        secondsTotal = (mins * 60) + secs;
    }

    public synchronized int seconds() { return seconds; }
    public synchronized int minutes() { return minutes; }

    public synchronized boolean active() { return active; }

    public synchronized void setMinutes(int mins) {
        minutes = mins;
    }

    public synchronized void setSeconds(int secs) {
        seconds = secs;
    }

    public synchronized void setActive(boolean act) {
        active = act;
    }

    /**
     * Convert the time to total number of seconds
     * @return
     */
    public synchronized int toSecondsTotal() {
        return secondsTotal;
    }

    @Override
    public synchronized String toString() {
        return (minutes + "m" + seconds + "s");
    }
}
