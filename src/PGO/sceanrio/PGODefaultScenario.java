package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToAddFirstPt;
import PGO.cmd.PGOCmdToAskBeforeSave;
import PGO.cmd.PGOCmdToSetImageVisibility;
import PGO.cmd.PGOCmdToStartHSBControl;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGODefaultScenario extends XScenario {
    // singleton pattern
    private static PGODefaultScenario mSingleton = null;

    public static PGODefaultScenario createSingleton(XApp app) {
        assert (PGODefaultScenario.mSingleton == null);
        PGODefaultScenario.mSingleton = new PGODefaultScenario(app);
        return PGODefaultScenario.mSingleton;
    }

    public static PGODefaultScenario getSingleton() {
        assert (PGODefaultScenario.mSingleton != null);
        return PGODefaultScenario.mSingleton;
    }

    // contructor
    private PGODefaultScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGODefaultScenario.ReadyScene.createSingleton(this));
        this.addScene(PGODefaultScenario.ImageHideScene.createSingleton(this));
    }

    public static class ReadyScene extends PGOScene {
        // singleton pattern
        private static ReadyScene mSingleton = null;

        public static ReadyScene createSingleton(XScenario scenario) {
            assert (ReadyScene.mSingleton == null);
            ReadyScene.mSingleton = new ReadyScene(scenario);
            return ReadyScene.mSingleton;
        }

        public static ReadyScene getSingleton() {
            assert (ReadyScene.mSingleton != null);
            return ReadyScene.mSingleton;
        }

        private ReadyScene(XScenario scenario) {
            super(scenario);
        }

        // field
        private boolean mCtrlPressed = false;

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToAddFirstPt.execute(pgo, e.getPoint());
            if (pgo.getPolygonMgr().getCurPolygon() != null) {
                XCmdToChangeScene.execute(pgo,
                        PGOCreatePolygonScenario.SetFirstPtScene.getSingleton(),
                        this);
            } else {
                XCmdToChangeScene.execute(pgo,
                        PGODeleteScenario.DeleteReadyScene.getSingleton(),
                        this);
            }
        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.DeformReadyScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_C:
                    PGOCmdToStartHSBControl.execute(pgo);
                    XCmdToChangeScene.execute(pgo,
                            PGOColorScenario.ColorReadyScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_V:
                    PGOCmdToSetImageVisibility.execute(pgo, false);
                    XCmdToChangeScene.execute(pgo,
                            PGODefaultScenario.ImageHideScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_CONTROL:
                    this.mCtrlPressed = true;
                    break;
                case KeyEvent.VK_S:
                    if (this.mCtrlPressed) {
                        PGOCmdToSetImageVisibility.execute(pgo, false);
                        PGOCmdToAskBeforeSave.execute(pgo);

                        XCmdToChangeScene.execute(pgo,
                                PGOSaveScenario.SaveReadyScene.getSingleton(),
                                null);
                    }
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_CONTROL:
                    this.mCtrlPressed = false;
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

    public static class ImageHideScene extends PGOScene {
        // singleton pattern
        private static ImageHideScene mSingleton = null;

        public static ImageHideScene createSingleton(XScenario scenario) {
            assert (ImageHideScene.mSingleton == null);
            ImageHideScene.mSingleton = new ImageHideScene(scenario);
            return ImageHideScene.mSingleton;
        }

        public static ImageHideScene getSingleton() {
            assert (ImageHideScene.mSingleton != null);
            return ImageHideScene.mSingleton;
        }

        private ImageHideScene(XScenario scenario) {
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
                case KeyEvent.VK_V:
                    PGOCmdToSetImageVisibility.execute(pgo, true);
                    XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
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
