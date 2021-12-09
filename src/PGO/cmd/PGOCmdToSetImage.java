package PGO.cmd;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.sceanrio.PGOStartScenario;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSetImage extends XLoggableCmd {
    // fields
    private DropTargetDropEvent mEvent = null;
    private String mFileName = null;

    // private constructor
    private PGOCmdToSetImage(XApp app, DropTargetDropEvent ev) {
        super(app);
        this.mEvent = ev;
    }

    public static boolean execute(XApp app, DropTargetDropEvent ev) {
        PGOCmdToSetImage cmd = new PGOCmdToSetImage(app, ev);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        // accept dropped items
        this.mEvent.acceptDrop(DnDConstants.ACTION_COPY);

        // dropped items
        Transferable t = this.mEvent.getTransferable();
        // get data formats of the items
        DataFlavor[] df = t.getTransferDataFlavors();

        // loop through flavors
        for (DataFlavor f : df) {
            try {
                // check if items are file type
                if (f.isFlavorJavaFileListType()) {
                    // get list of them
                    List<File> files = (List<File>) t.getTransferData(f);
                    // loop through them
                    for (File file : files) {
                        this.mFileName = file.getName();
                        displayImage(file.getPath());
                    }
                } else {
                    File file = (File) t.getTransferData(f);
                    this.mFileName = file.getName();
                    displayImage(file.getPath());
                }
            } catch (Exception ex) {
            }
        }
        return true;
    }

    private void displayImage(String path) {
        PGO pgo = (PGO) this.mApp;
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();

        if (scenario.getPrevImage() == null) {
            panelMgr.getImagePane().remove(panelMgr.getTextLabel());
            scenario.setPrevPath(path);
            ;
            this.setImageLabel(path);
        } else {
            if (!scenario.getPrevPath().equals(path)) {
                panelMgr.getImagePane().remove(scenario.getPrevImage());
                scenario.setPrevImage(null);
                scenario.setPrevPath(path);
                this.setImageLabel(path);
            }
        }
    }

    private void setImageLabel(String path) {
        PGO pgo = (PGO) this.mApp;
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();

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
            this.mFileName = "image with invalid proprotion";
        } else {
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(
                    width, height, Image.SCALE_DEFAULT));
            panelMgr.setImageLabel(imageIcon);

            scenario.setPrevImage(new JLabel());
            scenario.getPrevImage().setIcon(imageIcon);

            pgo.getCanvas2D().setSize(width, height);
            if (width > PGO.SLIDER_WIDTH * 3) {
                panelMgr.setHSBPanelLayout(0);
                panelMgr.getHSBPanel().setSize(width, PGO.SLIDER_HEIGHT);
                panelMgr.getHSBPanel().setLocation(
                        0, height + PGO.SLIDER_HEIGHT);
                pgo.setDeltaFrameHeight(PGO.DELTA_WINDOW_HEIGTH_MIN);
            } else {
                panelMgr.setHSBPanelLayout(1);
                panelMgr.getHSBPanel().setSize(width, PGO.SLIDER_HEIGHT * 3);
                panelMgr.getHSBPanel().setLocation(
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

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mFileName);
        return sb.toString();
    }

}
