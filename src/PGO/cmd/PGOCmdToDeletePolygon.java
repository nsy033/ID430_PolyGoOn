package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygonCalcMgr;
import PGO.sceanrio.PGODeleteScenario;
import java.awt.Point;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToDeletePolygon extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToDeletePolygon(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToDeletePolygon cmd = new PGOCmdToDeletePolygon(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
        pgo.getPolygonCalcMgr();
        Point pt = PGOPolygonCalcMgr.isValidPt(this.mPt, pgo.getCanvas2D().getWidth(),
                pgo.getCanvas2D().getHeight());

        if (!pgo.getDeleteArea().contains(pt)) {
            pgo.getPolygonMgr().getDraggedPolygon().translatePolygon(
                    scenario.getFirstLocation().getX() - pt.getX(),
                    scenario.getFirstLocation().getY() - pt.getY());

            pgo.getPolygonMgr().getPolygons().add(pgo.getPolygonMgr().getDraggedPolygon());
            pgo.vibrate();
        } else {
            pgo.getPolygonMgr().getFixedPts().removeAll(pgo.getPolygonMgr().getDraggedPolygon().getPts());
        }
        pgo.getPolygonMgr().setDraggedPolygon(null);
        scenario.setFirstLocation(null);
        scenario.setLastLocation(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
