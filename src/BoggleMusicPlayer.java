package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 9, 2022
 * Description: A program that can play a single audio file in the background
 */

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class BoggleMusicPlayer {
    // The following is the extension that all music files this program uses will have
    static final String MUSIC_EXTENSION = ".wav";
    // The following boolean controls whether or not the track that is playing is paused
    // It has the volatile modifier because manageClipPlayback must continuously get the 
    // newest value of clipPaused
    volatile boolean clipPaused;
    
    public BoggleMusicPlayer(String trackName) {
        // Create a new thread for playing the audio file so that this is done in the 
        // background while any other programs continue to run
        new Thread() {
            @Override
            public void run() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new File("ICS4UBoggle/audio/" + trackName + MUSIC_EXTENSION).getAbsoluteFile());
                    clipPaused = false;

                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    manageClipPlayback(clip);
                } 
                catch (UnsupportedAudioFileException e) {e.printStackTrace();} 
                catch (IOException e) {e.printStackTrace();} 
                catch (LineUnavailableException e) {e.printStackTrace();}
            }
        }.start();
    }

    /**
     * This method controls the playback of the audio by pausing or playing it as appropriate
     * 
     * @param clip The clip that is to be played
     */
    public void manageClipPlayback(Clip clip) {
        long currPosition = -1;
        while (true) {
            if (clipPaused) {
                if (currPosition == -1) {
                    currPosition = clip.getMicrosecondPosition();
                    clip.stop();
                }
            } else {
                if (currPosition != -1) {
                    clip.setMicrosecondPosition(currPosition);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    currPosition = -1;
                }
            }
        }
    }

    /**
     * This method pauses the clip
     */
    public void pauseClip() {
        clipPaused = true;
    }

    /**
     * This method unpauses the clip
     */
    public void unpauseClip() {
        clipPaused = false;
    }

    /**
     * This method checks if the clip is paused
     * 
     * @return Whether or not the clip is paused
     */
    public boolean checkIfPaused() {
        return clipPaused;
    }
}
