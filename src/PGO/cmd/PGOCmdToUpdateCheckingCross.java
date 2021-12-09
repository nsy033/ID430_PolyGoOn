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

public class PGOCmdToUpdateCheckingCross extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;
    
    // constructor
    private PGOCmdToUpdateCheckingCross(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        PGOCmdToUpdateCheckingCross cmd = new PGOCmdToUpdateCheckingCross(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);
        
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
            this.mPt = polygonCalcMgr.findNearPt(this.mPt);
            polygonMgr.getCurPolygon().updatePolygon(this.mPt);
        }
        
        if (pgo.getEventListener().getMousePrevPt().
            distance(mPt.getX(), mPt.getY()) > 250.0) {
            pgo.getEventListener().setMousePrevPt(mPt);
        } else {
            pgo.getLogMgr().setPrintOn(false);
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
