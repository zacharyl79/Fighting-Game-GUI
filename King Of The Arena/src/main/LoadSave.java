package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String samuraiSprites = "Samurai.png";
    public static final String lightningMageSprites = "Lightning_Mage.png";
    public static final String statusHealth = "HealthBarBlockBar.png";
    public static final String charge = "Charge.png";
    private int count;
    public static BufferedImage GetSpriteSheet(String fileName) {
        BufferedImage img = null;
        InputStream iS = LoadSave.class.getResourceAsStream("/" + fileName);
        try{
            img = ImageIO.read(iS);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                iS.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

}
