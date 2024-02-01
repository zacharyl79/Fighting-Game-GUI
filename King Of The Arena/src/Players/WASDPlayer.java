package Players;

import audio.Audioplayer;
import gamestates.GameState;
import gamestates.Playing;
import lightning_mage.Constants;
import lightning_mage.Projectile;
import main.Game;
import main.LoadSave;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;

import gamestates.Playing.*;

import static lightning_mage.Constants.PlayerConstants.*;
import static main.HelpMethods.*;
import Players.ArrowKeyPlayer.*;

public class WASDPlayer extends Entity {
    private BufferedImage[][] animations;
    private Game game;
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.5f;
    private boolean moving, attacking1, attacking2, lightningBall, regen, dwindle = false;
    public static boolean hurt;
    public static boolean alive = true;
    private float airSpeed = 2.7f;
    private float xOffSet = 90 * Game.scale;
    private float yOffset = 66 * Game.scale;
    private float gravity = 0.03f * Game.scale;
    private float jumpSpeed = -3.3f * Game.scale;
    private float fallSpeed = 0.5f * Game.scale;
    private boolean inAir = false;
    private int flipX = 0;
    private int flipY = 1;
    private BufferedImage statusBar;
    private int statusBarWidth = 666;
    private int statusBarHeight = 375;
    private int statusBarX = 0;
    private int statusBarY = 0;
    private int HealthBarWidth = 555;
    private int HealthBarHeight = 40;
    private int HealthBarX = 70;
    private int HealthBarY = 61;
    private int maxHealth = 850;
    private int currentHealth = maxHealth;
    private int changeOfHealth = HealthBarWidth;
    private int BlockBarWidth = 276;
    private int BlockBarHeight = 42;
    private int BlockBarX = HealthBarX;
    private int BlockBarY = HealthBarY + (int)(25 * Game.scale);
    private int BlockDuration = 1000;
    private int currentDuration = BlockDuration;
    private int BlockBarChange = BlockBarWidth;

    private double attack1DMG = 75;
    private double attack2DMG = 30;
    private final double lightBallDMG = 125.0;
    // ATTACK BOX
    private Rectangle2D.Float attack1Box, attack2Box;
    public static boolean currentlyAttacking;
    public static int S1DamageCount, S2DamageCount, S3DamageCount, numAttacks;




    public WASDPlayer(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initializeHitBox(x, y, (float) ((width - 400) /2 - 9.5), height - 190);
        initializeAttackBoxes();
    }

    private void initializeAttackBoxes() {
        attack1Box = new Rectangle2D.Float(x + 195, y, (int)(100*Game.scale), 80);
        attack2Box = new Rectangle2D.Float(x+445, y, (int)(100*Game.scale), 160);
    }

    public void update() {
        updateHealthBar();
        durationLeft();
        updateAttack1Box();
        updateAttack2Box();
        updateDurationOfBlock();
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    private void updateAttack1Box() {
        if(right){
            attack1Box.x = hitBox.x + (2*hitBox.width) + 125 - attack1Box.width;
        }else if(left){
            attack1Box.x = hitBox.x - (hitBox.width) - 125;
        }
        attack1Box.y = hitBox.y + (Game.scale * 30);
    }
    private void updateAttack2Box(){
        if (right){
            attack2Box.x = hitBox.x + (2*hitBox.width) + 125 - attack2Box.width;
        }else if (left){
            attack2Box.x = hitBox.x - (hitBox.width) + 125 - attack2Box.width;
        }
        attack2Box.y = hitBox.y + (Game.scale * 30);
    }


    private void updateDurationOfBlock() {
        BlockBarChange = (int) ((currentDuration / (float) BlockDuration) * BlockBarWidth);
    }

    private void durationLeft(){
        if((playerAction == ATTACK_1 || playerAction == ATTACK_2) && BlockDuration == 1000 && dwindle) {
            regen = false;
            currentDuration -= 10;
            if (currentDuration == 0) {
                attack1DMG = 75;
                attack2DMG = 30;
                dwindle = false;
                regen = true;
            }
        }
        else if(currentDuration == 0 || regen) {
            currentDuration += 1;
        }
        if (currentDuration == BlockDuration) {
            attack1DMG = 150.0;
            attack2DMG = 100.0;
            regen = false;
            dwindle = true;
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xOffSet) + flipX, (int) (hitBox.y - yOffset), width * flipY, height, null);
//        drawHitBox(g);
//        drawAttack1Box(g);
//        drawAttack2Box(g);
    }



//    private void drawAttack2Box(Graphics g) {
//        g.setColor(Color.MAGENTA);
//        g.drawRect((int)(attack2Box.x), (int) attack2Box.y, (int)attack2Box.width, (int)attack2Box.height);
//    }
//
//    private void drawAttack1Box(Graphics g) {
//        g.setColor(Color.PINK);
//        g.drawRect((int)(attack1Box.x), (int)attack1Box.y, (int)attack1Box.width, (int)(attack1Box.height));
//    }

    private void updateHealthBar() {
        changeOfHealth = (int) ((currentHealth / (float) maxHealth) * HealthBarWidth);
        if(currentHealth <= 0) currentHealth = 0;
    }


    public void drawUI(Graphics g) {
        g.drawImage(statusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        drawHealth(g);
        drawBlock(g);
    }

    private void drawHealth(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(HealthBarX + statusBarX, HealthBarY + statusBarY, changeOfHealth, HealthBarHeight);
    }

    private void drawBlock(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(BlockBarX, BlockBarY + statusBarY, BlockBarChange, BlockBarHeight);
    }


    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction) && alive) {
                aniIndex = 0;
                attacking1 = false;
                attacking2 = false;
                lightningBall = false;
                currentlyAttacking = false;
                hurt = false;
            }
            if(aniIndex >= GetSpriteAmount(playerAction) && !alive){
                GameState.state = GameState.GAMEOVER;
            }
        }
    }

    private void setAnimations() {
        int startAni = playerAction;
        if (moving && alive)
            playerAction = WALK;
        else
            playerAction = IDLE;
        if (inAir && alive) {
            if (airSpeed > 50) {
                aniSpeed = 1;
                playerAction = JUMP;
            } else {
                aniSpeed = 50;
                playerAction = FALL;
            }
        }
        if (hurt && alive){
            moving = false;
            playerAction = HURT;
        }
        if (attacking1 && !attacking2 && !lightningBall && !hurt && alive) {
            aniSpeed = 15;
            playerAction = ATTACK_1;
        }
        else if (lightningBall && !attacking1 && !attacking2 && !hurt && alive){
            aniSpeed = 10;
            playerAction = LIGHT_BALL;
        }
        else if(attacking2 && !attacking1 && !lightningBall && !hurt && alive) {
            aniSpeed = 25;
            playerAction = ATTACK_2;
        }
        if (!alive) {
            moving = false;
            aniSpeed = 120;
            playerAction = DEAD;
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
        aniSpeed = 50;
    }

    private void updatePos(){

        moving = false;
        float xSpeed = 0;
        if (left && !currentlyAttacking) {
            xSpeed -= playerSpeed;
            flipX = width-90;
            flipY = -1;
        }
        if (right && !currentlyAttacking) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipY = 1;
        }

        if(jump) {
            theArtOfJumping();
        }


        if (inAir) {
            if (canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else {
                hitBox.y = GetEntityYPosWithinFrame(hitBox, airSpeed);
                if (airSpeed > 0){
                    resetInAir();
                }
                else {
                    airSpeed = fallSpeed;
                }
                updateXPos(xSpeed);
            }
        }
        else {
            updateXPos(xSpeed);
        }

        if (!left && !right && !jump) return;

        if(!inAir){
            if(EntityOnFloor(hitBox)){
                inAir = true;
            }
        }

        moving = true;
    }

    private void theArtOfJumping() {
        if(inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitBox.x+xSpeed, hitBox.y, hitBox.width, hitBox.height)){
            hitBox.x += xSpeed;
        }
        else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }
    }


    private void loadAnimations(){
        BufferedImage img = LoadSave.GetSpriteSheet(LoadSave.lightningMageSprites);
        animations = new BufferedImage[11][12];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 128, i * 101, 132, 100);
            }
        }
        statusBar = LoadSave.GetSpriteSheet(LoadSave.statusHealth);
    }

    public void resetDirectionBooleans(){
        left = false;
        right  = false;
        up = false;
        down = false;
    }

    public void attackMode(boolean attack, double DMG){
        if (attack == attacking1){
            Playing.getArrowKeyPlayer().defendMode(attack1Box, DMG);
        }
        else if(attack == attacking2){
            Playing.getArrowKeyPlayer().defendMode(attack2Box, DMG);
        }
        else if(attack == lightningBall){
            Playing.getArrowKeyPlayer().defendMode(Playing.getController().getTempProjectile().getHitBox(), DMG);
        }
    }
    public void defendMode(Rectangle2D.Float attack, double attackDMG){
        if (attack.intersects(this.hitBox)){
            currentHealth -= (int) attackDMG;
            ArrowKeyPlayer.numAttacks++;
            hurt = true;
            if (right) updateXPos(-50);
            if (left) updateXPos(50);
            if(attackDMG == Playing.getArrowKeyPlayer().getAttack1DMG()) {S1DamageCount++;}
            else if(attackDMG == Playing.getArrowKeyPlayer().getAttack2DMG()) {S2DamageCount++;}
            else if(attackDMG == Playing.getArrowKeyPlayer().getAttack3DMG()) {S3DamageCount++;}
        }
        if (currentHealth<= 0){
            currentHealth = 0;
            alive = false;
        }
    }

    public void setAttacking1(boolean attacking1){
        if (!lightningBall && !attacking2 && !currentlyAttacking) {
            this.attacking1 = attacking1;
            attackMode(attacking1, attack1DMG);
        }
        currentlyAttacking = true;
    }

    public void setLightningBall(boolean lightningBall){
        if (!attacking2 && !attacking1 && !currentlyAttacking) {
            Projectile.currentProjectile = true;
            this.lightningBall = lightningBall;
            for (int i = aniIndex; i < GetSpriteAmount(playerAction); i++) {
                attackMode(lightningBall, lightBallDMG);
            }
        }
        currentlyAttacking = true;
    }

    public void setAttacking2(boolean attacking2) {
        if (!lightningBall && !attacking1 && !currentlyAttacking) {
            this.attacking2 = attacking2;
            attackMode(attacking2, attack2DMG);
        }
        currentlyAttacking = true;
    }

    public boolean isLeft() {
        return left;
    }
    public boolean isInAir(){
        return inAir;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }

    public int getFlipY(){
        return flipY;
    }
    public double getLightBallDMG(){
        return lightBallDMG;
    }

    public double getAttack1DMG() {
        return attack1DMG;
    }

    public double getAttack2DMG() {
        return attack2DMG;
    }

    public int getS1DamageCount() {
        return S1DamageCount;
    }

    public int getS2DamageCount() {
        return S2DamageCount;
    }

    public int getS3DamageCount() {
        return S3DamageCount;
    }

}

