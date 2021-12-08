package PGO;

import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

import PGO.sceanrio.PGOStartScenario;
import x.XApp;
import x.XLogMgr;
import x.XScenarioMgr;

public class PGO extends XApp {
    // constants
    public static final int DEFAULT_WINDOW_WIDTH = 800;
    public static final int DEFAULT_WINDOW_HEIGHT = 600;
    public static final int DELTA_WINDOW_HEIGTH_MIN = 50;
    public static final int DELTA_WINDOW_HEIGTH_MAX = 120;
    public static final int TEXT_LABEL_HEIGHT = 250;
    public static final int SLIDER_HEIGHT = 29;
    public static final int SLIDER_WIDTH = 280;
    public static final int MIN_HEIGHT = 300;
    public static final int MIN_WIDTH = 400;
    public static final int EMPTY_BORDER = -5;

    // fields
    private JFrame mFrame = null;
    public JFrame getFrame() {
        return this.mFrame;
    }
    private int mDeltaFrameheight = 0;
    public int getDeltaFrameHeight() {
        return this.mDeltaFrameheight;
    }
    public void setDeltaFrameHeight(int delta) {
        this.mDeltaFrameheight = delta;
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

    // constructor
    public PGO() {
        // create components
        // 1. frmae 2. canvas 3. other components
        // 4. event listeners 5. managers (except panelMgr)
        this.mFrame = new JFrame("Poly-Go-On");
        this.mCanvas2D = new PGOCanvas2D(this);
        this.mEventListener = new PGOEventListener(this);
        this.mDragListener = new PGODragListener(this);
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
        BorderLayout bLayout = new BorderLayout();
        this.mCanvas2D.setOpaque(false);
        // this.mCanvas2D.setBorder(BorderFactory.createEmptyBorder(PGO.EMPTY_BORDER, 0, 0, 0));
        this.mFrame.setLayout(bLayout);
        this.mFrame.add(this.mCanvas2D, BorderLayout.CENTER);

        this.mPanelMgr = new PGOPanelMgr(this);

        this.mFrame.setResizable(false);
        this.mFrame.setVisible(true);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.startProgram();
    }

    private void startProgram() {
        java.util.Timer startTimer = new java.util.Timer();

        this.mFrame.add(this.mPanelMgr.getStartPane(), BorderLayout.CENTER);
        this.mFrame.pack();
        this.mFrame.setLocationRelativeTo(null);
        startTimer.schedule(new TimerTask() {
            PGO pgo = (PGO) PGOStartScenario.getSingleton().getApp();

            public void run() {
                pgo.getPanelMgr().getStartPane().setVisible(false);
                pgo.getFrame().remove(pgo.getPanelMgr().getStartPane());

                pgo.getFrame().add(pgo.getPanelMgr().getHSBPanel(), BorderLayout.SOUTH);
                pgo.getFrame().add(pgo.getPanelMgr().getTranslucentPane(), BorderLayout.CENTER);
                pgo.getFrame().add(pgo.getPanelMgr().getImagePane(), BorderLayout.CENTER);

                pgo.getFrame().setSize(DEFAULT_WINDOW_WIDTH / 2, DEFAULT_WINDOW_HEIGHT / 2);
                pgo.getFrame().setLocationRelativeTo(null);
                pgo.getFrame().setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
                pgo.getFrame().setLocationRelativeTo(null);
            }
        }, 3000);
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
        for (int i = 0; i < VIBRATION_LENGTH; i++) {
            try {
                Thread.sleep(15);
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
