package PGO.cmd;

import PGO.sceanrio.PGOStartScenario;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToGetExtension extends XLoggableCmd {
    // fields
    private String mFilePath = null;
    private String mExtension = null;

    // private constructor
    private PGOCmdToGetExtension(XApp app) {
        super(app);
        this.mFilePath = PGOStartScenario.FileReadyScene.getSingleton().getFilePath();
    }

    public static boolean execute(XApp app) {
        PGOCmdToGetExtension cmd = new PGOCmdToGetExtension(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        mExtension = "";
        for (int i = mFilePath.length()-1; i >= 0; i--) {
            if (this.mFilePath.charAt(i) == '.') {
                break;
            }
            mExtension = this.mFilePath.charAt(i) + mExtension;
        }
        PGOStartScenario.FileReadyScene.getSingleton().setExtenstion(mExtension);

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mExtension);
        return sb.toString();
    }

}
