package View.ResultGraphView;

import View.UI.Renderable;

import javax.swing.*;

/**
 * CharView.java
 * View container for a chart
 *
 * Stephen Fleming 100963909
 */
public class ChartView implements Renderable {
    private JPanel panelContent;
    private JPanel panChrt;

    public ChartView() { }

    @Override
    public JPanel getRenderPanel() {
        return panelContent;
    }

    /**
     * Load a graph into the view
     * @param g
     */
    public void loadChartIntoView(Graph g) {
        panChrt.add(g);

        panelContent.updateUI();
    }
}

