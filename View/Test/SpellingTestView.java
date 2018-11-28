package View.Test;

import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;
import Model.Test.SpellingTestModel;
import Model.Test.TestDataContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * SpellingTestView.java
 * The view file for Spelling Tests.
 *
 * Stephen Fleming 100963909
 */
public class SpellingTestView extends TestView {
    private JLabel wordTmp;

    private JPanel panContent;
    private JPanel testingForm;
    private JPanel wordHintPanel;
    private JButton btnNext;
    private JLabel labelWordToSpell;

    private JTextField[] inputBoxes;
    private int currentLetter;
    private int hintIndex;

    private SpellingTestModel.SpellingTestWordData currentQuestion;

    public SpellingTestView() {
        currentLetter = 0;
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testResult();
            }
        });
    }

    @Override
    public void loadQuestionData(TestDataContainer d) {
        if (d == null) {
            btnNext.setEnabled(false);

            for(JTextField t : inputBoxes){
                t.setEnabled(false);
            }

            labelWordToSpell.setText("");
            labelWordToSpell.setEnabled(false);

            wordTmp.setText("Test completed, click Done to return to the menu...");

            onComplete(TimerManager.getInstance().getTimerValues(TimerType.TESTING));

            return;
        }

        currentQuestion = (SpellingTestModel.SpellingTestWordData)d;

        prepareFormForTest();

        //set the first letter as a hint
        hintIndex = 0;
        giveHint(hintIndex);

        TimerManager.getInstance().setTimerValues(TimerType.TESTING, 0, 20);
    }

    /**
     * @return View element to be rendered.
     */
    @Override
    public JPanel getRenderPanel() {
        return panContent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * Called every time the view is opened.
     * In case user closes and re-opens the test, the values must be resetTimer.
     */
    @Override
    public void onLoad() {
        super.onLoad();
        //focusOnBox(1);

        currentLetter = 1;
        focusOnBox(currentLetter);
    }

    @Override
    public void onClose() {
        onComplete(TimerManager.getInstance().getTimerValues(TimerType.TESTING));
    }

    @Override
    public void onComplete(TimerValueContainer time) {
        super.onComplete(time);
    }

    @Override
    public void onAnswerComplete() {
        super.onAnswerComplete();
    }

    @Override
    public void onCorrectAnswer() {
        super.onCorrectAnswer();
        super.onAnswerComplete();
    }

    @Override
    public void onIncorrectAnswer() {
        super.onIncorrectAnswer();
        super.onAnswerComplete();
    }

    @Override
    public void lockNextButton() {
        btnNext.setEnabled(false);

        super.lockNextButton();
    }

    /**
     * test given answer for correctness, trigger relevent events
     */
    private void testResult(){
        if (validateResult()) {
            onCorrectAnswer();
        } else {
            if (hintIndex < 2)
                giveHint(++hintIndex);
            else
                onIncorrectAnswer();
        }
    }

    /**
     * display next hint
     * @param charIndex index of letter
     */
    private void giveHint(int charIndex) {
        inputBoxes[charIndex].setText("" + currentQuestion.getWord().charAt(charIndex));
        inputBoxes[charIndex].setEditable(false);

        inputBoxes[charIndex].setBackground(Color.LIGHT_GRAY);

        focusOnBox(hintIndex);
    }

    /**
     * Test the given answer for correctness
     * @return true if answer is correct
     */
    private boolean validateResult() {
        String expected = currentQuestion.getWord().toUpperCase();
        String fullRes = "";

        for (JTextField b : inputBoxes) {
            //if not all boxes are filled, answer is false
            if (b.getText().length() == 0)
                return false;

            fullRes += b.getText().charAt(0);;
        }

        fullRes = fullRes.toUpperCase();

        for (int i = 0; i < fullRes.length(); i++) {
            if (!(fullRes.charAt(i) == expected.charAt(i)))
                return false;
        }

        return true;
    }

    /**
     * Set the window focus to the input box with index i.
     * @param index Index of a JTextField in inputBoxes.
     */
    private void focusOnBox(int index) {
        int boxToFoxus = index;
        if (index < inputBoxes.length) {
            if (index <= hintIndex)
                boxToFoxus = hintIndex + 1;

            inputBoxes[boxToFoxus].requestFocusInWindow();
            inputBoxes[boxToFoxus].setText("");

            currentLetter = boxToFoxus;
        }
    }

    /**
     * Clear the array of input boxes
     */
    private void resetInputBoxes() {
        testingForm.removeAll();

        if (inputBoxes != null) {
            for (JTextField t : inputBoxes) {
                t = null;
            }
        }

        inputBoxes = new JTextField[currentQuestion.getWord().length()];

    }

    /**
     * Create dynamically loaded content, such as text fields.
     * Add relevent event listners.
     */
    private void prepareFormForTest() {
        String word = currentQuestion.getWord();

        //display the word hint
        wordTmp.setText(currentQuestion.getHint());

        resetInputBoxes();

        //display the testing form
        for (int i = 0; i < word.length(); i++) {
            if (inputBoxes[i] == null) {
                JTextField newLetter = new JTextField(1);

                inputBoxes[i] = newLetter;

                testingForm.add(newLetter);

                //move to next box when key is pressed
                //source:  java2s.com/Tutorial/Java/0260__Swing-Event/HowtoWriteaKeyListener.htm
                inputBoxes[i].addKeyListener(new KeyListener() {

                    //key down
                    @Override
                    public void keyTyped(KeyEvent e) { }

                    //key active
                    @Override
                    public void keyPressed(KeyEvent e) { }

                    //key up
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {

                            //limit to one char
                            String curInput = inputBoxes[currentLetter].getText();
                            if (curInput.length() > 0) {
                                curInput = ("" + curInput.charAt(0));

                                //convert to upper case
                                curInput = curInput.toUpperCase();

                                //re-fill content of input box
                                inputBoxes[currentLetter].setText(curInput);

                                //focus on next box
                                focusOnBox(++currentLetter);
                            }
                        } else {
                            focusOnBox(--currentLetter);
                        }
                    }
                });
                //endsource
            }
        }
    }
}
