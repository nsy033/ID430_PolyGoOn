 package PGO;

import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class PGO extends XApp {
    public static final int SLIDER_HEIGHT = 40;
    
    private JFrame mFrame = null;
    public JFrame getFrame() {
        return this.mFrame;
    }
    private Ellipse2D mDeleteArea = null;
    public Ellipse2D getDeleteArea() {
        return this.mDeleteArea;
    }
    public void setDeleteArea(Ellipse2D area) {
        this.mDeleteArea = area;
    }
    
    private PGOCanvas2D mCanvas2D = null;
    public PGOCanvas2D getCanvas2D() {
        return this.mCanvas2D;
    }
    
    private PGOEventListener mEventListener = null;
    public PGOEventListener getEventListener() {
        return this.mEventListener;
    }
    private PGODragListener mDragListener = null;
    public PGODragListener getDragListener() {
        return this.mDragListener;
    }
    private PGOChangeListener mHueChangeListener = null;
    public PGOChangeListener getHueChangeListener() {
        return this.mHueChangeListener;
    }
    private PGOChangeListener mSatChangeListener = null;
    public PGOChangeListener getSatChangeListener() {
        return this.mSatChangeListener;
    }
    private PGOChangeListener mBriChangeListener = null;
    public PGOChangeListener getBriChangeListener() {
        return this.mBriChangeListener;
    }
    public PGOChangeListener getChangeListener() {
        return this.mHueChangeListener;
    }
    
    private PGOPolygonMgr mPolygonMgr = null;
    public PGOPolygonMgr getPolygonMgr() {
        return this.mPolygonMgr;
    }
    
    private XScenarioMgr mScenarioMgr = null;
    @Override
    public XScenarioMgr getScenarioMgr() {
        return this.mScenarioMgr;
    }
    
    private XLogMgr mLogMgr = null;
    @Override
    public XLogMgr getLogMgr() {
        return this.mLogMgr;
    }
    
    private PGOCalcMgr mCalcMgr = null;
    public PGOCalcMgr getCalcMgr() {
        return this.mCalcMgr;
    }
    private PGOPanelMgr mPanelMgr = null;
    public PGOPanelMgr getPanelMgr() {
        return this.mPanelMgr;
    }
    
    //constructor
    public PGO() {
        // create components
        // 1. frmae 2. canvas 3. other components
        // 4. event listeners 5. managers (except panelMgr)
        this.mFrame = new JFrame("Poly-Go-On");
        this.mCanvas2D = new PGOCanvas2D(this);
        this.mEventListener = new PGOEventListener(this);
        this.mDragListener = new PGODragListener(this);
        this.mHueChangeListener = new PGOChangeListener(this);
        this.mSatChangeListener = new PGOChangeListener(this);
        this.mBriChangeListener = new PGOChangeListener(this);
        this.mPolygonMgr = new PGOPolygonMgr();
        this.mScenarioMgr = new PGOScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mCalcMgr = new PGOCalcMgr(this);
                
        // connect event listeners
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.addKeyListener(this.mEventListener);
        this.mCanvas2D.setFocusable(true);
                
        // build and show visible components
        this.mCanvas2D.setOpaque(false);
        this.mFrame.add(this.mCanvas2D, BorderLayout.CENTER);

        this.mPanelMgr = new PGOPanelMgr(this);
        
        this.mFrame.setSize(800, 600);
        this.mFrame.setLocationRelativeTo(null);
        this.mFrame.setResizable(false);
        this.mFrame.setVisible(true);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new PGO();
    }
    
    public Point findNearPt(Point pt) {
        ArrayList<Point> fixedPts = this.mPolygonMgr.getFixedPts();
        for (Point fixedPt : fixedPts) {
            if (pt.distance(fixedPt) < 10.0) {
                pt.x = fixedPt.x;
                pt.y = fixedPt.y;
                break;
            }
        }
        return pt;
    }
    
    private final static int VIBRATION_LENGTH = 10;
    private final static int VIBRATION_VELOCITY = 5;
  
    public void vibrate() {
        final int originalX = this.mFrame.getLocationOnScreen().x; 
        final int originalY = this.mFrame.getLocationOnScreen().y; 
        for(int i = 0; i < VIBRATION_LENGTH; i++) { 
            try {
                Thread.sleep(5);
                this.mFrame.setLocation(originalX, originalY + VIBRATION_VELOCITY);
                Thread.sleep(5);
                this.mFrame.setLocation(originalX, originalY - VIBRATION_VELOCITY);
                Thread.sleep(5);
                this.mFrame.setLocation(originalX + VIBRATION_VELOCITY, originalY);
                Thread.sleep(5); 
                this.mFrame.setLocation(originalX, originalY);
            } catch (InterruptedException ex) {
                Logger.getLogger(PGO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
