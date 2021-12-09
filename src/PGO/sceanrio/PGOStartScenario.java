package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPanelMgr;
import PGO.PGOScene;
import PGO.cmd.PGOCmdToCalcPolygonColor;
import PGO.cmd.PGOCmdToControlImageHSB;
import PGO.cmd.PGOCmdToGetExtension;
import PGO.cmd.PGOCmdToGetFile;
import PGO.cmd.PGOCmdToImportJSON;
import PGO.cmd.PGOCmdToInitiate;
import PGO.cmd.PGOCmdToSetFinalImage;
import PGO.cmd.PGOCmdToSetImage;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOStartScenario extends XScenario {
    // fields
    private JLabel mPrevImage = null;

    public JLabel getPrevImage() {
        return this.mPrevImage;
    };

    public void setPrevImage(JLabel imageLabel) {
        this.mPrevImage = imageLabel;
    }

    private String mPrevPath = null;

    public String getPrevPath() {
        return this.mPrevPath;
    }

    public void setPrevPath(String path) {
        this.mPrevPath = path;
    }

    // singleton pattern
    private static PGOStartScenario mSingleton = null;

    public static PGOStartScenario createSingleton(XApp app) {
        assert (PGOStartScenario.mSingleton == null);
        PGOStartScenario.mSingleton = new PGOStartScenario(app);
        return PGOStartScenario.mSingleton;
    }

    public static PGOStartScenario getSingleton() {
        assert (PGOStartScenario.mSingleton != null);
        return PGOStartScenario.mSingleton;
    }

    // contructor
    private PGOStartScenario(XApp app) {
        super(app);
    }

    @Override
    protected void addScenes() {
        this.addScene(PGOStartScenario.FileReadyScene.createSingleton(this));
    }

    public static class FileReadyScene extends PGOScene {
        // singleton pattern
        private static FileReadyScene mSingleton = null;

        public static FileReadyScene createSingleton(XScenario scenario) {
            assert (FileReadyScene.mSingleton == null);
            FileReadyScene.mSingleton = new FileReadyScene(scenario);
            return FileReadyScene.mSingleton;
        }

        public static FileReadyScene getSingleton() {
            assert (FileReadyScene.mSingleton != null);
            return FileReadyScene.mSingleton;
        }

        private FileReadyScene(XScenario scenario) {
            super(scenario);
        }

        // field
        private boolean mCtrlPressed = false;

        private String mFilePath = null;
        public String getFilePath() {
            return this.mFilePath;
        }
        public void setFilePath(String path) {
            this.mFilePath = path;
        }
        private String mFileName = null;
        public String getFileName() {
            return this.mFileName;
        }
        public void setFileName(String name) {
            this.mFileName = name;
        }

        private String mExtenstion = null;
        public String getExtenstion() {
            return this.mExtenstion;
        }
        public void setExtenstion(String ext) {
            this.mExtenstion = ext;
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
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_CONTROL:
                    this.mCtrlPressed = true;
                    break;
                case KeyEvent.VK_I:
                    if (this.mCtrlPressed) {
                        PGOCmdToImportJSON.execute(pgo);
                        XCmdToChangeScene.execute(pgo,
                            PGODefaultScenario.ReadyScene.getSingleton(),
                            null);
                    }
                    break;
            }
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOStartScenario scenario = (PGOStartScenario) this.mScenario;
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_ENTER:
                    if (scenario.getPrevImage() != null) {
                        PGOCmdToSetFinalImage.execute(pgo);
                        XCmdToChangeScene.execute(pgo,
                                PGODefaultScenario.ReadyScene.getSingleton(), null);
                    }
                    break;
                case KeyEvent.VK_CONTROL:
                    this.mCtrlPressed = false;
                    break;
            }
        }

        public void handleDragDrop(DropTargetDropEvent ev) {
            PGO pgo = (PGO) this.mScenario.getApp();
            PGOCmdToGetFile.execute(pgo, ev);
            PGOCmdToGetExtension.execute(pgo);
            pgo.getPanelMgr().setImageLoaded(false);
            switch (this.mExtenstion.toLowerCase()) {
                case "png":
                case "jpg":
                case "jpeg":
                    PGOCmdToSetImage.execute(pgo);
                    break;
                case "json":
                    PGOCmdToImportJSON.execute(pgo);
                    if (pgo.getPanelMgr().isImageLoaded()) {
                        PGOCmdToSetFinalImage.execute(pgo);
                        PGOCmdToControlImageHSB.execute(pgo);
                        PGOCmdToCalcPolygonColor.execute(pgo);
                        XCmdToChangeScene.execute(pgo,
                                PGODefaultScenario.ReadyScene.getSingleton(), null);
                    } else {
                        PGOCmdToInitiate.execute(pgo);
                    }
                    break;
            }
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
