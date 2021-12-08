package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOCanvas2D;
import PGO.PGOPanelMgr;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import PGO.PGOScene;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
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

    // private contructor
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

        private boolean mCtrlPressed = false;

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();
            if (!pgo.getCalcMgr().contained(pt, polygons)) {
                pgo.getPolygonMgr().createCurPolygon(pt,
                        pgo.getCanvas2D().getCurColorForPolygon(),
                        pgo.getCanvas2D().getCurStrokeForPolygon());

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
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPanelMgr panelMgr = pgo.getPanelMgr();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.DeformReadyScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_C:
                    Dimension prevSize = pgo.getFrame().getSize();
                    pgo.getFrame().setSize(prevSize.width,
                            // prevSize.height + pgo.getPanelMgr().getHSBPanel().getHeight());
                            prevSize.height + pgo.getDeltaFrameHeight());
                    panelMgr.getHSBPanel().setVisible(true);
                    XCmdToChangeScene.execute(pgo,
                            PGOColorScenario.ColorReadyScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_V:
                    panelMgr.getImagePane().setVisible(false);
                    XCmdToChangeScene.execute(pgo,
                            PGODefaultScenario.ImageHideScene.getSingleton(),
                            this);
                    break;
                case KeyEvent.VK_CONTROL:
                    this.mCtrlPressed = true;
                    break;
                case KeyEvent.VK_S:
                    if (this.mCtrlPressed) {
                        panelMgr.getImagePane().setVisible(false);

                        panelMgr.setTextLabel(new JLabel("Press Enter to save your art"));
                        panelMgr.getTextLabel().setFont(PGOCanvas2D.FONT_INFO);
                        panelMgr.getTextLabel().setVerticalAlignment(JLabel.CENTER);
                        panelMgr.getTextLabel().setHorizontalAlignment(JLabel.CENTER);

                        pgo.getCanvas2D().setOpaque(true);
                        pgo.getCanvas2D().add(panelMgr.getTextLabel());

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
        public void handleMouseDrag(MouseEvent e) {
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
                    pgo.getPanelMgr().getImagePane().setVisible(true);
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
