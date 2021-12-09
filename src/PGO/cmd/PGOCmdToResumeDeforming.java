package PGO.cmd;

import java.util.ArrayList;

import PGO.PGOPolygon;
import PGO.sceanrio.PGODeformScenario;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToResumeDeforming extends XLoggableCmd {
    // private constructor
    private PGOCmdToResumeDeforming(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToResumeDeforming cmd = new PGOCmdToResumeDeforming(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGODeformScenario.getSingleton().setPrevPolygons(new ArrayList<PGOPolygon>());
        PGODeformScenario.getSingleton().getPrevPolygons()
                .add(PGODeformScenario.getSingleton().getPrevPolygon());
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
