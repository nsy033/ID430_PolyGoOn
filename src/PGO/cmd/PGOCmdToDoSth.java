package PGO.cmd;

import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToDoSth extends XLoggableCmd {
    // fields
    // ...

    // private constructor
    private PGOCmdToDoSth(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToDoSth cmd = new PGOCmdToDoSth(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        // PGO pgo = (PGO) this.mApp;
        // ...
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        // ...
        return sb.toString();
    }

}
