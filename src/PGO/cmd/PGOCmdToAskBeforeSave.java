package PGO.cmd;

import javax.swing.JLabel;

import PGO.PGO;
import PGO.PGOCanvas2D;
import PGO.PGOPanelMgr;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToAskBeforeSave extends XLoggableCmd {
    // private constructor
    private PGOCmdToAskBeforeSave(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToAskBeforeSave cmd = new PGOCmdToAskBeforeSave(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPanelMgr panelMgr = pgo.getPanelMgr();
        panelMgr.setTextLabel(new JLabel("Press Enter to save your art"));
        panelMgr.getTextLabel().setFont(PGOCanvas2D.FONT_INFO);
        panelMgr.getTextLabel().setVerticalAlignment(JLabel.CENTER);
        panelMgr.getTextLabel().setHorizontalAlignment(JLabel.CENTER);

        pgo.getCanvas2D().setOpaque(true);
        pgo.getCanvas2D().add(panelMgr.getTextLabel());
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
