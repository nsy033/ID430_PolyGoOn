package PGO.cmd;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.sceanrio.PGOStartScenario;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSetImage extends XLoggableCmd {
    // fields
    private String mFileName = null;

    // private constructor
    private PGOCmdToSetImage(XApp app) {
        super(app);
        this.mFileName = PGOStartScenario.FileReadyScene.getSingleton().getFilePath();
    }

    public static boolean execute(XApp app) {
        PGOCmdToSetImage cmd = new PGOCmdToSetImage(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        displayImage(PGOStartScenario.FileReadyScene.getSingleton().getFilePath());
        return true;
    }

    private void displayImage(String path) {
        PGO pgo = (PGO) this.mApp;
        System.out.println(path);
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();

        if (scenario.getPrevImage() == null) {
            panelMgr.getImagePane().remove(panelMgr.getTextLabel());
            scenario.setPrevPath(path);
            panelMgr.setImageLabel(path);
        } else {
            if (!scenario.getPrevPath().equals(path)) {
                panelMgr.getImagePane().remove(scenario.getPrevImage());
                scenario.setPrevImage(null);
                scenario.setPrevPath(path);
                panelMgr.setImageLabel(path);
            }
        }
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.mFileName);
        return sb.toString();
    }

}
