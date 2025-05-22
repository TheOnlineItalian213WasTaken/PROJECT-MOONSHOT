package forts.game;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[]=new URL[5];

    public Sound() {
        soundURL[0] = getClass().getResource("/audio/IronSound.wav");
        soundURL[1] = getClass().getResource("/audio/DesertBackgroundMusic.wav");
        soundURL[2] = getClass().getResource("/audio/GreenBackgroundMusic.wav");
        soundURL[3] = getClass().getResource("/audio/WoodSound.wav");
        soundURL[4] = getClass().getResource("/audio/BrokenPlankSound.wav");
    }

    public void playSound(int sound) {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL[sound]);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void loopSound() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}
