package PGO;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;

public class PGOSliderMgr {
    // fields
    private PGO pgo = null;
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
    public PGOSliderMgr(PGO pgo) {
        // create components
        this.pgo = pgo;
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
        PGOEventListener pgoEventListener = pgo.getEventListener();
        for (int i = 0; i < 3; i++) {
            JSlider slider = this.mHSBSliders.get(i);
            slider.addChangeListener(this.mHSBChangeListeners.get(i));
            slider.addMouseListener(pgoEventListener);
            slider.addKeyListener(pgoEventListener);
        }

        // build and show visible components
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
