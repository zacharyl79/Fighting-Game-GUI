package com.game.smallgiant1010;

import Players.ArrowKeyPlayer;
import Players.WASDPlayer;
import audio.Audioplayer;
import gamestates.*;
import lightning_mage.Controller;
import java.awt.*;


import static gamestates.GameState.PLAYING;
import static gamestates.Playing.getWASDPlayer;

public class Game implements Runnable{
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    private final int fps_set = 60;
    private final int ups_set = 200;
    private ArrowKeyPlayer arrowKeyPlayer;
    private WASDPlayer wasdplayer;
    private Floor floor;
    private Playing playing;
    private MenuScreen menuScreen;
    private Controls controls;
    private Controller controller;
    private GameOver gameOver;
    public final static int tilesDefaultSize = 32;
    public final static float scale = 2.5f;
    public final static int tilesWidth = 24;
    public final static int tilesHeight = 12;
    public final static int tilesSize = (int)(tilesDefaultSize * scale);
    public final static int gameWidth = tilesWidth * tilesSize;
    public final static int gameHeight = tilesHeight * tilesSize;
    private Audioplayer audioPlayer;
    public Game(){
        initializeClasses();
        System.out.println(audioPlayer);
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(true);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    private void initializeClasses() {
        audioPlayer = new Audioplayer();
        menuScreen = new MenuScreen(this);
        controls = new Controls(this);
        playing = new Playing(this);
        gameOver = new GameOver(this);
    }
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update(){
        switch (GameState.state){
            case MENU:
                menuScreen.update();
                break;
            case CONTROL:
                controls.update();
                break;
            case PLAYING:
                playing.update();
                if(!Playing.getArrowKeyPlayer().isAlive || !Playing.getWASDPlayer().alive){
                    getAudioPlayer().stopSong();
                    getAudioPlayer().playEffects(Audioplayer.BEATDROP);
                    getAudioPlayer().playSongs(Audioplayer.TEST3);
                }
                break;
            case GAMEOVER:
                gameOver.update();

                break;
            default:
                break;
        }
    }

    public void render(Graphics g){
        switch (GameState.state){
            case MENU:
                menuScreen.draw(g);
                break;
            case CONTROL:
                controls.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case GAMEOVER:
                gameOver.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / fps_set;
        double timePerUpdate = 1000000000.0 / ups_set;
        long previousTime = System.nanoTime();
        int updates = 0;
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;
        while(true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime)/ timePerUpdate;
            deltaF += (currentTime - previousTime)/ timePerFrame;
            previousTime = currentTime;
            if (deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.state == PLAYING) {
            playing.getArrowKeyPlayer().resetDirectionBooleans();
            getWASDPlayer().resetDirectionBooleans();
        }
    }

    public GamePanel getGamePanel(){
        return gamePanel;
    }

    public MenuScreen getMenu(){
        return menuScreen;
    }

    public Playing getPlaying(){
        return playing;
    }
    public Controls getControls(){return controls;}

    public Audioplayer getAudioPlayer(){
        return audioPlayer;
    }

    public GameOver getGameOver() {
        return gameOver;
    }
}
