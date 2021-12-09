package PGO.cmd;

import PGO.PGO;
import PGO.sceanrio.PGODeformScenario;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToDeselectSelectedPolygons extends XLoggableCmd {
    // private constructor
    private PGOCmdToDeselectSelectedPolygons(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToDeselectSelectedPolygons cmd = new PGOCmdToDeselectSelectedPolygons(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        pgo.getPolygonMgr().getPolygons().addAll(
                pgo.getPolygonMgr().getSelectedPolygons());
        pgo.getPolygonMgr().getSelectedPolygons().clear();
        PGODeformScenario.getSingleton().setPrevPts(null);
        PGODeformScenario.getSingleton().setPrevPt(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
