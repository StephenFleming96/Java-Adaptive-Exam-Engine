package View.ResultGraphView;

import javax.swing.*;
import java.awt.*;

/**
 * Graph.java
 * Base class for graphs.
 * Instantiate with ideal width and height, and correct and incorrect answers
 *
 * Stephen Fleming 100963909
 */
public class Graph extends JComponent {
    protected Graphics2D g2d;

    protected int w;
    protected int h;

    protected int correct;
    protected int incorrect;

    protected float scale;
    protected float percentCorrect;
    protected float percentIncorrect;

    protected Color blue;
    protected Color red;

    /**
     * Base for a Graph.
     * @param x Width
     * @param y Height
     * @param cor Correct answers
     * @param incor Incorrect answers
     */
    public Graph(int x, int y, int cor, int incor) {
        setSize(new Dimension(x, y));
        setPreferredSize(new Dimension(x,y));

        w = getWidth();
        h = getHeight();

        correct = cor;
        incorrect = incor;

        scale = correct + incorrect;
        percentCorrect = (correct / scale);
        percentIncorrect = (incorrect / scale);

        blue = CustomColours.getBlue();
        red = CustomColours.getRed();

        repaint();
    }
}
