package View;

import AEE.AdaptiveExamEngine;
import AEE.Callbacks.ViewLifecycle;
import View.UI.Renderable;

import javax.swing.*;
import java.util.ArrayList;

/**
 * View.java
 * Base class for Views
 *
 * Manages View lifecycle related events and callbacks
 */
public class View implements Renderable {
    private ArrayList<ViewLifecycle> viewLifecycleCallbacks;

    public View() {
        viewLifecycleCallbacks = new ArrayList<ViewLifecycle>();
    }

    /**
     * From Renderable, return render panel
     */
    public JPanel getRenderPanel() { return null; }

    /**
     * Called after View is created
     */
    public void onCreate() {viewLifecycleCallbacks.forEach(cb -> cb.onCreate()); }

    /**
     * Called when View is loaded into a container (user is seeing view)
     */
    public void onLoad() {viewLifecycleCallbacks.forEach(cb -> cb.onLoad()); }

    /**
     * Called when view needs to update
     */
    public void onRefresh() {
        //
        viewLifecycleCallbacks.forEach(cb -> cb.onRefresh());
    }

    /**
     * Called when View is leaving container
     */
    public void onClose() {viewLifecycleCallbacks.forEach(cb -> cb.onClose()); }

    /**
     * Register a new Lifecycle callback event
     * @param cb
     */
    public void registerCallback(ViewLifecycle cb) {
        viewLifecycleCallbacks.add(cb);
    }

    /**
     * Refresh the UI
     */
    public void refresh() {
        AdaptiveExamEngine.getUiController().refreshViews();
    }
}
