package PGO.cmd;

import PGO.PGO;
import PGO.PGOPanelMgr;
import x.XApp;
import x.XLoggableCmd;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PGOCmdToSaveWorkToPng extends XLoggableCmd {
    // field
    private boolean mShouldSave = false;

    // private constructor
    private PGOCmdToSaveWorkToPng(XApp app, boolean save) {
        super(app);
        this.mShouldSave = save;
    }

    public static boolean execute(XApp app, boolean save) {
        PGOCmdToSaveWorkToPng cmd = new PGOCmdToSaveWorkToPng(app, save);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        PGOPanelMgr panelMgr = pgo.getPanelMgr();

        pgo.getCanvas2D().remove(panelMgr.getTextLabel());

        if (this.mShouldSave) {
            BufferedImage captured = new BufferedImage(
                    pgo.getCanvas2D().getWidth(),
                    pgo.getCanvas2D().getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = captured.createGraphics();
            pgo.getCanvas2D().printAll(g);
            g.dispose();
            try {
                String[] pathList = panelMgr.getFilePath().split("/");
                String path = "";
                String name = pathList[pathList.length - 1].substring(0,
                        pathList[pathList.length - 1].indexOf("."));
                for (int i = 0; i < pathList.length - 1; i++) {
                    String p = pathList[i];
                    path = path + "/" + p;
                }
                ImageIO.write(captured, "png", new File(
                        path + "/" + name + "_PGO.png"));
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

        pgo.getCanvas2D().setOpaque(false);
        panelMgr.getImagePane().setVisible(true);
        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        if (this.mShouldSave) {
            sb.append("saved image");
        } else {
            sb.append("save cancled");
        }
        return sb.toString();
    }

}
