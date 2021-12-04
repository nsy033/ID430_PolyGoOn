package PGO;

import PGO.sceanrio.PGOStartScenario;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class PGODragListener implements DropTargetListener {
    private PGO mPGO = null;
    
    public PGODragListener(PGO pgo) {
        this.mPGO = pgo;
    }
    
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    
    // get droppted img and add to label
    @Override
    public void drop(DropTargetDropEvent ev) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        if (curScene == PGOStartScenario.ImageReadyScene.getSingleton()) {
            PGOStartScenario.ImageReadyScene.getSingleton().handleDragDrop(ev);
        }
        this.mPGO.getCanvas2D().repaint();
    }
}
