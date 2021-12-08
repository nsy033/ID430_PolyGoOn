package PGO.cmd;

import PGO.PGO;
import java.awt.Dimension;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToWrapUpHSBControl extends XLoggableCmd {
    // constructor
    private PGOCmdToWrapUpHSBControl(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        PGOCmdToWrapUpHSBControl cmd = new PGOCmdToWrapUpHSBControl(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getPanelMgr().getHSBPanel().setVisible(false);
        Dimension prevSize = pgo.getFrame().getSize();
        pgo.getFrame().setSize(prevSize.width,
        prevSize.height - pgo.getDeltaFrameHeight());
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
    
}
