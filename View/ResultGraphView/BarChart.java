package View.ResultGraphView;

import java.awt.*;

/**
 * BarChart.java
 * Child of Graph which demonstrates correct vs incorrect results.
 *
 * Stephen Fleming 100963909
 */
public class BarChart extends Graph {
    public BarChart(int x, int y, int cor, int incor) {
        super(x, y, cor, incor);
    }

    /**
     * Draw a bar chart which shows total correct vs incorrect answers
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g2d = (Graphics2D)g;

        //determine left and right borders
        int left = w / 4;
        int right = w / 2;

        //determine total chart height
        int fixedH = h;

        //determine height of correct and incorrect sections
        int totalCorrect = (int)(fixedH * percentIncorrect);
        int totalIncorrect = fixedH - totalCorrect;

        //draw incorrect background
        g2d.setColor(red);
        g2d.fillRect(left, 0, right, fixedH);

        //draw correct answer percentage
        g2d.setColor(blue);
        g2d.fillRect(left, totalCorrect, right, totalIncorrect);

        //draw 10% line markers
        int rightBorder = right + left - 1;
        for (int i = 0; i < h; i+=(h/10)) {
            g2d.setColor(Color.black);
            g2d.drawLine(left + 1, i , rightBorder, i);
        }
    }
}
