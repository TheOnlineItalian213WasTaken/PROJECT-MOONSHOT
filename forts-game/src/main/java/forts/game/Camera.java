package forts.game;

import java.rmi.ConnectIOException;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Camera extends Application {
    private Vector2 position; // Variabile per tenere la posizione della telecamera
    private Vector2 rootWorldPosition; // La posizione del punto (0, 0) nel mondo, questo valore è basato sulla grandezza dello schermo
    private double zoom; // Variabile per tenere lo zoom della telecamera

    // Movimento telecamera
    private Vector2 cameraVelocity; // Vettore che verrà aggiunto alla posizione della camera ogni tick

    // Ascoltatori / event handler
    private KeyInputHandler keyInputHandler;
    private VertexCreationHandler vertexCreationHandler; // Ascoltatore per la creazione di vertici e connessioni

    // Stoccaggio di informazioni relative al forte
    private Fort mainFort;

    private Scene rootScene;
    private StackPane rootPane;
    
    private Pane terrainPane;
    private Pane decorationPane;
    private Pane buildingsPane;
    private Pane buildingsVertexPane;
    private Pane backgroundPane;

    private ImageView backgroundImageView;

    // Metodi "costruttori" (poiché JavaFX crea dei thread per la gestione dell'interfaccia, non si può creare un vero e proprio oggetto all'interno del main: Bisogna spostare il main all'interno di questa classe)
    public void start(Stage primaryStage) {
        // Inizializzazione attributi
        this.position = new Vector2();
        this.rootWorldPosition = new Vector2(960, -490);
        this.zoom = 0.2;
        this.cameraVelocity = new Vector2();
        this.keyInputHandler = new KeyInputHandler(this);
        this.vertexCreationHandler = new VertexCreationHandler(this);
        this.mainFort = new Fort(); // TODO: AGGIUNGI FUNZIONI DI CARICMANETO DA FILE

        // Creazione dei pane differenti
        rootPane = new StackPane();

        backgroundImageView = new ImageView(new Image("sfondo.png"));
        backgroundImageView.setPreserveRatio(false); // Non mantiene il rapporto, riempie tutto
        // Bind larghezza e altezza dell'immagine alle dimensioni del rootPane
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());

        terrainPane = new Pane();
        decorationPane = new Pane();
        buildingsPane = new Pane();
        buildingsVertexPane = new Pane();
        backgroundPane = new Pane(backgroundImageView);

        rootPane.setMouseTransparent(true);
        terrainPane.setMouseTransparent(true);
        decorationPane.setMouseTransparent(true);
        buildingsVertexPane.setMouseTransparent(true);
        buildingsPane.setMouseTransparent(true);
        backgroundPane.setMouseTransparent(true);

        rootPane.getChildren().addAll(terrainPane, decorationPane, buildingsVertexPane, buildingsPane, backgroundPane);

        buildingsPane.toFront();
        buildingsVertexPane.toFront();
        decorationPane.toFront();
        terrainPane.toFront();

        Vertex testVertex = new Vertex(new Vector2(0, 300));
        testVertex.draw(this);
        mainFort.addVertex(testVertex);
        Vertex testVertex2 = new Vertex(new Vector2(0, 1600));
        testVertex2.draw(this);
        mainFort.addVertex(testVertex2);
        Vertex testVertex3 = new Vertex(new Vector2(1600, 1600));
        testVertex3.draw(this);
        mainFort.addVertex(testVertex3);

        Connection testConnection = new Connection(testVertex2, testVertex, new Wood());
        testConnection.draw(this);
        mainFort.addConnection(testConnection);
        Connection testConnection2 = new Connection(testVertex3, testVertex, new Wood());
        testConnection2.draw(this);
        mainFort.addConnection(testConnection2);

        // Creazione della scene e messa in mostra della finestra
        rootScene = new Scene(rootPane);

        rootScene.setOnKeyPressed(keyInputHandler);
        rootScene.setOnKeyReleased(keyInputHandler);
        rootScene.setOnMouseClicked(vertexCreationHandler);
        
        primaryStage.setScene(rootScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(new String());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        //rootScene.setFill(Color.GRAY);

        primaryStage.show();

        // Gestione di thread secondari
        CameraPositionUpdateThread updateLoop = new CameraPositionUpdateThread(this);
        GUIPositionUpdateThread guiUpdateLoop = new GUIPositionUpdateThread(this);

        updateLoop.setDaemon(true);
        updateLoop.start();

        guiUpdateLoop.setDaemon(true);
        guiUpdateLoop.start();
        
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
     public VertexCreationHandler getVertexCreationHandler() {
        return vertexCreationHandler;
    }

    public void setVertexCreationHandler(VertexCreationHandler handler) {
        this.vertexCreationHandler = handler;
    }

    public Fort getMainFort() {
        return mainFort;
    }

    public void setMainFort(Fort mainFort) {
        this.mainFort = mainFort;
    }

    public Pane getBuildingsVertexPane() {
        return buildingsVertexPane;
    }

    public void setBuildingsVertexPane(Pane buildingsVertexPane) {
        this.buildingsVertexPane = buildingsVertexPane;
    }

    public ImageView getBackgroundImageView() {
        return backgroundImageView;
    }

    public void setBackgroundImageView(ImageView backgroundImageView) {
        this.backgroundImageView = backgroundImageView;
    }

    // Metodi classe
    public Vector2 calculateOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = baseVector.subtract(this.position).multiply(this.zoom).add(this.rootWorldPosition);

        finishedVector = new Vector2(finishedVector.getX(), -finishedVector.getY());

        return finishedVector;
    }

    public Vector2 inverseOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = new Vector2(baseVector.getX(), -baseVector.getY());

        finishedVector = finishedVector.subtract(this.rootWorldPosition).divide(this.zoom).add(this.position);

        return finishedVector;
    }

}