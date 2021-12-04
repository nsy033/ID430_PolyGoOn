package PGO;

import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

public class PGOPolygonMgr {
    // fields
    private PGOPolygon mCurPolygon = null;
    public PGOPolygon getCurPolygon() {
        return this.mCurPolygon;
    }
    public void setCurPolygon(PGOPolygon polygon) {
        this.mCurPolygon = polygon;
    }
    public void createCurPolygon(Point pt, Color c, Stroke s) {
        this.mCurPolygon = new PGOPolygon(pt, c, s);
    }
    private ArrayList<PGOPolygon> mPolygons = null;
    public ArrayList<PGOPolygon> getPolygons() {
        return this.mPolygons;
    }
    
    private ArrayList<PGOPolygon> mSelectedPolygons = null;
    public ArrayList<PGOPolygon> getSelectedPolygons() {
        return this.mSelectedPolygons;
    }
    private PGOPolygon mDraggedPolygon = null;
    public PGOPolygon getDraggedPolygon() {
        return this.mDraggedPolygon;
    }
    public void setDraggedPolygon(PGOPolygon polygon) {
        this.mDraggedPolygon = polygon;
    }
    
    private ArrayList<Point> mFixedPts = null;
    public ArrayList<Point> getFixedPts() {
        return this.mFixedPts;
    }
    public void setFixedPts(ArrayList<Point> pts) {
        this.mFixedPts = pts;
    }
    
    // constructor
    public PGOPolygonMgr() {
        this.mPolygons = new ArrayList<PGOPolygon>();
        this.mSelectedPolygons = new ArrayList<PGOPolygon>();
        this.mFixedPts = new ArrayList<Point>();
    }
}
