package PGO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PGOCanvas2D extends JPanel {
    //constants
    private static final Color COLOR_PT_CURVE_DEFAULT = new Color(0, 0, 0, 60);
    public static final Color COLOR_INFO = new Color(255, 0, 0, 128);
    
    private static final Stroke STROKE_PT_CURVE_DEFAULT = new BasicStroke(5f,
        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final double MAG_RATIO = 1.5;
    
    public static final Font FONT_INFO = new Font("Monospaced", Font.PLAIN, 24);
    
    private static final int INFO_TOP_ALIGNMENT_X = 20;
    private static final int INFO_TOP_ALIGNMENT_Y = 30;
    
    public static final float STROKE_MIN_WIDTH = 1f;
    
    //fields
    private PGO mPGO = null;
    private Color mCurColorForPolygon = null;
    public Color getCurColorForPolygon() {
        return this.mCurColorForPolygon;
    }
    public Color setCurColorForPolygon(Color c) {
        return this.mCurColorForPolygon = c;
    }
    private Stroke mCurStrokeForPolygon = null;
    public Stroke getCurStrokeForPolygon() {
        return this.mCurStrokeForPolygon;
    }
    private RadialGradientPaint mDeleteAreaPaint = null;
    public void setDeleteAreaPaint(Point2D pt, int radius) {
        float[] dist = {0.0f, 0.5f, 1.0f};
        Color[] colors = {new Color(0, 0, 0, 128), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0)};
        this.mDeleteAreaPaint = new RadialGradientPaint(pt, (float) radius, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);
    }
    
    //constructor
    public PGOCanvas2D(PGO pgo) {
        this.mPGO = pgo;
        this.mCurColorForPolygon = PGOCanvas2D.COLOR_PT_CURVE_DEFAULT;
        this.mCurStrokeForPolygon = PGOCanvas2D.STROKE_PT_CURVE_DEFAULT;
    }
    
    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);
        
        Graphics2D gfx2 = (Graphics2D) gfx;
        
        this.drawPolygons(gfx2);
        this.drawCurPolygon(gfx2);
        this.drawDraggedPolygon(gfx2);
        this.drawSelectedPolygons(gfx2);
        // this.drawInfo(gfx2);
        
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        curScene.renderScreenObjects(gfx2);
    }

    private void drawPolygons(Graphics2D gfx2) {
        for(PGOPolygon polygon : this.mPGO.getPolygonMgr().getPolygons()) {
            this.drawPolygon(gfx2, polygon,
                    polygon.getColor(),
                    polygon.getStroke());
//            gfx2.draw(polygon.getBoundingBox());
        }
    }
    
    private void drawSelectedPolygons(Graphics2D gfx2) {
        ArrayList<PGOPolygon> polygons = this.mPGO.getPolygonMgr().getSelectedPolygons();
        for(PGOPolygon polygon : polygons) {
            this.drawPolygon(gfx2, polygon,
                    polygon.getColor(),
                    polygon.getStroke());
            
        }
    }
    
    private void drawDraggedPolygon(Graphics2D gfx2) {
        PGOPolygon draggedPolygon = this.mPGO.getPolygonMgr().getDraggedPolygon();
        if (draggedPolygon != null) {
            ArrayList<Point> pts = draggedPolygon.getPts();
            int nPts = 3;
            int[] xPts = new int[nPts];
            int[] yPts = new int[nPts];
            Point gravity = this.mPGO.getCalcMgr().calcGravityPt(pts);
            for (int i = 0; i < pts.size(); i++) {
                Point pt = pts.get(i);
                xPts[i] = (int) (gravity.x + (pt.x - gravity.x) * MAG_RATIO);
                yPts[i] = (int) (gravity.y + (pt.y - gravity.y) * MAG_RATIO);
            }
            gfx2.setColor(draggedPolygon.getColor());
            gfx2.setStroke(draggedPolygon.getStroke());
            gfx2.fillPolygon(xPts, yPts, nPts);
            gfx2.setPaint(this.mDeleteAreaPaint);
            gfx2.fill(this.mPGO.getDeleteArea());
        }
    }

    private void drawCurPolygon(Graphics2D gfx2) {
        PGOPolygon polygon = this.mPGO.getPolygonMgr().getCurPolygon();
        if(polygon != null) {
            switch (polygon.getPts().size()) {
                case 1:
                    this.drawDots(gfx2, polygon, polygon.getColor());
                    break;
                case 2:
                    this.drawDots(gfx2, polygon, polygon.getColor());
                    this.drawPolyline(gfx2, polygon,
                        polygon.getStroke());
                    break;
                case 3:
                    this.drawDots(gfx2, polygon, polygon.getColor());
                    this.drawPolygon(gfx2, polygon,
                        polygon.getColor(),
                        polygon.getStroke());
                    break;
            }
        }
    }
    
    private void drawPolygon (Graphics2D g2, PGOPolygon polygon,
                                Color color, Stroke stroke) {
        ArrayList<Point> pts = polygon.getPts();
        int nPts = 3;
        int[] xPts = new int[nPts];
        int[] yPts = new int[nPts];
        for (int i = 0; i < pts.size(); i++) {
            Point pt = pts.get(i);
            xPts[i] = pt.x;
            yPts[i] = pt.y;
        }
        g2.setColor(color);
        g2.setStroke(stroke);
        g2.fillPolygon(xPts, yPts, nPts);
    }
    
    private void drawPolyline (Graphics2D g2, PGOPolygon polygon,
                                Stroke stroke) {
        ArrayList<Point> pts = polygon.getPts();
        int nPts = 2;
        int[] xPts = new int[nPts];
        int[] yPts = new int[nPts];
        for (int i = 0; i < pts.size(); i++) {
            Point pt = pts.get(i);
            xPts[i] = pt.x;
            yPts[i] = pt.y;
        }
        g2.setStroke(stroke);
        g2.drawPolyline(xPts, yPts, nPts);
    }
    
    private void drawDots (Graphics2D g2, PGOPolygon polygon,
                                Color color) {
        ArrayList<Point> pts = polygon.getPts();
        g2.setColor(color);
        double d = (double) PGOCanvas2D.STROKE_MIN_WIDTH * 10.0;
        for (Point pt : pts) {
            Ellipse2D.Double e = new Ellipse2D.Double(pt.x - (d / 2.0), pt.y - (d / 2.0), d, d);
            g2.fill(e);
        }
    }

    private void drawInfo(Graphics2D gfx2) {
        PGOScene curScene = (PGOScene) this.mPGO.getScenarioMgr().getCurScene();
        String str = curScene.getClass().getSimpleName();
        
        gfx2.setColor(PGOCanvas2D.COLOR_INFO);
        gfx2.setFont(PGOCanvas2D.FONT_INFO);
        
        gfx2.drawString(str, PGOCanvas2D.INFO_TOP_ALIGNMENT_X,
            PGOCanvas2D.INFO_TOP_ALIGNMENT_Y);
    }
}