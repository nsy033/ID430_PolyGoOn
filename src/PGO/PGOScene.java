package PGO;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import x.XScenario;
import x.XScene;

public abstract class PGOScene extends XScene{
    // constructor
    protected PGOScene(XScenario scenario) {
        super(scenario);
    }
    
    // Event handling abstract methods
    public abstract void handleMousePress(MouseEvent e);
    // public abstract void handleMouseDrag(MouseEvent e);
    public abstract void handleMouseDrag(Point pt);
    public abstract void handleMouseRelease(MouseEvent e);
    public abstract void handleKeyDown(KeyEvent e);
    public abstract void handleKeyUp(KeyEvent e);
    public abstract void handleChange(ChangeEvent e);
    // public abstract void handleDragDrop(DropTargetDropEvent ev);
    
    // Other abstract methods
    public abstract void updateSupportObjects();
    public abstract void renderWorldObjects();
    public abstract void renderScreenObjects(Graphics2D g2);
}
