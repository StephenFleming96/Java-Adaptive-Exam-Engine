package View.ResultGraphView;

import java.awt.*;

/**
 * PieChart.java
 * Child of Graph which draws a pie chart based on correct and incorrect results
 *
 * Stephen Fleming 100963909
 */
public class PieChart extends Graph {
    public PieChart(int x, int y, int cor, int incor) {
        super(x, y, cor,incor);
    }

    /**
     * Draw a pie chart. Blue or percent correct, red = percent incorrect
     * @param
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g2d = (Graphics2D)g;

        int diameter; // ideal diameter (width or height, whichever smaller)
        if (w > h) {
            diameter = h;
        } else {
            diameter = w;
        }

        //draw incorrect circle first
        //source: http://cs111.wellesley.edu/~cs111/archive/cs111_fall06/public_html/labs/lab12/arc.html
        g2d.setColor(red);
        g2d.fillOval(0, 0, diameter, diameter);

        //draw correct arc
        g2d.setColor(blue);
        g2d.fillArc(0, 0, diameter, diameter, 90, (int)(360 * percentCorrect));
        //end source
    }
}
