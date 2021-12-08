package PGO;

import PGO.sceanrio.PGODefaultScenario;
import PGO.sceanrio.PGODeleteScenario;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Robot;

public class PGOEventListener implements MouseListener, MouseMotionListener,
    KeyListener {
    // fields
    private PGO mPGO = null;
    private Point mMousePressedPt = null;
    public Point getMousePressedPt() {
        return this.mMousePressedPt;
    }
    private Point mMouseLastPt = null;
    public Point getMouseLastPt() {
        return this.mMouseLastPt;
    }
    private java.util.Timer mTimer = null;
    private Robot mRobot = null;
    
    // constructor
    public PGOEventListener(PGO pgo) {
        this.mPGO = pgo;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleMousePress(e);
        this.mMousePressedPt = e.getPoint();
        this.mMouseLastPt = e.getPoint();
        this.mPGO.getCanvas2D().repaint();
        
        if (curScene == PGODefaultScenario.ReadyScene.getSingleton()) {
            if (this.mTimer == null) {
                this.mTimer = new java.util.Timer();
            }
            this.mTimer.schedule(new TimerTask()
            {
                public void run()
                {
                    PGO pgo = (PGO) PGODeleteScenario.getSingleton().getApp();
                    PGOScene curScene = (PGOScene) pgo.getScenarioMgr().getCurScene();
                    if (curScene == PGODeleteScenario.DeleteReadyScene.getSingleton()) {
                        PGODeleteScenario.DeleteReadyScene.getSingleton().
                            handleMouseLongPress(
                                pgo.getEventListener().getMousePressedPt(),
                                pgo.getEventListener().getMouseLastPt());
                        pgo.getCanvas2D().repaint();
                    }
                }
            },600,500);
        }
        
        try {
            this.mRobot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        JPanel pgoCanvas = this.mPGO.getCanvas2D();
        this.mMouseLastPt = e.getPoint();
        
        Point newPt = this.mPGO.getCalcMgr().isValidPt(e.getPoint());
        
        PGOScene curScene =
            (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.handleMouseDrag(newPt);
        this.mPGO.getCanvas2D().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
            this.mMousePressedPt = null;
            this.mMouseLastPt = null;
        }
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
