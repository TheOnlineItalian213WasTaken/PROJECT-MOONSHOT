package forts.game;

public abstract class Material {
    protected String name; // Nome del materiale
    
    protected double density; // Densità del materiale in kg/m^3
    protected double weightResistance; // Quanto peso può portare il materiale prima di cedere

    protected double compressionFactor; // Limite di quanto il materiale si possa fisicamente rimpicciolire
    protected double compressionThreshold; // Quanto il materiale può TENTARE di rimpicciolirsi prima di rompersi

    protected double tensionFactor; // Limite di quanto il materiale si possa fisicamente allungare
    protected double tensionThreshold; // Quanto il materiale può TENTARE di allungarsi prima di rompersi

    protected double fatigue; // Valore da 0-1000 per specificare sotto quanto stress è il materiale
    protected double[] fatigueBuffer; // Questo buffer viene riempito con ogni fonte di stress per il materiale, per poi calcolare la fatigue in un singolo colpo più tardi per evitare che la fatigue venga calcolata incorrettamente
    // N.B. Valori di "fatigue" sopra 1000 romperanno istantaneamente il materiale, andare sotto il "compressionThreshold" oppure sopra il "tensionThreshold" aumenterà rapidamente la fatigue in base a quanto si è sopra / sotto la soglia.
    // Alla "fatigue", oltre allo stress causato da dilatazione del materiale, verrà aggiunto il valore del peso che il materiale deve sopportare, seguendo una scala lineare determinata dalla "weightResistance"

    // Metodi set() e get()
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getWeightResistance() {
        return weightResistance;
    }

    public void setWeightResistance(double weightResistance) {
        this.weightResistance = weightResistance;
    }

    public double getCompressionFactor() {
        return compressionFactor;
    }

    public void setCompressionFactor(double compressionFactor) {
        this.compressionFactor = compressionFactor;
    }

    public double getCompressionThreshold() {
        return compressionThreshold;
    }

    public void setCompressionThreshold(double compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    public double getTensionFactor() {
        return tensionFactor;
    }

    public void setTensionFactor(double tensionFactor) {
        this.tensionFactor = tensionFactor;
    }

    public double getTensionThreshold() {
        return tensionThreshold;
    }

    public void setTensionThreshold(double tensionThreshold) {
        this.tensionThreshold = tensionThreshold;
    }

    public double getFatigue() {
        return fatigue;
    }

    public void setFatigue(double fatigue) {
        this.fatigue = fatigue;
    }

    public double[] getFatigueBuffer() {
        return fatigueBuffer;
    }

    public void setFatigueBuffer(double[] fatigueBuffer) {
        this.fatigueBuffer = fatigueBuffer;
    }

}
