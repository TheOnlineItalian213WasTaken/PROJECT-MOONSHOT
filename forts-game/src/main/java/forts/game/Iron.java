package forts.game;

import java.io.Serializable;

public class Iron extends Material implements Serializable {
    // Costruttore
    public Iron() {
        super("iron", 3, 10000, 0.5, 0.5, 0.5,"ironSprite.png");
    }
    
    
}
