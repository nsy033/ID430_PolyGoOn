package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import x.XApp;
import x.XLoggableCmd;
import java.awt.Color;

public class PGOCmdToCalcPolygonColor extends XLoggableCmd {
    // fields
    // ...

    // private constructor
    private PGOCmdToCalcPolygonColor(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToCalcPolygonColor cmd = new PGOCmdToCalcPolygonColor(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        
        for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
            Color c = pgo.getColorCalcMgr().getBgColor(pgo, polygon);
            polygon.setColor(c);
        }
        
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
