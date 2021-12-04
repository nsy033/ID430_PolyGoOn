package PGO.sceanrio;

import PGO.PGOScene;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import x.XApp;
import x.XScenario;

public class PGOColorScenario extends XScenario {
    // The template for JSIScenario.
    
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
    
    // private contructor
    private PGOColorScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOColorScenario.ColorReadyScene.createSingleton(this));
        this.addScene(PGOColorScenario.ColorChangeReadyScene.createSingleton(this));
        this.addScene(PGOColorScenario.ColorChooseReadyScene.createSingleton(this));
        this.addScene(PGOColorScenario.ColorChangeScene.createSingleton(this));
        this.addScene(PGOColorScenario.SaturationChangeScene.createSingleton(this));
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
    
    public static class ColorChangeReadyScene extends PGOScene {
        // singleton pattern
        private static ColorChangeReadyScene mSingleton = null;
        public static ColorChangeReadyScene createSingleton(XScenario scenario) {
            assert (ColorChangeReadyScene.mSingleton == null);
            ColorChangeReadyScene.mSingleton = new ColorChangeReadyScene(scenario);
            return ColorChangeReadyScene.mSingleton;
        }
        public static ColorChangeReadyScene getSingleton() {
            assert (ColorChangeReadyScene.mSingleton != null);
            return ColorChangeReadyScene.mSingleton;
        }
        
        private ColorChangeReadyScene(XScenario scenario) {
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
    
    public static class ColorChooseReadyScene extends PGOScene {
        // singleton pattern
        private static ColorChooseReadyScene mSingleton = null;
        public static ColorChooseReadyScene createSingleton(XScenario scenario) {
            assert (ColorChooseReadyScene.mSingleton == null);
            ColorChooseReadyScene.mSingleton = new ColorChooseReadyScene(scenario);
            return ColorChooseReadyScene.mSingleton;
        }
        public static ColorChooseReadyScene getSingleton() {
            assert (ColorChooseReadyScene.mSingleton != null);
            return ColorChooseReadyScene.mSingleton;
        }
        
        private ColorChooseReadyScene(XScenario scenario) {
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
    
    public static class ColorChangeScene extends PGOScene {
        // singleton pattern
        private static ColorChangeScene mSingleton = null;
        public static ColorChangeScene createSingleton(XScenario scenario) {
            assert (ColorChangeScene.mSingleton == null);
            ColorChangeScene.mSingleton = new ColorChangeScene(scenario);
            return ColorChangeScene.mSingleton;
        }
        public static ColorChangeScene getSingleton() {
            assert (ColorChangeScene.mSingleton != null);
            return ColorChangeScene.mSingleton;
        }
        
        private ColorChangeScene(XScenario scenario) {
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
    
    public static class SaturationChangeScene extends PGOScene {
        // singleton pattern
        private static SaturationChangeScene mSingleton = null;
        public static SaturationChangeScene createSingleton(XScenario scenario) {
            assert (SaturationChangeScene.mSingleton == null);
            SaturationChangeScene.mSingleton = new SaturationChangeScene(scenario);
            return SaturationChangeScene.mSingleton;
        }
        public static SaturationChangeScene getSingleton() {
            assert (SaturationChangeScene.mSingleton != null);
            return SaturationChangeScene.mSingleton;
        }
        
        private SaturationChangeScene(XScenario scenario) {
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
