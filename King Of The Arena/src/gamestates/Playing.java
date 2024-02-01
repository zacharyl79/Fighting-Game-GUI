package gamestates;
import audio.Audioplayer;
import java.awt.*;
import java.awt.event.*;
import Players.*;
import lightning_mage.Controller;
import lightning_mage.Projectile;
import main.Game;
import main.LoadSave;


import static lightning_mage.Constants.Projectiles.CHARGE_HEIGHT;
import static lightning_mage.Constants.Projectiles.CHARGE_WIDTH;


public class Playing extends State implements StateMethods{
    private static ArrowKeyPlayer arrowKeyPlayer;
    private static WASDPlayer wasdPlayer;
    private static Controller controller;
    private Floor floor;
    private final Game game;

    private static final long COOLDOWN_TIME = 1500;
    private long currentTime = System.currentTimeMillis();

    private long lastTimeClicked;

    private boolean showProjectile;

    public Playing(Game game) {
        super(game);
        this.game = game;
        initializeClasses();
    }

    private void initializeClasses() {
        arrowKeyPlayer = new ArrowKeyPlayer(1300, 510, 675, 520);
        wasdPlayer = new WASDPlayer(500, 550, 675, 520);
        floor = new Floor(game.getGamePanel(),0,880,1920,80);
        controller = new Controller(this.getGame());
    }

    public void windowFocusLost() {
        arrowKeyPlayer.resetDirectionBooleans();
        wasdPlayer.resetDirectionBooleans();
    }

    public static ArrowKeyPlayer getArrowKeyPlayer(){
        return arrowKeyPlayer;
    }
    public static WASDPlayer getWASDPlayer(){
        return wasdPlayer;
    }
    public static Controller getController() {
        return controller;
    }

    @Override
    public void update() {
        controller.update();
        arrowKeyPlayer.update();
        wasdPlayer.update();
    }

    @Override
    public void draw(Graphics g) {
        floor.render(g);
        arrowKeyPlayer.render(g);
        wasdPlayer.render(g);
        arrowKeyPlayer.drawUI(g);
        wasdPlayer.drawUI(g);
        if (showProjectile)
            controller.render(g);
        g.drawImage(LoadSave.GetSpriteSheet("LMName.png"), 0, -33, 175, 120, null);
        g.drawImage(LoadSave.GetSpriteSheet("SName.png"), Game.gameWidth - 145, -33, 175, 120, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        currentTime = System.currentTimeMillis();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                arrowKeyPlayer.setJump(true);
                game.getAudioPlayer().playEffects(Audioplayer.JUMP);
                if(getArrowKeyPlayer().isInAir()) {
                    game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                }
                break;
            case KeyEvent.VK_LEFT:
                arrowKeyPlayer.setLeft(true);
                game.getAudioPlayer().playEffectsInfinite(Audioplayer.WALKING);
                if(getArrowKeyPlayer().isInAir())
                    game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                else
                    game.getAudioPlayer().playEffectsInfinite(Audioplayer.WALKING);
                break;
            case KeyEvent.VK_RIGHT:
                arrowKeyPlayer.setRight(true);
                if(getArrowKeyPlayer().isInAir())
                    game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                else
                    game.getAudioPlayer().playEffectsInfinite(Audioplayer.WALKING);
                break;
            case KeyEvent.VK_DOWN:
                arrowKeyPlayer.setBlocking(true);
                game.getAudioPlayer().playEffects(Audioplayer.BLOCK);
                break;
            case KeyEvent.VK_W:
                wasdPlayer.setJump(true);
                game.getAudioPlayer().playEffects(Audioplayer.JUMP);
                if(getWASDPlayer().isInAir()) {
                    game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                }
                game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                break;
            case KeyEvent.VK_A:
                wasdPlayer.setLeft(true);
                if(getWASDPlayer().isInAir())
                    game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                else
                    game.getAudioPlayer().playEffectsInfinite(Audioplayer.LMWALKING);
                break;
            case KeyEvent.VK_D:
                wasdPlayer.setRight(true);
                if(getWASDPlayer().isInAir())
                    game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                else
                    game.getAudioPlayer().playEffectsInfinite(Audioplayer.LMWALKING);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentTime = System.currentTimeMillis();
        switch (e.getKeyCode()){
            case KeyEvent.VK_NUMPAD3:
                if (currentTime - lastTimeClicked >= 3000 && !ArrowKeyPlayer.currentlyAttacking) {
                    arrowKeyPlayer.setAttacking2(true);
                    lastTimeClicked = currentTime;
                    game.getAudioPlayer().playEffects(Audioplayer.ATTACK2);
                    if(WASDPlayer.hurt){
                        game.getAudioPlayer().playEffects(Audioplayer.LMHURT);
                    }
                    if(!WASDPlayer.alive){
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }
                }
                else if (currentTime - lastTimeClicked < COOLDOWN_TIME && ArrowKeyPlayer.currentlyAttacking)
                    arrowKeyPlayer.setAttacking2(false);
                break;
            case KeyEvent.VK_NUMPAD2:
                if (currentTime - lastTimeClicked >= COOLDOWN_TIME && !ArrowKeyPlayer.currentlyAttacking) {
                    arrowKeyPlayer.setAttacking3(true);
                    lastTimeClicked = currentTime;
                    game.getAudioPlayer().playEffects(Audioplayer.ATTACK3);
                    if(WASDPlayer.hurt){
                        game.getAudioPlayer().playEffects(Audioplayer.LMHURT);
                    }
                    if(!WASDPlayer.alive) {
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }
                }
                else if (currentTime - lastTimeClicked < COOLDOWN_TIME && ArrowKeyPlayer.currentlyAttacking)
                    arrowKeyPlayer.setAttacking3(false);
                break;
            case KeyEvent.VK_NUMPAD1:
                if (currentTime - lastTimeClicked >= COOLDOWN_TIME && !ArrowKeyPlayer.currentlyAttacking) {

                    arrowKeyPlayer.setAttacking1(true);
                    game.getAudioPlayer().playEffects(Audioplayer.ATTACK1);
                    lastTimeClicked = currentTime;
                    if(WASDPlayer.hurt){
                        game.getAudioPlayer().playEffects(Audioplayer.LMHURT);
                    }
                    if(!WASDPlayer.alive) {
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }
                }
                else if (currentTime - lastTimeClicked < COOLDOWN_TIME && ArrowKeyPlayer.currentlyAttacking)
                    arrowKeyPlayer.setAttacking1(false);
                break;
            case KeyEvent.VK_M:
                if (currentTime - lastTimeClicked >= COOLDOWN_TIME && !WASDPlayer.currentlyAttacking) {
                    wasdPlayer.setAttacking1(true);
                    game.getAudioPlayer().playEffects(Audioplayer.LMATTACK1);
                    lastTimeClicked = currentTime;
                    if(ArrowKeyPlayer.isHurt) {
                        game.getAudioPlayer().playEffects(Audioplayer.HURT);
                    }
                    if(!ArrowKeyPlayer.isAlive) {
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }
                }
                else if(currentTime - lastTimeClicked < COOLDOWN_TIME && WASDPlayer.currentlyAttacking)
                    wasdPlayer.setAttacking1(false);
                break;
            case KeyEvent.VK_N:
                showProjectile = true;
                if (currentTime - lastTimeClicked >= 3000 && !WASDPlayer.currentlyAttacking) {
                    wasdPlayer.setLightningBall(true);
                    game.getAudioPlayer().playEffects(Audioplayer.CHARGE);

                    controller.addProjectile(new Projectile(Playing.getWASDPlayer().getHitBox().x,
                            Playing.getWASDPlayer().getHitBox().y, CHARGE_WIDTH, CHARGE_HEIGHT, Playing.getWASDPlayer().getFlipY(), this.getGame()));
                    lastTimeClicked = currentTime;
                    if(Playing.getController().getTempProjectile().getHitBox().intersects(Playing.getArrowKeyPlayer().getHitBox())
                            && !Playing.getArrowKeyPlayer().getBlocking() && Projectile.currentProjectile){
                        game.getAudioPlayer().playEffects(Audioplayer.HURT);
                    }
                    if(!ArrowKeyPlayer.isAlive) {
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }

                }
                else if(currentTime - lastTimeClicked < COOLDOWN_TIME && WASDPlayer.currentlyAttacking)
                    wasdPlayer.setLightningBall(false);
                break;
            case KeyEvent.VK_B:
                if (currentTime - lastTimeClicked >= COOLDOWN_TIME && !WASDPlayer.currentlyAttacking) {
                    wasdPlayer.setAttacking2(true);
                    game.getAudioPlayer().playEffects(Audioplayer.LMATTACK2);
                    lastTimeClicked = currentTime;
                    if(ArrowKeyPlayer.isHurt){
                        game.getAudioPlayer().playEffects(Audioplayer.HURT);
                    }
                    if(!ArrowKeyPlayer.isAlive) {
                        game.getAudioPlayer().playEffects(Audioplayer.LMDIE);
                    }
                }
                else if(currentTime - lastTimeClicked < COOLDOWN_TIME && WASDPlayer.currentlyAttacking)
                    wasdPlayer.setAttacking2(false);
                break;
            case KeyEvent.VK_UP:
                arrowKeyPlayer.setJump(false);
                game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                break;
            case KeyEvent.VK_LEFT:
                arrowKeyPlayer.setLeft(false);
                game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                break;
            case KeyEvent.VK_RIGHT:
                arrowKeyPlayer.setRight(false);
                game.getAudioPlayer().stopEffects(Audioplayer.WALKING);
                break;
            case KeyEvent.VK_DOWN:
                arrowKeyPlayer.setBlocking(false);
                game.getAudioPlayer().stopEffects(Audioplayer.BLOCK);
                break;
            case KeyEvent.VK_W:
                wasdPlayer.setJump(false);
                game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                break;
            case KeyEvent.VK_A:
                wasdPlayer.setLeft(false);
                game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                break;
            case KeyEvent.VK_D:
                wasdPlayer.setRight(false);
                game.getAudioPlayer().stopEffects(Audioplayer.LMWALKING);
                break;

        }
    }
}
