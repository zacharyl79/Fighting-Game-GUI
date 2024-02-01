package gamestates;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import audio.Audioplayer;
import Players.*;
import main.GameWindow;
import main.LoadSave;

import static Players.ArrowKeyPlayer.*;
import static Players.WASDPlayer.*;

public class GameOver extends State implements MouseListener, MouseMotionListener, StateMethods {
    private double LMTotalDamage, STotalDamage;
    private int[] LMDamage = {(int) Playing.getWASDPlayer().getAttack1DMG(), (int) Playing.getWASDPlayer().getAttack2DMG(), (int) Playing.getWASDPlayer().getLightBallDMG()};
    private int[] SDamage = {(int)Playing.getArrowKeyPlayer().getAttack1DMG(), (int)Playing.getArrowKeyPlayer().getAttack2DMG(), (int)Playing.getArrowKeyPlayer().getAttack3DMG()};
    private int averageDamageLM, averageDamageS, mostDamagingAttack;


    public GameOver(Game game){
        super(game);
    }



    private String SC(double var){
        return String.valueOf(var);
    }
    private void setTotalDamages(boolean life){
        if(!life){
            LMTotalDamage = LMDamage[0] * LM1DamageCount + LMDamage[1] * LM2DamageCount + LMDamage[2] * LM3DamageCount;
            STotalDamage = SDamage[0] * S1DamageCount + SDamage[1] * S2DamageCount + SDamage[2] * S3DamageCount;
        }
    }

    private void averageDamage(){
        averageDamageLM = (int) (LMTotalDamage / WASDPlayer.numAttacks);
        averageDamageS = (int) (STotalDamage / ArrowKeyPlayer.numAttacks);
    }

    private void mode(){
        int currentHighest = 0;
        int newHighest = 0;
        for(int i = 0;i < LMDamage.length;i ++){
            for(int j : LMDamage){
                if(LMDamage[i] == j)
                    currentHighest++;
            }
            if(currentHighest > newHighest) {
                newHighest = currentHighest;
                mostDamagingAttack = LMDamage[i];
            }
            else
                currentHighest = 0;
        }
    }


    @Override
    public void update() {
        if(!alive || !isAlive) {
            setTotalDamages(alive);
            setTotalDamages(isAlive);
            averageDamage();
            mode();

        }
    }

    public void draw(Graphics g){
        if(!alive || !isAlive) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Game.gameWidth, Game.gameHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            g.drawString("Samurai Damage: " + SC(STotalDamage), 100, 100);
            g.drawString("Lightning Mage Damage: " + SC(LMTotalDamage), 100, 150);
            g.drawString("Samurai Average Damage: " + SC(averageDamageS), 100, 200);
            g.drawString("Lightning Average Damage: " + SC(averageDamageLM), 100, 250);
            g.drawString("Most Frequent Attack Damage: " + SC(mostDamagingAttack), 100, 300);
        }
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
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
