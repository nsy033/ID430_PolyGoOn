package PGO;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PGOEventListener implements MouseListener, MouseMotionListener,
    KeyListener {
    // field
    private PGO mPGO = null;
    
    // constructor
    public PGOEventListener(PGO pgo) {
        this.mPGO = pgo;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        PGOScene curScene =
            (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleMousePress(e);
        this.mPGO.getCanvas2D().repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        PGOScene curScene =
            (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleMouseDrag(e);
        this.mPGO.getCanvas2D().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        PGOScene curScene =
            (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleMouseRelease(e);
        this.mPGO.getCanvas2D().repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleKeyDown(e);
        this.mPGO.getCanvas2D().repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleKeyUp(e);
        this.mPGO.getCanvas2D().repaint();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
