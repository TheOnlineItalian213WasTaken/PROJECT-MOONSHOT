package forts.game;

import java.lang.Math;

import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.transform.*;

// Classe che gestisce le connessioni tra vertici di costruzione

public class Connection implements Drawable {
    private Vertex[] vertices; // I due vertici della connessione sono tenuti qui dentro

    private ImageView sprite;

    private Material material;
    private double weight;

    private double baseLength;

    // Metodo costruttore 
    Connection(Vertex firstVertex, Vertex secondVertex, Material material){ 
        this.material = material;
        this.vertices = new Vertex[2];

        this.vertices[0] = firstVertex;
        this.vertices[1] = secondVertex;
        
        // Calcolo peso
        this.baseLength = (firstVertex.getPosition().subtract(secondVertex.getPosition())).getMagnitude();
        this.weight = this.baseLength * material.getDensity();
    }

    // Metodi set e get
    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    // Metodi classe
    public Vertex findOtherVertex(Vertex baseVertex) { // Metodo d'utilità per trovare l'altro met
        Vertex otherVertex = null;
        int i, length;

        length = this.vertices.length;
        for(i = 0; i < length; i++) {
            if((this.vertices[i] == baseVertex)) { // TODO: Controlla se questo da errori, è probabile che il Java non funzioni così.
                continue;
            }

            otherVertex = this.vertices[i];
            
            break;
        }

        return otherVertex;
    }

    public void draw(Camera camera) {
        this.sprite = new ImageView(this.material.getSpriteDirectory()); // Creazione iniziale dell'elemento grafico per il vertice
        this.sprite.setVisible(false);
        this.sprite.setPreserveRatio(false);

        camera.getBuildingsPane().getChildren().add(sprite);

        this.update(camera);
        this.sprite.setVisible(true);
    }

    public void update(Camera camera) {
        double relativeScale, theta;
        Vector2 absolutePosition, relativePosition, sizeOffset;
        Rotate rotate;

        // Modifica della grandezza in base allo zoom della telecamera
        relativeScale = (this.baseLength / 500) * camera.getZoom();

        // Riposizionamento del gui dinamico in base alla posizione root del mondo e posizizone della telecamera
        sizeOffset = new Vector2((100 * camera.getZoom()), (250 * camera.getZoom() * (relativeScale / camera.getZoom())));
        absolutePosition = (this.vertices[0].getPosition().add(this.vertices[1].getPosition())).divide(2);
        relativePosition = camera.calculateOffset(absolutePosition).subtract(sizeOffset);

        //Rotazione in base all'angolo tra i due vertici
        theta = (this.vertices[0].getPosition().subtract(this.vertices[1].getPosition()).theta(Vector2.yAxis));
        rotate = new Rotate(theta, sizeOffset.getX(), sizeOffset.getY());

        Platform.runLater(() -> {
            this.sprite.getTransforms().clear(); // Rimuove tutti i transform, così che il programma non inizia a crashare

            this.sprite.setFitWidth(200 * camera.getZoom());
            this.sprite.setFitHeight(500 * relativeScale);

            this.sprite.setLayoutX(relativePosition.getX());
            this.sprite.setLayoutY(relativePosition.getY());

            this.sprite.getTransforms().add(rotate);
        });
    }
    
}