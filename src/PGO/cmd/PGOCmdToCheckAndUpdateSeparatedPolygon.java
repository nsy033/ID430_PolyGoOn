package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToCheckAndUpdateSeparatedPolygon extends XLoggableCmd {
    // private constructor
    private PGOCmdToCheckAndUpdateSeparatedPolygon(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToCheckAndUpdateSeparatedPolygon cmd = new PGOCmdToCheckAndUpdateSeparatedPolygon(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        boolean anyIntersected = false;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
            if (PGOPolygonCalcMgr.isIntersected(polygon, polygonMgr.getPolygons())) {
                anyIntersected = true;
                break;
            }
        }
        if (anyIntersected || !PGOPolygonCalcMgr.areValidPolygons(
                polygonMgr.getSelectedPolygons())) {
            polygonMgr.getPolygons().add(PGODeformScenario.getSingleton().getPrevPolygon().clonePolygon());
            polygonMgr.setFixedPts((ArrayList<Point>) PGODeformScenario.getSingleton().getPrevPts().clone());
            pgo.vibrate();
        } else {
            for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                Color c = pgo.getColorCalcMgr().getBgColor(pgo, polygon);
                polygon.setColor(c);
            }
            polygonMgr.getPolygons().addAll(
                    polygonMgr.getSelectedPolygons());
        }

        polygonMgr.getSelectedPolygons().clear();
        PGODeformScenario.getSingleton().setPrevPt(null);
        PGODeformScenario.getSingleton().setPrevPts(null);
        PGODeformScenario.getSingleton().setPrevPolygon(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        // ...
        return sb.toString();
    }

}
