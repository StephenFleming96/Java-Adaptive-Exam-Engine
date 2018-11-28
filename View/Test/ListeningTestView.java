package View.Test;

import AEE.Audio.AudioManager;
import AEE.Timer.TimerType;
import AEE.Timer.TimerValueContainer;
import AEE.TimerManager;
import Model.Test.ListeningTestModel;
import Model.Test.TestDataContainer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ListeningTestView.java
 * View for listening test, user must correctly select keywords from recording.
 *
 * Stephen Fleming 100963909
 */
public class ListeningTestView extends TestView {
    private class KeywordCheckboxContainer {
        public JCheckBox checkBox;
        public boolean isCorrectAnswer;
    }

    private JPanel panelContent;
    private JPanel panelPlay;
    private JPanel panelKeywords;
    private JButton buttonPlayAudio;
    private JButton buttonNext;

    private ListeningTestModel.ListeningTestData currentQuestion;

    private ArrayList<KeywordCheckboxContainer> checkboxes;

    public ListeningTestView() {
        checkboxes = new ArrayList<KeywordCheckboxContainer>();

        //play recording
        buttonPlayAudio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.getInstance().playCurrentFile();
            }
        });

        //validate response
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAnswerComplete();
            }
        });
    }

    @Override
    public JPanel getRenderPanel() {
        return panelContent;
    }


    @Override
    public void loadQuestionData(TestDataContainer d) {
        currentQuestion = (ListeningTestModel.ListeningTestData) d;

        if (d == null)
            return;

        AudioManager.getInstance().loadAudioFile(currentQuestion.file);

        generateKeywordCheckboxes();
        prepareFormForData();
    }

    @Override
    public void onClose() {
        onComplete(TimerManager.getInstance().getTimerValues(TimerType.TESTING));

        super.onClose();
    }

    @Override
    public void onComplete(TimerValueContainer time) {
        checkboxes.forEach(chk -> { chk.checkBox.setEnabled(false); });
    }

    @Override
    public void onAnswerComplete() {
        if (validateResult())
            onCorrectAnswer();
        else
            onIncorrectAnswer();

        super.onAnswerComplete();
    }

    @Override
    public void lockNextButton() {
        buttonNext.setEnabled(false);
        super.lockNextButton();
    }

    /**
     * Test the result
     * @return true if all correct
     */
    private boolean validateResult() {
        for (KeywordCheckboxContainer k : checkboxes) {
            if (k.isCorrectAnswer && !k.checkBox.isSelected())
                return false;
        }

        return true;
    }

    private void enableNextButton() {
        buttonNext.setEnabled(true);
    }

    private void prepareFormForData() {
        buttonNext.setEnabled(false);
        panelKeywords.removeAll();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel row;

        for (int i = 0; i < checkboxes.size(); i++) {
            row = new JPanel();

            for (int j = i; j < (i + 3); j++) {
                if (j < checkboxes.size()) {
                    row.add(checkboxes.get(i).checkBox);
                    checkboxes.get(i).checkBox.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            enableNextButton();
                        }
                    });
                }
            }

            container.add(row);
         }

         panelKeywords.add(container);

         refresh();
    }

    /**
     * generate checkboxes for the keywords
     */
    private void generateKeywordCheckboxes() {
        panelKeywords.removeAll();

        checkboxes.clear();

        ArrayList<String> keywords = new ArrayList<String>();
        KeywordCheckboxContainer k;
        JCheckBox c;

        //check checkboxes for correct keywords
        for (String s : currentQuestion.keywords) {
            c = new JCheckBox();
            c.setText(s);

            k = new KeywordCheckboxContainer();
            k.checkBox = c;
            k.isCorrectAnswer = true;

            checkboxes.add(k);
        }

        //create checkboxes for incorrect keywords
        for (String s : currentQuestion.incorrectWords) {
            c = new JCheckBox();
            c.setText(s);

            k = new KeywordCheckboxContainer();
            k.checkBox = c;
            k.isCorrectAnswer = false;

            checkboxes.add(k);
        }

        //shuffle list  source: https://stackoverflow.com/questions/16112515/how-to-shuffle-an-arraylist
        Collections.shuffle(checkboxes);
        //end source
    }
}
