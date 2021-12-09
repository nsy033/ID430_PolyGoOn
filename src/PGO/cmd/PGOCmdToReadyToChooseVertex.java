package PGO.cmd;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.sceanrio.PGODeformScenario;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToReadyToChooseVertex extends XLoggableCmd {
    // field
    private Point mPt = null;

    // private constructor
    private PGOCmdToReadyToChooseVertex(XApp app, Point pt) {
        super(app);
        this.mPt = pt;
    }

    public static boolean execute(XApp app, Point pt) {
        PGOCmdToReadyToChooseVertex cmd = new PGOCmdToReadyToChooseVertex(app, pt);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        Rectangle boundingBox = new Rectangle(this.mPt.x - 100, this.mPt.y - 100, 200, 200);
        ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();

        for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
            if (boundingBox.intersects(polygon.getBoundingBox())) {
                if (polygon.getPts().contains(this.mPt)) {
                    newSelectedPolygons.add(polygon);
                }
            }
        }

        if (newSelectedPolygons.size() > 1) {
            pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
            pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
            newSelectedPolygons.clear();

            PGODeformScenario.getSingleton().setPrevPt(this.mPt);
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
