package PGO.cmd;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.sceanrio.PGOStartScenario;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToSetFinalImage extends XLoggableCmd {
    // private constructor
    private PGOCmdToSetFinalImage(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToSetFinalImage cmd = new PGOCmdToSetFinalImage(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();
        int width = pgo.getPanelMgr().getImagePane().getWidth();
        int height = pgo.getPanelMgr().getImagePane().getHeight();

        // Set image and prepare for drawing.
        panelMgr.setImageLabel(scenario.getPrevImage());
        panelMgr.getTranslucentPane().setVisible(true);
        panelMgr.setFilePath(scenario.getPrevPath());

        // Set the position and size of delete area.
        pgo.setDeleteArea(new Ellipse2D.Double(
                (double) width - 0.5 * height,
                (double) height - 0.5 * height,
                height, height));
        pgo.getCanvas2D().setDeleteAreaPaint(
                new Point2D.Float(width, height), height);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName());
        return sb.toString();
    }

}
