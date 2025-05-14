package forts.game;

import javafx.application.*;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;

public class Camera extends Application {
    private Vector2 position; // Variabile per tenere la posizione della telecamera
    private Vector2 rootWorldPosition; // La posizione del punto (0, 0) nel mondo, questo valore è basato sulla grandezza dello schermo
    private double zoom; // Variabile per tenere lo zoom della telecamera

    // Movimento telecamera
    private Vector2 cameraVelocity; // Vettore che verrà aggiunto alla posizione della camera ogni tick

    // Ascoltatori / event handler
    private KeyInputHandler keyInputHandler;

    private Scene rootScene;
    private StackPane rootPane;
    
    private Pane terrainPane;
    private Pane decorationPane;
    private Pane buildingsPane;
    private Pane backgroundPane;

    // Metodi "costruttori" (poiché JavaFX crea dei thread per la gestione dell'interfaccia, non si può creare un vero e proprio oggetto all'interno del main: Bisogna spostare il main all'interno di questa classe)
    public void start(Stage primaryStage) {
        // Inizializzazione attributi
        this.position = new Vector2();
        this.rootWorldPosition = new Vector2();
        this.zoom = 0.1;
        this.cameraVelocity = new Vector2();
        this.keyInputHandler = new KeyInputHandler(this);

        // Creazione dei pane differenti
        rootPane = new StackPane();

        terrainPane = new Pane();
        decorationPane = new Pane();
        buildingsPane = new Pane();
        backgroundPane = new Pane();

        rootPane.getChildren().addAll(terrainPane, decorationPane, buildingsPane, backgroundPane);

        Vertex testVertex = new Vertex(new Vector2(200, 300));
        testVertex.draw(this);

        // Creazione della scene e messa in mostra della finestra
        rootScene = new Scene(rootPane);

        rootScene.setOnKeyPressed(keyInputHandler);
        rootScene.setOnKeyReleased(keyInputHandler);
        
        primaryStage.setScene(rootScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(new String());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        rootScene.setFill(Color.GRAY);

        primaryStage.show();

        // Gestione di thread secondari
        PositionUpdateThread updateLoop = new PositionUpdateThread(this);

        updateLoop.setDaemon(true);
        updateLoop.start();
        
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

    public Pane getTerrainPane() {
        return terrainPane;
    }

    public void setTerrainPane(Pane terrainPane) {
        this.terrainPane = terrainPane;
    }

    public Pane getDecorationPane() {
        return decorationPane;
    }

    public void setDecorationPane(Pane decorationPane) {
        this.decorationPane = decorationPane;
    }

    public Pane getBuildingsPane() {
        return buildingsPane;
    }

    public void setBuildingsPane(Pane buildingsPane) {
        this.buildingsPane = buildingsPane;
    }

    public Pane getBackgroundPane() {
        return backgroundPane;
    }

    public void setBackgroundPane(Pane backgroundPane) {
        this.backgroundPane = backgroundPane;
    }

    public Vector2 getCameraVelocity() {
        return cameraVelocity;
    }

    public void setCameraVelocity(Vector2 cameraVelocity) {
        this.cameraVelocity = cameraVelocity;
    }

    public KeyInputHandler getKeyInputHandler() {
        return keyInputHandler;
    }

    public void setKeyInputHandler(KeyInputHandler keyInputHandler) {
        this.keyInputHandler = keyInputHandler;
    }

    // Metodi classe
    public Vector2 calculateOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = baseVector.subtract(this.position).multiply(this.zoom).add(this.rootWorldPosition);

        return finishedVector;
    }

}