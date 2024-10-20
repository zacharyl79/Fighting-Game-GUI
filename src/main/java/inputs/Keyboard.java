package inputs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.smallgiant1010.GamePanel;
import gamestates.GameState;


public class Keyboard implements KeyListener {
    private GamePanel gamePanel;
    public Keyboard(GamePanel gamepanel){
        this.gamePanel = gamepanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
