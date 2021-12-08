package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToAddSecondPt extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;
    
    // constructor
    private PGOCmdToAddSecondPt(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        PGOCmdToAddSecondPt cmd = new PGOCmdToAddSecondPt(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        PGOPolygonCalcMgr polygonCalcMgr = pgo.getPolygonCalcMgr();
        polygons = polygonMgr.getPolygons();
        Line2D.Double line = new Line2D.Double();
        line.setLine((Point2D) this.mPt,
            (Point2D) polygonMgr.getCurPolygon().getPts().get(0));

        boolean isContained = false;
        for (PGOPolygon polygon: polygons) {
            if (polygonCalcMgr.checkContent(this.mPt, polygon)) {
                isContained = true;
                break;
            }
        }

        if (!isContained && !polygonCalcMgr.isCrossed(line, polygons)) {
            polygonMgr.getCurPolygon().addPt(this.mPt);
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
