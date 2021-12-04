package x;

import java.util.ArrayList;

public abstract class XScenario {
    // fields
    protected XApp mApp = null;
    public XApp getApp() {
        return this.mApp;
    }
    protected ArrayList<XScene> mScenes = null;
    
    // constructor
    protected XScenario(XApp app) {
        this.mApp = app;
        this.mScenes = new ArrayList<XScene>();
        this.addScenes();
    }
    
    // abstract method
    protected abstract void addScenes();
    
    // concrete method
    protected void addScene(XScene scene) {
        this.mScenes.add(scene);
    }
}
