package PGO.cmd;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import PGO.PGO;
import PGO.PGOPolygon;
import x.XApp;
import x.XLoggableCmd;

public class PGOCmdToExportJSON extends XLoggableCmd {
    // fields
    private JSONArray info = null;
    ArrayList<PGOPolygon> polygons = null;
    String path = null;
    String name = null;
    String fullPath = null;

    // private constructor
    private PGOCmdToExportJSON(XApp app) {
        super(app);
        info = new JSONArray();
    }

    public static boolean execute(XApp app) {
        PGOCmdToExportJSON cmd = new PGOCmdToExportJSON(app);
        return cmd.execute();
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        polygons = pgo.getPolygonMgr().getPolygons();

        JSONObject tmpImage = new JSONObject();
        tmpImage.put("type", "BackgroundImg");
        tmpImage.put("path", pgo.getPanelMgr().getFilePath());
        tmpImage.put("hue", pgo.getSliderMgr().getHSBSliders().get(0).getValue());
        tmpImage.put("sat", pgo.getSliderMgr().getHSBSliders().get(1).getValue());
        tmpImage.put("bri", pgo.getSliderMgr().getHSBSliders().get(2).getValue());
        info.add(tmpImage);

        for (PGOPolygon polygon : this.polygons) {
            JSONObject tmpPolygon = new JSONObject();
            tmpPolygon.put("type", "PGOPolygon");
            ArrayList<Point> pts = polygon.getPts();

            for (int i = 0; i < pts.size(); i++) {
                tmpPolygon.put("x" + i, "" + pts.get(i).x);
                tmpPolygon.put("y" + i, "" + pts.get(i).y);
            }
            info.add(tmpPolygon);
        }

        String[] pathList = pgo.getPanelMgr().getFilePath().split("/");
        path = "";
        name = pathList[pathList.length - 1].substring(0,
                pathList[pathList.length - 1].indexOf("."));
        for (int i = 0; i < pathList.length - 1; i++) {
            String p = pathList[i];
            path = path + "/" + p;
        }

        fullPath = path + "/" + name + "_PGO.json";

        try  {
            FileWriter file = new FileWriter(fullPath);
            file.write(info.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        sb.append(this.fullPath);
        return sb.toString();
    }

}
