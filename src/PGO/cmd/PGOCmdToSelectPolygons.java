package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSelectPolygons extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToSelectPolygons(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToSelectPolygons cmd = new PGOCmdToSelectPolygons(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        Rectangle boundingBox = new Rectangle(this.mPt.x - 100, this.mPt.y - 100, 200, 200);
        ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();

        PGODeformScenario.getSingleton().setPrevPolygons(new ArrayList<PGOPolygon>());
        PGODeformScenario.getSingleton().setPrevPts((ArrayList<Point>) pgo.getPolygonMgr().getFixedPts().clone());

        for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
            if (boundingBox.intersects(polygon.getBoundingBox())) {
                PGOPolygon tmpPolygon = polygon.clonePolygon();
                if (polygon.getPts().contains(this.mPt)) {
                    newSelectedPolygons.add(polygon);
                    PGODeformScenario.getSingleton().getPrevPolygons().add(tmpPolygon);
                }
            }
        }
        pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
        pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
        newSelectedPolygons.clear();

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
