package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOPolygonMgr;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToCheckAndUpdateSelectedPolygons;
import PGO.cmd.PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate;
import PGO.cmd.PGOCmdToCheckAndUpdateSeparatedPolygon;
import PGO.cmd.PGOCmdToChooseVertex;
import PGO.cmd.PGOCmdToDeselectSelectedPolygons;
import PGO.cmd.PGOCmdToReadyToChooseVertex;
import PGO.cmd.PGOCmdToResumeDeforming;
import PGO.cmd.PGOCmdToSelectMorePolygons;
import PGO.cmd.PGOCmdToSelectPolygons;
import PGO.cmd.PGOCmdToSeparateVertex;
import PGO.cmd.PGOCmdToUpdateSelectedPolygons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGODeformScenario extends XScenario {
    // fields
    private Point mPrevPt = null;

    public Point getPrevPt() {
        return this.mPrevPt;
    }

    public void setPrevPt(Point pt) {
        this.mPrevPt = pt;
    }

    private PGOPolygon mPrevPolygon = null;

    public PGOPolygon getPrevPolygon() {
        return this.mPrevPolygon;
    }

    public void setPrevPolygon(PGOPolygon polygon) {
        this.mPrevPolygon = polygon;
    }

    private ArrayList<Point> mPrevPts = null;

    public ArrayList<Point> getPrevPts() {
        return this.mPrevPts;
    }

    public void setPrevPts(ArrayList<Point> pts) {
        this.mPrevPts = pts;
    }

    private ArrayList<PGOPolygon> mPrevPolygons = null;

    public ArrayList<PGOPolygon> getPrevPolygons() {
        return this.mPrevPolygons;
    }

    public void setPrevPolygons(ArrayList<PGOPolygon> polygons) {
        this.mPrevPolygons = polygons;
    }

    private boolean mAnySelected = false;

    public boolean getAnySelected() {
        return this.mAnySelected;
    }

    public void setAnySelected(boolean state) {
        this.mAnySelected = state;
    }

    private boolean mIsFarEnough = false;

    public boolean getIsFarEnough() {
        return this.mIsFarEnough;
    }

    public void setIsFarEnough(boolean state) {
        this.mIsFarEnough = state;
    }

    // singleton pattern
    private static PGODeformScenario mSingleton = null;

    public static PGODeformScenario createSingleton(XApp app) {
        assert (PGODeformScenario.mSingleton == null);
        PGODeformScenario.mSingleton = new PGODeformScenario(app);
        return PGODeformScenario.mSingleton;
    }

    public static PGODeformScenario getSingleton() {
        assert (PGODeformScenario.mSingleton != null);
        return PGODeformScenario.mSingleton;
    }

    // contructor
    private PGODeformScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGODeformScenario.DeformReadyScene.createSingleton(this));
        this.addScene(PGODeformScenario.DeformScene.createSingleton(this));
        this.addScene(PGODeformScenario.SeparateReadyScene.createSingleton(this));
        this.addScene(PGODeformScenario.ChooseVertexScene.createSingleton(this));
        this.addScene(PGODeformScenario.SeparateScene.createSingleton(this));
    }

    public static class DeformReadyScene extends PGOScene {
        // singleton pattern
        private static DeformReadyScene mSingleton = null;

        public static DeformReadyScene createSingleton(XScenario scenario) {
            assert (DeformReadyScene.mSingleton == null);
            DeformReadyScene.mSingleton = new DeformReadyScene(scenario);
            return DeformReadyScene.mSingleton;
        }

        public static DeformReadyScene getSingleton() {
            assert (DeformReadyScene.mSingleton != null);
            return DeformReadyScene.mSingleton;
        }

        private DeformReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            Point pt = PGOPolygonCalcMgr.findNearPt(e.getPoint(), pgo.getPolygonMgr().getFixedPts());

            if (pgo.getPolygonMgr().getFixedPts().contains(pt)) {
                PGOCmdToSelectPolygons.execute(pgo, pt);

                XCmdToChangeScene.execute(pgo,
                        PGODeformScenario.DeformScene.getSingleton(),
                        this.mReturnScene);
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
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.SeparateReadyScene.getSingleton(),
                            this.mReturnScene);
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_SHIFT:
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

    public static class DeformScene extends PGOScene {
        // singleton pattern
        private static DeformScene mSingleton = null;

        public static DeformScene createSingleton(XScenario scenario) {
            assert (DeformScene.mSingleton == null);
            DeformScene.mSingleton = new DeformScene(scenario);
            return DeformScene.mSingleton;
        }

        public static DeformScene getSingleton() {
            assert (DeformScene.mSingleton != null);
            return DeformScene.mSingleton;
        }

        private DeformScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToUpdateSelectedPolygons.execute(pgo, pt);
            PGOCmdToSelectMorePolygons.execute(pgo, pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToCheckAndUpdateSelectedPolygons.execute(pgo);

            XCmdToChangeScene.execute(pgo,
                    PGODeformScenario.DeformReadyScene.getSingleton(),
                    this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_ALT:
                    PGOCmdToCheckAndUpdateSelectedPolygonsBeforeSeparate.execute(pgo);
                    if (pgo.getPolygonMgr().getSelectedPolygons().isEmpty()) {
                        XCmdToChangeScene.execute(pgo,
                                PGODeformScenario.SeparateReadyScene.getSingleton(),
                                this.mReturnScene);
                    } else {
                        XCmdToChangeScene.execute(pgo,
                                PGODeformScenario.ChooseVertexScene.getSingleton(),
                                this.mReturnScene);
                    }
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_SHIFT:
                    PGOCmdToDeselectSelectedPolygons.execute(pgo);
                    PGODeformScenario.getSingleton().setPrevPolygons(null);
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

    public static class SeparateReadyScene extends PGOScene {
        // singleton pattern
        private static SeparateReadyScene mSingleton = null;

        public static SeparateReadyScene createSingleton(XScenario scenario) {
            assert (SeparateReadyScene.mSingleton == null);
            SeparateReadyScene.mSingleton = new SeparateReadyScene(scenario);
            return SeparateReadyScene.mSingleton;
        }

        public static SeparateReadyScene getSingleton() {
            assert (SeparateReadyScene.mSingleton != null);
            return SeparateReadyScene.mSingleton;
        }

        private SeparateReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            Point pt = PGOPolygonCalcMgr.findNearPt(e.getPoint(), pgo.getPolygonMgr().getFixedPts());

            if (pgo.getPolygonMgr().getFixedPts().contains(pt)) {
                PGOCmdToReadyToChooseVertex.execute(pgo, pt);
                if (PGODeformScenario.getSingleton().getPrevPt() != null) {
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.ChooseVertexScene.getSingleton(),
                            this.mReturnScene);
                }
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
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ALT:
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.DeformReadyScene.getSingleton(),
                            this.mReturnScene);
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

    public static class ChooseVertexScene extends PGOScene {
        // singleton pattern
        private static ChooseVertexScene mSingleton = null;

        public static ChooseVertexScene createSingleton(XScenario scenario) {
            assert (ChooseVertexScene.mSingleton == null);
            ChooseVertexScene.mSingleton = new ChooseVertexScene(scenario);
            return ChooseVertexScene.mSingleton;
        }

        public static ChooseVertexScene getSingleton() {
            assert (ChooseVertexScene.mSingleton != null);
            return ChooseVertexScene.mSingleton;
        }

        private ChooseVertexScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();

            PGOCmdToChooseVertex.execute(pgo, pt);
            if (PGODeformScenario.getSingleton().getIsFarEnough()) {
                if (PGODeformScenario.getSingleton().getAnySelected()) {
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.SeparateScene.getSingleton(),
                            this.mReturnScene);
                } else {
                    PGOCmdToDeselectSelectedPolygons.execute(pgo);
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.SeparateReadyScene.getSingleton(),
                            this.mReturnScene);
                }
            }

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
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ALT:
                    PGOCmdToDeselectSelectedPolygons.execute(pgo);
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.DeformReadyScene.getSingleton(),
                            this.mReturnScene);
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

    public static class SeparateScene extends PGOScene {
        // singleton pattern
        private static SeparateScene mSingleton = null;

        public static SeparateScene createSingleton(XScenario scenario) {
            assert (SeparateScene.mSingleton == null);
            SeparateScene.mSingleton = new SeparateScene(scenario);
            return SeparateScene.mSingleton;
        }

        public static SeparateScene getSingleton() {
            assert (SeparateScene.mSingleton != null);
            return SeparateScene.mSingleton;
        }

        private SeparateScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToSeparateVertex.execute(pgo, pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToCheckAndUpdateSeparatedPolygon.execute(pgo);

            XCmdToChangeScene.execute(pgo,
                    PGODeformScenario.SeparateReadyScene.getSingleton(),
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
                case KeyEvent.VK_SHIFT:
                    XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
                    break;
                case KeyEvent.VK_ALT:
                    PGOCmdToResumeDeforming.execute(pgo);
                    XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.DeformScene.getSingleton(),
                            this.mReturnScene);
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
            throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
                                                                           // choose Tools | Templates.
        }
    }
}
