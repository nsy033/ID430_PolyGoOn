 package PGO;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
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
    private JPanel mTranslucentPane = null;
    public JPanel getTranslucentPane() {
        return this.mTranslucentPane;
    }
    private JPanel mImagePane = null;
    public JPanel getImagePane() {
        return this.mImagePane;
    }
    private JLabel mImageLabel = null;
    public JLabel getImageLabel() {
        return this.mImageLabel;
    }
    public void setImageLabel(JLabel image) {
        this.mImageLabel = image;
    }
    private ImageIcon mImageIcon = null;
    public ImageIcon getImageIcon() {
        return this.mImageIcon;
    }
    public void setImageLabel(ImageIcon icon) {
        this.mImageIcon = icon;
    }
    private JLabel mTextLabel = null;
    public JLabel getTextLabel() {
        return this.mTextLabel;
    }
    public void setTextLabel(JLabel label) {
        this.mTextLabel = label;
    }
    private String mFilePath = null;
    public String getFilePath() {
        return this.mFilePath;
    }
    public void setFilePath(String filepath) {
        this.mFilePath = filepath;
    }
            
    
    private JPanel hsbPanel = null;
    public JPanel getHSBPanel() {
        return this.hsbPanel;
    }
    private JSlider hueSlider = null;
    public JSlider getHueSlider() {
        return this.hueSlider;
    }
    private JSlider satSlider = null;
    public JSlider getSatSlider() {
        return this.satSlider;
    }
    private JSlider briSlider = null;
    public JSlider getBriSlider() {
        return this.briSlider;
    }
    
    private PGOChangeListener mHueChangeListener = null;
    private PGOChangeListener mSatChangeListener = null;
    private PGOChangeListener mBriChangeListener = null;
    public PGOChangeListener getChangeListener() {
        return this.mHueChangeListener;
    }
    
    private PGOEventListener mEventListener = null;
    public PGOEventListener getEventListener() {
        return this.mEventListener;
    }
    private PGODragListener mDragListener = null;
    
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
    
    //constructor
    public PGO() {
        // create components
        // 1. frmae 2. canvas 3. other components
        // 4. event listeners 5. managers
        this.mFrame = new JFrame("Poly-Go-On");
        this.mCanvas2D = new PGOCanvas2D(this);
        this.mTranslucentPane = new JPanel();
        this.mImagePane = new JPanel();
        this.mTextLabel = new JLabel("[Drop Image Here]");
        this.mEventListener = new PGOEventListener(this);
        this.mDragListener = new PGODragListener(this);
        this.mPolygonMgr = new PGOPolygonMgr();
        this.mScenarioMgr = new PGOScenarioMgr(this);
        this.mLogMgr = new XLogMgr();
        this.mCalcMgr = new PGOCalcMgr(this);
        this.mHueChangeListener = new PGOChangeListener(this);
        this.mSatChangeListener = new PGOChangeListener(this);
        this.mBriChangeListener = new PGOChangeListener(this);
        
        this.hsbPanel = new JPanel();
        this.hueSlider = new JSlider(-360, 360, 0);
        this.satSlider = new JSlider(-255, 255, 0);
        this.briSlider = new JSlider(-255, 255, 0);
        
        // connect event listeners
        DropTarget dropTarget = new DropTarget(this.mImagePane, this.mDragListener);
        this.mCanvas2D.addMouseListener(this.mEventListener);
        this.mCanvas2D.addMouseMotionListener(this.mEventListener);
        this.mCanvas2D.addKeyListener(this.mEventListener);
        this.mCanvas2D.setFocusable(true);
        this.hueSlider.addChangeListener(mHueChangeListener);
        this.satSlider.addChangeListener(mSatChangeListener);
        this.briSlider.addChangeListener(mBriChangeListener);
        this.hueSlider.addMouseListener(this.mEventListener);
        this.satSlider.addMouseListener(this.mEventListener);
        this.briSlider.addMouseListener(this.mEventListener);
        this.hueSlider.addKeyListener(this.mEventListener);
        this.satSlider.addKeyListener(this.mEventListener);
        this.briSlider.addKeyListener(this.mEventListener);
                
        // build and show visible components
        this.mTranslucentPane.setBackground(new Color(255, 255, 255, 128));
        this.mTranslucentPane.setVisible(false);
        this.mCanvas2D.setOpaque(false);
        this.mTextLabel.setFont(PGOCanvas2D.FONT_INFO);
        this.mTextLabel.setBackground(new Color(0,0,0,30));
        this.mTextLabel.setVerticalAlignment(JLabel.CENTER);
        this.mTextLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mImagePane.add(this.mTextLabel, BorderLayout.CENTER);
        
        this.hsbPanel.add(new JLabel("Hue"));
        this.hsbPanel.add(hueSlider, BorderLayout.CENTER);
        this.hsbPanel.add(new JLabel("Saturation"));
        this.hsbPanel.add(satSlider, BorderLayout.CENTER);
        this.hsbPanel.add(new JLabel("Brightness"));
        this.hsbPanel.add(briSlider, BorderLayout.CENTER);
        this.hsbPanel.setVisible(false);
        
        this.mFrame.add(this.hsbPanel, BorderLayout.SOUTH);
        this.mFrame.add(this.mCanvas2D, BorderLayout.CENTER);
        this.mFrame.add(this.mTranslucentPane, BorderLayout.CENTER);
        this.mFrame.add(this.mImagePane, BorderLayout.CENTER);
        
        this.mFrame.setSize(800, 600);
        this.mFrame.setLocationRelativeTo(null);
        this.mFrame.setResizable(false);
        this.mFrame.setVisible(true);
        this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        PGO myPGO = new PGO();
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
