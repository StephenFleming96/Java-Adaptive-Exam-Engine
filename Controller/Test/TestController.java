package Controller.Test;

import AEE.AdaptiveExamEngine;
import AEE.Callbacks.TestLifecycle;
import AEE.Callbacks.ViewLifecycle;
import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;
import Model.Test.TestDataContainer;
import Model.Test.TestModel;
import View.Test.TestView;

/**
 * TestController.java
 * Controller for a test. Contains getters and initialisation of views.
 *
 * Stephen Fleming 100963909
 */
public class TestController {

    TestView testView;
    TestModel testModel;

    String testName;

    /**
     * Constructor for a TestModel Controller
     * @param name  String name, ie. SpellingTestModel. Used as identifier.
     * @param view  TestView for relevent view
     * @param model TestModel for relevent model
     */
    public TestController(String name, TestView view, TestModel model) {
        testName = name;
        testView = view;
        testModel = model;

        prepareView();
    }

    /**
     * @return The test's view.
     */
    public TestView getView() {
        return testView;
    }

    /**
     * @return The test's model.
     */
    public TestModel getTest() {
        return testModel;
    }

    /**
     * @return The full test name. i.e. Spelling TestModel
     */
    public String getTestName() {
        return testName;
    }

    /**
     * stop the test
     */
    public void stopTest() {
        testModel.setTestCompleted(true);
    }

    /**
     * Get the next question data from the model, pass to view
     */
    private void loadNextQuestionIntoView() {
        TestDataContainer data = testModel.getNextQuestion();

        if (data == null || testModel.getTestCompleted()) {
            testView.onComplete(TimerManager.getInstance().getTimerValues(TimerType.TESTING));
        } else {
            testView.loadQuestionData(data);
        }

        testView.onRefresh();
    }

    /**
     * Initialise the view with the data from the model.
     */
    private void prepareView() {
        //view lifecycle events
        testView.registerCallback(new ViewLifecycle() {
            @Override
            public void onCreate() {
                loadNextQuestionIntoView();
            }

            @Override
            public void onLoad() {
                AdaptiveExamEngine.getUiController().startTestTimer(getTest().getTotalTestTime());
            }

            @Override
            public void onRefresh() { }

            @Override
            public void onClose() { }
        });

        //test lifecycle events
        testView.registerCallback(new TestLifecycle() {
            @Override
            public void onComplete(TimerValueContainer time) {
                testView.lockNextButton();

                testModel.setTestCompleted(true);
                testModel.setTestEndTime(time);

                AdaptiveExamEngine.getUiController().testComplete();
            }

            @Override
            public void onIncorrectAnswer() {
                testModel.decreaseDifficulty();
            }

            @Override
            public void onCorrectAnswer() {
                AdaptiveExamEngine.getUiController().enableTestDoneButton();
                testModel.increaseDifficulty();
            }

            @Override
            public void onAnswerComplete() {
                loadNextQuestionIntoView();
            }
        });

        testView.onCreate();
    }
}
