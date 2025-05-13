package forts.game;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
        this.position = new Vector2();
        this.rootWorldPosition = new Vector2();
        this.zoom = 1;

        rootPane = new StackPane();

        terrainPane = new StackPane();
        decorationPane = new StackPane();
        buildingsPane = new StackPane();
        backgroundPane = new StackPane();

        rootPane.getChildren().addAll(terrainPane, decorationPane, buildingsPane, backgroundPane);

        rootScene = new Scene(rootPane);
        
        primaryStage.setScene(rootScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(new String());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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

    // Metodi classe
    public Vector2 calculateOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = (baseVector.multiply(this.zoom)).subtract(this.position).add(this.rootWorldPosition);

        return finishedVector;
    }

}