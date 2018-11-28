package View.UI;

import javax.swing.*;

/**
 *  Renderable.java
 *  Contains required methods for views to be rendered.
 *
 *  Stephen Fleming 100963909
 */
public interface Renderable {

    /**
     * Get the panel for this component that will be rendered
     * @return Panel to be rendered, usually top level JFrame
     */
    JPanel getRenderPanel();
}
