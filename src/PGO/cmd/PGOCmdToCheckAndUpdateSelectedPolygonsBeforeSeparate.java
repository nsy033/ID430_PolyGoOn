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

public class PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate extends XLoggableCmd {
    // private constructor
    private PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate cmd = new PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate(
                app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        boolean anyIntersected = false;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();

        ArrayList<PGOPolygon> polygons = (ArrayList<PGOPolygon>) polygonMgr.getSelectedPolygons().clone();
        polygons.addAll((ArrayList<PGOPolygon>) polygonMgr.getPolygons().clone());
        ArrayList<PGOPolygon> polygons2 = (ArrayList<PGOPolygon>) polygons.clone();
        for (PGOPolygon polygon : polygons) {
            polygons2.remove(polygon);
            if (PGOPolygonCalcMgr.isIntersected(polygon, polygons2)) {
                anyIntersected = true;
                polygons2.add(polygon);
                break;
            }
            polygons2.add(polygon);
        }
        if (anyIntersected || !PGOPolygonCalcMgr.areValidPolygons(
                polygonMgr.getSelectedPolygons())) {
            polygonMgr.getPolygons().addAll(
                    (ArrayList<PGOPolygon>) PGODeformScenario.getSingleton().getPrevPolygons().clone());
            polygonMgr
                    .setFixedPts((ArrayList<Point>) PGODeformScenario.getSingleton().getPrevPts().clone());

            pgo.vibrate();

            pgo.getPolygonMgr().getSelectedPolygons().clear();
            PGODeformScenario.getSingleton().setPrevPolygons(null);
            PGODeformScenario.getSingleton().setPrevPts(null);
        } else {
            for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                Color c = pgo.getColorCalcMgr().getBgColor(pgo, polygon);
                polygon.setColor(c);
            }
            if (polygonMgr.getSelectedPolygons().size() <= 1) {
                polygonMgr.getPolygons().addAll(
                        polygonMgr.getSelectedPolygons());
                polygonMgr.getSelectedPolygons().clear();

                PGODeformScenario.getSingleton().setPrevPolygons(null);
                PGODeformScenario.getSingleton().setPrevPts(null);
            } else {
                PGODeformScenario.getSingleton().setPrevPolygons(null);
            }
        }
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
