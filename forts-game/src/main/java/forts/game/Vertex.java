package forts.game;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.transform.*;

// Classe che gestisce ogni singolo vertice su cui verranno applicate le forze all'interno del gioco

public class Vertex implements Drawable {
    private String spriteDirectory = "buildVertexIcon.png"; // Directory della sprite per 

    private ImageView sprite;

    private ArrayList connections; // Tutte le connessioni che originano con / terminano in questo vertica
    private ArrayList startingForces; // Forze che vengono disperse, mentre le actingForces non verranno disperse per la struttura
    private ArrayList actingForces; // Tutte le forze che agiscono su questo vertice in un determinato momento (forze globali (es. gravità) sono escluse)

    private Vector2 finalForce;
    private Vector2 dispersedForce;
    private Vector2 position; // Posizione nel mondo del vertice
    private Vector2 acceleration; // Accelerazione del vertice
    private Vector2 velocity; // Velocità corrente del vertice

    private boolean anchored; // Determina se il vertice è ancorato nel mondo
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

    public boolean branchDisperse(Vector2 force) { // Funzione ricorsiva per la dispersione delle forze
        int i, j, length, dispersionDiv;
        Vector2 finalForce = force.clone();

        dispersionDiv = 0;
        length = this.connections.size();
        for(i = 0; i < length; i++) { // Primo ciclo itera ogni connessione per determinare su quanti vertici può essere dispersa la forza
            Connection currConnection = (Connection) this.connections.get(i);
            Vertex otherVertex = currConnection.findOtherVertex(this);

            if(otherVertex == null) {
                continue;
            }

            double dotProduct = ((Math.abs(this.position.inverse().dotProduct(otherVertex.getPosition())) - 0.5) * 2); // Ritorna il dotProduct tra i due vettori per calcolare la dispersione 
            if(dotProduct < 0) {
                continue;
            }

            dispersionDiv += 1;
        }

        for(i = 0; i < length; i++) { // Secondo ciclo itera ogni connessione per disperdere le forze e continuare la funzione ricorsiva
            Connection currConnection = (Connection) this.connections.get(i);
            Vertex otherVertex = currConnection.findOtherVertex(this);

            if(otherVertex == null) {
                continue;
            }

            double dotProduct = ((Math.abs(this.position.inverse().dotProduct(otherVertex.getPosition())) - 0.5) * 2); // Ritorna il dotProduct tra i due vettori per calcolare la dispersione 
            if(dotProduct < 0) {
                continue;
            }

            Vector2 finalDispersedForce = force.divide(dispersionDiv).multiply(dotProduct);
            finalForce.subtract(finalDispersedForce);

            otherVertex.branchDisperse(force);
        }

        this.addActingForce(finalForce);

        return true;
    }

    public void disperseForces() { // Calcola la dispersione tra tutte le forze
        int i;
        Vector2 sumForces = new Vector2();

        for(i = 0; i < startingForces.size(); i++) {
            sumForces.add((Vector2)(startingForces.get(i)));
        }

        branchDisperse(sumForces);
    }

    public void draw(Camera camera) {
        this.sprite = new ImageView(this.spriteDirectory); // Creazione iniziale dell'elemento grafico per il vertice

        camera.getBuildingsVertexPane().getChildren().add(sprite);
        VertexCreationHandler handler = camera.getVertexCreationHandler();
        if (handler != null) {
            this.sprite.setOnMouseClicked(e -> {
                e.consume();
                handler.onVertexClicked(this);
            });
        }
        this.update(camera);
    }

    public void update(Camera camera) {
        Vector2 relativeVector, sizeOffset;

        this.sprite.getTransforms().clear(); // Rimuove tutti i transform, così che il programma non inizia a crashare

        // Riposizionamento del gui dinamico in base alla posizione root del mondo e posizizone della telecamera
        sizeOffset = new Vector2(125 * camera.getZoom(), 125 * camera.getZoom());
        relativeVector = camera.calculateOffset(this.position).subtract(sizeOffset);

        Platform.runLater(() -> {
            this.sprite.setFitWidth(250 * camera.getZoom());
            this.sprite.setFitHeight(250 * camera.getZoom());

            this.sprite.setLayoutX(relativeVector.getX());
            this.sprite.setLayoutY(relativeVector.getY());
        });
    }
}
