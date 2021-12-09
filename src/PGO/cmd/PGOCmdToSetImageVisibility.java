package PGO.cmd;

import PGO.PGO;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSetImageVisibility extends XLoggableCmd {
    // fields
    private boolean mIsVisible = true;

    // private constructor
    private PGOCmdToSetImageVisibility(XApp app, boolean visible) {
        super(app);
        this.mIsVisible = visible;
    }

    public static boolean execute(XApp app, boolean visible) {
        PGOCmdToSetImageVisibility cmd = new PGOCmdToSetImageVisibility(app, visible);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getPanelMgr().getImagePane().setVisible(this.mIsVisible);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append("Image visibility: " + this.mIsVisible);
        return sb.toString();
    }

}
