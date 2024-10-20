/**
 * Plays audio
 * @Raymond Weng
 * @version 69.0
 */
package audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class Audioplayer {
    /**
     * Constructor that runs the LoadSongs() and loadEffects() method
     * sets the method playSongs to playSongs(TEST1)
     */

    public Audioplayer(){
        loadSongs();
        loadEffects();
        playSongs(TEST1);
    }

    //variables for the index of name array
    public static int TEST1 = 0;
    public static int TEST2 = 1;
    public static int TEST3 = 2;
    //variables for the index of effectName array
    public static int ATTACK1 =0;
    public static int ATTACK2 =1;
    public static int ATTACK3 =2;
    public static int HURT =3;
    public static int LMATTACK1 =4;
    public static int LMATTACK2 =5;
    public static int LMHURT =6;
    public static int LMDIE = 7;
    public static int N=8;
    public static int BLOCK = 9;
    public static int WALKING = 10;
    public static int JUMP =11;
    public static int LMWALKING = 12;
    public static int CHARGE = 13;
    public static int BEATDROP = 14;

    private Clip[] songs ,effects;
    private int currentSongId;
    private double volume =1f;
    private boolean songMute,effectMute;

    //uploads the songs to the Clip method
    private void loadSongs(){
        String[] names = {"test1", "HalfedIndianaJonesMusic","end"};
        songs = new Clip[names.length];
        for(int i = 0; i<songs.length;i++)
            songs[i] = getClip(names[i]);
    }
    //upload the effects to the Clip method
    private void loadEffects(){
        String[] effectNames = {"attack1", "attack2","attack3","Hurt","lmattack1_2","lmattack2","LM hurt","LM die","n","block","walk","jump","lmwalk1","Charge","bingchilling"};
        effects = new Clip[effectNames.length];
        for(int i = 0; i<effects.length;i++)
            effects[i] = getClip(effectNames[i]);
    }
    /**Method that Upload Audio files
     * para name is a string
     * para name is the name of the file
     */
    private Clip getClip(String name) {
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        }catch(UnsupportedAudioFileException | IOException |LineUnavailableException e){
            e.printStackTrace();
        }
        return null;
    }

    /**Method plays the song
     * @param song is integer
     * precondition: song is one of the static variables
     */

    public void playSongs(int song){
        if(songs[currentSongId].isActive())songs[currentSongId].stop();
        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * method stops the current song
     */
    public void stopSong(){
        if(songs[currentSongId].isActive()){
            songs[currentSongId].stop();
        }
    }
    /**
     * Method changes the volume
     * @param volume is a float
     * precondition: 0f=<volume<=1f
     * Postcondition: updatesEffectsVolume is run
     */
    public void setVolume(float volume){
        this.volume = volume;
        updateEffectsVolume();
    }

    /**
     * method plays the effects
     * @param effect is integer
     * precondition: effects is one of the static variables
     */
    public void playEffects(int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    /**
     * Method plays the effects but infinitely
     * @param effect is integer
     * precondition: effects is one of the static variables
     */
    public void playEffectsInfinite(int effect){
        if(effects[effect].isActive())effects[effect].stop();
        effects[effect].setMicrosecondPosition(0);
        effects[effect].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Method if the effect is playing it stops
     * @param effect is integer
     */
    public void stopEffects(int effect){
        if(effects[effect].isActive()){
            effects[effect].stop();
        }
    }

    /**Method updates the song volume
     */
    private  void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (float) ((range*volume )+gainControl.getMinimum());
        gainControl.setValue(gain);
    }

    /**
     * Method updates the effect volume
     *
     */
    private void updateEffectsVolume(){
        for(Clip c: effects) {
            FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (float) ((range * volume) + gainControl.getMinimum());
            gainControl.setValue(gain);
        }
    }

    public String toString() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            count++;
            if (count == 6) {
                return "THIS IS NOT POSSIBLE";
            }
        }
        return currentSongId + "" + count;
    }
}

