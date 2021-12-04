package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOScene;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGODeleteScenario extends XScenario {
    // The template for JSIScenario.
    
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
    
    // private contructor
    private PGODeleteScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGODeleteScenario.DeleteReadyScene.createSingleton(this));
        this.addScene(PGODeleteScenario.DeleteScene.createSingleton(this));
    }
    
    
    private PGOPolygon draggedPolygon = null;
    private Point firstLocation = null;
    private Point lastLocation = null;

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
            PGO pgo = (PGO) this.mScenario.getApp();
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            Point pt = e.getPoint();
            Point2D.Double pt2d = new Point2D.Double((double) pt.x, (double) pt.y);
            for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
                if (pgo.getCalcMgr().contained(pt2d, polygon)) {
                    scenario.draggedPolygon = polygon;
                    scenario.firstLocation = pt;
                    scenario.lastLocation = pt;
                    
                    XCmdToChangeScene.execute(pgo,
                        PGODeleteScenario.DeleteScene.getSingleton(),
                        this.mReturnScene);
                    break;
                }
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
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_D:
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
        public void handleMouseDrag(MouseEvent e) {
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            Point pt = e.getPoint();
            if (scenario.draggedPolygon != null) {
                    scenario.draggedPolygon.translatePolygon(
                        pt.getX() - scenario.lastLocation.getX(),
                        pt.getY() - scenario.lastLocation.getY());
                    scenario.lastLocation = pt;
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            Point pt = e.getPoint();
            
            if ((pt.getX() < 700) || (pt.getY() < 500)) {
                scenario.draggedPolygon.translatePolygon(
                    scenario.firstLocation.getX() - pt.getX(),
                    scenario.firstLocation.getY() - pt.getY());

                scenario.draggedPolygon = null;
                scenario.firstLocation = null;
                scenario.lastLocation = null;
                
                pgo.vibrate();

                XCmdToChangeScene.execute(pgo,
                    PGODeleteScenario.DeleteReadyScene.getSingleton(),
                    this.mReturnScene);
            } else {
                pgo.getPolygonMgr().getPolygons().remove(scenario.draggedPolygon);
                pgo.getPolygonMgr().getFixedPts().removeAll(scenario.draggedPolygon.getPts());
                scenario.draggedPolygon = null;
                scenario.firstLocation = null;
                scenario.lastLocation = null;

                XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
            }
            
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_D:
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
