package com.game.smallgiant1010;
import gamestates.Floor;
import inputs.*;

import javax.swing.*;
import java.awt.*;
import static com.game.smallgiant1010.Game.gameHeight;
import static com.game.smallgiant1010.Game.gameWidth;


// Background Arena:
// https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DED6SPbAdAZI&psig=AOvVaw0n30XK6aYEB301f9iJAoVA&ust=1703702543113000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCLCA6ePgrYMDFQAAAAAdAAAAABAD

public class GamePanel extends JPanel{

    private Mouse mouseListener;
    private Game game;
    private Floor floor;


    public GamePanel(Game game) {
        mouseListener = new Mouse(this);
        this.game = game;
//        floor = new Floor(this,0,880,1920,80);
        setPanelSize();
        addKeyListener(new Keyboard(this));
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

    }



    private void setPanelSize() {
        Dimension size = new Dimension(gameWidth,gameHeight);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        System.out.println("size: " + gameWidth + " x " + gameHeight );
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public void updateGame() {

    }

    public Game getGame(){
        return game;
    }

}
