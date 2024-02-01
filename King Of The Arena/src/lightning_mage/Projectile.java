package lightning_mage;

import Players.ArrowKeyPlayer;
import gamestates.Playing;
import main.Game;
import main.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.*;

import static lightning_mage.Constants.PlayerConstants.GetSpriteAmount;
import static lightning_mage.Constants.Projectiles.*;
import static main.LoadSave.charge;
import gamestates.Playing.*;


public class Projectile {
    private double x, y, width, height;
    private Game game;
    private BufferedImage image;
    private int aniTick, aniIndex, aniSpeed = 69;  // FUNNI
    private BufferedImage[] animations;
    private Rectangle2D.Float hitBox;
    private int direction;

    private final double projectileDMG = Playing.getWASDPlayer().getLightBallDMG();

    public static boolean currentProjectile;



    public Projectile(float x, float y, float width, float height, int direction, Game game){
        this.x= x;
        this.y= y;
        this.width = width;
        this.height = height;
        this.direction = direction;
        hitBox = new Rectangle2D.Float(x, y, width, height);
        loadAnimations();
    }

    private void loadAnimations(){
        image = LoadSave.GetSpriteSheet(charge);
        animations = new BufferedImage[9];
        for (int i = 0; i < animations.length; i++)
            animations[i] = image.getSubimage(i * 64, 0, 64, 64);
    }

    public void update() {
        updateAnimationTick();
        x += direction * 10;
        hitBox.x += direction * 10;
        attackMode();
    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= animations.length) {
                aniIndex = 0;
                currentProjectile = false;
                }
            }
        }
    public void render(Graphics g){
        g.drawImage(animations[aniIndex], (int)x - 128,(int)y - 128, (int)(CHARGE_WIDTH* Game.scale), (int)(CHARGE_WIDTH* Game.scale), null);
//        g.setColor(Color.red);
//        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    public void attackMode() {
        if (this.hitBox.intersects(Playing.getArrowKeyPlayer().getHitBox()) && !Playing.getArrowKeyPlayer().getBlocking() && currentProjectile) {
            ArrowKeyPlayer.LM3DamageCount++;
            Playing.getController().updateCollision();
            hitBox.x = 10000; // Don't ask
            Playing.getArrowKeyPlayer().updateHealth(projectileDMG);
            ArrowKeyPlayer.isHurt = true;
            currentProjectile = false;
        }
        if ((this.hitBox.x <= Playing.getArrowKeyPlayer().getHitBox().x - 20 && currentProjectile && Playing.getArrowKeyPlayer().getBlocking()) || (this.hitBox.x <= Playing.getArrowKeyPlayer().getHitBox().x + Playing.getArrowKeyPlayer().getHitBox().width + 20 && currentProjectile && Playing.getArrowKeyPlayer().getBlocking())){
            hitBox.x = 10000;
            Playing.getController().updateCollision();
            currentProjectile = false;
        }
        if (Playing.getArrowKeyPlayer().getCurrentHealth() <= 0){
            Playing.getArrowKeyPlayer().updateHealth(100000);
            ArrowKeyPlayer.isAlive = false;
        }
    }

    public Game getGame() {
        return game;
    }
    public Rectangle2D.Float getHitBox(){
        return hitBox;
    }
    public double getX() {
        return x;
    }

}
