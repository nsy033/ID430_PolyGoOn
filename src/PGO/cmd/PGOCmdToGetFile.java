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

public class PGOCmdToGetFile extends XLoggableCmd {
    // fields
    private DropTargetDropEvent mEvent = null;
    private String mFileName = null;

    // private constructor
    private PGOCmdToGetFile(XApp app, DropTargetDropEvent ev) {
        super(app);
        this.mEvent = ev;
    }

    public static boolean execute(XApp app, DropTargetDropEvent ev) {
        PGOCmdToGetFile cmd = new PGOCmdToGetFile(app, ev);
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
                        PGOStartScenario.FileReadyScene.getSingleton().setFileName(this.mFileName);
                        PGOStartScenario.FileReadyScene.getSingleton().setFilePath(file.getPath());
                    }
                } else {
                    File file = (File) t.getTransferData(f);
                    this.mFileName = file.getName();
                    PGOStartScenario.FileReadyScene.getSingleton().setFileName(this.mFileName);
                    PGOStartScenario.FileReadyScene.getSingleton().setFilePath(file.getPath());
                }
            } catch (Exception ex) {
            }
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mFileName);
        return sb.toString();
    }

}
