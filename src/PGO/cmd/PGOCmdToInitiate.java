package PGO.cmd;

import javax.swing.UIManager;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.sceanrio.PGOStartScenario;

import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToInitiate extends XLoggableCmd {
    // private constructor
    private PGOCmdToInitiate(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToInitiate cmd = new PGOCmdToInitiate(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();
        panelMgr.getImagePane().add(panelMgr.getTextLabel());
        scenario.setPrevPath(null);
        scenario.setPrevImage(null);
        panelMgr.setFileName("image with invalid path");
        
        pgo.getFrame().setSize(PGO.DEFAULT_WINDOW_WIDTH, PGO.DEFAULT_WINDOW_HEIGHT);
        pgo.getFrame().setLocationRelativeTo(null);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
