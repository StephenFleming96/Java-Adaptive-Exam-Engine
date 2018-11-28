package View.Core;

import AEE.AdaptiveExamEngine;
import AEE.Timer.TimerType;
import AEE.TimerManager;
import Model.User.User;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PINGenerationWindow.java
 * Displays the newly generated PIN to the user
 *
 * Stephen Fleming 100963909
 */
public class PINGenerationWindow extends View {
    private JPanel contentPanel;
    private JTextPane generatedPinField;
    private JButton nextButton;
    private JLabel labWelcomeStudent;

    public PINGenerationWindow() {
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
    }

    @Override
    public JPanel getRenderPanel() {
        return contentPanel;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //set text fields with values
        User user = AdaptiveExamEngine.getUserController().getCurrentUser();
        generatedPinField.setText(user.PIN);
        labWelcomeStudent.setText("Hello " + user.userName + ",");
    }

    @Override
    public void onClose() {
        super.onClose();

        AdaptiveExamEngine.activateMainScreenPage("TestOptionWindow");
        TimerManager.getInstance().startTimer(TimerType.MAIN);
    }
}
