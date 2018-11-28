package View.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import View.View;

/**
 * WindowContainer.java
 * This class encapsulates a JFrame and related behaviour.
 * It is the parent class for the Main and Testing windows.
 *
 * Stephen Fleming 100963909
 */
public class WindowContainer {
    public static final int WINDOW_X = 400;
    public static final int WINDOW_Y = 0;

    protected JFrame window;

    protected JPanel renderPanel;   // Parent panel, contains header and content
    protected JPanel headerPanel;   // Header section
    protected JPanel contentPanel;  // Content section

    protected View headerContent;
    protected View mainContent;

    public WindowContainer(String name, int onClose, boolean visible) {
        window = new JFrame(name);

        window.setSize(WINDOW_X, WINDOW_Y);
        window.setDefaultCloseOperation(onClose);

        centreWindow(window);

        window.setVisible(visible);

        setWindowListeners();
    }

    /**
     * Set content for main section
     * @param content
     */
    public void setMainContent(View content) {
        mainContent = content;

        content.onLoad();

        loadViewIntoContentSection(content.getRenderPanel());
    }

    public void setHeaderContent(View header) {
        headerContent = header;
        loadViewIntoHeaderSection(header.getRenderPanel());
    }

    public void setTimer(int minutes, int seconds) { }

    /**
     * Set window to visible
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * Set window to not visible
     */
    public void hide() {
        window.setVisible(false);
    }

    /**
     * Call on views to reload.
     */
    public void refreshPanels() {
        if (headerContent != null) {
            headerContent.onRefresh();
        }

        if (mainContent != null) {
            mainContent.onRefresh();
        }

        window.pack();
    }

    /**
     * Called whenever content is loaded into window.
     * Can be overridden by child-classes
     */
    protected void onLoad() {
        window.pack();
        centreWindow(window);
    }

    /**
     * call for the window to close
     */
    protected void closeWindow() {
        //https://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        window.dispose();
    }

    protected void loadViewIntoHeaderSection(JPanel view) {
        loadContentIntoPanel(headerPanel, view);
    }

    /**
     * Take a view and load it into the content section of the window.
     * @param view  View, in the form of a JPanel.
     */
    protected void loadViewIntoContentSection(JPanel view) {
        if (contentPanel == null)
            System.out.println("ERROR: NO CONTENT PANEL");
        else if (view == null)
            System.out.println("ERROR NO VIEW");

        loadContentIntoPanel(contentPanel, view);
    }

    /**
     * Called when window close requested
     */
    protected void onClose() { }

    /**
     * Empty a panel, replace it's content, update UI.
     * Triggers onLoad
     *
     * @param panelToFill   The panel that will be filled
     * @param content   The panel that will be placed in panelToFill
     */
    private void loadContentIntoPanel(JPanel panelToFill, JPanel content) {
        panelToFill.removeAll();
        panelToFill.add(content);
        panelToFill.updateUI();

        this.onLoad();
    }

    /**
     * Move window to centre of screen
     * source: stackoverflow.com/questions/144892/how-to-center-a-window-in-java
     *
     * @param frame The window that will be moved
     */
    private void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);

        frame.setLocation(x, y);
    }

    /**
     * create listner to trigger onClose
     */
    private void setWindowListeners() {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();

                super.windowClosing(e);
            }
        });
    }
}
