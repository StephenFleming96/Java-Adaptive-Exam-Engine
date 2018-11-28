package Model.Test;

import AEE.AdaptiveExamEngine;
import AEE.TestResultStamp;
import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;

import java.util.ArrayList;
import java.util.Timer;

/**
 * TestModel.java
 *  Base class for a TestModel
 *  Handles results and change of difficulty level.
 *
 *  Stephen Fleming 100963909
 */
public class TestModel {

    protected boolean testCompleted;

    //time to complete test
    private TimerValueContainer testEndTime;

    protected int difficulty;

    private final int MIN_DIFFICULTY = 0;
    private final int MAX_DIFFICULTY = 2;

    protected ArrayList<TestDataContainer> questions;

    //protected TestResult results;
    protected ArrayList<TestResultStamp> results;

    //time test will run for
    protected TimerValueContainer totalTestTime;

    public TestModel() {
        difficulty = 0;
        testCompleted = false;

        results = new ArrayList<TestResultStamp>();
        totalTestTime = new TimerValueContainer(0,0);

        questions = new ArrayList<TestDataContainer>();
    }

    /**
     * @return True if test has completed
     */
    public boolean getTestCompleted() {
        return testCompleted;
    }

    /**
     * Change TestCompleted value
     */
    public void setTestCompleted(boolean value) { testCompleted = value; }

    /**
     * Load result as correct, increase difficulty level (if possible)
     */
    public void increaseDifficulty() {
        TestResultStamp r = generateNewResultStamp();
        r.correct = 1;
        r.incorrect = 0;


        if (difficulty < MAX_DIFFICULTY)
            difficulty++;

        results.add(r);
    }

    /**
     * Load result as incorrect, decrease difficulty level (if possible)
     */
    public void decreaseDifficulty() {
        TestResultStamp r = generateNewResultStamp();
        r.correct = 0;
        r.incorrect = 1;

        if (difficulty > MIN_DIFFICULTY)
            difficulty--;

        results.add(r);
    }

    /**
     * Get test time as a string, used in displaying tool tip
     */
    public String getTestTimeString() {
        if (testEndTime == null)
            testEndTime = TimerManager.getInstance().getTimerValues(TimerType.TESTING);

        return testEndTime.minutes() + " minutes, " + testEndTime.seconds() + " seconds";
    }

    /**
     * Set the end of test time
     * @param t
     */
    public void setTestEndTime(TimerValueContainer t) {
        testEndTime = t;
    }

    /**
     * @return The currently selected question
     */
    public TestDataContainer getCurrentQuestion() { return null; }

    /**
     * @return The next question
     */
    public TestDataContainer getNextQuestion() { return null; }

    /**
     * @return ArrayList containing test results up to this point, list is updated on correct and incorrect answers
     */
    public ArrayList<TestResultStamp> getTestResult() {
        return results;
    }

    /**
     * @return Calculate total performance over test (correct and incorrect results)
     */
    public TestResultStamp getTotalResultForTest() {
        TestResultStamp resultTotal = new TestResultStamp();

        for (TestResultStamp r : results) {
            resultTotal.correct += r.correct;
            resultTotal.incorrect += r.incorrect;

            if (r.correct == 1)
                resultTotal.score += AdaptiveExamEngine.getScoreForDifficulty(r.score);
        }

        return resultTotal;
    }

    /**
     * Time taken to complete test
     * @return
     */
    public TimerValueContainer getTotalTestTime() {
        return totalTestTime;
    }

    /**
     * @return A new Result Stamp with difficulty and time set
     */
    private TestResultStamp generateNewResultStamp() {
        TestResultStamp t = new TestResultStamp();

        t.score = difficulty;
        t.timeStamp = TimerManager.getInstance().getTimer(TimerType.MAIN).getTimerValues();

        return t;
    }
}
