package PGO.cmd;

import PGO.PGO;
import PGO.PGOEventListener;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Point;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSeparateVertex extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToSeparateVertex(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToSeparateVertex cmd = new PGOCmdToSeparateVertex(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        Point prevPt = PGODeformScenario.getSingleton().getPrevPt();

        for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
            int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(prevPt);
            polygon.updateSelectedPt(this.mPt, prevPt);
            polygon.updateBoundingBox(polygon.getPts());
            polygonMgr.getFixedPts().set(ptIndexInFixedPts, this.mPt);
        }
        PGODeformScenario.getSingleton().setPrevPt(this.mPt);
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
        sb.append(this.getClass().getSimpleName());
        sb.append(this.mPt);
        return sb.toString();
    }

}
