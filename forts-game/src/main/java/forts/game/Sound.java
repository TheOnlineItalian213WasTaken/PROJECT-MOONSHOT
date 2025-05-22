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

            // Abbassa il volume (es: -15.0f dB, puoi regolare il valore)
            if (clip.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN)) {
                javax.sound.sampled.FloatControl gainControl =
                    (javax.sound.sampled.FloatControl) clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-25.0f); // Più negativo = più basso il volume
            }

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
