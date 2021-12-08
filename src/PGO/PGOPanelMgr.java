package PGO;

import java.awt.Color;
import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;

public class PGOPanelMgr {
    // fields
    private JLabel mTextLabel = null;
    public JLabel getTextLabel() {
        return this.mTextLabel;
    }
    public void setTextLabel(JLabel label) {
        this.mTextLabel = label;
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
    private String mFilePath = null;
    public String getFilePath() {
        return this.mFilePath;
    }
    public void setFilePath(String filepath) {
        this.mFilePath = filepath;
    }

    private ArrayList<PGOChangeListener> mHSBChangeListeners =
        new ArrayList<PGOChangeListener>();
    private ArrayList<JPanel> mHSBPanels = new ArrayList<JPanel>();
    private JPanel mHSBPanel = null;
    public JPanel getHSBPanel() {
        return this.mHSBPanel;
    }
    private ArrayList<JSlider> mHSBSliders = new ArrayList<JSlider>(); 
    public ArrayList<JSlider> getHSBSliders() {
        return this.mHSBSliders;
    }
    
    // constructor
    public PGOPanelMgr(PGO pgo) {
        // create components
        this.mTranslucentPane = new JPanel();
        this.mImagePane = new JPanel();
        this.mTextLabel = new JLabel("Drop Image Here");
        this.mHSBPanel = new JPanel();
        this.mHSBSliders.add(new JSlider(-360, 360, 0));
        this.mHSBSliders.add(new JSlider(-255, 255, 0));
        this.mHSBSliders.add(new JSlider(-255, 255, 0));
        for (int i = 0; i < 3; i++) {
            this.mHSBSliders.get(i).setName("" + i);
            this.mHSBChangeListeners.add(new PGOChangeListener(pgo));
            this.mHSBPanels.add(new JPanel());
        }

        // connect event listeners
        DropTarget dropTarget = new DropTarget(this.mImagePane,
            pgo.getDragListener());
        PGOEventListener pgoEventListener = pgo.getEventListener();
        for (int i = 0; i < 3; i++) {
            JSlider slider = this.mHSBSliders.get(i);
            slider.addChangeListener(this.mHSBChangeListeners.get(i));
            slider.addMouseListener(pgoEventListener);
            slider.addKeyListener(pgoEventListener);
        }

        // build and show visible components
        this.mTranslucentPane.setBackground(new Color(255, 255, 255, 128));
        this.mTranslucentPane.setVisible(false);
        this.mTranslucentPane.setBorder(BorderFactory.createEmptyBorder(
            PGO.EMPTY_BORDER, 0, 0, 0));
        this.mTextLabel.setFont(PGOCanvas2D.FONT_INFO);
        this.mTextLabel.setBackground(new Color(0, 0, 0, 30));
        this.mTextLabel.setVerticalAlignment(JLabel.CENTER);
        this.mTextLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mTextLabel.setBorder(BorderFactory.createEmptyBorder(
            PGO.TEXT_LABEL_HEIGHT, 0, 0, 0));
        this.mImagePane.add(this.mTextLabel, BorderLayout.CENTER);
        this.mImagePane.setBorder(BorderFactory.createEmptyBorder(
            PGO.EMPTY_BORDER, 0, 0, 0));

        // compose HSBPanel
        FlowLayout fl = new FlowLayout(FlowLayout.TRAILING);
        Dimension maximumSize = new Dimension(PGO.SLIDER_WIDTH,
            PGO.SLIDER_HEIGHT * 3);
        ArrayList<String> labels = new ArrayList<String>(
            Arrays.asList("Hue", "Saturation", "Brightness"));
        for (int i = 0; i < 3; i++) {
            JPanel nPanel = this.mHSBPanels.get(i);
            nPanel.add(new JLabel(String.format("%10s", labels.get(i))));
            nPanel.add(this.mHSBSliders.get(i));
            nPanel.setMaximumSize(maximumSize);
            nPanel.setLayout(fl);
            nPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            this.mHSBPanel.add(nPanel);
        }
        this.mHSBPanel.setVisible(false);

        // add all panels to PGOFrame
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
