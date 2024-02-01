package Players;

import audio.Audioplayer;
import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import main.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static samurai.Constants.PlayerConstants.*;
import static main.HelpMethods.*;


public class ArrowKeyPlayer extends Entity{
    // Animations variables
    private Game game;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = IDLE;
    private boolean up, right, left, jump;
    private boolean moving, attacking1, attacking2, attacking3, blocking, regen, dwindle = false;
    public static boolean isHurt;
    public static boolean isAlive = true;
    private boolean canBlock = true;

    // Jump Variables
    private float speed = 2.7f;
    private float xOffSet = 100 * Game.scale;
    private float yOffset = 48 * Game.scale;
    private float airSpeed = 0f;
    private final float gravity = 0.03f * Game.scale;
    private float jumpSpeed = -3.3f * Game.scale;
    private final float fallSpeed = 0.5f * Game.scale;
    private boolean inAir = false;

    // Reverse Image
    private int flipX = width-50;;
    private int flipY = -1;

    // Status Variables;
    private BufferedImage statusBar;
    private int statusBarWidth = -666;
    private int statusBarHeight = 375;
    private int statusBarX = (Game.gameWidth - 622) + (width-50);
    private int statusBarY = 0;

    // Health Bar Variables
    private int healthBarWidth = 555;
    private int healthBarHeight = 40;
    private int healthBarX = 65;
    private int healthBarY = 61;
    private int maxHealth = 1000;
    private int currentHealth = maxHealth;
    private int changeOfHealth = healthBarWidth;

    // Block Bar
    private int BlockBarWidth = 276;
    private int BlockHeight = 42;
    private int BlockBarX = healthBarX;
    private int BlockBarY = healthBarY + (int)(25 * Game.scale);
    private int BlockDuration = 500;
    private int currentDuration = BlockDuration;
    private int BlockBarChange = BlockBarWidth;

    public static boolean currentlyAttacking;
    private double attack1DMG = 50;
    private double attack2DMG = 120;
    private double attack3DMG = 70;
    public static int LM1DamageCount, LM2DamageCount, LM3DamageCount, numAttacks;


//    private double ATKcooldown = 0.5;  // COOLDOWN




    // Attack HitBoxes
    private Rectangle2D.Float attack1Box, attack2Box, attack3Box;


    public ArrowKeyPlayer(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initializeHitBox(x, y, (float) ((width - 400) /2 - 9.5), height - 150);
        initializeAttackBoxes();
    }

    private void initializeAttackBoxes() {
        attack1Box = new Rectangle2D.Float(x,y,(int)(100 * Game.scale), 180);
        attack2Box = new Rectangle2D.Float(x,y,(int)(100 * Game.scale), 225);
        attack3Box = new Rectangle2D.Float(x- hitBox.width + 155,(int)(y - 127.5),(int)(130 * Game.scale), 450);
    }

    public void update() {
        updateHealthBar();
        durationLeft();
        updateDurationOfBlock();
        updateAttack1Box();
        updateAttack2Box();
        updateAttack3Box();
        updatePos();
        updateAnimationTick();
        setAnimations();
    }

    private void updateAttack3Box() {
        if(right){
            attack3Box.x = hitBox.x + (2*hitBox.width) + 170 - attack3Box.width;
        }
        else if(left){
            attack3Box.x = hitBox.x - hitBox.width + 155 - attack3Box.width;
        }
        attack3Box.y = hitBox.y - (Game.scale * 54);
    }

    private void updateAttack2Box() {
        if(right){
            attack2Box.x = hitBox.x + (2*hitBox.width) + 125 - attack2Box.width;
        }
        else if(left){
            attack2Box.x = hitBox.x - hitBox.width + 125 - attack2Box.width;
        }
        attack2Box.y = hitBox.y;
    }

    private void updateAttack1Box() {
        if(right){
            attack1Box.x = hitBox.x + (2*hitBox.width) + 125 - attack1Box.width;
        }
        else if(left){
            attack1Box.x = hitBox.x - hitBox.width + 125 - attack1Box.width;
        }
        attack1Box.y = hitBox.y + (Game.scale * 30);
    }

    private void updateHealthBar() {
        changeOfHealth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updateDurationOfBlock() {
        BlockBarChange = (int) ((currentDuration / (float) BlockDuration) * BlockBarWidth);
    }

    private void durationLeft(){
        if(blocking && canBlock && BlockDuration == 500) {
            regen = false;
            dwindle = true;
            currentDuration -= 10;
            if (currentDuration == 0) {
                dwindle = false;
                regen = true;
                canBlock = false;
            }
        }
        else if(currentDuration == 0 || regen) {
            currentDuration += 1;
        }
        if (currentDuration == BlockDuration) {
            regen = false;
            dwindle = true;
            canBlock = true;
        }
    }

//    private void changeHealth(int value){
//        currentHealth += value;
//        if(currentHealth <= 0) currentHealth = 0;
//    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xOffSet) + flipX, (int) (hitBox.y - yOffset), width * flipY, height, null);
//        drawHitBox(g);
//        drawAttack1Box(g);
//        drawAttack2Box(g);
//        drawAttack3Box(g);
//        drawUI(g);
    }

//    private void drawAttack3Box(Graphics g) {
//        g.setColor(Color.MAGENTA);
//        g.drawRect((int)attack3Box.x, (int)attack3Box.y, (int)attack3Box.width, (int)attack3Box.height);
//    }
//
//    private void drawAttack2Box(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.drawRect((int)attack2Box.x, (int)attack2Box.y, (int)attack2Box.width, (int)attack2Box.height);
//    }
//
//    private void drawAttack1Box(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.drawRect((int)attack1Box.x, (int)attack1Box.y, (int)attack1Box.width, (int)attack1Box.height);
//    }

    public void drawUI(Graphics g) {
        g.drawImage(statusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        drawHealth(g);
        drawBlock(g);
    }

    private void drawHealth(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(Game.gameWidth - healthBarX - changeOfHealth, healthBarY + statusBarY, changeOfHealth, healthBarHeight);
    }

    private void drawBlock(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(Game.gameWidth - BlockBarX - BlockBarChange, BlockBarY + statusBarY, BlockBarChange, BlockHeight);
    }


    private void setAnimations() {
        int startAni = playerAction;
        if (moving && isAlive)
            playerAction = WALK;
        else
            playerAction = IDLE;
        if (inAir&& isAlive) {
            if (airSpeed > 0) {
                aniSpeed = 35;
                playerAction = JUMP;
            } else {
                aniSpeed = 35;
                playerAction = FALL;
            }
        }
        if (!isAlive){
            moving = false;
            aniSpeed = 120;
            playerAction = DEAD;
        }
        if (isHurt && isAlive){
            playerAction = HURT;
        }
        if (blocking && canBlock && !attacking1 && !attacking2 && !attacking3 && !isHurt && isAlive) {
            aniSpeed = 35;
            playerAction = PROTECTION;
        }
        if (attacking1 && !attacking2 && !attacking3 && !blocking && !isHurt && isAlive) {
            aniSpeed = 25;
            playerAction = ATTACK_1;
        }
        else if(attacking2 && !attacking1 && !attacking3 && !blocking && !isHurt && isAlive) {
            aniSpeed = 30;
            playerAction = ATTACK_2;
        }
        else if(attacking3 && !attacking1 && !attacking2 && !blocking && !isHurt && isAlive) {
            aniSpeed = 50;
            playerAction = ATTACK_3;
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    private void updateAnimationTick(){
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction) && isAlive) {
                aniIndex = 0;
                isHurt = false;
                attacking1 = false;
                attacking2 = false;
                attacking3 = false;
                currentlyAttacking = false;
            }
            if(aniIndex >= GetSpriteAmount(playerAction) && !isAlive){
                GameState.state = GameState.GAMEOVER;
            }
        }
    }
    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
        aniSpeed = 40;
    }

    private void updatePos(){

        moving = false;
        float xSpeed = 0;
        if (left && !currentlyAttacking && !isHurt) {
            xSpeed -= speed;
            flipX = width-50;
            flipY = -1;
        }
        if (right && !currentlyAttacking && !isHurt) {
            xSpeed += speed;
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
            System.out.println(hitBox.x);
        }
    }


    private void loadAnimations(){
        BufferedImage img = LoadSave.GetSpriteSheet(LoadSave.samuraiSprites);
        animations = new BufferedImage[10][9];
        for(int i = 0;i<animations.length;i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j*127, i*98, 132, 98);
            }
        }
        statusBar = LoadSave.GetSpriteSheet(LoadSave.statusHealth);
    }

    public void attackMode(boolean attack, double DMG){
        if (attack == attacking1){
            Playing.getWASDPlayer().defendMode(attack1Box, DMG);
        }else if(attack == attacking2){
            Playing.getWASDPlayer().defendMode(attack2Box, DMG);
        }else if (attack == attacking3){
            Playing.getWASDPlayer().defendMode(attack3Box, DMG);
        }
    }
    public void defendMode(Rectangle2D.Float attack, double attackDMG){
        if (attack.intersects(this.hitBox) && !blocking){
            currentHealth -= (int) attackDMG;
            isHurt = true;
            WASDPlayer.numAttacks++;
            if (attackDMG == Playing.getWASDPlayer().getAttack1DMG()) {
                LM1DamageCount++;
            }
            else if(attackDMG == Playing.getWASDPlayer().getAttack2DMG()) {
                LM2DamageCount++;
            }
        }
        if (currentHealth <= 0){
            currentHealth = 0;
            isAlive = false;
        }
    }

    public void resetDirectionBooleans(){
        left = false;
        right = false;
        up = false;
    }

    public void setAttacking1(boolean attacking){
        if(!attacking2 && !attacking3 && !blocking && !currentlyAttacking) {
            this.attacking1 = attacking;
            attackMode(attacking1, attack1DMG);
        }
        currentlyAttacking = true;
    }

    public void setAttacking2(boolean attacking2) {
        if(!attacking1 && !attacking3 && !blocking && !currentlyAttacking) {
            this.attacking2 = attacking2;
            attackMode(attacking2, attack2DMG);
        }
        currentlyAttacking = true;
    }

    public void setAttacking3(boolean attacking3) {
        if(!attacking1 && !attacking2 && !blocking && !currentlyAttacking ){
            this.attacking3 = attacking3;
            attackMode(attacking3, attack3DMG);
        }
        currentlyAttacking = true;
    }

    public void updateHealth(double DMG){
        currentHealth -= DMG;
        if (currentHealth <=0) currentHealth = 0;
    }

    public boolean isInAir(){
        return inAir;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }
    public boolean getBlocking() {return blocking;}


    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }


    public void setJump(boolean jump) {
        this.jump = jump;
    }
    public int getCurrentHealth(){
        return currentHealth;
    }

    public double getAttack1DMG() {
        return attack1DMG;
    }

    public double getAttack2DMG() {
        return attack2DMG;
    }

    public double getAttack3DMG() {
        return attack3DMG;
    }

}
