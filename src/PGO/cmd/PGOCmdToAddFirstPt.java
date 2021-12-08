package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import java.awt.Point;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToAddFirstPt extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;
    
    // constructor
    private PGOCmdToAddFirstPt(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        PGOCmdToAddFirstPt cmd = new PGOCmdToAddFirstPt(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        polygons = polygonMgr.getPolygons();
        if (!pgo.getPolygonCalcMgr().isContained(this.mPt, polygons)) {
            pgo.getPolygonMgr().createCurPolygon(this.mPt,
                pgo.getCanvas2D().getCurColorForPolygon(),
                pgo.getCanvas2D().getCurStrokeForPolygon());
        }
        
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mPt).append("\t");
        sb.append(this.polygons);
        
        return sb.toString();
    }
    
}
