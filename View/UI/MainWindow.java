package View.UI;

import AEE.AdaptiveExamEngine;
import AEE.Callbacks.TimerLifecycle;
import AEE.TestManager;
import AEE.Timer.TimerType;
import AEE.TimerManager;
import Controller.UI.UIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainWindow.java
 * This is the container for the main window. This window is used to log into the system and
 * to select tests.
 *
 * Stephen Fleming 100963909
 */
public class MainWindow extends WindowContainer {
    private JPanel panMainWindow;
    private JPanel panMainContent;
    private JPanel panelMainHeader;

    private JLabel labTimer;
    private JButton buttonAbout;

    public MainWindow() {
        super("Adaptive Exam Engine", JFrame.EXIT_ON_CLOSE, true);

        renderPanel = panMainWindow;
        headerPanel = panelMainHeader;
        contentPanel = panMainContent;

        window.setContentPane(renderPanel);

        //button to toggle about
        buttonAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdaptiveExamEngine.getUiController().showAboutWindow();
            }
        });
    }

    /**
     * Set the timer for the main application
     * @param minutes
     * @param seconds
     */
    @Override
    public void setTimer(int minutes, int seconds) {
        TimerManager.getInstance().setTimer(TimerType.MAIN, minutes, seconds, new TimerLifecycle() {
            @Override
            public void onUpdate(int minutes, int seconds) {
                labTimer.setText(""+minutes+":"+seconds);
            }

            @Override
            public void onFinished() {
                TestManager.getInstance().setAllTestsComplete();
            }
        });
    }
}
