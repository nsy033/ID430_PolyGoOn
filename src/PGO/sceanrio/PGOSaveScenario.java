package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToSaveWorkToPng;

import javax.swing.event.ChangeEvent;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_ENTER:
                    // Save current work into .png file.
                    PGOCmdToSaveWorkToPng.execute(pgo, true);
                    XCmdToChangeScene.execute(pgo,
                            PGODefaultScenario.ReadyScene.getSingleton(),
                            null);
                    break;
                case KeyEvent.VK_ESCAPE:
                    // Cancle saving the image.
                    PGOCmdToSaveWorkToPng.execute(pgo, false);
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
