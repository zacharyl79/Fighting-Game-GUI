package gamestates;

import main.GamePanel;
import main.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.gameHeight;
import static main.Game.gameWidth;

public class Floor extends JPanel {
    private int x, y;
    private int width, height;
    private GamePanel gamePanel;
    private BufferedImage background;
    private Rectangle floorhitbox;

    public Floor(GamePanel gamePanel, int x, int y, int width, int height){
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
        initHitbox();
    }
//    private void drawHitbox(Graphics g){
//        g.setColor(Color.YELLOW);
//        g.drawRect(floorhitbox.x,floorhitbox.y,floorhitbox.width,floorhitbox.height);
//    }
    private void initHitbox(){
        floorhitbox = new Rectangle(x,y,width, height);
    }

    public void render(Graphics g) {
        super.paintComponent(g);
        background = LoadSave.GetSpriteSheet("Backalley.png");
        g.drawImage(background, 0, 0, gameWidth, gameHeight, null);
//        drawHitbox(g);
    }

}
