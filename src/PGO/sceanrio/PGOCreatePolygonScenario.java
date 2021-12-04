package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOCalcMgr;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import PGO.PGOScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOCreatePolygonScenario extends XScenario {
    // The template for JSIScenario.
    
    // singleton pattern
    private static PGOCreatePolygonScenario mSingleton = null;
    public static PGOCreatePolygonScenario createSingleton(XApp app) {
        assert (PGOCreatePolygonScenario.mSingleton == null);
        PGOCreatePolygonScenario.mSingleton = new PGOCreatePolygonScenario(app);
        return PGOCreatePolygonScenario.mSingleton;
    }
    public static PGOCreatePolygonScenario getSingleton() {
        assert (PGOCreatePolygonScenario.mSingleton != null);
        return PGOCreatePolygonScenario.mSingleton;
    }
    
    // private contructor
    private PGOCreatePolygonScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOCreatePolygonScenario.SetFirstPtScene.createSingleton(this));
        this.addScene(PGOCreatePolygonScenario.SecondPtReadyScene.createSingleton(this));
        this.addScene(PGOCreatePolygonScenario.SetSecondPtScene.createSingleton(this));
        this.addScene(PGOCreatePolygonScenario.ThirdPtReadyScene.createSingleton(this));
        this.addScene(PGOCreatePolygonScenario.SetThirdPtScene.createSingleton(this));
    }

    public static class SetFirstPtScene extends PGOScene {
        // singleton pattern
        private static SetFirstPtScene mSingleton = null;
        public static SetFirstPtScene createSingleton(XScenario scenario) {
            assert (SetFirstPtScene.mSingleton == null);
            SetFirstPtScene.mSingleton = new SetFirstPtScene(scenario);
            return SetFirstPtScene.mSingleton;
        }
        public static SetFirstPtScene getSingleton() {
            assert (SetFirstPtScene.mSingleton != null);
            return SetFirstPtScene.mSingleton;
        }
        
        private SetFirstPtScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            
        }

        @Override
        public synchronized void handleMouseDrag(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();
            if (!pgo.getCalcMgr().contained(pt, polygons)) {
                pt = pgo.findNearPt(pt);
                polygonMgr.getCurPolygon().updatePolygon(pt);
            }
            
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
//            System.out.println(pgo.getPolygonMgr().getCurPolygon().getPts().get(0));
            XCmdToChangeScene.execute(pgo,
                PGOCreatePolygonScenario.SecondPtReadyScene.getSingleton(),
                this.mReturnScene);
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
    
    public static class SecondPtReadyScene extends PGOScene {
        // singleton pattern
        private static SecondPtReadyScene mSingleton = null;
        public static SecondPtReadyScene createSingleton(XScenario scenario) {
            assert (SecondPtReadyScene.mSingleton == null);
            SecondPtReadyScene.mSingleton = new SecondPtReadyScene(scenario);
            return SecondPtReadyScene.mSingleton;
        }
        public static SecondPtReadyScene getSingleton() {
            assert (SecondPtReadyScene.mSingleton != null);
            return SecondPtReadyScene.mSingleton;
        }
        
        private SecondPtReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();
            Line2D.Double line = new Line2D.Double();
            line.setLine((Point2D) pt, (Point2D) polygonMgr.getCurPolygon().getPts().get(0));
            
            boolean flag = true;
            
            for (PGOPolygon polygon: polygons) {
                if (pgo.getCalcMgr().contained((Point2D) pt, polygon))
                    flag = false;
            }
                    
            if (flag && !pgo.getCalcMgr().crossed(line, polygons)) {
                polygonMgr.getCurPolygon().addPt(pt);
                
                XCmdToChangeScene.execute(pgo,
                    PGOCreatePolygonScenario.SetSecondPtScene.getSingleton(),
                    this.mReturnScene);
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
                case KeyEvent.VK_ESCAPE:
                    pgo.getPolygonMgr().setCurPolygon(null);
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
    
    public static class SetSecondPtScene extends PGOScene {
        // singleton pattern
        private static SetSecondPtScene mSingleton = null;
        public static SetSecondPtScene createSingleton(XScenario scenario) {
            assert (SetSecondPtScene.mSingleton == null);
            SetSecondPtScene.mSingleton = new SetSecondPtScene(scenario);
            return SetSecondPtScene.mSingleton;
        }
        public static SetSecondPtScene getSingleton() {
            assert (SetSecondPtScene.mSingleton != null);
            return SetSecondPtScene.mSingleton;
        }
        
        private SetSecondPtScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public synchronized void handleMouseDrag(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();
            Line2D.Double line = new Line2D.Double();
            line.setLine((Point2D) pt, (Point2D) polygonMgr.getCurPolygon().getPts().get(0));
            
            boolean flag = true;

            for (PGOPolygon polygon: polygons) {
                if (pgo.getCalcMgr().contained((Point2D) pt, polygon))
                    flag = false;
            }

            if (flag && !pgo.getCalcMgr().crossed(line, polygons)) {
                pt = pgo.findNearPt(pt);
                polygonMgr.getCurPolygon().updatePolygon(pt);
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
//            System.out.println(pgo.getPolygonMgr().getCurPolygon().getPts().get(1));
            XCmdToChangeScene.execute(pgo,
                PGOCreatePolygonScenario.ThirdPtReadyScene.getSingleton(),
                this.mReturnScene);
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
    
    public static class ThirdPtReadyScene extends PGOScene {
        // singleton pattern
        private static ThirdPtReadyScene mSingleton = null;
        public static ThirdPtReadyScene createSingleton(XScenario scenario) {
            assert (ThirdPtReadyScene.mSingleton == null);
            ThirdPtReadyScene.mSingleton = new ThirdPtReadyScene(scenario);
            return ThirdPtReadyScene.mSingleton;
        }
        public static ThirdPtReadyScene getSingleton() {
            assert (ThirdPtReadyScene.mSingleton != null);
            return ThirdPtReadyScene.mSingleton;
        }
        
        private ThirdPtReadyScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();
            
            ArrayList<Point> tempPts = polygonMgr.getCurPolygon().getPts();
            Color tempC = polygonMgr.getCurPolygon().getColor();
            Stroke tempS = polygonMgr.getCurPolygon().getStroke();
            PGOPolygon tempPolygon = new PGOPolygon(tempPts.get(0), tempC, tempS);
            tempPolygon.addPt(tempPts.get(1));
            tempPolygon.addPt(pt);
            
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            for (PGOPolygon polygon: polygons) {
                if (pgo.getCalcMgr().contained((Point2D) pt, polygon))
                    flag1 = false;
            }
            if (pgo.getCalcMgr().intersected(tempPolygon, polygons)) {
                flag2 = false;
            }
            if (pgo.getCalcMgr().overlapped(tempPolygon, polygons)) {
                flag3 = false;
            }

            if (flag1 && flag2 && flag3) {
                polygonMgr.getCurPolygon().addPt(pt);
                
                XCmdToChangeScene.execute(pgo,
                    PGOCreatePolygonScenario.SetThirdPtScene.getSingleton(),
                    this.mReturnScene);
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
                case KeyEvent.VK_ESCAPE:
                    pgo.getPolygonMgr().setCurPolygon(null);
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
    
    public static class SetThirdPtScene extends PGOScene {
        // singleton pattern
        private static SetThirdPtScene mSingleton = null;
        public static SetThirdPtScene createSingleton(XScenario scenario) {
            assert (SetThirdPtScene.mSingleton == null);
            SetThirdPtScene.mSingleton = new SetThirdPtScene(scenario);
            return SetThirdPtScene.mSingleton;
        }
        public static SetThirdPtScene getSingleton() {
            assert (SetThirdPtScene.mSingleton != null);
            return SetThirdPtScene.mSingleton;
        }
        
        private SetThirdPtScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public synchronized void handleMouseDrag(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point pt = e.getPoint();
            ArrayList<PGOPolygon> polygons = polygonMgr.getPolygons();

            PGOPolygon tempPolygon = polygonMgr.getCurPolygon().clonePolygon();
            tempPolygon.updatePolygon(pt);
            
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            
            for (PGOPolygon polygon: polygons) {
                if (pgo.getCalcMgr().contained((Point2D) pt, polygon))
                    flag1 = false;
            }
            if (pgo.getCalcMgr().intersected(tempPolygon, polygons)) {
                flag2 = false;
            }
            if (pgo.getCalcMgr().overlapped(tempPolygon, polygons)) {
                flag3 = false;
            }

            if (flag1 && flag2 && flag3) {
                pt = pgo.findNearPt(pt);
                polygonMgr.getCurPolygon().updatePolygon(pt);
            }
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            
            if (pgo.getCalcMgr().validPolygon(polygonMgr.getCurPolygon())) {
                Color c = polygonMgr.getCurPolygon().getBgColor(pgo);
            
                polygonMgr.getCurPolygon().setColor(c);
                polygonMgr.getPolygons().add(polygonMgr.getCurPolygon());
                polygonMgr.getFixedPts().addAll(polygonMgr.getCurPolygon().getPts());
            } else {
                pgo.vibrate();
            }
            
            
            polygonMgr.setCurPolygon(null);
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
