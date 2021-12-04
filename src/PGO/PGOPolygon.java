package PGO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class PGOPolygon {
    // constants
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
        
        this.mColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        BasicStroke bs = (BasicStroke) s;
        this.mStroke = new BasicStroke(bs.getLineWidth(), bs.getEndCap(), bs.getLineJoin());
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
    
    // utility methods
    public PGOPolygon clonePolygon() {
        ArrayList<Point> pts = (ArrayList<Point>) this.mPts.clone();
        PGOPolygon tmpPolygon = new PGOPolygon(pts.get(0), this.mColor, this.mStroke);
        tmpPolygon.addPt(pts.get(1));
        tmpPolygon.addPt(pts.get(2));
        return tmpPolygon;
    }
    
    public Color getBgColor(PGO pgo) {
        ImageIcon imgIcon = (ImageIcon) pgo.getImageLabel().getIcon();

        Image img = imgIcon.getImage();
        BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        ArrayList<Point> pts = this.getPts();
        Point pt1 = pts.get(0);
        Point pt2 = pts.get(1);
        Point pt3 = pts.get(2);
        Point gravity = pgo.getCalcMgr().calcGravityPt(pts);
        
        int pt1RGB = bImg.getRGB((pt1.x + gravity.x)/2, (pt1.y + gravity.y)/2);
        int pt2RGB = bImg.getRGB((pt2.x + gravity.x)/2, (pt2.y + gravity.y)/2);
        int pt3RGB = bImg.getRGB((pt3.x + gravity.x)/2, (pt3.y + gravity.y)/2);
        int gravRGB = bImg.getRGB(gravity.x, gravity.y);
        
        Color c1 = new Color(pt1RGB);
        Color c2 = new Color(pt2RGB);
        Color c3 = new Color(pt3RGB);
        Color c4 = new Color(gravRGB);
        
        int r = (c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed())/4;
        int g = (c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen())/4;
        int b = (c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue())/4;
        
        return new Color(r, g, b);
    }
}