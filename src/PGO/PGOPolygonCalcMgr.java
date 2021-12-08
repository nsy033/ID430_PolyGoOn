package PGO;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PGOPolygonCalcMgr {
    // constant
    private static final int MIN_VISIBLE_HEIGHT = 20;
    private static final double MIN_RATIO_SUM = 1.05;
    private static final double MIN_RATIO_LENGTH = 0.2;
    
    // field
    private PGO mPGO = null;
    
    // constructor
    public PGOPolygonCalcMgr(PGO pgo) {
        this.mPGO = pgo;
    }
    
    // methods
    public boolean isContained(Point test, ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            boolean result = checkContent(test, polygon);
            if (result == true) {
                return result;
            }
        }
        return false;
    }
    
    public boolean checkContent(Point test, PGOPolygon polygon) {
        ArrayList<Point> points = polygon.getPts();
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if (((points.get(i).y > test.getY()) !=
                (points.get(j).y > test.getY())) &&
                (test.getX() < (points.get(j).x - points.get(i).x) *
                (test.getY() - points.get(i).y) /
                (points.get(j).y-points.get(i).y) + points.get(i).x)) {
                result = !result;
            }
        }     
        return result;
    }
    
    public boolean isCrossed(Line2D.Double drawing,
        ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            ArrayList<Point> points = polygon.getPts();
            
            for (int i = 0; i < 3; i ++) {
                Point2D pt1 = points.get(i % 3);
                Point2D pt2 = points.get((i + 1) % 3);
                Line2D.Double line = new Line2D.Double();
                line.setLine(pt1, pt2);
                
                if (drawing.intersectsLine(line) &&
                    (!isPointOnTheLine(line,
                    (Point2D.Double) drawing.getP1())) && 
                    (!isPointOnTheLine(line,
                    (Point2D.Double) drawing.getP2()))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isPointOnTheLine(Line2D.Double line,
        Point2D.Double pt) {
        Point2D.Double startPt = (Point2D.Double) line.getP1();
        Point2D.Double endPt = (Point2D.Double) line.getP2();
        if (startPt.x == pt.x && startPt.y == pt.y) {
            return true;
        } else if (endPt.x == pt.x && endPt.y == pt.y) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isIntersected(PGOPolygon curPolygon,
        ArrayList<PGOPolygon> polygons) {
        if (polygons.size() < 1) return false;
        
        for (int i = 0; i < 3; i ++) {
                Point2D pt1 = curPolygon.getPts().get(i % 3);
                Point2D pt2 = curPolygon.getPts().get((i + 1) % 3);
                Line2D.Double line = new Line2D.Double();
                line.setLine(pt1, pt2);
                
                if (isCrossed(line, polygons)) {
                    return true;
                }
            }
                       
        return false;
    }
    
    public boolean isOverlapped(PGOPolygon curPolygon,
        ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            Point ePt1 = polygon.getPts().get(0);
            Point ePt2 = polygon.getPts().get(1);
            Point ePt3 = polygon.getPts().get(2);
            ArrayList<Point> pts = curPolygon.getPts();
                        
            if (checkContent(ePt1, curPolygon) && !pts.contains(ePt1)) {
                return true;
            } else if (checkContent(ePt2, curPolygon) && !pts.contains(ePt2)) {
                return true;
            } else if (checkContent(ePt3, curPolygon) && !pts.contains(ePt3)) {
                return true;
            }
        }
        return false;
    }

    public Point isValidPt(Point oldPt) {
        PGOCanvas2D pgoCanvas = this.mPGO.getCanvas2D();
        Point newPt = oldPt;

        if (oldPt.getX() > pgoCanvas.getWidth()) {
            newPt.x = pgoCanvas.getWidth();
        } else if (oldPt.getX() < 0) {
            newPt.x = 0;
        }

        if (oldPt.getY() > pgoCanvas.getHeight()) {
            newPt.y = pgoCanvas.getHeight();
        } else if (oldPt.getY() < 0) {
            newPt.y = 0;
        }

        return newPt;
    }
    
    public boolean areValidPolygons(ArrayList<PGOPolygon> polygons) {
        for (PGOPolygon polygon : polygons) {
            if (!isValidPolygon(polygon)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isValidPolygon(PGOPolygon polygon) {
        ArrayList<Point> points = polygon.getPts();
        double[] distance = new double[3];
        double base = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double minHeight = Double.MAX_VALUE;
        
        for (int i = 0; i < 3; i ++) {
            Point2D pt1 = points.get(i % 3);
            Point2D pt2 = points.get((i + 1) % 3);
            Point2D pt3 = points.get((i + 2) % 3);
            distance[i] = pt1.distance(pt2);
            double m = (pt2.getY() - pt1.getY()) / (pt2.getX() - pt1.getX());
            
            double height = Math.abs(-m * pt3.getX() + pt3.getY() +
                m * pt1.getX() - pt1.getY()) /
                Math.sqrt(1 + Math.pow(m, 2));
            
            if (base < distance[i]) base = distance[i];
            if (min > distance[i]) min = distance[i];
            if (minHeight > height) minHeight = height;
        }
        
        double rest = distance[0] + distance[1] + distance[2] - base;
        double ratio_sum = rest / base;
        double ratio_length = min / base;
        
        if (ratio_sum < PGOPolygonCalcMgr.MIN_RATIO_SUM) {
            return false;
        } else if (minHeight < PGOPolygonCalcMgr.MIN_VISIBLE_HEIGHT) {
            return false;
        } else if (ratio_length < PGOPolygonCalcMgr.MIN_RATIO_LENGTH) {
            return false;
        }
        return true;
    }
        
    public Point calcGravityPt(ArrayList<Point> pts) {
        Point pt1 = pts.get(0);
        Point pt2 = pts.get(1);
        Point pt3 = pts.get(2);
        
        return new Point((pt1.x + pt2.x + pt3.x)/3, (pt1.y + pt2.y + pt3.y)/3);
    }
    
    public Point findNearPt(Point pt) {
        ArrayList<Point> fixedPts = this.mPGO.getPolygonMgr().getFixedPts();
        for (Point fixedPt : fixedPts) {
            if (pt.distance(fixedPt) < 10.0) {
                pt.x = fixedPt.x;
                pt.y = fixedPt.y;
                break;
            }
        }
        return pt;
    }
}
