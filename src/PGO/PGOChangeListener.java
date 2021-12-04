package PGO;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PGOChangeListener implements ChangeListener {
    // field
    private PGO mPGO = null;

    public PGOChangeListener(PGO pgo) {
        this.mPGO = pgo;
    }
        
    @Override
    public void stateChanged(ChangeEvent e) {
          PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
          curScene.handleChange(e);
    }
    
}
