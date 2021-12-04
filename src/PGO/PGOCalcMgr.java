package PGO;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PGOCalcMgr {
    // constant
    private static final int MIN_VISIBLE_HEIGHT = 20;
    private static final double MIN_RATIO_SUM = 1.05;
    private static final double MIN_RATIO_LENGTH = 0.2;
    
    // field
    private PGO mPGO = null;
    
    // methods
    public boolean contained(Point test, ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            boolean result = contained(test, polygon);
            if (result == true) {
                return result;
            }
        }
        return false;
    }
    
    public boolean contained(Point test, PGOPolygon polygon) {
        ArrayList<Point> points = polygon.getPts();
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).y > test.getY()) != (points.get(j).y > test.getY()) &&
                (test.getX() < (points.get(j).x - points.get(i).x) *
                (test.getY() - points.get(i).y) / (points.get(j).y-points.get(i).y) +
                points.get(i).x)) {
                result = !result;
            }
        }
                
        return result;
    }
    
    public boolean contained(Point2D test, PGOPolygon polygon) {
        ArrayList<Point> points = polygon.getPts();
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).y > test.getY()) != (points.get(j).y > test.getY()) &&
                (test.getX() < (points.get(j).x - points.get(i).x) *
                (test.getY() - points.get(i).y) / (points.get(j).y-points.get(i).y) +
                points.get(i).x)) {
                result = !result;
            }
        }
                
        return result;
    }
    
    public boolean crossed(Line2D.Double drawing, ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            ArrayList<Point> points = polygon.getPts();
            Point2D pt1 = points.get(0);
            Point2D pt2 = points.get(1);
            Point2D pt3 = points.get(2);
            
            Line2D.Double line1 = new Line2D.Double();
            Line2D.Double line2 = new Line2D.Double();
            Line2D.Double line3 = new Line2D.Double();
            line1.setLine(pt1, pt2);
            line2.setLine(pt2, pt3);
            line3.setLine(pt3, pt1);
            
            boolean cond1 = drawing.intersectsLine(line1) &&
                (!isPointOnTheLine(line1, (Point2D.Double) drawing.getP1())) &&
                (!isPointOnTheLine(line1, (Point2D.Double) drawing.getP2()));
            boolean cond2 = drawing.intersectsLine(line2) &&
                (!isPointOnTheLine(line2, (Point2D.Double) drawing.getP1())) &&
                (!isPointOnTheLine(line2, (Point2D.Double) drawing.getP2()));
            boolean cond3 = drawing.intersectsLine(line3) &&
                (!isPointOnTheLine(line3, (Point2D.Double) drawing.getP1())) &&
                (!isPointOnTheLine(line3, (Point2D.Double) drawing.getP2()));
            
            if (cond1 || cond2 || cond3) {
                return true;
            }
        }
        return false;
    }
    
    public synchronized boolean isPointOnTheLine(Line2D.Double line, Point2D.Double pt) {
        Point2D.Double startPt = (Point2D.Double) line.getP1();
        Point2D.Double endPt = (Point2D.Double) line.getP2();
        if (startPt.x == pt.x && startPt.y == pt.y) return true;
        else if (endPt.x == pt.x && endPt.y == pt.y) return true;
        else return false;
    }
    
    public boolean intersected(PGOPolygon curPolygon, ArrayList<PGOPolygon> polygons) {
        if (polygons.size() < 1) return false;
        
        Point2D pt1 = curPolygon.getPts().get(0);
        Point2D pt2 = curPolygon.getPts().get(1);
        Point2D pt3 = curPolygon.getPts().get(2);
        
        Line2D.Double line1 = new Line2D.Double();
        Line2D.Double line2 = new Line2D.Double();
        Line2D.Double line3 = new Line2D.Double();
        line1.setLine(pt1, pt2);
        line2.setLine(pt2, pt3);
        line3.setLine(pt3, pt1);
        
        if (crossed(line1, polygons) || crossed(line2, polygons) || crossed(line3, polygons)) {
            return true;
        }
                
        return false;
    }
    
    public boolean overlapped(PGOPolygon curPolygon, ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            Point2D ePt1 = polygon.getPts().get(0);
            Point2D ePt2 = polygon.getPts().get(1);
            Point2D ePt3 = polygon.getPts().get(2);
            ArrayList<Point> pts = curPolygon.getPts();
                        
            if (contained(ePt1, curPolygon) && !pts.contains((Point) ePt1)) {
                return true;
            } else if (contained(ePt2, curPolygon) && !pts.contains((Point) ePt2)) {
                return true;
            } else if (contained(ePt3, curPolygon) && !pts.contains((Point) ePt3)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validPolygon(PGOPolygon polygon) {
        ArrayList<Point> points = polygon.getPts();
        Point2D pt1 = points.get(0);
        Point2D pt2 = points.get(1);
        Point2D pt3 = points.get(2);
        
        double line1 = pt1.distance(pt2);
        double line2 = pt2.distance(pt3);
        double line3 = pt3.distance(pt1);
        
        double m1 = (pt2.getY() - pt1.getY()) / (pt2.getX() - pt1.getX());
        double m2 = (pt3.getY() - pt2.getY()) / (pt3.getX() - pt2.getX());
        double m3 = (pt1.getY() - pt3.getY()) / (pt1.getX() - pt3.getX());
        
        double height1 = Math.abs(-m1 * pt3.getX() + pt3.getY() + m1 * pt1.getX() - pt1.getY()) / Math.sqrt(1 + Math.pow(m1, 2));
        double height2 = Math.abs(-m2 * pt2.getX() + pt3.getY() + m2 * pt1.getX() - pt1.getY()) / Math.sqrt(1 + Math.pow(m2, 2));
        double height3 = Math.abs(-m3 * pt3.getX() + pt3.getY() + m3 * pt1.getX() - pt1.getY()) / Math.sqrt(1 + Math.pow(m3, 2));
        double minHeight = Math.max(Math.max(height1, height2), height3);
        
        double base = Math.max(Math.max(line1, line2), line3);
        double min = Math.min(Math.min(line1, line2), line3);
        double rest = line1 + line2 + line3 - base;
        double ratio_sum = rest / base;
        double ratio_length = min / base;
        if (ratio_sum < PGOCalcMgr.MIN_RATIO_SUM) {
            return false;
        } else if (minHeight < PGOCalcMgr.MIN_VISIBLE_HEIGHT) {
            return false;
        } else if (ratio_length < PGOCalcMgr.MIN_RATIO_LENGTH) {
            return false;
        } else if (!this.mPGO.getFrame().contains((Point) pt1) ||
            !this.mPGO.getFrame().contains((Point) pt2) ||
            !this.mPGO.getFrame().contains((Point) pt3)) {
            return false;
        }
        return true;
    }
    
    public boolean validPolygons(ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            if (!validPolygon(polygon)) {
                return false;
            }
        }
        return true;
    }
    
    public Point calcGravityPt(ArrayList<Point> pts) {
        Point pt1 = pts.get(0);
        Point pt2 = pts.get(1);
        Point pt3 = pts.get(2);
        
        return new Point((pt1.x + pt2.x + pt3.x)/3, (pt1.y + pt2.y + pt3.y)/3);
    }
    
    // constructor
    public PGOCalcMgr(PGO pgo) {
        this.mPGO = pgo;
    }
}
