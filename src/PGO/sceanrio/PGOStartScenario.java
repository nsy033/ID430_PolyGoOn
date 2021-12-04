package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOScene;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import static java.lang.Double.min;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOStartScenario extends XScenario {
    // The template for JSIScenario.
    
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
    
    // private contructor
    private PGOStartScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOStartScenario.ImageReadyScene.createSingleton(this));
    }

    public static class ImageReadyScene extends PGOScene {
        // singleton pattern
        private static ImageReadyScene mSingleton = null;
        public static ImageReadyScene createSingleton(XScenario scenario) {
            assert (ImageReadyScene.mSingleton == null);
            ImageReadyScene.mSingleton = new ImageReadyScene(scenario);
            return ImageReadyScene.mSingleton;
        }
        public static ImageReadyScene getSingleton() {
            assert (ImageReadyScene.mSingleton != null);
            return ImageReadyScene.mSingleton;
        }
        
        private ImageReadyScene(XScenario scenario) {
            super(scenario);
        }

        private JLabel mImageLabel = null;
        private String mPrevPath = null;
    
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();
            
            switch (code) {
                case KeyEvent.VK_ENTER:
                    if (this.mImageLabel != null) {
                        pgo.setImageLabel(this.mImageLabel);
                        pgo.getTranslucentPane().setVisible(true);
                        XCmdToChangeScene.execute(pgo,
                            PGODefaultScenario.ReadyScene.getSingleton(), null);
                    }
                    break;
            }
        }
        
        public void handleDragDrop(DropTargetDropEvent ev) {
            // accept dropped items
            ev.acceptDrop(DnDConstants.ACTION_COPY);

            // we want dropped items
            Transferable t = ev.getTransferable();
            // get data formats of the items
            DataFlavor[] df = t.getTransferDataFlavors();

            // loop through flavors
            for (DataFlavor f : df) {
                try {
                    // check if items are file type
                    if (f.isFlavorJavaFileListType()) {
                        // get list of them
                        List<File> files = (List<File>) t.getTransferData(f);

                        // loop through them
                        for (File file : files) {
                            displayImage(file.getPath());
                        }

                    } else {
                        File file = (File) t.getTransferData(f);
                        displayImage(file.getPath());
                    }
                } catch(Exception ex) {
//                    System.out.println("image loading");
                }
            }
        }
        
        private void displayImage(String path) {
            PGO pgo = (PGO) this.mScenario.getApp();

            if (this.mImageLabel == null) {
                pgo.getCanvas2D().remove(pgo.getTextLabel());
                this.mPrevPath = path;
                this.setImageLabel(path);
            } else {
                if (!this.mPrevPath.equals(path)) {
                    pgo.getFrame().remove(this.mImageLabel);
                    pgo.getFrame().remove(pgo.getTranslucentPane());
                    this.mImageLabel = null;
                    this.mPrevPath = path;
                    this.setImageLabel(path);
                }
            }
        }
        
        private void setImageLabel(String path) {
            PGO pgo = (PGO) this.mScenario.getApp();
            Image image = new ImageIcon(path).getImage();

            int imgWidth = image.getWidth(null);
            int imgHeight = image.getHeight(null);
            double d = min(1280.0 / imgWidth, 1080.0 / imgHeight);
            int width = (int) (imgWidth * d);
            int height = (int) (imgHeight * d);
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
            pgo.setImageLabel(imageIcon);
            
            this.mImageLabel = new JLabel();
            this.mImageLabel.setIcon(imageIcon);
            this.mImageLabel.setVerticalAlignment(JLabel.CENTER);
            this.mImageLabel.setHorizontalAlignment(JLabel.CENTER);
            pgo.getCanvas2D().setSize(width, height);
            pgo.getTranslucentPane().setSize(width, height);
//            pgo.setFrameWidth(width);
//            pgo.setFrameHeight(height);
            pgo.getFrame().setSize(width, height);
            pgo.getFrame().setLocationRelativeTo(null);
            pgo.getFrame().add(pgo.getTranslucentPane(), BorderLayout.CENTER);
            pgo.getFrame().add(this.mImageLabel, BorderLayout.CENTER);
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
