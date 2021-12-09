package PGO.cmd;

import PGO.PGO;
import PGO.PGOEventListener;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToUpdateCheckingIntersection extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;
    PGOPolygon tempPolygon = null;

    // constructor
    private PGOCmdToUpdateCheckingIntersection(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToUpdateCheckingIntersection cmd = new PGOCmdToUpdateCheckingIntersection(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);

        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        PGOPolygonCalcMgr polygonCalcMgr = pgo.getPolygonCalcMgr();
        ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();

        tempPolygon = polygonMgr.getCurPolygon().clonePolygon();
        tempPolygon.updatePolygon(this.mPt);

        boolean isContained = false;
        boolean isIntersected = false;
        boolean isOverlapped = false;

        for (PGOPolygon polygon : polygons) {
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
            this.mPt = polygonCalcMgr.findNearPt(this.mPt, pgo.getPolygonMgr().getFixedPts());
            polygonMgr.getCurPolygon().updatePolygon(this.mPt);
        }

        if (pgo.getEventListener().getMousePrevPt().distance(mPt.getX(),
                mPt.getY()) > PGOEventListener.MIN_DISTANCE_FOR_LOGGING) {
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
        sb.append(this.tempPolygon);
        sb.append(this.polygons);

        return sb.toString();
    }

}
