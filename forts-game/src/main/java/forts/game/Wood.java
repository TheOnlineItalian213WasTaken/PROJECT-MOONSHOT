package forts.game;

public class Wood extends Material {
    String descripion;
    // Costruttore
    public Wood() {
        super("wood", 0.5, 0.5, 0.5, 0.5, 0.5, "woodSprite.png");
    }

    @Override
    public String getName() {
        return "Wood";
    }

    
    public String getDescription() {
        return "A basic building material.";
    }
    public void setDescription(String description) {
        this.descripion = description;
    }
}