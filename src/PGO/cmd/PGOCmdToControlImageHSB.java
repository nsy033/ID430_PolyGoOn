package PGO.cmd;

import PGO.PGO;
import java.util.ArrayList;
import javax.swing.JSlider;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToControlImageHSB extends XLoggableCmd {
    // fields
    float hue = Float.NaN;
    float sat = Float.NaN;
    float bri = Float.NaN;
    
    // constructor
    private PGOCmdToControlImageHSB(XApp app) {
        super(app);
    }
    
    public static boolean execute(XApp app) {
        PGOCmdToControlImageHSB cmd = new PGOCmdToControlImageHSB(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        ArrayList<JSlider> hsbSliders = pgo.getPanelMgr().getHSBSliders();
        hue = (float) hsbSliders.get(0).getValue() / 360f;
        sat = (float) hsbSliders.get(1).getValue() / 255f;
        bri = (float) hsbSliders.get(2).getValue() / 255f;
        System.out.println(hue);
        pgo.getColorCalcMgr().changeImageHSB(hue, sat, bri);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.hue).append("\t");
        sb.append(this.sat).append("\t");
        sb.append(this.bri);
        return sb.toString();
    }
    
}
