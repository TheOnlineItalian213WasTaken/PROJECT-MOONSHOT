package forts.game;

// Classe che gestisce ogni singolo vertice su cui verranno applicate le forze all'interno del gioco

public class Vertex {
    String spriteDirectory = "buildVertexIcon.png"; // Directory della sprite per 

    Connection[] connections; // Tutte le connessioni che originano con / terminano in questo vertica
    Vector2[] actingForces; // Tutte le forze che agiscono su questo vertice in un determinato momento (forze globali (es. gravità) sono escluse)

    Vector2 position; // Posizione nel mondo del vertice
    Vector2 acceleration; // Accelerazione del vertice
    Vector2 velocity; // Velocità corrente del vertice

    boolean anchored; // Determina se il vertice è ancorato nel mondo
    // N.B. Un vertice ancorato, non soltanto è completamente escluso da qualsiasi manipolazione fisica dovuta alle forze che agiscono su di esso, ma esso verrà anche contato come punto di dispersione e svuotamento di tutte le forze che potrebbero agire su altri vertici.
    // In parole povere, un vertice ancorato non si muove tranne se si modifica la posizione direttamente.

    // Metodi costruttori
    Vertex() { // Costruttore base per creare un vertice in (0, 0)
        this.connections = new Connection[20]; // Massimo di 20 connessioni
        this.actingForces = new Vector2[20]; // Massimo di 20 forze che possono agire su un vertice  

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = new Vector2();
    }

    Vertex(Vector2 position) { // Costruttore per creare un vertice con posizione data dall'utente
        this.connections = new Connection[20]; // Massimo di 20 connessioni
        this.actingForces = new Vector2[20]; // Massimo di 20 forze che possono agire su un vertice  

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = position;
    }

    Vertex(Vector2 position, Connection[] connections) { // Costruttore per creare un vertice con posizione data dall'utente ed una serie di connessioni già esistenti
        int i, length;

        this.connections = connections;
        this.actingForces = new Vector2[20]; // Massimo di 20 forze che possono agire su un vertice  

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = position;

        length = connections.length;
        for(i = 0; i < length; i++) {
            // TODO: Aggiungere ad ogni connessione il vertice appena creato
        }
    }

    // Metodi set() e get()
    public String getSpriteDirectory() {
        return spriteDirectory;
    }

    public void setSpriteDirectory(String spriteDirectory) {
        this.spriteDirectory = spriteDirectory;
    }

    public Connection[] getConnections() {
        return connections;
    }

    public void setConnections(Connection[] connections) {
        this.connections = connections;
    }

    public Vector2[] getActingForces() {
        return actingForces;
    }

    public void setActingForces(Vector2[] actingForces) {
        this.actingForces = actingForces;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    // Metodi classe
}
