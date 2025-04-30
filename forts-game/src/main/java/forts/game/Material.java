package forts.game;

public abstract class Material {
    String name; // Nome del materiale
    
    double density; // Densità del materiale in kg/m^3
    double weightResistance; // Quanto peso può portare il materiale prima di cedere
    double compressionFactor;
    double compressionThreshold;
}
