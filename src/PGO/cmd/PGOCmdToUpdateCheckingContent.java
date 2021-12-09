package PGO.cmd;

import PGO.PGO;
import PGO.PGOEventListener;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import java.awt.Point;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToUpdateCheckingContent extends XLoggableCmd {
    // fields
    Point mPt = null;
    ArrayList<PGOPolygon> polygons = null;

    // constructor
    private PGOCmdToUpdateCheckingContent(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToUpdateCheckingContent cmd = new PGOCmdToUpdateCheckingContent(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);

        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        PGOPolygonCalcMgr polygonCalcMgr = pgo.getPolygonCalcMgr();
        this.polygons = polygonMgr.getPolygons();

        if (!polygonCalcMgr.isContained(this.mPt, polygons)) {
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
        sb.append(this.polygons);

        return sb.toString();
    }

}
