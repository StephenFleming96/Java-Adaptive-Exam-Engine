package View.ResultGraphView;

import AEE.TestResultStamp;
import AEE.Timer.TimerValueContainer;

import java.awt.*;
import java.util.ArrayList;

/**
 * LineChart.java
 * Child of graph which displays answers over time.
 *
 * Stephen Fleming 100963909
 */
public class LineChart extends Graph {
    private ArrayList<TestResultStamp> results;

    private final int GRID_SIZE = 10;
    private final int GRAPH_LABELS = 3;
    private final int NODE_SIZE = 6;

    //how detialed the node graph is (lower = more cluttered map)
    private final int GRAPH_NODE_SCALE = 1;

    //total number of questions answered
    private int finalScore;

    //time (second) at which the first question was answered (e.g. equal to 14 mins x seconds)
    private int firstAnswerSecond;

    //time (second) at which last question as answered (anything in range of 0m to 15m)
    private int lastAnswerSecond;

    //range between first and last answer
    private int answerTimeRange;

    private int maxNodeRight;
    private int maxNodeBottom;

    public LineChart(int x, int y, int cor, int incor, ArrayList<TestResultStamp> stampList) {

        super(x, y, cor, incor);

        results = stampList;

        firstAnswerSecond = Integer.MIN_VALUE;
        lastAnswerSecond = Integer.MAX_VALUE;

        for (TestResultStamp result : results) {
            if (result.timeStamp.toSecondsTotal() < lastAnswerSecond)
                lastAnswerSecond = result.timeStamp.toSecondsTotal();

            if (result.timeStamp.toSecondsTotal() > firstAnswerSecond)
                firstAnswerSecond = result.timeStamp.toSecondsTotal();

        }

        answerTimeRange = firstAnswerSecond - lastAnswerSecond;

        finalScore = cor + incor;

        maxNodeRight = w-NODE_SIZE;
        maxNodeBottom = h-NODE_SIZE;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g2d = (Graphics2D)g;

        //paint the graph
        drawGrid();
        drawGridNodes();
        drawGridLabels();
    }

    /**
     * Draw the grid background
     */
    private void drawGrid() {
        int gridStep = (int)(w / (GRID_SIZE*1f));

        //draw the background
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //draw the grid
        g2d.setColor(Color.black);
        for (int i = 0; i <= w; i+=gridStep) {
            g2d.drawLine(0, i, w, i);
            g2d.drawLine(i, 0, i, h);
        }
    }

    /**
     * Draw the time stamp labels on the graph
     */
    private void drawGridLabels() {
        String timeStr;
        int mins;
        float pcComplete = 0f;

        g2d.setColor(Color.black);
        for (int i = GRAPH_LABELS; i >= 0; i--) {
            pcComplete = (i / (GRAPH_LABELS*1f));

            //will be equal to one third of total test time (lower limit 1 min ea)
            mins = (int)((firstAnswerSecond - (answerTimeRange * pcComplete))/60);

            timeStr = mins + "m";

            g2d.drawString(timeStr, (w * pcComplete), h-1 );
        }
    }

    /**
     * Draw the answer points on the graph
     */
    private void drawGridNodes() {
        int[] stCoords, lastCoords = null;
        int nodeSizeFixed = (NODE_SIZE / 2);

        TestResultStamp tmp;
        for (int i = 0; i < results.size(); i += GRAPH_NODE_SCALE) {
            tmp = results.get(i);

            stCoords = getCoordsForStamp(i, tmp.timeStamp);

            //create line to previous node if exists
            if (lastCoords != null) {
                g2d.setColor(Color.black);
                g2d.drawLine(stCoords[0] + nodeSizeFixed, stCoords[1]+nodeSizeFixed,
                        lastCoords[0]+nodeSizeFixed, lastCoords[1]+nodeSizeFixed);
            }

            //draw current node, correct answers blue
            if (tmp.correct == 1)
                g2d.setColor(blue);
            else
                g2d.setColor(red);

            //if last node to be displayed, make green
            if ((i+GRAPH_NODE_SCALE) >= results.size())
                g2d.setColor(Color.green);

            g2d.fillOval(stCoords[0], stCoords[1], NODE_SIZE,NODE_SIZE);

            //set last coords, for line
            lastCoords = stCoords;
        }
    }

    /**
     * Take a score and a time, return the graph coords for the score
     * @param qNumber The current question index
     * @param time Answer timestamp
     * @return int array with [x,y] coords
     */
    private int[] getCoordsForStamp(int qNumber, TimerValueContainer time) {
        int qTimeToSeconds = time.toSecondsTotal();

        // (time in secs - lowest time in secs) / (time range in secs)
        float timePc = (qTimeToSeconds - lastAnswerSecond) / (1f*answerTimeRange);

        //x position
        float xFloat = (maxNodeRight * (1f-timePc));

        return new int[] {(int)xFloat, scoreToHeight(qNumber)};
    }

    /**
     * @param qNumber Index of the current question
     * @return The graph height of the score
     */
    private int scoreToHeight(int qNumber) {
        //convert current score to percentage of final score
        float scoreAsPc = ((qNumber * 1f) / finalScore);

        //multiply graph height by score percent = score y position
        float yFloat = (maxNodeBottom * (1f-scoreAsPc));

        return (int)yFloat;
    }
}
