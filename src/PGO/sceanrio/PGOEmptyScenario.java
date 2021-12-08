package PGO.sceanrio;

import PGO.PGOScene;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XScenario;

public class PGOEmptyScenario extends XScenario {
    // singleton pattern
    private static PGOEmptyScenario mSingleton = null;
    public static PGOEmptyScenario createSingleton(XApp app) {
        assert (PGOEmptyScenario.mSingleton == null);
        PGOEmptyScenario.mSingleton = new PGOEmptyScenario(app);
        return PGOEmptyScenario.mSingleton;
    }
    public static PGOEmptyScenario getSingleton() {
        assert (PGOEmptyScenario.mSingleton != null);
        return PGOEmptyScenario.mSingleton;
    }
    
    // private contructor
    private PGOEmptyScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOEmptyScenario.EmptyScene.createSingleton(this));
    }

    public static class EmptyScene extends PGOScene {
        // singleton pattern
        private static EmptyScene mSingleton = null;
        public static EmptyScene createSingleton(XScenario scenario) {
            assert (EmptyScene.mSingleton == null);
            EmptyScene.mSingleton = new EmptyScene(scenario);
            return EmptyScene.mSingleton;
        }
        public static EmptyScene getSingleton() {
            assert (EmptyScene.mSingleton != null);
            return EmptyScene.mSingleton;
        }
        
        private EmptyScene(XScenario scenario) {
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
