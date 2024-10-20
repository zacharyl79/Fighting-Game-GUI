package gamestates;
import com.game.smallgiant1010.Game;
import com.game.smallgiant1010.LoadSave;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MenuScreen extends State implements StateMethods{
    private BufferedImage menuScreen = LoadSave.GetSpriteSheet("MenuState.png");
    private BufferedImage title = LoadSave.GetSpriteSheet("title.png");
    private BufferedImage playButton = LoadSave.GetSpriteSheet("play button.png");
    public MenuScreen(Game game) {
        super(game);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menuScreen, 0, 0, Game.gameWidth, Game.gameHeight, null);
        g.drawImage(title, Game.gameWidth/2- 320, 30, 650, 420,null);
        g.drawImage(playButton, Game.gameWidth/ 2 - 150, Game.gameHeight - 200, 300, 100, null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() >= Game.gameWidth/ 2 - 150 && e.getX() <= Game.gameWidth/ 2 +150){
            if (e.getY() >= Game.gameHeight-200 && e.getY() <= Game.gameHeight-100){
                if(e.getButton() == MouseEvent.BUTTON1) {
                    GameState.state = GameState.CONTROL;
                    game.getAudioPlayer().stopSong();
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
