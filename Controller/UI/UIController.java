package Controller.UI;

import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;
import View.UI.TestWindow;
import View.UI.*;

import View.View;

/**
 * UIController.java
 * Controller for application windows.
 * Contains references to window containers, manages view switching.
 *
 * Stephen Fleming 100963909
 */

public class UIController {
    private MainWindow mainWindowContainer;
    private TestWindow testWindowContainer;
    private AboutWindow aboutWindow;

    private final int MAIN_TIMER_MINUTES = 15;

    /**
     *
     */
    public UIController(){
        mainWindowContainer = new MainWindow();
        testWindowContainer = new TestWindow();
        aboutWindow = new AboutWindow();
    }

    /**
     * Take a Renderable view and load it into the main windows content section
     * @param view
     */
    public void loadViewForMainWindow(View view) {
        mainWindowContainer.setMainContent(view);
    }

    /**
     * Take a Renderable view and load it into the testing windows content section,
     * Disable the 'Done' button and show the window
     * @param view
     */
    public void loadViewForTestWindow(View view) {
        testWindowContainer.setMainContent(view);

        testWindowContainer.disableDoneButton();

        testWindowContainer.show();
    }

    /**
     * Refresh the views currently loaded into the window containers.
     * Will update anything that needs to be updated, re-pack window.
     * No effect on views that dont need to update
     */
    public void refreshViews() {
        mainWindowContainer.refreshPanels();
        testWindowContainer.refreshPanels();
        aboutWindow.refreshPanels();
    }

    /**
     * Initialise the main timer
     */
    public void startMainTimer() {
        mainWindowContainer.setTimer(MAIN_TIMER_MINUTES,00);
    }

    /**
     * Initialise the test timer with the timer
     * @param time
     */
    public void startTestTimer(TimerValueContainer time) {
        testWindowContainer.setTimer(time.minutes(), time.seconds());
    }

    /**
     * Current test is completed, enable the done button and stop the timer
     */
    public void testComplete() {
        TimerManager.getInstance().pause(TimerType.TESTING);

        enableTestDoneButton();
    }

    /**
     * Enable the done button on the test window
     */
    public void enableTestDoneButton() {
        testWindowContainer.enableDoneButton();
    }

    /**
     * Show and hide about widow
     */
    public void showAboutWindow() {
        aboutWindow.show();
    }
    public void hideAboutWindow() { aboutWindow.hide(); }
}
