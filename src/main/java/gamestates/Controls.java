package gamestates;
import Players.ArrowKeyPlayer;
import Players.WASDPlayer;
import com.game.smallgiant1010.Game;
import com.game.smallgiant1010.LoadSave;
import audio.Audioplayer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Controls extends State implements StateMethods {
    private BufferedImage controlScreen = LoadSave.GetSpriteSheet("controls.png");
    private ArrowKeyPlayer arrowKeyPlayer;
    private WASDPlayer wasdPlayer;
    private BufferedImage nextButton = LoadSave.GetSpriteSheet("nextButton.png");
    public Controls(Game game) {
        super(game);
        initializeClasses();
    }

    private void initializeClasses() {
        arrowKeyPlayer = new ArrowKeyPlayer(1400, 290, 810, 624);
        wasdPlayer = new WASDPlayer(500, 390, 810, 624);

    }

    @Override
    public void update() {
        arrowKeyPlayer.update();
        wasdPlayer.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(controlScreen, 0, 0, Game.gameWidth, Game.gameHeight, null);
        g.drawImage(nextButton, Game.gameWidth - 250, Game.gameHeight - 250, 250, 250, null);
        arrowKeyPlayer.render(g);
        wasdPlayer.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= Game.gameWidth-250 && e.getX() <= Game.gameWidth){
            if (e.getY() >= Game.gameHeight-250 && e.getY() <= Game.gameHeight){
                if(e.getButton() == MouseEvent.BUTTON1) {
                    GameState.state = GameState.PLAYING;
                    game.getAudioPlayer().playSongs(Audioplayer.TEST2);
                }
            }
        }
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

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

}
