package lightning_mage;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.time.temporal.Temporal;
import java.util.LinkedList;

import static lightning_mage.Constants.Projectiles.*;

public class Controller {

    private LinkedList<Projectile> projectiles = new LinkedList<Projectile>();
    private Projectile tempProjectile;
    private Game game;
    public Controller(Game game){
        this.game = game;
        addProjectile(new Projectile(Playing.getWASDPlayer().getHitBox().x, Playing.getWASDPlayer().getHitBox().y,
                CHARGE_WIDTH,CHARGE_HEIGHT,
                Playing.getWASDPlayer().getFlipY(), game));
    }

    public void update(){
        for (Projectile projectile : projectiles) {
            tempProjectile = projectile;
            if (tempProjectile.getX() < 0 || tempProjectile.getX() > Game.gameWidth)
                projectiles.remove(tempProjectile);
            tempProjectile.update();
        }
    }

    public void updateCollision(){
        projectiles.remove(tempProjectile);
    }

    public void render(Graphics g){
        for (Projectile projectile : projectiles) {
            tempProjectile = projectile;
            tempProjectile.render(g);
        }
    }
    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
    }

    public Projectile getTempProjectile(){
        return tempProjectile;
    }

}
