package View.Core;

import AEE.TestManager;
import AEE.TestResultStamp;
import View.ResultGraphView.ResultChartView;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * TestCompletedWindow.java
 * Displays feedback to the user on their performance
 *
 * Stephen Fleming 100963909
 */
public class TestCompletedWindow extends View {
    private JPanel panelContent;
    private JPanel panelTestCompleted;
    private JPanel panelButtonList;
    private JPanel panelFeedback;
    private JButton buttonResultsAll;

    private ResultChartView resultGraph;

    public TestCompletedWindow() {
        panelFeedback.setLayout(new GridLayout(0,1));
    }

    @Override
    public JPanel getRenderPanel() {
        return panelContent;
    }

    /**
     * Take a test name, return the appropriate results
     * @param testName eg. Spelling
     */
    private void viewResultsForTest(String testName) {
        panelFeedback.removeAll();

        TestResultStamp results = TestManager.getInstance().getResultForTest(testName);

        panelFeedback.add(new JLabel("Results for " + testName));
        panelFeedback.add(new JLabel(""));

        panelFeedback.add(new JLabel("Correct: " + results.correct));
        panelFeedback.add(new JLabel("Incorrect or unanswered: " + results.incorrect));
        panelFeedback.add(new JLabel(""));

        panelFeedback.add(new JLabel("Total score from question: " + results.score));

        refresh();
    }

    /**
     * Display panel with graphs showing overall performance
     */
    private void viewOverallPerformance() {
        panelFeedback.removeAll();

        if (resultGraph == null)
            resultGraph = new ResultChartView();

        panelFeedback.add(resultGraph.getRenderPanel());

        refresh();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //create buttons for each test
        ArrayList<String> testNames = TestManager.getInstance().getTestNames();
        for (String s : testNames) {
            JButton btn = new JButton();
            btn.setText("View " + s + " results");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewResultsForTest(s);
                }
            });

            panelButtonList.add(btn);
        }

        //button to display graphs
        buttonResultsAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOverallPerformance();
            }
        });
    }

    @Override
    public void onRefresh() {
        panelFeedback.updateUI();

        super.onRefresh();
    }
}

