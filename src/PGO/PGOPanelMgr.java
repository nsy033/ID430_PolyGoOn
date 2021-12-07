package PGO;

import java.awt.Color;
import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Dimension;

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
        this.mTextLabel.setBackground(new Color(0, 0, 0, 30));
        this.mTextLabel.setVerticalAlignment(JLabel.CENTER);
        this.mTextLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mTextLabel.setBorder(BorderFactory.createEmptyBorder(PGO.TEXT_LABEL_HEIGHT, 0, 0, 0));
        this.mImagePane.add(this.mTextLabel, BorderLayout.CENTER);
        this.mImagePane.setBorder(BorderFactory.createEmptyBorder(PGO.EMPTY_BORDER, 0, 0, 0));

        JPanel HPanel = new JPanel();
        JPanel SPanel = new JPanel();
        JPanel BPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.TRAILING);
        Dimension maximumSize = new Dimension(PGO.SLIDER_WIDTH, PGO.SLIDER_HEIGHT * 3);
        HPanel.add(new JLabel(String.format("%10s", "Hue")));
        HPanel.add(mHueSlider);
        HPanel.setMaximumSize(maximumSize);
        HPanel.setLayout(fl);
        HPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        HPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        SPanel.add(new JLabel(String.format("%10s", "Saturation")));
        SPanel.add(mSatSlider);
        SPanel.setMaximumSize(maximumSize);
        SPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        SPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        SPanel.setLayout(fl);
        BPanel.add(new JLabel(String.format("%10s", "Brightness")));
        BPanel.add(mBriSlider);
        BPanel.setMaximumSize(maximumSize);
        BPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        BPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        BPanel.setLayout(fl);
        this.mHSBPanel.add(HPanel);
        this.mHSBPanel.add(SPanel);
        this.mHSBPanel.add(BPanel);
        this.mHSBPanel.setVisible(false);

        JFrame pgoFrame = pgo.getFrame();
        pgoFrame.add(this.mHSBPanel, BorderLayout.SOUTH);
        pgoFrame.add(this.mTranslucentPane, BorderLayout.CENTER);
        pgoFrame.add(this.mImagePane, BorderLayout.CENTER);
    }

    public void setHSBPanelLayout(int axis) {
        switch (axis) {
            case 0:
                FlowLayout fl = new FlowLayout();
                this.mHSBPanel.setLayout(fl);
                this.mHSBPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                this.mHSBPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
                break;
            case 1:
                BoxLayout bl = new BoxLayout(this.mHSBPanel, axis);
                this.mHSBPanel.setLayout(bl);
                this.mHSBPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                break;
        }
    }
}
