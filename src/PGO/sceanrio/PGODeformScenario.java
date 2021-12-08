package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOPolygonMgr;
import PGO.PGOScene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
    
    // private contructor
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
            Point pt = pgo.findNearPt(e.getPoint());
            Rectangle boundingBox = new Rectangle(pt.x - 100, pt.y - 100, 200, 200);
            ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();
            PGODeformScenario.getSingleton().setPrevPolygons(new ArrayList<PGOPolygon>());
            PGODeformScenario.getSingleton().setPrevPts((ArrayList<Point>) pgo.getPolygonMgr().getFixedPts().clone());
            
            if (pgo.getPolygonMgr().getFixedPts().contains(pt)) {
                for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {                
                    if (boundingBox.intersects(polygon.getBoundingBox())) {
                        PGOPolygon tmpPolygon = polygon.clonePolygon();
                        if (polygon.getPts().contains(pt)) {
                            newSelectedPolygons.add(polygon);
                            PGODeformScenario.getSingleton().getPrevPolygons().add(tmpPolygon);
                        }
                    }
                }
                PGODeformScenario.getSingleton().setPrevPt(pt);
//            pgo.getPolygonMgr().setSelectedPt(
//                pgo.findNearPt(pt, pgo.getPolygonMgr().getFixedPts()));
                
                pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
                pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
                newSelectedPolygons.clear();
                
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
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            // Point pt = e.getPoint();
            Point prevPt = PGODeformScenario.getSingleton().getPrevPt();
            
            for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(prevPt);
                polygon.updateSelectedPt(pt, prevPt);
                polygon.updateBoundingBox(polygon.getPts());
                polygonMgr.getFixedPts().set(ptIndexInFixedPts, pt);
            }
            
            Point nearPt = pgo.findNearPt(pt);
            Rectangle boundingBox = new Rectangle(nearPt.x - 100, nearPt.y - 100, 200, 200);
            ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();
            if (pgo.getPolygonMgr().getFixedPts().contains(nearPt)) {
                for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {
                    if (boundingBox.intersects(polygon.getBoundingBox())) {
                        PGOPolygon tmpPolygon = polygon.clonePolygon();
                        if (polygon.getPts().contains(nearPt)) {
                            newSelectedPolygons.add(polygon);
                            PGODeformScenario.getSingleton().getPrevPolygons().add(tmpPolygon);
                            
                            int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(nearPt);
                            polygon.updateSelectedPt(pt, nearPt);
                            polygon.updateBoundingBox(polygon.getPts());
                            polygonMgr.getFixedPts().set(ptIndexInFixedPts, pt);
                        }
                    }
                }
                pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
                pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
                newSelectedPolygons.clear();
            }
           
            // pgo.getPolygonMgr().setSelectedPt(pt); 
            PGODeformScenario.getSingleton().setPrevPt(pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            boolean anyIntersected = false;
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            ArrayList<PGOPolygon> polygons = (ArrayList<PGOPolygon>) polygonMgr.getSelectedPolygons().clone();
            polygons.addAll((ArrayList<PGOPolygon>) polygonMgr.getPolygons().clone());
            ArrayList<PGOPolygon> polygons2 = (ArrayList<PGOPolygon>) polygons.clone();
            for (PGOPolygon polygon : polygons) {
                polygons2.remove(polygon);
                if (pgo.getCalcMgr().intersected(polygon, polygons2)) {
                    anyIntersected = true;
                    polygons2.add(polygon);
                    break;
                }
                polygons2.add(polygon);
            }
            if (anyIntersected || !pgo.getCalcMgr().validPolygons(
                polygonMgr.getSelectedPolygons())) {
                polygonMgr.getPolygons().addAll((ArrayList<PGOPolygon>) PGODeformScenario.getSingleton().getPrevPolygons().clone());
                polygonMgr.setFixedPts((ArrayList<Point>) PGODeformScenario.getSingleton().getPrevPts().clone());
                
                pgo.vibrate();
            } else {
                pgo.getPolygonMgr().getPolygons().addAll(
                    polygonMgr.getSelectedPolygons());

                for (PGOPolygon polygon : polygonMgr.getPolygons()) {
                    Color c = polygon.getBgColor(pgo);
                    polygon.setColor(c);
                }
            }
            
            pgo.getPolygonMgr().getSelectedPolygons().clear();
            PGODeformScenario.getSingleton().setPrevPolygons(null);
            PGODeformScenario.getSingleton().setPrevPts(null);
            PGODeformScenario.getSingleton().setPrevPt(null);
            XCmdToChangeScene.execute(pgo,
                PGODeformScenario.DeformReadyScene.getSingleton(),
                this.mReturnScene);
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();
            boolean anyIntersected = false;
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            
            switch (code) {
                case KeyEvent.VK_ALT:
                    ArrayList<PGOPolygon> polygons = (ArrayList<PGOPolygon>) polygonMgr.getSelectedPolygons().clone();
                    polygons.addAll((ArrayList<PGOPolygon>) polygonMgr.getPolygons().clone());
                    ArrayList<PGOPolygon> polygons2 = (ArrayList<PGOPolygon>) polygons.clone();
                    for (PGOPolygon polygon : polygons) {
                        polygons2.remove(polygon);
                        if (pgo.getCalcMgr().intersected(polygon, polygons2)) {
                            anyIntersected = true;
                            polygons2.add(polygon);
                            break;
                        }
                        polygons2.add(polygon);
                    }
                    if (anyIntersected || !pgo.getCalcMgr().validPolygons(
                        polygonMgr.getSelectedPolygons())) {
                        polygonMgr.getPolygons().addAll((ArrayList<PGOPolygon>) PGODeformScenario.getSingleton().getPrevPolygons().clone());
                        polygonMgr.setFixedPts((ArrayList<Point>) PGODeformScenario.getSingleton().getPrevPts().clone());

                        pgo.vibrate();
                        
                        pgo.getPolygonMgr().getSelectedPolygons().clear();
                        PGODeformScenario.getSingleton().setPrevPolygons(null);
                        PGODeformScenario.getSingleton().setPrevPts(null);
                        XCmdToChangeScene.execute(pgo,
                            PGODeformScenario.SeparateReadyScene.getSingleton(),
                            this.mReturnScene);
                    
                    } else {
                        for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                            Color c = polygon.getBgColor(pgo);
                            polygon.setColor(c);
                        }
                        if (polygonMgr.getSelectedPolygons().size() <= 1) {
                            polygonMgr.getPolygons().addAll(
                                polygonMgr.getSelectedPolygons());
                            polygonMgr.getSelectedPolygons().clear();
                    
                    
                            PGODeformScenario.getSingleton().setPrevPolygons(null);
                            PGODeformScenario.getSingleton().setPrevPts(null);

                            XCmdToChangeScene.execute(pgo,
                                PGODeformScenario.SeparateReadyScene.getSingleton(),
                                this.mReturnScene);
                        }
                        PGODeformScenario.getSingleton().setPrevPolygons(null);
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
                    pgo.getPolygonMgr().getPolygons().addAll(
                        pgo.getPolygonMgr().getSelectedPolygons());
                    pgo.getPolygonMgr().getSelectedPolygons().clear();
                    PGODeformScenario.getSingleton().setPrevPolygons(null);
                    PGODeformScenario.getSingleton().setPrevPts(null);
                    PGODeformScenario.getSingleton().setPrevPt(null);
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
            Point pt = pgo.findNearPt(e.getPoint());
            Rectangle boundingBox = new Rectangle(pt.x - 100, pt.y - 100, 200, 200);
            ArrayList<PGOPolygon> newSelectedPolygons = new ArrayList<PGOPolygon>();
            
            if (pgo.getPolygonMgr().getFixedPts().contains(pt)) {
                for (PGOPolygon polygon : pgo.getPolygonMgr().getPolygons()) {                
                    if (boundingBox.intersects(polygon.getBoundingBox())) {
                        if (polygon.getPts().contains(pt)) {
                            newSelectedPolygons.add(polygon);
                        }
                    }
                }
                
                if (newSelectedPolygons.size() > 1) {
                    pgo.getPolygonMgr().getPolygons().removeAll(newSelectedPolygons);
                    pgo.getPolygonMgr().getSelectedPolygons().addAll(newSelectedPolygons);
                    newSelectedPolygons.clear();
                    
                    PGODeformScenario.getSingleton().setPrevPt(pt);
                    
                    XCmdToChangeScene.execute(pgo,
                        PGODeformScenario.ChooseVertexScene.getSingleton(),
                        this.mReturnScene);
                } else {
                    
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
            // Point pt = e.getPoint();
            boolean anySelected = false;
            
            if (pt.distance(PGODeformScenario.getSingleton().getPrevPt()) > 10) {
                for (PGOPolygon polygon : pgo.getPolygonMgr().getSelectedPolygons()) {
                    if (pgo.getCalcMgr().contained(pt, polygon)) {                       
                        PGODeformScenario.getSingleton().setPrevPolygon(polygon.clonePolygon());
                        PGODeformScenario.getSingleton().setPrevPts((ArrayList<Point>) pgo.getPolygonMgr().getFixedPts().clone());
                        pgo.getPolygonMgr().getPolygons().addAll(
                            pgo.getPolygonMgr().getSelectedPolygons());
                        pgo.getPolygonMgr().getSelectedPolygons().clear();
                        
                        pgo.getPolygonMgr().getPolygons().remove(polygon);
                        pgo.getPolygonMgr().getSelectedPolygons().add(polygon);
                        anySelected = true;
                        
                        break;
                    }
                }
                if (anySelected) {
                    XCmdToChangeScene.execute(pgo,
                        PGODeformScenario.SeparateScene.getSingleton(),
                        this.mReturnScene);
                } else {
                    PGODeformScenario.getSingleton().setPrevPt(null);
                    PGODeformScenario.getSingleton().setPrevPts(null);
                    pgo.getPolygonMgr().getPolygons().addAll(
                            pgo.getPolygonMgr().getSelectedPolygons());
                    pgo.getPolygonMgr().getSelectedPolygons().clear();
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
                    PGODeformScenario.getSingleton().setPrevPt(null);
                    PGODeformScenario.getSingleton().setPrevPts(null);
                    pgo.getPolygonMgr().getPolygons().addAll(
                        pgo.getPolygonMgr().getSelectedPolygons());
                    pgo.getPolygonMgr().getSelectedPolygons().clear();
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
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            // Point pt = e.getPoint();
            Point prevPt = PGODeformScenario.getSingleton().getPrevPt();
            
            for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                int ptIndexInFixedPts = polygonMgr.getFixedPts().indexOf(prevPt);
                polygon.updateSelectedPt(pt, prevPt);
                polygon.updateBoundingBox(polygon.getPts());
                polygonMgr.getFixedPts().set(ptIndexInFixedPts, pt);
            }
            PGODeformScenario.getSingleton().setPrevPt(pt);
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            boolean anyIntersected = false;
            PGOPolygonMgr polygonMgr = pgo.getPolygonMgr();
            Point prevPt = PGODeformScenario.getSingleton().getPrevPt();
            for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                if (pgo.getCalcMgr().intersected(polygon, polygonMgr.getPolygons())) {
                    anyIntersected = true;
                    break;
                }
            }
            if (anyIntersected || !pgo.getCalcMgr().validPolygons(
                polygonMgr.getSelectedPolygons())) {
                polygonMgr.getPolygons().add(PGODeformScenario.getSingleton().getPrevPolygon().clonePolygon());
                polygonMgr.setFixedPts((ArrayList<Point>) PGODeformScenario.getSingleton().getPrevPts().clone());
                pgo.vibrate();
            } else {
                for (PGOPolygon polygon : polygonMgr.getSelectedPolygons()) {
                    Color c = polygon.getBgColor(pgo);
                    polygon.setColor(c);
                }
                polygonMgr.getPolygons().addAll(
                    polygonMgr.getSelectedPolygons());
            }
            
            polygonMgr.getSelectedPolygons().clear();
            PGODeformScenario.getSingleton().setPrevPt(null);
            PGODeformScenario.getSingleton().setPrevPts(null);
            PGODeformScenario.getSingleton().setPrevPolygon(null);
            
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
                    PGODeformScenario.getSingleton().setPrevPolygons(new ArrayList<PGOPolygon>());
                    PGODeformScenario.getSingleton().getPrevPolygons().add(PGODeformScenario.getSingleton().getPrevPolygon());
                    PGODeformScenario.getSingleton().setPrevPolygon(null);
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
