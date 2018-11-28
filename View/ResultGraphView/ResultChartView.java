package View.ResultGraphView;

import AEE.AdaptiveExamEngine;
import AEE.TestManager;
import AEE.TestResultStamp;
import Controller.Test.TestController;
import View.UI.Renderable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ResultChartView.java
 * View to display the three graphs
 *
 * Stephen Fleming 100963909
 */
public class ResultChartView implements Renderable {
    private JPanel panelContent;

    private JPanel panelBar;
    private JPanel panelLine;
    private JPanel panelPie;
    private JPanel panHeader;
    private JPanel panelBarContainer;

    private HashMap<String, TestController> tcMap;

    //x&y size of each graph
    private final int chartWidth = 150;
    private final int chartHeight = 150;

    private ArrayList<TestResultStamp> results;
    private TestResultStamp total;

    public ResultChartView() {
        tcMap = TestManager.getInstance().getTestMap();

        results = TestManager.getInstance().getResultList();
        total = TestManager.getInstance().getFinalTestResults();

        generateGraphs();
    }

    @Override
    public JPanel getRenderPanel() {
        return panelContent;
    }

    /**
     * Generate a bar chart
     * @return View containing the chart
     */
    private JPanel generateBarChart() {
        ChartView a = new ChartView();
        a.loadChartIntoView(new BarChart(chartWidth, chartHeight, total.correct, total.incorrect));

        JPanel ret = a.getRenderPanel();
        ret.setToolTipText("BLUE: Correct answers ("+total.correct+") RED: Incorrect answers ("+total.incorrect+")");

        return a.getRenderPanel();
    }

    /**
     * Generate a line chart
     * @return View containing the chart
     */
    private JPanel generateLineChart() {
        ChartView a = new ChartView();
        a.loadChartIntoView(new LineChart(chartWidth, chartHeight, total.correct, total.incorrect, results));

        JPanel ret = a.getRenderPanel();
        ret.setToolTipText("RED: Incorrect answer BLUE: Correct answer GREEN: Final answer");

        return a.getRenderPanel();
    }

    /**
     * Generate a Pie chart
     * @return View containing the chart
     */
    private JPanel generatePieChart() {
        ChartView a = new ChartView();
        a.loadChartIntoView(new PieChart(chartWidth, chartHeight, total.correct, total.incorrect));

        JPanel ret = a.getRenderPanel();
        ret.setToolTipText("BLUE: Correct answers ("+total.correct+") RED: Incorrect answers ("+total.incorrect+")");

        return a.getRenderPanel();
    }

    /**
     * Create the graphs, load them into their panels, refresh the UI
     */
    private void generateGraphs() {
        panelBar.add(generateBarChart());
        panelLine.add(generateLineChart());
        panelPie.add(generatePieChart());

        panelContent.updateUI();
        AdaptiveExamEngine.getUiController().refreshViews();
    }
}
