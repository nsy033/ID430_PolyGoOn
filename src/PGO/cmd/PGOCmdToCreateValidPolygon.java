package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import java.awt.Color;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToCreateValidPolygon extends XLoggableCmd {
    // field
    PGOPolygon curPolygon = null;
    boolean mResult = true;
    
    // constructor
    private PGOCmdToCreateValidPolygon(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        PGOCmdToCreateValidPolygon cmd = new PGOCmdToCreateValidPolygon(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        curPolygon = polygonMgr.getCurPolygon();

        if (pgo.getPolygonCalcMgr().isValidPolygon(curPolygon)){
            Color c = pgo.getColorCalcMgr().getBgColor(pgo, curPolygon);
            polygonMgr.getCurPolygon().setColor(c);
            polygonMgr.getPolygons().add(curPolygon);
            polygonMgr.getFixedPts().addAll(curPolygon.getPts());
        } else {
            this.mResult = false;
            pgo.vibrate();
        }

        polygonMgr.setCurPolygon(null);
        
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.curPolygon).append("\t");
        sb.append(this.mResult).append("\t");
        return sb.toString();
    }
    
}
