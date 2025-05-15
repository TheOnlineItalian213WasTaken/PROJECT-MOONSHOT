package forts.game;

public class Iron extends Material {
    String description;
    // Costruttore
    public Iron() {
        super("iron", 0.5, 0.5, 0.5, 0.5, 0.5);
    }
    // metodi get e set
    @Override
    public String getName() {
        return "Iron";
    }

    public String getDescription() {
        return "A basic building material.";
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
