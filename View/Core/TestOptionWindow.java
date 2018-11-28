package View.Core;

import AEE.AdaptiveExamEngine;
import AEE.TestManager;
import Controller.Test.TestController;
import Model.Test.TestModel;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * TestOptionWindow.java
 * Displays list of avaliable tests for the user to complete
 *
 * Stephen Fleming 100963909
 */
public class TestOptionWindow extends View {
    /**
     * Simple container for test buttons, link JButton to test complete value
     */
    private class TestButtonContainer {
        public JButton button;
        public boolean completed;

        public TestButtonContainer(JButton btn) {
            button = btn;
            completed = false;
        }
    }

    private ArrayList<TestButtonContainer> testButtons;

    private JPanel contentPanel;
    private JButton skipBtn;

    public TestOptionWindow() {
        testButtons = new ArrayList<TestButtonContainer>();
    }

    @Override
    public JPanel getRenderPanel() {
        return contentPanel;
    }

    /**
     * Dynamically generate testing buttons based on TestControllers in TestManager.
     */
    private void generateTestButtons() {
        for (String s : TestManager.getInstance().getTestNames()) {
            //create button and container for it
            JButton btn = new JButton();
            TestButtonContainer btnCont = new TestButtonContainer(btn);

            //set btn text value to test name
            btn.setText(s);

            //add onclick listner
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnCont.completed = true;

                    AdaptiveExamEngine.activateTest(s);

                    lockTestOptions();
                }
            });

            //add to btn list and content panel
            testButtons.add(btnCont);
            contentPanel.add(btn);
        }
    }

    /**
     * Load all test buttons
     */
    private void lockTestOptions() {
        for (TestButtonContainer t : testButtons) {
            t.button.setEnabled(false);
        }
    }

    /**
     * Re-enable test buttons that have not been completed.
     */
    private void unlockTestOptions() {
        boolean allTestsCompleted = true;
        for (TestButtonContainer t : testButtons) {
            if (!t.completed) {
                t.button.setEnabled(true);
                allTestsCompleted = false;
            }
        }

        if (allTestsCompleted) {
            AdaptiveExamEngine.activateMainScreenPage("TestCompletedWindow");
            return;
        }

        //set tooltip for completed tests
        HashMap<String, TestController> map = TestManager.getInstance().getTestMap();
        TestModel testModel;

        for (String s : map.keySet()) {
            testModel = map.get(s).getTest();

            if (testModel.getTestCompleted()) {
                for (TestButtonContainer t : testButtons) {
                    if (t.button.getText().equals(s)) {
                        t.button.setToolTipText(s + " completed with " + testModel.getTestTimeString() + " remaining");
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        generateTestButtons();
        skipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (TestButtonContainer t : testButtons) {
                    t.completed = true;
                }

                onRefresh();
            }
        });

        AdaptiveExamEngine.getUiController().startMainTimer();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        unlockTestOptions();
    }
}
