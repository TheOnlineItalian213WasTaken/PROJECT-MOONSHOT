package forts.game;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;

public class Camera extends Application {
    private Vector2 position; // Variabile per tenere la posizione della telecamera
    private Vector2 rootWorldPosition; // La posizione del punto (0, 0) nel mondo, questo valore è basato sulla grandezza dello schermo
    private double zoom; // Variabile per tenere lo zoom della telecamera

    private Scene rootScene;
    private StackPane rootPane;
    
    private StackPane terrainPane;
    private StackPane decorationPane;
    private StackPane buildingsPane;
    private StackPane backgroundPane;

    // Metodi "costruttori" (poiché JavaFX crea dei thread per la gestione dell'interfaccia, non si può creare un vero e proprio oggetto all'interno del main: Bisogna spostare il main all'interno di questa classe)
    public void start(Stage primaryStage) {
        // Inizializzazione attributi
        this.position = new Vector2();
        this.rootWorldPosition = new Vector2();
        this.zoom = 0.5;

        // Creazione dei pane differenti
        rootPane = new StackPane();

        terrainPane = new StackPane();
        decorationPane = new StackPane();
        buildingsPane = new StackPane();
        backgroundPane = new StackPane();

        rootPane.getChildren().addAll(terrainPane, decorationPane, buildingsPane, backgroundPane);

        Vertex testVertex = new Vertex(new Vector2(200, 300));
        testVertex.draw(this);

        // Creazione della scene e messa in mostra della finestra
        rootScene = new Scene(rootPane);
        
        primaryStage.setScene(rootScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(new String());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        rootScene.setFill(Color.GRAY);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Metodi get() e set()
    public Vector2 getPosition(){
        return this.position;
    }

    public double getZoom(){
        return this.zoom;
    }

    public void setPosition(Vector2 newPosition){
        this.position = newPosition;
    }

    public void setZoom(double newZoom){
        this.zoom = newZoom;
    }

    public Vector2 getRootWorldPosition() {
        return rootWorldPosition;
    }

    public void setRootWorldPosition(Vector2 rootWorldPosition) {
        this.rootWorldPosition = rootWorldPosition;
    }

    public Scene getRootScene() {
        return rootScene;
    }

    public void setRootScene(Scene rootScene) {
        this.rootScene = rootScene;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

    public void setRootPane(StackPane rootPane) {
        this.rootPane = rootPane;
    }

    public StackPane getTerrainPane() {
        return terrainPane;
    }

    public void setTerrainPane(StackPane terrainPane) {
        this.terrainPane = terrainPane;
    }

    public StackPane getDecorationPane() {
        return decorationPane;
    }

    public void setDecorationPane(StackPane decorationPane) {
        this.decorationPane = decorationPane;
    }

    public StackPane getBuildingsPane() {
        return buildingsPane;
    }

    public void setBuildingsPane(StackPane buildingsPane) {
        this.buildingsPane = buildingsPane;
    }

    public StackPane getBackgroundPane() {
        return backgroundPane;
    }

    public void setBackgroundPane(StackPane backgroundPane) {
        this.backgroundPane = backgroundPane;
    }

    // Metodi classe
    public Vector2 calculateOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = (baseVector.multiply(this.zoom)).subtract(this.position).add(this.rootWorldPosition);

        return finishedVector;
    }

}