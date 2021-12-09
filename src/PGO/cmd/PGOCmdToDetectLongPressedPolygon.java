package PGO.cmd;

import java.awt.Point;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.sceanrio.PGODeleteScenario;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToDetectLongPressedPolygon extends XLoggableCmd {
    // field
    private Point mPt = null;
    private Point mPressedPt = null;

    // private constructor
    private PGOCmdToDetectLongPressedPolygon(XApp app, Point pressedPt, Point pt) {
        super(app);
        this.mPressedPt = pressedPt;
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pressedPt, Point pt) {
        PGOCmdToDetectLongPressedPolygon cmd = new PGOCmdToDetectLongPressedPolygon(app, pressedPt, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
        for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
            pgo.getPolygonCalcMgr();
            if (PGOPolygonCalcMgr.checkContent(this.mPressedPt, polygon)) {
                pgo.getPolygonCalcMgr();
                if (PGOPolygonCalcMgr.checkContent(this.mPt, polygon)) {
                    pgo.getPolygonMgr().setDraggedPolygon(polygon);
                    pgo.getPolygonMgr().getPolygons().remove(pgo.getPolygonMgr().getDraggedPolygon());

                    scenario.setFirstLocation(this.mPressedPt);
                    scenario.setLastLocation(this.mPressedPt);

                    scenario.setContained(true);
                    break;
                } else {
                    break;
                }
            }
        }
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
