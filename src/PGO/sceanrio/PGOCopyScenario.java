package PGO.sceanrio;

import PGO.PGOScene;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XScenario;

public class PGOCopyScenario extends XScenario {
    // singleton pattern
    private static PGOCopyScenario mSingleton = null;
    public static PGOCopyScenario createSingleton(XApp app) {
        assert (PGOCopyScenario.mSingleton == null);
        PGOCopyScenario.mSingleton = new PGOCopyScenario(app);
        return PGOCopyScenario.mSingleton;
    }
    public static PGOCopyScenario getSingleton() {
        assert (PGOCopyScenario.mSingleton != null);
        return PGOCopyScenario.mSingleton;
    }
    
    // private contructor
    private PGOCopyScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOCopyScenario.CopyReadyScene.createSingleton(this));
        this.addScene(PGOCopyScenario.CopyScene.createSingleton(this));
    }

    public static class CopyReadyScene extends PGOScene {
        // singleton pattern
        private static CopyReadyScene mSingleton = null;
        public static CopyReadyScene createSingleton(XScenario scenario) {
            assert (CopyReadyScene.mSingleton == null);
            CopyReadyScene.mSingleton = new CopyReadyScene(scenario);
            return CopyReadyScene.mSingleton;
        }
        public static CopyReadyScene getSingleton() {
            assert (CopyReadyScene.mSingleton != null);
            return CopyReadyScene.mSingleton;
        }
        
        private CopyReadyScene(XScenario scenario) {
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
    
    public static class CopyScene extends PGOScene {
        // singleton pattern
        private static CopyScene mSingleton = null;
        public static CopyScene createSingleton(XScenario scenario) {
            assert (CopyScene.mSingleton == null);
            CopyScene.mSingleton = new CopyScene(scenario);
            return CopyScene.mSingleton;
        }
        public static CopyScene getSingleton() {
            assert (CopyScene.mSingleton != null);
            return CopyScene.mSingleton;
        }
        
        private CopyScene(XScenario scenario) {
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
