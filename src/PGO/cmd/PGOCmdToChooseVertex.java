package PGO.cmd;

import PGO.PGO;
import PGO.PGOEventListener;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Point;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToChooseVertex extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToChooseVertex(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToChooseVertex cmd = new PGOCmdToChooseVertex(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getLogMgr().setPrintOn(true);
        if (this.mPt.distance(PGODeformScenario.getSingleton().getPrevPt()) > 10) {
            PGODeformScenario.getSingleton().setIsFarEnough(true);
            for (PGOPolygon polygon : pgo.getPolygonMgr().getSelectedPolygons()) {
                if (PGOPolygonCalcMgr.checkContent(this.mPt, polygon)) {
                    PGODeformScenario.getSingleton().setPrevPolygon(polygon.clonePolygon());
                    PGODeformScenario.getSingleton()
                            .setPrevPts((ArrayList<Point>) pgo.getPolygonMgr().getFixedPts().clone());
                    pgo.getPolygonMgr().getPolygons().addAll(
                            pgo.getPolygonMgr().getSelectedPolygons());
                    pgo.getPolygonMgr().getSelectedPolygons().clear();

                    pgo.getPolygonMgr().getPolygons().remove(polygon);
                    pgo.getPolygonMgr().getSelectedPolygons().add(polygon);
                    PGODeformScenario.getSingleton().setAnySelected(true);
                    break;
                }
            }
        } else {
            PGODeformScenario.getSingleton().setIsFarEnough(false);
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
