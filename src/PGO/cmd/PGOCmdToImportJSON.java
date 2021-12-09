package PGO.cmd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.Point;
import java.util.ArrayList;
import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.PGOPolygon;
import PGO.sceanrio.PGOStartScenario;
import x.XApp;
import x.XLoggableCmd;
public class PGOCmdToImportJSON extends XLoggableCmd {
    // fields

    // private constructor
    private PGOCmdToImportJSON(XApp app) {
        super(app);
    }

    public static boolean execute(XApp app) {
        PGOCmdToImportJSON cmd = new PGOCmdToImportJSON(app);
        return cmd.execute();
    }

    private void addPolygon(JSONObject polygon) {
        PGO pgo = (PGO) this.mApp;
        ArrayList<Point> pts = new ArrayList<Point>();

        for (int i = 0; i < 3; i++) {
            int x = Integer.parseInt((String) polygon.get("x" + i));
            int y = Integer.parseInt((String) polygon.get("y" + i));
            Point pt = new Point(x, y);
            pts.add(pt);
        }

        PGOPolygon tmpPolygon = new PGOPolygon(pts.get(0), 
            pgo.getCanvas2D().getCurColorForPolygon(),
            pgo.getCanvas2D().getCurStrokeForPolygon());
        tmpPolygon.addPt(pts.get(1));
        tmpPolygon.addPt(pts.get(2));

        pgo.getPolygonMgr().getPolygons().add(tmpPolygon);
        pgo.getPolygonMgr().getFixedPts().addAll(tmpPolygon.getPts());
    }

    private void setImage(JSONObject image) {
        PGO pgo = (PGO) this.mApp;
        PGOStartScenario scenario = PGOStartScenario.getSingleton();
        PGOPanelMgr panelMgr = pgo.getPanelMgr();
        String path = (String) image.get("path");

        System.out.println(path);

        if (scenario.getPrevImage() == null) {
            panelMgr.getImagePane().remove(panelMgr.getTextLabel());
            scenario.setPrevPath(path);
            panelMgr.setImageLabel(path);
        } else {
            if (!scenario.getPrevPath().equals(path)) {
                panelMgr.getImagePane().remove(scenario.getPrevImage());
                scenario.setPrevImage(null);
                scenario.setPrevPath(path);
                panelMgr.setImageLabel(path);
            }
        }
    }

    @Override
    protected boolean defineCmd() {
        PGO pgo = (PGO) this.mApp;
        String path = PGOStartScenario.FileReadyScene.getSingleton().getFilePath();
        JSONParser parser = new JSONParser();
        try {
            Object arr = parser.parse(new FileReader(path));
            JSONArray loaded = (JSONArray) arr;
            Iterator<JSONObject> iterator = loaded.iterator();

            while (iterator.hasNext()) {
                JSONObject object = iterator.next();
                switch ((String) object.get("type")) {
                    case "PGOPolygon":
                        addPolygon(object);
                        break;
                    case "BackgroundImg":
                        setImage(object);
                        break;
                    default:
                        return false;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected String createLog() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getSimpleName()).append("\t");
        // ...
        return sb.toString();
    }

}
