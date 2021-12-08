package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.PGOScene;
import javax.swing.event.ChangeEvent;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOSaveScenario extends XScenario {
    // singleton pattern
    private static PGOSaveScenario mSingleton = null;
    public static PGOSaveScenario createSingleton(XApp app) {
        assert (PGOSaveScenario.mSingleton == null);
        PGOSaveScenario.mSingleton = new PGOSaveScenario(app);
        return PGOSaveScenario.mSingleton;
    }
    public static PGOSaveScenario getSingleton() {
        assert (PGOSaveScenario.mSingleton != null);
        return PGOSaveScenario.mSingleton;
    }
    
    // contructor
    private PGOSaveScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOSaveScenario.SaveReadyScene.createSingleton(this));
    }

    public static class SaveReadyScene extends PGOScene {
        // singleton pattern
        private static SaveReadyScene mSingleton = null;
        public static SaveReadyScene createSingleton(XScenario scenario) {
            assert (SaveReadyScene.mSingleton == null);
            SaveReadyScene.mSingleton = new SaveReadyScene(scenario);
            return SaveReadyScene.mSingleton;
        }
        public static SaveReadyScene getSingleton() {
            assert (SaveReadyScene.mSingleton != null);
            return SaveReadyScene.mSingleton;
        }
        
        private SaveReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPanelMgr panelMgr = pgo.getPanelMgr();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_ENTER:
                    pgo.getCanvas2D().remove(panelMgr.getTextLabel());
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
                        String name = pathList[pathList.length - 1].substring(0, pathList[pathList.length - 1].indexOf("."));
                        for (int i = 0; i < pathList.length - 1; i++) {
                            String p = pathList[i];
                            path = path + "/" + p;
                        }
                        ImageIO.write(captured, "png", new File(
                            path + "/" + name +"_PGO.png"));
                    } catch (IOException exp) {
                        exp.printStackTrace();
                    }
                    pgo.getCanvas2D().setOpaque(false);
                    panelMgr.getImagePane().setVisible(true);
                    
                    XCmdToChangeScene.execute(pgo,
                        PGODefaultScenario.ReadyScene.getSingleton(),
                        null);
                    break;
                case KeyEvent.VK_ESCAPE:
                    pgo.getCanvas2D().remove(panelMgr.getTextLabel());
                    pgo.getCanvas2D().setOpaque(false);
                    panelMgr.getImagePane().setVisible(true);
                    XCmdToChangeScene.execute(pgo,
                        PGODefaultScenario.ReadyScene.getSingleton(),
                        null);
                    break;
            }
        }

        @Override
        public void updateSupportObjects() {
        }

        @Override
        public void renderWorldObjects() {
        }

        @Override
        public void renderScreenObjects(Graphics2D g2) {
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }

        @Override
        public void handleChange(ChangeEvent e) {
        }
    }
}
