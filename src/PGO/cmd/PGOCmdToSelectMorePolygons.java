package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSelectMorePolygons extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToSelectMorePolygons(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToSelectMorePolygons cmd = new PGOCmdToSelectMorePolygons(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
        Point nearPt = PGOPolygonCalcMgr.findNearPt(this.mPt, pgo.getPolygonMgr().getFixedPts());
        Rectangle boundingBox = new Rectangle(nearPt.x - 100, nearPt.y - 100, 200, 200);
        ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();

        PGODeformScenario.getSingleton().setPrevPolygons(new ArrayList<PGOPolygon>());
        PGODeformScenario.getSingleton().setPrevPts((ArrayList<Point>) pgo.getPolygonMgr().getFixedPts().clone());

        if (pgo.getPolygonMgr().getFixedPts().contains(nearPt)) {
            for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
                if (boundingBox.intersects(polygon.getBoundingBox())) {
                    PGOPolygon tmpPolygon = polygon.clonePolygon();
                    if (polygon.getPts().contains(nearPt)) {
                        newSelectedPolygons.add(polygon);
                        PGODeformScenario.getSingleton().getPrevPolygons().add(tmpPolygon);

                        int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(nearPt);
                        polygon.updateSelectedPt(this.mPt, nearPt);
                        polygon.updateBoundingBox(polygon.getPts());
                        polygonMgr.getFixedPts().set(ptIndexInFixedPts, this.mPt);
                    }
                }
            }
            pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
            pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
            newSelectedPolygons.clear();
        }
        PGODeformScenario.getSingleton().setPrevPt(this.mPt);
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
