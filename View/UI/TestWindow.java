package View.UI;

import AEE.AdaptiveExamEngine;
import AEE.TestManager;
import AEE.Callbacks.TimerLifecycle;
import AEE.Timer.TimerType;
import AEE.TimerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * TestWindow.java
 * Window which tests are displayed in
 *
 * Stephen Fleming 100963909
 */
public class TestWindow extends WindowContainer {
    private JPanel panelRender;

    private JButton btnDone;
    private JLabel labTime;

    private JPanel panelContent;
    private JPanel panelHeader;

    private static final int windowMoveUp = 175;

    public TestWindow() {
        super("AEE: Active TestModel", JFrame.DO_NOTHING_ON_CLOSE, false);

        renderPanel = panelRender;

        headerPanel = panelHeader;
        contentPanel = panelContent;

        window.setContentPane(renderPanel);
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });

        btnDone.setEnabled(false);
    }

    /**
     * Start the timer for this window with the given params
     * Should only be called once (on window creation), primarily for setting callbacks
     * @param mins
     * @param secs
     */
    @Override
    public void setTimer(int mins, int secs) {
        TimerManager.getInstance().setTimer(TimerType.TESTING, mins, secs, new TimerLifecycle() {
            @Override
            public void onUpdate(int minutes, int seconds) {
                synchronized (this) {
                    labTime.setText("" + minutes + ":" + seconds);
                }
            }

            @Override
            public void onFinished() {
                synchronized (this) {
                    TestManager.getInstance().onTestTimeout();
                }
            }
        });

        TimerManager.getInstance().startTimer(TimerType.TESTING);

        labTime.setVisible(true);
    }

    /**
     * Show the Done button, allowing the test to be closed
     */
    public void enableDoneButton() {
        btnDone.setEnabled(true);
    }

    /**
     * Hide the done button, forcing waiting for user to answer a question
     */
    public void disableDoneButton() {
        btnDone.setEnabled(false);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        Point pos = window.getLocation();
        window.setLocation(pos.x, pos.y - windowMoveUp);
    }

    @Override
    protected void onClose() {
        mainContent.onClose();

        TestManager.getInstance().setCurrentSetComplete();
        AdaptiveExamEngine.getUiController().refreshViews();
    }
}
