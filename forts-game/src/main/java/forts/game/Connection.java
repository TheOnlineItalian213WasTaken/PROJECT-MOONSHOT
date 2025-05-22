package forts.game;

import java.lang.Math;

import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;
import java.io.Serializable;
import javafx.scene.*;
import javafx.scene.effect.ColorAdjust;

// Classe che gestisce le connessioni tra vertici di costruzione

public class Connection implements Drawable, Serializable {
    private Vertex[] vertices; // I due vertici della connessione sono tenuti qui dentro

    private transient ImageView sprite; // <-- AGGIUNTO transient
    private transient ColorAdjust fatigueColor;

    private Material material;
    private double weight;
    private double holdingWeight;

    private double baseLength;
    private double currLength;

    // Metodo costruttore 
    Connection(Vertex firstVertex, Vertex secondVertex, Material material){ 
        this.material = material;
        this.vertices = new Vertex[2];

        this.vertices[0] = firstVertex;
        this.vertices[1] = secondVertex;
        
        // Calcolo peso
        this.baseLength = (firstVertex.getPosition().subtract(secondVertex.getPosition())).getMagnitude();
        this.currLength = baseLength;
        this.weight = (this.baseLength / 4) * material.getDensity();
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

    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHoldingWeight() {
        return holdingWeight;
    }

    public void setHoldingWeight(double holdingWeight) {
        this.holdingWeight = holdingWeight;
    }

    public double getBaseLength() {
        return baseLength;
    }

    public void setBaseLength(double baseLength) {
        this.baseLength = baseLength;
    }

    public double getCurrLength() {
        return currLength;
    }

    public void setCurrLength(double currLength) {
        this.currLength = currLength;
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

        this.fatigueColor = new ColorAdjust(0, 0, 0, 0);

        this.sprite.setEffect(fatigueColor);

        camera.getBuildingsPane().getChildren().add(sprite);

        this.update(camera);
        this.sprite.setVisible(true);
    }

    public void update(Camera camera) {
        double relativeScale, theta;
        Vector2 absolutePosition, relativePosition, sizeOffset;
        Rotate rotate;

        // Modifica della grandezza in base allo zoom della telecamera
        this.currLength = (this.vertices[0].getPosition().subtract(this.vertices[1].getPosition())).getMagnitude();

        relativeScale = (this.currLength / 500) * camera.getZoom();

        // Colorazione in base alla fatigue
        double fatigueFactor = (this.material.getFatigue() / 1000);
        this.fatigueColor.setSaturation(fatigueFactor);

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