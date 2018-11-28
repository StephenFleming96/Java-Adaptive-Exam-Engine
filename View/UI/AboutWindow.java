package View.UI;

import AEE.AdaptiveExamEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AboutWindow.java
 * Window container for about menu popup
 *
 * Stephen Fleming 100963909
 */
public class AboutWindow extends WindowContainer {
    private JPanel panelMainWindow;
    private JPanel panelHeader;
    private JPanel panelContent;
    private JButton closeButton;

    public AboutWindow() {
        super("About", JFrame.HIDE_ON_CLOSE, false);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdaptiveExamEngine.getUiController().hideAboutWindow();
            }
        });

        headerPanel = panelHeader;
        contentPanel = panelContent;
        renderPanel = panelMainWindow;

        window.setContentPane(renderPanel);

        refreshPanels();
        panelMainWindow.updateUI();
        window.pack();

    }
}
