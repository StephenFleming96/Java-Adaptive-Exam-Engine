package AEE;

import Controller.UI.UIController;
import Controller.Core.UserController;
import View.Core.TestCompletedWindow;
import View.UI.Renderable;
import View.Core.TestOptionWindow;
import View.Core.LoginWindow;
import View.Core.PINGenerationWindow;

import View.View;

/**
 * AdaptiveExamEngine.java
 * This is the main container for all application logic.
 * It contains references to all controllers and manages messenging between them if required.
 *
 * Stephen Fleming 100963909
 */
public class AdaptiveExamEngine {
    private static AdaptiveExamEngine instance;

    public UIController uiController;
    public UserController userController;

    /***
     *
     */
    public AdaptiveExamEngine() {
        instance = this;

        uiController = new UIController();
        userController = new UserController();
    }

    /**
     * Begin simulation
     */
    public void start() {
        activateMainScreenPage("LoginWindow");
    }

    /**
     * Instance of AEE.
     * Must be sync, callbacks will be able to access AEE.
     * @return Singleton AEE instance
     */
    public static synchronized AdaptiveExamEngine getInstance() { return instance; }

    /// Controller & Manager getters
    public static UIController getUiController() { return instance.uiController; }
    public static UserController getUserController() { return instance.userController; }

    /***
     * Take a test name, pass it onto relevant controllers to start test
     * @param testName  TestModel name: SpellingTestModel, MathTestModel. Assumed valid name in TestManager.
     */
    public static void activateTest(String testName) {
        View newTest = null;

        try {
            TestManager.getInstance().activateTest(testName);
            newTest = TestManager.getInstance().getViewForCurrentTest();

            getUiController().loadViewForTestWindow(newTest);

            TestManager.getInstance().runCurrentTest();
        } catch (NullPointerException e) {
            System.out.println("ERROR: " + testName + " IS NOT A VALID TEST!");
            e.printStackTrace();
        }
    }

    /***
     *  Take a main window page name and pass onto relevant controllers
     * @param pageName
     */
    public static void activateMainScreenPage(String pageName) {
        View newView = null;

        if (pageName == "LoginWindow") {
            newView = new LoginWindow();
        } else if (pageName == "TestOptionWindow") {
            newView = new TestOptionWindow();
        } else if (pageName == "PINGenerationWindow") {
            newView = new PINGenerationWindow();
        } else {
            newView = new TestCompletedWindow();
        }

        newView.onCreate();
        getUiController().loadViewForMainWindow(newView);
    }

    /**
     * Take a difficulty and return the score for that level
     * @param diff Integer from 0 - 2
     * @return Score matching difficulty
     */
    public static int getScoreForDifficulty(int diff) {
        switch(diff) {
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 10;
            default:
                return 0;
        }
    }
}
