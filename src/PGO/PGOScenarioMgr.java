package PGO;
import PGO.sceanrio.*;
import x.XScenarioMgr;

public class PGOScenarioMgr extends XScenarioMgr {
    // constructor
    public PGOScenarioMgr(PGO pgo) {
        super(pgo);
    }

    @Override
    protected void addScenarios() {
        this.mScenarios.add(PGOStartScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGODefaultScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGOCreatePolygonScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGODeleteScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGODeformScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGOColorScenario.createSingleton(this.mApp));
        this.mScenarios.add(PGOSaveScenario.createSingleton(this.mApp));
    }

    @Override
    protected void setInitCurScene() {
        // this.setCurScene(PGODefaultScenario.ReadyScene.getSingleton());
        this.setCurScene(PGOStartScenario.ImageReadyScene.getSingleton());
    }
    
}
