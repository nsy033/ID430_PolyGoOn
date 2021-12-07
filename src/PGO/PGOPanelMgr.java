package PGO;

import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PGOPanelMgr {
    // fields
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
    
    private JPanel mHSBPanel = null;
    public JPanel getHSBPanel() {
        return this.mHSBPanel;
    }
    private JSlider mHueSlider = null;
    public JSlider getHueSlider() {
        return this.mHueSlider;
    }
    private JSlider mSatSlider = null;
    public JSlider getSatSlider() {
        return this.mSatSlider;
    }
    private JSlider mBriSlider = null;
    public JSlider getBriSlider() {
        return this.mBriSlider;
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

    // constructor
    public PGOPanelMgr(PGO pgo) {
        // create components
        this.mTranslucentPane = new JPanel();
        this.mImagePane = new JPanel();
        this.mTextLabel = new JLabel("Drop Image Here");
        this.mHSBPanel = new JPanel();
        this.mHueSlider = new JSlider(-360, 360, 0);
        this.mSatSlider = new JSlider(-255, 255, 0);
        this.mBriSlider = new JSlider(-255, 255, 0);
        this.mHueChangeListener = new PGOChangeListener(pgo);
        this.mSatChangeListener = new PGOChangeListener(pgo);
        this.mBriChangeListener = new PGOChangeListener(pgo);

        // connect event listeners
        DropTarget dropTarget = new DropTarget(this.mImagePane, pgo.getDragListener());
        PGOEventListener pgoEventListener = pgo.getEventListener();
        this.mHueSlider.addChangeListener(this.mHueChangeListener);
        this.mSatSlider.addChangeListener(this.mSatChangeListener);
        this.mBriSlider.addChangeListener(this.mBriChangeListener);
        this.mHueSlider.addMouseListener(pgoEventListener);
        this.mSatSlider.addMouseListener(pgoEventListener);
        this.mBriSlider.addMouseListener(pgoEventListener);
        this.mHueSlider.addKeyListener(pgoEventListener);
        this.mSatSlider.addKeyListener(pgoEventListener);
        this.mBriSlider.addKeyListener(pgoEventListener);

        // build and show visible components
        this.mTranslucentPane.setBackground(new Color(255, 255, 255, 128));
        this.mTranslucentPane.setVisible(false);
        this.mTranslucentPane.setBorder(BorderFactory.createEmptyBorder(PGO.EMPTY_BORDER, 0, 0, 0));
        this.mTextLabel.setFont(PGOCanvas2D.FONT_INFO);
        this.mTextLabel.setBackground(new Color(0,0,0,30));
        this.mTextLabel.setVerticalAlignment(JLabel.CENTER);
        this.mTextLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mTextLabel.setBorder(BorderFactory.createEmptyBorder((int)(PGO.DEFAULT_WINDOW_HEIGHT / 2.2), 0, 0, 0));
        this.mImagePane.add(this.mTextLabel, BorderLayout.CENTER);
        this.mImagePane.setBorder(BorderFactory.createEmptyBorder(PGO.EMPTY_BORDER, 0, 0, 0));

        this.mHSBPanel.add(new JLabel("Hue"));
        this.mHSBPanel.add(mHueSlider, BorderLayout.CENTER);
        this.mHSBPanel.add(new JLabel("Saturation"));
        this.mHSBPanel.add(mSatSlider, BorderLayout.CENTER);
        this.mHSBPanel.add(new JLabel("Brightness"));
        this.mHSBPanel.add(mBriSlider, BorderLayout.CENTER);
        this.mHSBPanel.setVisible(false);
        
        JFrame pgoFrame = pgo.getFrame();
        pgoFrame.add(this.mHSBPanel, BorderLayout.SOUTH);
        pgoFrame.add(this.mTranslucentPane, BorderLayout.CENTER);
        pgoFrame.add(this.mImagePane, BorderLayout.CENTER);
    }
}
