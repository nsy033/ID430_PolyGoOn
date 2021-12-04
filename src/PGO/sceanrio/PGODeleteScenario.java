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
        }
        
         public void handleMouseLongPress(Point pressedPt, Point pt) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            boolean contained = false;
            for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
                if (pgo.getCalcMgr().contained(pressedPt, polygon)) {
                    if (pgo.getCalcMgr().contained(pt, polygon)) {
                        pgo.getPolygonMgr().setDraggedPolygon(polygon);
                        pgo.getPolygonMgr().getPolygons().remove(pgo.getPolygonMgr().getDraggedPolygon());

                        scenario.firstLocation = pressedPt;
                        scenario.lastLocation = pressedPt;
                        
                        contained = true;
                        break;
                    } else {
                        break;
                    }
                }
            }
            if (contained) {
                XCmdToChangeScene.execute(pgo,
                            PGODeleteScenario.DeleteScene.getSingleton(),
                            this.mReturnScene);
            } else {
                XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
            }
            
         }

        @Override
        public void handleMouseDrag(MouseEvent e) {
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
        public void handleMouseDrag(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGODeleteScenario scenario = PGODeleteScenario.getSingleton();
            Point pt = e.getPoint();
            if (pgo.getPolygonMgr().getDraggedPolygon() != null) {
                    pgo.getPolygonMgr().getDraggedPolygon().translatePolygon(
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
            
            if ((pt.getX() < pgo.getDeleteArea().getX()) ||
                (pt.getY() < pgo.getDeleteArea().getY())) {
                pgo.getPolygonMgr().getDraggedPolygon().translatePolygon(
                    scenario.firstLocation.getX() - pt.getX(),
                    scenario.firstLocation.getY() - pt.getY());

                pgo.getPolygonMgr().getPolygons().add(pgo.getPolygonMgr().getDraggedPolygon());
                pgo.getPolygonMgr().setDraggedPolygon(null);
                scenario.firstLocation = null;
                scenario.lastLocation = null;
                
                pgo.vibrate();

                XCmdToChangeScene.execute(pgo, this.mReturnScene, null);
            } else {
                pgo.getPolygonMgr().getFixedPts().removeAll(pgo.getPolygonMgr().getDraggedPolygon().getPts());
                pgo.getPolygonMgr().setDraggedPolygon(null);
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
