package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToAddThirdPt extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;
    PGOPolygon tempPolygon = null;
    
    // constructor
    private PGOCmdToAddThirdPt(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }
    
    public static boolean execute(XApp app, Point pt) {
        PGOCmdToAddThirdPt cmd = new PGOCmdToAddThirdPt(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        PGOPolygonCalcMgr polygonCalcMgr = pgo.getPolygonCalcMgr();
        polygons = polygonMgr.getPolygons();

        ArrayList<Point> tempPts = polygonMgr.getCurPolygon().getPts();
        Color tempC = polygonMgr.getCurPolygon().getColor();
        Stroke tempS = polygonMgr.getCurPolygon().getStroke();
        tempPolygon = new PGOPolygon(tempPts.get(0), tempC, tempS);
        tempPolygon.addPt(tempPts.get(1));
        tempPolygon.addPt(this.mPt);

        boolean isContained = false;
        boolean isIntersected = false;
        boolean isOverlapped = false;
        for (PGOPolygon polygon: polygons) {
            if (polygonCalcMgr.checkContent(this.mPt, polygon)) {
                isContained = true;
                break;
            }
        }
        if (polygonCalcMgr.isIntersected(tempPolygon, polygons)) {
            isIntersected = true;
        }
        if (polygonCalcMgr.isOverlapped(tempPolygon, polygons)) {
            isOverlapped = true;
        }
        
        if (!isContained && !isIntersected && !isOverlapped) {
            polygonMgr.getCurPolygon().addPt(this.mPt);
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mPt).append("\t");
        sb.append(this.tempPolygon).append("\t");
        sb.append(this.polygons);
        
        return sb.toString();
    }
    
}
