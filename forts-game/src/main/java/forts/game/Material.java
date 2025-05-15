package forts.game;

public abstract class Material {
    protected String name; // Nome del materiale
    protected String spriteDirectory; // Directory della sprite per il materiale (LE SPRITE SONO 200x800)
    
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

    // Metodo costruttore
    Material() {
        fatigueBuffer = new double[10]; // Limite massimo di 10 fonti di stress
    }
    Material(String name, double density, double weightResistance, double compressionFactor, double compressionThreshold, double tensionFactor) {
        this.name = name;
        this.density = density;
        this.weightResistance = weightResistance;
        this.compressionFactor = compressionFactor;
        this.compressionThreshold = compressionThreshold;
        this.tensionFactor = tensionFactor;

        this.fatigueBuffer = new double[10]; // Limite massimo di 10 fonti di stress
    }

    // Metodi set() e get()
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpriteDirectory() {
        return spriteDirectory;
    }

    public void setSpriteDirectory(String spriteDirectory) {
        this.spriteDirectory = spriteDirectory;
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

    // Metodi classe
    private void clearFatigueBuffer() { // Metodo privato per ripulire il buffer dello stress una volta calcolato correttamente il valore di "fatigue"
        int i, length;

        length = this.fatigueBuffer.length;
        for(i = 0; i < length; i++) {
            fatigueBuffer[i] = 0;
        }
    }

    public void addFatigue(double fatigue) { // Metodo utilizzato dalle altre classi per aggiungere fonti di stress a "fatigueBuffer"
        int i, length;

        length = this.fatigueBuffer.length;
        for(i = 0; i < length; i++) {
            if(!(fatigueBuffer[i] == 0)) {
                continue;
            }

            fatigueBuffer[i] = fatigue;

            break;
        }
    }

    public void computeFatigue() { // Calcolo dello stress totale in base ai valori messi dentro "fatigueBuffer"
        int i, length;
        double finalFatigue;

        finalFatigue = 0; // Inizializza la somma di tutto a 0
        length = this.fatigueBuffer.length;
        for(i = 0; i < length; i++) {
            if(fatigueBuffer[i] == 0) {
                continue;
            }

            finalFatigue += fatigueBuffer[i];
        }

        clearFatigueBuffer(); // Svuota il buffer una volta calcolato lo stress totale

        this.fatigue = finalFatigue;
    }

}
