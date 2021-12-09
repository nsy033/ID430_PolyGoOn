package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToControlImageHSB;
import PGO.cmd.PGOCmdToWrapUpHSBControl;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;
import x.XScene;

public class PGOColorScenario extends XScenario {
    // singleton pattern
    private static PGOColorScenario mSingleton = null;

    public static PGOColorScenario createSingleton(XApp app) {
        assert (PGOColorScenario.mSingleton == null);
        PGOColorScenario.mSingleton = new PGOColorScenario(app);
        return PGOColorScenario.mSingleton;
    }

    public static PGOColorScenario getSingleton() {
        assert (PGOColorScenario.mSingleton != null);
        return PGOColorScenario.mSingleton;
    }

    // contructor
    private PGOColorScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGOColorScenario.ColorReadyScene.createSingleton(this));
        this.addScene(PGOColorScenario.HueChangeScene.createSingleton(this));
        this.addScene(PGOColorScenario.SatChangeScene.createSingleton(this));
        this.addScene(PGOColorScenario.BriChangeScene.createSingleton(this));
    }

    public static class ColorReadyScene extends PGOScene {
        // singleton pattern
        private static ColorReadyScene mSingleton = null;

        public static ColorReadyScene createSingleton(XScenario scenario) {
            assert (ColorReadyScene.mSingleton == null);
            ColorReadyScene.mSingleton = new ColorReadyScene(scenario);
            return ColorReadyScene.mSingleton;
        }

        public static ColorReadyScene getSingleton() {
            assert (ColorReadyScene.mSingleton != null);
            return ColorReadyScene.mSingleton;
        }

        private ColorReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            XScene nextScene = null;
            
            if (e.getSource() == pgo.getCanvas2D()) {
                return;
            }
            
            JSlider selectedSlider = (JSlider) e.getSource();
            switch (selectedSlider.getName()) {
                case "0" :
                    XCmdToChangeScene.execute(pgo, 
                        PGOColorScenario.HueChangeScene.getSingleton(),
                        this.mReturnScene);
                    break;
                case "1" :
                    XCmdToChangeScene.execute(pgo, 
                        PGOColorScenario.SatChangeScene.getSingleton(),
                        this.mReturnScene);
                    break;
                case "2" :
                    XCmdToChangeScene.execute(pgo, 
                        PGOColorScenario.BriChangeScene.getSingleton(),
                        this.mReturnScene);
                    break;
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
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_C:
                    PGOCmdToWrapUpHSBControl.execute(pgo);
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
            PGO pgo = (PGO) this.mScenario.getApp();
            pgo.getSliderMgr().getHSBPanel().setVisible(true);
            pgo.getFrame().revalidate();
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
    }

    public static class HueChangeScene extends PGOScene {
        // singleton pattern
        private static HueChangeScene mSingleton = null;

        public static HueChangeScene createSingleton(XScenario scenario) {
            assert (HueChangeScene.mSingleton == null);
            HueChangeScene.mSingleton = new HueChangeScene(scenario);
            return HueChangeScene.mSingleton;
        }

        public static HueChangeScene getSingleton() {
            assert (HueChangeScene.mSingleton != null);
            return HueChangeScene.mSingleton;
        }

        private HueChangeScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToControlImageHSB.execute(pgo);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            XCmdToChangeScene.execute(pgo,
                    PGOColorScenario.ColorReadyScene.getSingleton(),
                    this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    PGOCmdToWrapUpHSBControl.execute(pgo);
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
    }

    public static class SatChangeScene extends PGOScene {
        // singleton pattern
        private static SatChangeScene mSingleton = null;

        public static SatChangeScene createSingleton(XScenario scenario) {
            assert (SatChangeScene.mSingleton == null);
            SatChangeScene.mSingleton = new SatChangeScene(scenario);
            return SatChangeScene.mSingleton;
        }

        public static SatChangeScene getSingleton() {
            assert (SatChangeScene.mSingleton != null);
            return SatChangeScene.mSingleton;
        }

        private SatChangeScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToControlImageHSB.execute(pgo);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            XCmdToChangeScene.execute(pgo,
                PGOColorScenario.ColorReadyScene.getSingleton(),
                this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    PGOCmdToWrapUpHSBControl.execute(pgo);
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
    }

    public static class BriChangeScene extends PGOScene {
        // singleton pattern
        private static BriChangeScene mSingleton = null;

        public static BriChangeScene createSingleton(XScenario scenario) {
            assert (BriChangeScene.mSingleton == null);
            BriChangeScene.mSingleton = new BriChangeScene(scenario);
            return BriChangeScene.mSingleton;
        }

        public static BriChangeScene getSingleton() {
            assert (BriChangeScene.mSingleton != null);
            return BriChangeScene.mSingleton;
        }

        private BriChangeScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToControlImageHSB.execute(pgo);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            XCmdToChangeScene.execute(pgo,
                    PGOColorScenario.ColorReadyScene.getSingleton(),
                    this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    PGOCmdToWrapUpHSBControl.execute(pgo);
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
    }
}
