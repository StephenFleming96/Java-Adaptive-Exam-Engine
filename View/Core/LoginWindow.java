package View.Core;

import AEE.AdaptiveExamEngine;
import Controller.Core.UserController;
import Model.User.InputValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.User.User;
import View.View;

/**
 * LoginWindow.java
 * Page where user can log into the system
 *
 * Stephen Fleming 100963909
 */
public class LoginWindow extends View {
    private JTextField studentIDTextField;
    private JTextField studentNameTextField;
    private JTextField studentSchoolTextField;

    private JCheckBox iAcceptTheTermsCheckBox;

    private JButton nextButton;
    private JPanel panLoginWindow;
    private JLabel labelAcceptTC;
    private JButton SKIPButton;

    public LoginWindow() {
        setErrorMessage("");

        //event listner for next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateUserCredentials();
            }
        });

        SKIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdaptiveExamEngine.getUserController().logUserIntoSystem("1234", "abcd", "abcd", true);
                AdaptiveExamEngine.activateMainScreenPage("PINGenerationWindow");
            }
        });
    }

    @Override
    public JPanel getRenderPanel() {
        return panLoginWindow;
    }

    /**
     * set the text for the input error feedback message
     */
    private void setErrorMessage(String error) {
        labelAcceptTC.setText(error);
        onRefresh();

        AdaptiveExamEngine.getUiController().refreshViews();
    }

    @Override
    public void onClose() {
        super.onClose();

        //move to next window
        AdaptiveExamEngine.activateMainScreenPage("PINGenerationWindow");
    }

    /**
     * Validate the inputs and display relevent error messages
     */
    private void validateUserCredentials() {
        UserController uCtrl = AdaptiveExamEngine.getUserController();

        //validate params and set error message
        if (!InputValidator.validUserId(studentIDTextField.getText())) {
            setErrorMessage("Error: ID should be 4-10 numbers, no spaces.");
            return;
        }

        if (!InputValidator.validUserName(studentNameTextField.getText())) {
            setErrorMessage("Error: Name should be 2-15 letters.");
            return;
        }

        if (!InputValidator.validUserSchool(studentSchoolTextField.getText())) {
            setErrorMessage("Error: School should be 2-15 letters");
            return;
        }

        //if t&c accepted, move to next page
        if (iAcceptTheTermsCheckBox.isSelected()) {
            //params are valid, log user in
            uCtrl.logUserIntoSystem(
                    studentIDTextField.getText(), studentNameTextField.getText(),
                    studentSchoolTextField.getText(), iAcceptTheTermsCheckBox.isSelected());

            onClose();
        } else {
            //display error message
            setErrorMessage("You must accept the terms and conditions!");
        }
    }
}
