package forts.game;

import java.util.ArrayList;

// Classe che gestisce ogni singolo vertice su cui verranno applicate le forze all'interno del gioco

public class Vertex {
    String spriteDirectory = "buildVertexIcon.png"; // Directory della sprite per 

    ArrayList connections; // Tutte le connessioni che originano con / terminano in questo vertica
    ArrayList startingForces; // Forze che vengono disperse, mentre le actingForces non verranno disperse per la struttura
    ArrayList actingForces; // Tutte le forze che agiscono su questo vertice in un determinato momento (forze globali (es. gravità) sono escluse)

    Vector2 position; // Posizione nel mondo del vertice
    Vector2 acceleration; // Accelerazione del vertice
    Vector2 velocity; // Velocità corrente del vertice

    boolean anchored; // Determina se il vertice è ancorato nel mondo
    // N.B. Un vertice ancorato, non soltanto è completamente escluso da qualsiasi manipolazione fisica dovuta alle forze che agiscono su di esso, ma esso verrà anche contato come punto di dispersione e svuotamento di tutte le forze che potrebbero agire su altri vertici.
    // In parole povere, un vertice ancorato non si muove tranne se si modifica la posizione direttamente.

    // Metodi costruttori
    Vertex() { // Costruttore base per creare un vertice in (0, 0)
        this.connections = new ArrayList();
        this.actingForces = new ArrayList();

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = new Vector2();
    }

    Vertex(Vector2 position) { // Costruttore per creare un vertice con posizione data dall'utente
        this.connections = new ArrayList();
        this.actingForces = new ArrayList();

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = position;
    }

    Vertex(Vector2 position, ArrayList connections) { // Costruttore per creare un vertice con posizione data dall'utente ed una serie di connessioni già esistenti
        int i, length;

        this.connections = connections;
        this.actingForces = new ArrayList();

        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.position = position;

        length = connections.size();
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

    public ArrayList getConnections() {
        return connections;
    }

    public void setConnections(ArrayList connections) {
        this.connections = connections;
    }

    public ArrayList getActingForces() {
        return actingForces;
    }

    public void setActingForces(ArrayList actingForces) {
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
    public void addActingForce(Vector2 actingForce) {
        this.addActingForce(actingForce);
    }

    public boolean branchDisperse(ArrayList forces) { // Funzione ricorsiva per la dispersione delle forze
        int i, length;

        length = this.connections.size();
        for(i = 0; i < length; i++) {
            Connection currConnection = (Connection) this.connections.get(i);
            Vertex otherVertex = currConnection.findOtherVertex(this);
        }

        return true;
    }

    public void disperseForces() { // Calcola la dispersione tra tutte le forze
        int i, length;

        length = this.startingForces.size();
        for(i = 0; i < length; i++) { 
            this.addActingForce((Vector2)this.startingForces.get(i)); // Aggiunge tutte le forze pre-caricate all' ArrayList delle forze agenti
        }


    }

}
