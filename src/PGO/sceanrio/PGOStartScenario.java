package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToSetFinalImage;
import PGO.cmd.PGOCmdToSetImage;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOStartScenario extends XScenario {
    // fields
    private JLabel mPrevImage = null;

    public JLabel getPrevImage() {
        return this.mPrevImage;
    };

    public void setPrevImage(JLabel imageLabel) {
        this.mPrevImage = imageLabel;
    }

    private String mPrevPath = null;

    public String getPrevPath() {
        return this.mPrevPath;
    }

    public void setPrevPath(String path) {
        this.mPrevPath = path;
    }

    // singleton pattern
    private static PGOStartScenario mSingleton = null;

    public static PGOStartScenario createSingleton(XApp app) {
        assert (PGOStartScenario.mSingleton == null);
        PGOStartScenario.mSingleton = new PGOStartScenario(app);
        return PGOStartScenario.mSingleton;
    }

    public static PGOStartScenario getSingleton() {
        assert (PGOStartScenario.mSingleton != null);
        return PGOStartScenario.mSingleton;
    }

    // contructor
    private PGOStartScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGOStartScenario.ImageReadyScene.createSingleton(this));
    }

    public static class ImageReadyScene extends PGOScene {
        // singleton pattern
        private static ImageReadyScene mSingleton = null;

        public static ImageReadyScene createSingleton(XScenario scenario) {
            assert (ImageReadyScene.mSingleton == null);
            ImageReadyScene.mSingleton = new ImageReadyScene(scenario);
            return ImageReadyScene.mSingleton;
        }

        public static ImageReadyScene getSingleton() {
            assert (ImageReadyScene.mSingleton != null);
            return ImageReadyScene.mSingleton;
        }

        private ImageReadyScene(XScenario scenario) {
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
            PGOStartScenario scenario = (PGOStartScenario) this.mScenario;
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_ENTER:
                    if (scenario.getPrevImage() != null) {
                        PGOCmdToSetFinalImage.execute(pgo);
                        XCmdToChangeScene.execute(pgo,
                                PGODefaultScenario.ReadyScene.getSingleton(), null);
                    }
                    break;
            }
        }

        public void handleDragDrop(DropTargetDropEvent ev) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToSetImage.execute(pgo, ev);
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
