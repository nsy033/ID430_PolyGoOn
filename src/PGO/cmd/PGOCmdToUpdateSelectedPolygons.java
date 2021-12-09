package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import PGO.sceanrio.PGODeformScenario;
import java.awt.Point;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToUpdateSelectedPolygons extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToUpdateSelectedPolygons(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToUpdateSelectedPolygons cmd = new PGOCmdToUpdateSelectedPolygons(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        Point prevPt = PGODeformScenario.getSingleton().getPrevPt();

        for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
            int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(prevPt);
            polygon.updateSelectedPt(this.mPt, prevPt);
            polygon.updateBoundingBox(polygon.getPts());
            polygonMgr.getFixedPts().set(ptIndexInFixedPts, this.mPt);
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mPt);
        return sb.toString();
    }

}
