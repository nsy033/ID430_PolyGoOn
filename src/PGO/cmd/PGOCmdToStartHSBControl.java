package PGO.cmd;

import PGO.PGO;
import java.awt.Dimension;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToStartHSBControl extends XLoggableCmd {
    // constructor
    private PGOCmdToStartHSBControl(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        PGOCmdToStartHSBControl cmd = new PGOCmdToStartHSBControl(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        Dimension prevSize = pgo.getFrame().getSize();
        pgo.getFrame().setSize(prevSize.width,
            prevSize.height + pgo.getDeltaFrameHeight());
        pgo.getSliderMgr().getHSBPanel().setVisible(true);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }
    
}
