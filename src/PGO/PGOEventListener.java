package PGO;

import PGO.sceanrio.PGODeleteScenario;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PGOEventListener implements MouseListener, MouseMotionListener,
    KeyListener {
    // constant
    private static long PRESS_DELAY = 1000;
    
    // fields
    private PGO mPGO = null;
    private Long mMousePressedTime = null;
    private Point mMousePressedPt = null;
    
    // constructor
    public PGOEventListener(PGO pgo) {
        this.mPGO = pgo;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        this.mMousePressedTime = e.getWhen();
        this.mMousePressedPt = e.getPoint();
        curScene.handleMousePress(e);
        this.mPGO.getCanvas2D().repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        PGOScene curScene =
            (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        //curScene.handleMouseDrag(e);
        if (this.mMousePressedTime != null) {
            if (e.getWhen() - this.mMousePressedTime > PRESS_DELAY) {
                if (curScene == PGODeleteScenario.DeleteReadyScene.getSingleton()) {
                    PGODeleteScenario.DeleteReadyScene.getSingleton().handleMouseLongPress(this.mMousePressedPt, e.getPoint());
                }
            }
        }
        curScene.handleMouseDrag(e);
        this.mPGO.getCanvas2D().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mMousePressedTime = null;
        this.mMousePressedPt = null;
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
