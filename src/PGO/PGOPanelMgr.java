package PGO;

import java.awt.Color;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import PGO.sceanrio.PGOStartScenario;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PGOPanelMgr {
    // fields
    private PGO pgo = null;
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

    public void setImageIcon(ImageIcon icon) {
        this.mImageIcon = icon;
    }

    private String mFilePath = null;

    public String getFilePath() {
        return this.mFilePath;
    }

    public void setFilePath(String filepath) {
        this.mFilePath = filepath;
    }

    private String mFileName = null;

    public void setFileName(String filename) {
        this.mFileName = filename;
    }

    private boolean mImageLoaded = false;

    public boolean isImageLoaded() {
        return this.mImageLoaded;
    }

    public void setImageLoaded(boolean state) {
        this.mImageLoaded = state;
    }

    // constructor
    public PGOPanelMgr(PGO pgo) {
        // create components
        this.pgo = pgo;
        this.mTranslucentPane = new JPanel();
        this.mImagePane = new JPanel();
        this.mTextLabel = new JLabel("Drop Image Here");
        this.mFileName = PGOStartScenario.FileReadyScene.getSingleton().getFileName();

        // connect event listeners
        DropTarget dropTarget = new DropTarget(this.mImagePane,
                pgo.getDragListener());

        // build and show visible components
        this.mTranslucentPane.setBackground(new Color(255, 255, 255, 128));
        this.mTranslucentPane.setVisible(false);
        this.mTranslucentPane.setBorder(BorderFactory.createEmptyBorder(
                PGO.EMPTY_BORDER, 0, 0, 0));
        this.mTextLabel.setFont(PGOCanvas2D.FONT_INFO);
        this.mTextLabel.setVerticalAlignment(JLabel.CENTER);
        this.mTextLabel.setHorizontalAlignment(JLabel.CENTER);
        this.mTextLabel.setBorder(BorderFactory.createEmptyBorder(
                PGO.TEXT_LABEL_HEIGHT, 0, 0, 0));
        this.mImagePane.add(this.mTextLabel, BorderLayout.CENTER);
        this.mImagePane.setBorder(BorderFactory.createEmptyBorder(
                PGO.EMPTY_BORDER, 0, 0, 0));

        // add all panels to PGOFrame
        JFrame pgoFrame = pgo.getFrame();
        pgoFrame.add(this.mTranslucentPane, BorderLayout.CENTER);
        pgoFrame.add(this.mImagePane, BorderLayout.CENTER);
    }

    public void setImageLabel(String path) {
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();
        PGOSliderMgr sliderMgr = pgo.getSliderMgr();

        File file = new File(path);
        if (file.exists()) {
            this.mImageLoaded = true;
        } else {
            pgo.vibrate();
            return;
        }

        Image image = new ImageIcon(path).getImage();
        pgo.getFrame().setIconImage(image);

        int imgWidth = image.getWidth(null);
        int imgHeight = image.getHeight(null);
        double d = Double.min(1280.0 / imgWidth, 700.0 / imgHeight);
        int width = (int) (imgWidth * d);
        int height = (int) (imgHeight * d);

        pgo.getFrame().setSize(
                PGO.DEFAULT_WINDOW_WIDTH,
                PGO.DEFAULT_WINDOW_HEIGHT);
        pgo.getFrame().setLocationRelativeTo(null);

        if (width < PGO.MIN_WIDTH || height < PGO.MIN_HEIGHT) {
            // Do not assign when image has invalid proportion.
            panelMgr.getImagePane().add(panelMgr.getTextLabel());
            pgo.getFrame().setLocationRelativeTo(null);
            scenario.setPrevPath(null);
            scenario.setPrevImage(null);
            pgo.vibrate();
            this.mFileName = "image with invalid proportion";
        } else {
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(
                    width, height, Image.SCALE_DEFAULT));
            panelMgr.setImageIcon(imageIcon);

            scenario.setPrevImage(new JLabel());
            scenario.getPrevImage().setIcon(imageIcon);

            pgo.getCanvas2D().setSize(width, height);
            if (width > PGO.SLIDER_WIDTH * 3) {
                sliderMgr.setHSBPanelLayout(0);
                sliderMgr.getHSBPanel().setSize(width, PGO.SLIDER_HEIGHT);
                sliderMgr.getHSBPanel().setLocation(
                        0, height + PGO.SLIDER_HEIGHT);
                pgo.setDeltaFrameHeight(PGO.DELTA_WINDOW_HEIGTH_MIN);
            } else {
                sliderMgr.setHSBPanelLayout(1);
                sliderMgr.getHSBPanel().setSize(width, PGO.SLIDER_HEIGHT * 3);
                sliderMgr.getHSBPanel().setLocation(
                        0, height + PGO.SLIDER_HEIGHT * 3);
                pgo.setDeltaFrameHeight(PGO.DELTA_WINDOW_HEIGTH_MAX);
            }
            panelMgr.getTranslucentPane().setSize(width, height);
            panelMgr.getImagePane().setSize(width, height);

            pgo.getFrame().setSize(width, height + PGO.SLIDER_HEIGHT);
            pgo.getFrame().setLocationRelativeTo(null);
            panelMgr.getImagePane().add(scenario.getPrevImage(), JLabel.CENTER);
        }
    }
}
