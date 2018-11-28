package AEE.Callbacks;

import AEE.Timer.TimerValueContainer;

public interface TestLifecycle {
    /**
     * Called when user successfully completes test
     */
    void onComplete(TimerValueContainer time);

    /**
     * Called on every correct answer
     */
    void onCorrectAnswer();

    /**
     * Called on every incorrect answer
     */
    void onIncorrectAnswer();

    /**
     * Called when question is answered, truth value does not matter
     */
    void onAnswerComplete();
}

