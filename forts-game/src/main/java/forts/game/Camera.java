package forts.game;

import java.io.Serializable;

import javafx.application.*;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.io.Serializable;

public class Camera extends Application implements Serializable{
    private static String backgroundImageFile = "sfondo.png"; // default

    public static void setBackgroundImage(String filename) {
        backgroundImageFile = filename;
    }

    private Vector2 position; // Variabile per tenere la posizione della telecamera
    private Vector2 rootWorldPosition; // La posizione del punto (0, 0) nel mondo, questo valore è basato sulla grandezza dello schermo
    private double zoom; // Variabile per tenere lo zoom della telecamera

    // Movimento telecamera
    private Vector2 cameraVelocity; // Vettore che verrà aggiunto alla posizione della camera ogni tick

    // Ascoltatori / event handler
    private transient KeyInputHandler keyInputHandler;
    private transient VertexCreationHandler vertexCreationHandler; // Ascoltatore per la creazione di vertici e connessioni

    // Stoccaggio di informazioni relative al forte
    private Fort mainFort;

    private transient Scene rootScene;
    private transient StackPane rootPane;
    
    private transient Pane terrainPane;
    private transient Pane decorationPane;
    private transient Pane buildingsPane;
    private transient Pane buildingsVertexPane;
    private transient Pane backgroundPane;

    private transient ImageView backgroundImageView;

    // Variabile per tenere traccia se si sta usando il ferro per le connessioni
    private boolean useIronForConnections = false;

    public boolean isUseIronForConnections() {
        return useIronForConnections;
    }

    public void setUseIronForConnections(boolean useIronForConnections) {
        this.useIronForConnections = useIronForConnections;
    }

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

        backgroundImageView = new ImageView(new Image(backgroundImageFile));
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.fitWidthProperty().bind(rootPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(rootPane.heightProperty());

        terrainPane = new Pane();
        decorationPane = new Pane();
        buildingsPane = new Pane();
        buildingsVertexPane = new Pane();
        backgroundPane = new Pane(backgroundImageView);

        // Ordine corretto dei pane:
        rootPane.getChildren().addAll(backgroundPane, terrainPane, decorationPane, buildingsVertexPane, buildingsPane);

        buildingsPane.toFront();
        buildingsVertexPane.toFront();
        decorationPane.toFront();
        terrainPane.toFront();

        // Creazione della scene e messa in mostra della finestra
        rootScene = new Scene(rootPane);

        rootScene.setOnKeyPressed(keyInputHandler);
        rootScene.setOnKeyReleased(keyInputHandler);
        rootScene.setOnMouseClicked(vertexCreationHandler);
        
        primaryStage.setScene(rootScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(new String());
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);


        primaryStage.show();

        // Pulsante per tornare al menu in alto a destra
        Button menuButton = new Button("Menu");
        menuButton.setStyle("-fx-font-size: 18px; -fx-background-radius: 20; -fx-background-color: rgba(30,30,30,0.8); -fx-text-fill: white;");
        menuButton.setPrefWidth(100);
        menuButton.setPrefHeight(40);

        // Posiziona il pulsante in alto a destra
        StackPane.setAlignment(menuButton, Pos.TOP_RIGHT);
        StackPane.setMargin(menuButton, new Insets(20, 20, 0, 0));
        rootPane.getChildren().add(menuButton);

        menuButton.setOnAction(e -> {
            primaryStage.close();
            Stage menuStage = new Stage();
            MenuWindow menuApp = new MenuWindow();
            try {
                menuApp.start(menuStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Pulsante per usare il legno nelle connessioni (con icona e in basso al centro, a sinistra)
        Image woodImg = new Image("woodSprite.png"); // Assicurati che l'immagine esista
        ImageView woodIcon = new ImageView(woodImg);
        woodIcon.setFitWidth(40);
        woodIcon.setFitHeight(40);

        Button woodButton = new Button();
        woodButton.setGraphic(woodIcon);
        woodButton.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(139,69,19,0.8);");
        woodButton.setPrefWidth(60);
        woodButton.setPrefHeight(60);

        // Posiziona il pulsante in basso al centro, leggermente a sinistra
        StackPane.setAlignment(woodButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(woodButton, new Insets(0, 100, 30, 0)); // 40px verso sinistra
        rootPane.getChildren().add(woodButton);

        // Pulsante per usare il ferro nelle connessioni (già presente, lo spostiamo a destra)
        Image ironImg = new Image("ironSprite.png");
        ImageView ironIcon = new ImageView(ironImg);
        ironIcon.setFitWidth(40);
        ironIcon.setFitHeight(40);

        Button ironButton = new Button();
        ironButton.setGraphic(ironIcon);
        ironButton.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(80,80,80,0.8);");
        ironButton.setPrefWidth(60);
        ironButton.setPrefHeight(60);

        // Posiziona il pulsante in basso al centro, leggermente a destra
        StackPane.setAlignment(ironButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(ironButton, new Insets(0, 0, 30, 40)); // 40px verso sinistra

        woodButton.setOnAction(e -> {
            useIronForConnections = false;
            woodButton.setStyle("-fx-background-radius: 30; -fx-background-color: #deb887;"); // Colore attivo per legno
            ironButton.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(80,80,80,0.8);");
        });
        ironButton.setOnAction(e -> {
            useIronForConnections = true;
            ironButton.setStyle("-fx-background-radius: 30; -fx-background-color: #b87333;"); // Colore attivo per ferro
            woodButton.setStyle("-fx-background-radius: 30; -fx-background-color: rgba(139,69,19,0.8);");
        });


        rootPane.getChildren().add(ironButton);

        //  Bottone per SALVARE la partita
        Button saveButton = new Button("Salva");
        saveButton.setStyle("-fx-font-size: 16px; -fx-background-radius: 20; -fx-background-color: #4caf50; -fx-text-fill: white;");
        saveButton.setPrefWidth(90);
        saveButton.setPrefHeight(40);
        StackPane.setAlignment(saveButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(saveButton, new Insets(0, 0, 30, 30));
        rootPane.getChildren().add(saveButton);

        saveButton.setOnAction(e -> {
            try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream("fort_save.ser"))) {
                out.writeObject(mainFort);
                System.out.println("Partita salvata!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Bottone per CARICARE la partita
        Button loadButton = new Button("Carica");
        loadButton.setStyle("-fx-font-size: 16px; -fx-background-radius: 20; -fx-background-color: #2196f3; -fx-text-fill: white;");
        loadButton.setPrefWidth(90);
        loadButton.setPrefHeight(40);
        // Posiziona il bottone "Carica" leggermente a destra di "Salva"
        StackPane.setAlignment(loadButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(loadButton, new Insets(0, 0, 30, 130)); // 130px per non sovrapporsi a "Salva"
        rootPane.getChildren().add(loadButton);

        loadButton.setOnAction(e -> {
            try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream("fort_save.ser"))) {
                Fort loadedFort = (Fort) in.readObject();
                setMainFort(loadedFort);
                // Ridisegna tutto
                buildingsPane.getChildren().clear();
                buildingsVertexPane.getChildren().clear();
                for (Object vObj : loadedFort.getVertices()) {
                    Vertex v = (Vertex) vObj;
                    v.draw(this);
                }
                for (Object cObj : loadedFort.getConnections()) {
                    Connection c = (Connection) cObj;
                    c.draw(this);
                }
                System.out.println("Partita caricata!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        // Bottone per RESETTARE la struttura
        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-font-size: 16px; -fx-background-radius: 20; -fx-background-color: #f44336; -fx-text-fill: white;");
        resetButton.setPrefWidth(90);
        resetButton.setPrefHeight(40);
        // Posiziona il bottone "Reset" ancora più a destra di "Carica"
        StackPane.setAlignment(resetButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(resetButton, new Insets(0, 0, 30, 230)); // 230px per non sovrapporsi agli altri
        rootPane.getChildren().add(resetButton);

        resetButton.setOnAction(e -> {
            // Svuota la struttura attuale
            mainFort = new Fort();
            buildingsPane.getChildren().clear();
            buildingsVertexPane.getChildren().clear();

            // Ricrea i vertici e le connessioni iniziali
            Vertex testVertex4 = new Vertex(new Vector2(0, 300));
            testVertex4.draw(this);
            mainFort.addVertex(testVertex4);
            Vertex testVertex5 = new Vertex(new Vector2(0, 1600));
            testVertex5.draw(this);
            mainFort.addVertex(testVertex5);
            Vertex testVertex6 = new Vertex(new Vector2(1600, 1600));
            testVertex6.draw(this);
            mainFort.addVertex(testVertex6);

            Connection testConnection3 = new Connection(testVertex2, testVertex, new Wood());
            testConnection3.draw(this);
            mainFort.addConnection(testConnection3);
            Connection testConnection4 = new Connection(testVertex3, testVertex, new Wood());
            testConnection4.draw(this);
            mainFort.addConnection(testConnection4);

            System.out.println("Struttura resettata!");
        });


        // Gestione di thread secondari
        CameraPositionUpdateThread updateLoop = new CameraPositionUpdateThread(this);
        GUIPositionUpdateThread guiUpdateLoop = new GUIPositionUpdateThread(this);
        PhysicsHandler physicsLoop = new PhysicsHandler(this);

        updateLoop.setDaemon(true);
        updateLoop.start();

        guiUpdateLoop.setDaemon(true);
        guiUpdateLoop.start();
        
        physicsLoop.setDaemon(true);
        physicsLoop.start();
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