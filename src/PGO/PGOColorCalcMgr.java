package PGO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class PGOColorCalcMgr {
    // field
    private PGO mPGO = null;
    
    // constructor
    public PGOColorCalcMgr(PGO pgo) {
        this.mPGO = pgo;
    }
    
    // methods
    public Color getBgColor(PGO pgo, PGOPolygon polygon) {
        ImageIcon imgIcon =
            (ImageIcon) pgo.getPanelMgr().getImageLabel().getIcon();

        Image img = imgIcon.getImage();
        BufferedImage bImg = new BufferedImage(img.getWidth(null),
            img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        
        ArrayList<Point> pts = polygon.getPts();
        Point gravity = pgo.getPolygonCalcMgr().calcGravityPt(pts);
        int red = 0;
        int green = 0;
        int blue = 0;
        
        for (int i = 0; i < 3; i ++) {
            Point pt1 = pts.get(i % 3);
            Point pt2 = pts.get((i + 1) % 3);
            int rgb = bImg.getRGB((pt1.x + gravity.x)/2, (pt1.y + gravity.y)/2);
            Color c = new Color(rgb);
            red  = red + c.getRed();
            green = green + c.getGreen();
            blue = blue + c.getBlue();
        }
        
        return new Color(red / 4, green / 4, blue / 4);
    }
    
    public void changeImageHSB(float hue, float sat, float bri) {
        PGOPanelMgr panelMgr = this.mPGO.getPanelMgr();
        ImageIcon imgIcon = panelMgr.getImageIcon();
        Image img = imgIcon.getImage();
        BufferedImage bImg = new BufferedImage(img.getWidth(null),
            img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

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
                pixels = this.calcHSB(pixels, hue, sat, bri);
                raster.setPixel(xx, yy, pixels);
            }
        }

        panelMgr.getImagePane().remove(panelMgr.getImageLabel());
        panelMgr.getImageLabel()
                .setIcon(new ImageIcon(bImg.getScaledInstance(width, height,
                Image.SCALE_DEFAULT)));
        panelMgr.getImagePane().add(panelMgr.getImageLabel());

        ArrayList<PGOPolygon> polygons =
            this.mPGO.getPolygonMgr().getPolygons();
        for (PGOPolygon polygon : polygons) {
            polygon.setColor(getBgColor(this.mPGO, polygon));
        }
        
        this.mPGO.getFrame().revalidate();
    }

    public int[] calcHSB(int[] pixels, float deltaH, float deltaS,
        float deltaB) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(pixels[0], pixels[1], pixels[2], hsb);

        float hue = hsb[0] + deltaH;
        float saturation = hsb[1] + deltaS;
        float brightness = hsb[2] + deltaB;

        if (hue > 1f) {
            hue = 1f;
        } else if (hue < 0f) {
            hue = 0f;
        }
        if (saturation > 1f) {
            saturation = 1f;
        } else if (saturation < 0f) {
            saturation = 0f;
        }
        if (brightness > 1f) {
            brightness = 1f;
        } else if (brightness < 0f) {
            brightness = 0f;
        }

        Color c = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        pixels[0] = c.getRed();
        pixels[1] = c.getGreen();
        pixels[2] = c.getBlue();

        return pixels;
    }
}
