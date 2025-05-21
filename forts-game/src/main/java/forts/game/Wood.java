package forts.game;

import java.io.Serializable;

public class Wood extends Material implements Serializable{
    
    // Costruttore
    public Wood() {
        super("wood", 0.5, 0.5, 0.5, 0.5, 0.5, "woodSprite.png");
    }


}