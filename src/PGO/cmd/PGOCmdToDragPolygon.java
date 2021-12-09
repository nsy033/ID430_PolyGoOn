package PGO.cmd;

import x.XApp;
import x.XLoggableCmd;
import java.awt.Point;

import PGO.PGO;
import PGO.PGOEventListener;
import PGO.sceanrio.PGODeleteScenario;

public class PGOCmdToDragPolygon extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToDragPolygon(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToDragPolygon cmd = new PGOCmdToDragPolygon(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);
        PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
        if (pgo.getPolygonMgr().getDraggedPolygon() != null) {
            pgo.getPolygonMgr().getDraggedPolygon().translatePolygon(
                    this.mPt.getX() - scenario.getLastLocation().getX(),
                    this.mPt.getY() - scenario.getLastLocation().getY());
            scenario.setLastLocation(this.mPt);
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
        sb.append(this.mPt);
        return sb.toString();
    }

}
