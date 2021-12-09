package PGO;

import PGO.sceanrio.PGOStartScenario;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public class PGODragListener implements DropTargetListener {
    // field
    private PGO mPGO = null;
    
    // constructor
    public PGODragListener(PGO pgo) {
        this.mPGO = pgo;
    }
    
    @Override
    public void drop(DropTargetDropEvent ev) {
        // Get dropped img and add to label
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        if (curScene == PGOStartScenario.FileReadyScene.getSingleton()) {
            PGOStartScenario.FileReadyScene.getSingleton().handleDragDrop(ev);
        }
        this.mPGO.getCanvas2D().repaint();
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
}
