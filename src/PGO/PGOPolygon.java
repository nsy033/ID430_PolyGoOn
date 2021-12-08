package PGO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;

public class PGOPolygon {
    // constant
    public static final double MIN_DIST_BTWN_PTS = 5.0;
    
    //fields
    private ArrayList<Point> mPts = null;
    public ArrayList<Point> getPts() {
        return this.mPts;
    }
    private Rectangle mBoundingBox = null;
    public Rectangle getBoundingBox() {
        return this.mBoundingBox;
    }
    private Color mColor = null;
    public Color getColor() {
        return this.mColor;
    }
    public void setColor(Color c) {
        this.mColor = c;
    }
    private Stroke mStroke = null;
    public Stroke getStroke() {
        return this.mStroke;
    }
    
    //constructor
    public PGOPolygon(Point pt, Color c, Stroke s) {
        this.mPts = new ArrayList<Point>();
        this.mPts.add(pt);
        this.mBoundingBox = new Rectangle(pt.x, pt.y, 0, 0);
        
        this.mColor = new Color(
            c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        BasicStroke bs = (BasicStroke) s;
        this.mStroke = new BasicStroke(
            bs.getLineWidth(), bs.getEndCap(), bs.getLineJoin());
    }
    
    //methods
    public void addPt(Point pt) {
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }
    
    public void updatePolygon(Point pt) {
        int lastIndex = this.mPts.size();
        this.mPts.remove(lastIndex - 1);
        this.mPts.add(pt);
        this.mBoundingBox.add(pt);
    }
    
    public void updateSelectedPt(Point pt, Point prevPt) {
        int index = this.mPts.indexOf(prevPt);
        this.mPts.set(index, pt);
    }
    
    public void updateBoundingBox(ArrayList<Point> pts) {
        Point fstPt = pts.get(0);
        this.mBoundingBox = new Rectangle(fstPt.x, fstPt.y, 0, 0);
        for (Point pt : pts) {
            this.mBoundingBox.add(pt);
        }
    }
    
    public void translatePolygon(double dx, double dy) {
        int x = (int) dx;
        int y = (int) dy;
        
        for (Point pt : this.mPts) {
            Point tPt = new Point(pt.x + x, pt.y + y);
            this.updateSelectedPt(tPt, pt);
        }
        this.updateBoundingBox(this.mPts);
    }
    
    public PGOPolygon clonePolygon() {
        ArrayList<Point> pts = (ArrayList<Point>) this.mPts.clone();
        PGOPolygon tempPolygon =
            new PGOPolygon(pts.get(0), this.mColor, this.mStroke);
        tempPolygon.addPt(pts.get(1));
        tempPolygon.addPt(pts.get(2));
        return tempPolygon;
    }
}