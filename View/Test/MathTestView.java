package View.Test;

import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;
import Model.Test.MathTestModel;
import Model.Test.TestDataContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MathTestView extends TestView {
    private JPanel panelContent;
    private JLabel labelQuestion;
    private JTextField fieldAnswer;
    private JButton buttonNextQuestion;
    private JLabel labelProgress;

    private MathTestModel.MathQuestionData currentQuestion;

    private int correct;
    private int total;

    private final float ANSWER_TOLERANCE = 0.1f;

    private boolean testIsComplete;

    public MathTestView() {
        testIsComplete = false;

        buttonNextQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAnswerComplete();
            }
        });

        fieldAnswer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    onAnswerComplete();
            }
        });
    }

    /**
     * @return View element to be rendered.
     */
    @Override
    public JPanel getRenderPanel() {
        return panelContent;
    }

    /**
     * Called every time the view is opened.
     */
    @Override
    public void onRefresh() {
        super.onRefresh();

        loadNextQuestion();

        fieldAnswer.setColumns(3);
        fieldAnswer.requestFocusInWindow();
    }

    @Override
    public void onClose() {
        onComplete(TimerManager.getInstance().getTimerValues(TimerType.TESTING));
        super.onClose();
    }

    @Override
    public void onComplete(TimerValueContainer time) {
        lockFields();
        super.onComplete(time);
    }

    /**
     * Increase correct answer count
     */
    @Override
    public void onCorrectAnswer() {
        correct++;

        currentQuestion.correct = true;

        super.onCorrectAnswer();
    }

    @Override
    public void loadQuestionData(TestDataContainer d) {
        if (d == null) {
            lockFields();
            testIsComplete = true;
        } else {
            currentQuestion = (MathTestModel.MathQuestionData) d;
        }
    }

    @Override
    public void onAnswerComplete() {
        //user has not answered yet or test is done
        if (testIsComplete || fieldAnswer.getText().length() == 0)
            return;

        //validate their response before loading next
        if (evaluateResult())
            onCorrectAnswer();
        else
            onIncorrectAnswer();

        //trip onAnswerComplete callback, which loads next question
        super.onAnswerComplete();

        total++;

        //prepare form with next q
        loadNextQuestion();
    }

    @Override
    public void lockNextButton() {
        buttonNextQuestion.setEnabled(false);
        super.lockNextButton();
    }

    /**
     * @return True if given answer is correct
     */
    private boolean evaluateResult() {
        return answerCloseEnough(Float.parseFloat(fieldAnswer.getText()), currentQuestion.result);
    }

    /**
     * TestModel if given answer is close enough to be accepted.
     * Close enough determined by ANSWER_TOLERANCE.
     * @param ans Given answer
     * @param expected Expected answer
     * @return true if close enough to accept.
     */
    private boolean answerCloseEnough(float ans, float expected) {
        if (ans > expected)
            return ((ans - expected) < ANSWER_TOLERANCE);
        else
            return ((expected - ans) < ANSWER_TOLERANCE);
    }

    private void lockFields() {
        buttonNextQuestion.setEnabled(false);
        fieldAnswer.setEnabled(false);
    }

    /**
     * Get the next question from the generator, load into view
     */
    private void loadNextQuestion() {
        String labelTxt = "" + currentQuestion.firstNum;
        switch (currentQuestion.qType) {
            case ADD:
                labelTxt += " + ";
                break;
            case SUBTRACT:
                labelTxt += " - ";
                break;
            case MULTIPLY:
                labelTxt += " * ";
                break;
            case DIVIDE:
                labelTxt += " / ";
                break;
            default:
                labelTxt += " ERR ";
        }

        labelQuestion.setText(labelTxt + " " + currentQuestion.secondNum + " = ");

        fieldAnswer.setText("");
        fieldAnswer.requestFocusInWindow();

        labelProgress.setText(correct + " of " + total + " correct");
    }
}
