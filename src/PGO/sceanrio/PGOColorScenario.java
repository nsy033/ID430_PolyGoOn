package PGO.sceanrio;

import PGO.PGO;
import PGO.PGOPolygon;
import PGO.PGOScene;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import x.XApp;
import x.XCmdToChangeScene;
import x.XScenario;

public class PGOColorScenario extends XScenario {
    // singleton pattern
    private static PGOColorScenario mSingleton = null;
    public static PGOColorScenario createSingleton(XApp app) {
        assert (PGOColorScenario.mSingleton == null);
        PGOColorScenario.mSingleton = new PGOColorScenario(app);
        return PGOColorScenario.mSingleton;
    }
    public static PGOColorScenario getSingleton() {
        assert (PGOColorScenario.mSingleton != null);
        return PGOColorScenario.mSingleton;
    }
    
    // private contructor
    private PGOColorScenario(XApp app) {
        super(app);
    }
    
    @Override
    protected void addScenes() {
        this.addScene(PGOColorScenario.ColorReadyScene.createSingleton(this));
        this.addScene(PGOColorScenario.HueChangeScene.createSingleton(this));
        this.addScene(PGOColorScenario.SatChangeScene.createSingleton(this));
        this.addScene(PGOColorScenario.BriChangeScene.createSingleton(this));
    }

    public static class ColorReadyScene extends PGOScene {
        // singleton pattern
        private static ColorReadyScene mSingleton = null;
        public static ColorReadyScene createSingleton(XScenario scenario) {
            assert (ColorReadyScene.mSingleton == null);
            ColorReadyScene.mSingleton = new ColorReadyScene(scenario);
            return ColorReadyScene.mSingleton;
        }
        public static ColorReadyScene getSingleton() {
            assert (ColorReadyScene.mSingleton != null);
            return ColorReadyScene.mSingleton;
        }
        
        private ColorReadyScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleChange(ChangeEvent e) {
        }

        @Override
        public void handleMousePress(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            JSlider selectedSlider = (JSlider) e.getSource();
            int type = -1;
            if (selectedSlider.equals(pgo.getHueSlider())) type = 1;
            else if (selectedSlider.equals(pgo.getSatSlider())) type = 2;
            else if (selectedSlider.equals(pgo.getBriSlider())) type = 3;
            
            pgo.getHSBPanel().setFocusable(true);
            switch (type) {
                case 1:
                    XCmdToChangeScene.execute(pgo,
                        PGOColorScenario.HueChangeScene.getSingleton(),
                        this.getReturnScene());
                    break;
                case 2:
                    XCmdToChangeScene.execute(pgo,
                        PGOColorScenario.SatChangeScene.getSingleton(),
                        this.getReturnScene());
                    break;
                case 3:
                    XCmdToChangeScene.execute(pgo,
                        PGOColorScenario.BriChangeScene.getSingleton(),
                        this.getReturnScene());
                    break;
            }
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
                case KeyEvent.VK_C:
                    pgo.getHSBPanel().setVisible(false);
                    Dimension prevSize = pgo.getFrame().size();
                    pgo.getFrame().setSize(prevSize.width, prevSize.height - pgo.SLIDER_HEIGHT);
                    XCmdToChangeScene.execute(pgo, this.getReturnScene(), null);
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
            PGO pgo = (PGO) this.mScenario.getApp();
            pgo.getHSBPanel().setVisible(true);
            pgo.getFrame().revalidate();
        }

        @Override
        public void getReady() {
        }

        @Override
        public void wrapUp() {
        }
    }
    
    public static class HueChangeScene extends PGOScene {
        // singleton pattern
        private static HueChangeScene mSingleton = null;
        public static HueChangeScene createSingleton(XScenario scenario) {
            assert (HueChangeScene.mSingleton == null);
            HueChangeScene.mSingleton = new HueChangeScene(scenario);
            return HueChangeScene.mSingleton;
        }
        public static HueChangeScene getSingleton() {
            assert (HueChangeScene.mSingleton != null);
            return HueChangeScene.mSingleton;
        }
        
        private HueChangeScene(XScenario scenario) {
            super(scenario);
        }
        
        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            
            float curHue = (float) pgo.getHueSlider().getValue()/360f;
            float prevSat = (float) pgo.getSatSlider().getValue()/255f;
            float prevBri = (float) pgo.getBriSlider().getValue()/255f;
            
            if(pgo.getHueSlider().getValueIsAdjusting()) {
                ImageIcon imgIcon = pgo.getImageIcon();
                Image img = imgIcon.getImage();
                BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

                // Draw the image on to the buffered image
                Graphics2D bGr = bImg.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();

                int width = bImg.getWidth();
                int height = bImg.getHeight();
                WritableRaster raster = bImg.getRaster();

                for (int xx = 0; xx < width; xx++) {
                  for (int yy = 0; yy < height; yy++) {
                    int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                    pixels = PGOColorScenario.getSingleton().calcHSB(pixels, curHue, prevSat, prevBri);
                    raster.setPixel(xx, yy, pixels);
                  }
                }

                pgo.getImageLabel().setIcon(new ImageIcon(bImg.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                
                ArrayList<PGOPolygon> polygons = pgo.getPolygonMgr().getPolygons();
                for (PGOPolygon polygon : polygons) {
                    polygon.setColor(polygon.getBgColor(pgo));
                }
                pgo.getFrame().revalidate();
            }       
        }

        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            pgo.getCanvas2D().setFocusable(true);
            XCmdToChangeScene.execute(pgo,
                PGOColorScenario.ColorReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    pgo.getHSBPanel().setVisible(false);
                    Dimension prevSize = pgo.getFrame().size();
                    pgo.getFrame().setSize(prevSize.width, prevSize.height - pgo.SLIDER_HEIGHT);
                    pgo.getCanvas2D().setFocusable(true);
                    XCmdToChangeScene.execute(pgo, this.getReturnScene(), null);
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
    }
    
    public static class SatChangeScene extends PGOScene {
        // singleton pattern
        private static SatChangeScene mSingleton = null;
        public static SatChangeScene createSingleton(XScenario scenario) {
            assert (SatChangeScene.mSingleton == null);
            SatChangeScene.mSingleton = new SatChangeScene(scenario);
            return SatChangeScene.mSingleton;
        }
        public static SatChangeScene getSingleton() {
            assert (SatChangeScene.mSingleton != null);
            return SatChangeScene.mSingleton;
        }
        
        private SatChangeScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            
            float prevHue = (float) pgo.getHueSlider().getValue()/360f;
            float curSat = (float) pgo.getSatSlider().getValue()/255f;
            float prevBri = (float) pgo.getBriSlider().getValue()/255f;
            
            if(pgo.getSatSlider().getValueIsAdjusting()) {
                ImageIcon imgIcon = pgo.getImageIcon();
                Image img = imgIcon.getImage();
                BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

                // Draw the image on to the buffered image
                Graphics2D bGr = bImg.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();

                int width = bImg.getWidth();
                int height = bImg.getHeight();
                WritableRaster raster = bImg.getRaster();

                for (int xx = 0; xx < width; xx++) {
                  for (int yy = 0; yy < height; yy++) {
                    int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                    pixels = PGOColorScenario.getSingleton().calcHSB(pixels, prevHue, curSat, prevBri);
                    raster.setPixel(xx, yy, pixels);
                  }
                }

                pgo.getImageLabel().setIcon(new ImageIcon(bImg.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                
                ArrayList<PGOPolygon> polygons = pgo.getPolygonMgr().getPolygons();
                for (PGOPolygon polygon : polygons) {
                    polygon.setColor(polygon.getBgColor(pgo));
                }
                pgo.getFrame().revalidate();
            }       
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            pgo.getCanvas2D().setFocusable(true);
            XCmdToChangeScene.execute(pgo,
                PGOColorScenario.ColorReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    pgo.getHSBPanel().setVisible(false);
                    Dimension prevSize = pgo.getFrame().size();
                    pgo.getFrame().setSize(prevSize.width, prevSize.height - pgo.SLIDER_HEIGHT);
                    pgo.getCanvas2D().setFocusable(true);
                    XCmdToChangeScene.execute(pgo, this.getReturnScene(), null);
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
    }
    
    
    public static class BriChangeScene extends PGOScene {
        // singleton pattern
        private static BriChangeScene mSingleton = null;
        public static BriChangeScene createSingleton(XScenario scenario) {
            assert (BriChangeScene.mSingleton == null);
            BriChangeScene.mSingleton = new BriChangeScene(scenario);
            return BriChangeScene.mSingleton;
        }
        public static BriChangeScene getSingleton() {
            assert (BriChangeScene.mSingleton != null);
            return BriChangeScene.mSingleton;
        }
        
        private BriChangeScene(XScenario scenario) {
            super(scenario);
        }

        @Override
        public void handleChange(ChangeEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            float prevHue = (float) pgo.getHueSlider().getValue()/360f;
            float prevSat = (float) pgo.getSatSlider().getValue()/255f;
            float curBri = (float) pgo.getBriSlider().getValue()/255f;
            
            if(pgo.getBriSlider().getValueIsAdjusting()) {
                ImageIcon imgIcon = pgo.getImageIcon();
                Image img = imgIcon.getImage();
                BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

                // Draw the image on to the buffered image
                Graphics2D bGr = bImg.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();

                int width = bImg.getWidth();
                int height = bImg.getHeight();
                WritableRaster raster = bImg.getRaster();

                for (int xx = 0; xx < width; xx++) {
                  for (int yy = 0; yy < height; yy++) {
                    int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                    pixels = PGOColorScenario.getSingleton().calcHSB(pixels, prevHue, prevSat, curBri);
                    raster.setPixel(xx, yy, pixels);
                  }
                }

                pgo.getImageLabel().setIcon(new ImageIcon(bImg.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                
                ArrayList<PGOPolygon> polygons = pgo.getPolygonMgr().getPolygons();
                for (PGOPolygon polygon : polygons) {
                    polygon.setColor(polygon.getBgColor(pgo));
                }
                pgo.getFrame().revalidate();
            }       
        }
        
        @Override
        public void handleMousePress(MouseEvent e) {
        }

        @Override
        public void handleMouseDrag(MouseEvent e) {
        }

        @Override
        public void handleMouseRelease(MouseEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            pgo.getCanvas2D().setFocusable(true);
            XCmdToChangeScene.execute(pgo,
                PGOColorScenario.ColorReadyScene.getSingleton(),
                this.getReturnScene());
        }

        @Override
        public void handleKeyDown(KeyEvent e) {
        }

        @Override
        public void handleKeyUp(KeyEvent e) {
            PGO pgo = (PGO) this.mScenario.getApp();
            int code = e.getKeyCode();

            switch (code) {
                case KeyEvent.VK_C:
                    pgo.getHSBPanel().setVisible(false);
                    Dimension prevSize = pgo.getFrame().size();
                    pgo.getFrame().setSize(prevSize.width, prevSize.height - pgo.SLIDER_HEIGHT);
                    pgo.getCanvas2D().setFocusable(true);
                    XCmdToChangeScene.execute(pgo, this.getReturnScene(), null);
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
    }
    
    protected int[] calcHSB(int[] pixels, float deltaH, float deltaS, float deltaB) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(pixels[0], pixels[1], pixels[2], hsb);

        float hue = hsb[0] + deltaH;
        float saturation = hsb[1] + deltaS;
        float brightness = hsb[2] + deltaB;

        if (hue > 1f) hue = 1f;
        else if (hue < 0f) hue = 0f;
        if (saturation > 1f) saturation = 1f;
        else if (saturation < 0f) saturation = 0f;
        if (brightness > 1f) brightness = 1f;
        else if (brightness < 0f) brightness = 0f;

        Color c = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        pixels[0] = red;
        pixels[1] = green;
        pixels[2] = blue;
        
        return pixels;
    }
    
    protected int findArea(MouseEvent e) {
        PGO pgo = (PGO) this.mApp;
        return 1;
    }
}
