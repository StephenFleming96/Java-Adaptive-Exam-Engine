package View.Test;

import AEE.Callbacks.TestLifecycle;
import AEE.Timer.TimerValueContainer;
import Model.Test.TestDataContainer;
import View.View;

import javax.swing.*;
import java.util.ArrayList;

/**
 * TestView.java
 * Base class for all TestModel Views.
 * Handles callback methods and base level view methods.
 *
 * Stephen Fleming 100963909
 */
public class TestView extends View {
    //callback list
    private ArrayList<TestLifecycle> testCallbacks;

    public TestView() {
        testCallbacks = new ArrayList<TestLifecycle>();
    }

    /**
     * Called when test is completed
     * @param mins test time in minutes
     * @param secs test time in seconds
     */
    public void onComplete(TimerValueContainer time) {
        testCallbacks.forEach(v -> v.onComplete(time));
    }

    /**
     * Called when user answers question correctly
     */
    public void onCorrectAnswer() {
        testCallbacks.forEach(v -> v.onCorrectAnswer());
    }

    /**
     * Called when user answers question incorrectly
     */
    public void onIncorrectAnswer() {
        testCallbacks.forEach(v -> v.onIncorrectAnswer());
    }

    /**
     * Called when question is answered
     */
    public void onAnswerComplete() {
        testCallbacks.forEach(v -> v.onAnswerComplete());
    }

    /**
     * Register a test lifecycle callback
     * @param c callback
     */
    public void registerCallback(TestLifecycle c) {
        testCallbacks.add(c);
    }

    /**
     * Pass data to the view
     * @param data Data container which extends TestDataContainer
     */
    public void loadQuestionData(TestDataContainer data) {}

    /**
     * If this test has a next question button, lock it
     */
    public void lockNextButton() {
        refresh();
    }
}

