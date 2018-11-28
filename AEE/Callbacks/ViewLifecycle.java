package AEE.Callbacks;

/**
 * TestLifecycle.java
 * Events related to a tests lifecycle
 *
 * Stephen Fleming 100963909
 */
public interface ViewLifecycle {
    void onCreate();
    void onLoad();
    void onRefresh();
    void onClose();
}
