package x;

public abstract class XScene {
    // fields
    protected XScenario mScenario = null;
    public XScenario getScenario() {
        return this.mScenario;
    }
    protected XScene mReturnScene = null;
    public XScene getReturnScene() {
        return this.mReturnScene;
    }
    public void setReturnScene(XScene scene) {
        this.mReturnScene = scene;
    }
    
    // constructor
    protected XScene(XScenario scenario) {
        this.mScenario = scenario;
        this.mReturnScene = null;
    }
    
    // abstract methods
    public abstract void getReady();
    public abstract void wrapUp();
}
