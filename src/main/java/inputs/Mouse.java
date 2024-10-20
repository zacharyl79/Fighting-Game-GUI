package inputs;
import com.game.smallgiant1010.GamePanel;
import gamestates.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public Mouse(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        switch (GameState.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case CONTROL:
                gamePanel.getGame().getControls().mouseClicked(e);
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case GAMEOVER:
                gamePanel.getGame().getGameOver().mouseClicked(e);
                break;
            default:
                break;
        }
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
}
