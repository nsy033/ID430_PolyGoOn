package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonCalcMgr;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToDeletePolygon;
import PGO.cmd.PGOCmdToDetectLongPressedPolygon;
import PGO.cmd.PGOCmdToDragPolygon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGODeleteScenario extends XScenario {
    // singleton pattern
    private static PGODeleteScenario mSingleton = null;

    public static PGODeleteScenario createSingleton(XApp app) {
        assert (PGODeleteScenario.mSingleton == null);
        PGODeleteScenario.mSingleton = new PGODeleteScenario(app);
        return PGODeleteScenario.mSingleton;
    }

    public static PGODeleteScenario getSingleton() {
        assert (PGODeleteScenario.mSingleton != null);
        return PGODeleteScenario.mSingleton;
    }

    // contructor
    private PGODeleteScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGODeleteScenario.DeleteReadyScene.createSingleton(this));
        this.addScene(PGODeleteScenario.DeleteScene.createSingleton(this));
    }

    private PGOPolygon draggedPolygon = null;

    public PGOPolygon getDraggedPolygon() {
        return this.draggedPolygon;
    }

    public void setDraggedPolygon(PGOPolygon polygon) {
        this.draggedPolygon = polygon;
    }

    private Point firstLocation = null;

    public Point getFirstLocation() {
        return this.firstLocation;
    }

    public void setFirstLocation(Point fst) {
        this.firstLocation = fst;
    }

    private Point lastLocation = null;

    public Point getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation(Point lst) {
        this.lastLocation = lst;
    }

    private boolean contained = false;

    public boolean getContained() {
        return this.contained;
    }

    public void setContained(boolean state) {
        this.contained = state;
    }

    public static class DeleteReadyScene extends PGOScene {
        // singleton pattern
        private static DeleteReadyScene mSingleton = null;

        public static DeleteReadyScene createSingleton(XScenario scenario) {
            assert (DeleteReadyScene.mSingleton == null);
            DeleteReadyScene.mSingleton = new DeleteReadyScene(scenario);
            return DeleteReadyScene.mSingleton;
        }

        public static DeleteReadyScene getSingleton() {
            assert (DeleteReadyScene.mSingleton != null);
            return DeleteReadyScene.mSingleton;
        }

        private DeleteReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        public void handleMouseLongPress(Point pressedPt, Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            PGOCmdToDetectLongPressedPolygon.execute(pgo, pressedPt, pt);
            if (scenario.getContained()) {
                XCmdToChangeScene.execute(pgo,
                        PGODeleteScenario.DeleteScene.getSingleton(),
                        this.mReturnScene);
            } else {
                XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
            }

        }

        @Override
        public void handleMouseDrag(Point pt) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
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

    public static class DeleteScene extends PGOScene {
        // singleton pattern
        private static DeleteScene mSingleton = null;

        public static DeleteScene createSingleton(XScenario scenario) {
            assert (DeleteScene.mSingleton == null);
            DeleteScene.mSingleton = new DeleteScene(scenario);
            return DeleteScene.mSingleton;
        }

        public static DeleteScene getSingleton() {
            assert (DeleteScene.mSingleton != null);
            return DeleteScene.mSingleton;
        }

        private DeleteScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToDragPolygon.execute(pgo, pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToDeletePolygon.execute(pgo, e.getPoint());
            XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
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
