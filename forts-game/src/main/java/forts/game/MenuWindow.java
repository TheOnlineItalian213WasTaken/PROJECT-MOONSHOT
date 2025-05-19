package forts.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.ContentDisplay;

public class MenuWindow extends Application {

    private static final String BACKGROUND_IMAGE = "sfondo2.png"; // Cambia con il tuo sfondo
    private static final String PLAY_ICON = "iconaMartello.png";   // Cambia con la tua icona
    private static final String EXIT_ICON = "iconaExit.png";     // Cambia con la tua icona

    @Override
    public void start(Stage primaryStage) {
        // Sfondo
        ImageView background = new ImageView(new Image(BACKGROUND_IMAGE));
        background.setFitWidth(1280);
        background.setFitHeight(720);
        background.setPreserveRatio(false);

        // Layout centrale per i bottoni
        VBox menuBox = new VBox(30);
        menuBox.setAlignment(Pos.CENTER_LEFT);
        menuBox.setTranslateX(60); // Sposta i bottoni verso destra (distanza dal bordo sinistro)
        menuBox.setPrefWidth(400); // Larghezza preferita per evitare che i bottoni tocchino il bordo

        // Bottone "Gioca"
        Button playButton = createMenuButton("Gioca", PLAY_ICON);
        // Bottone "Impostazioni"
        Button settingsButton = createMenuButton("Impostazioni", "iconaSettings.png"); // Cambia con la tua icona impostazioni
        // Bottone "Esci"
        Button exitButton = createMenuButton("Esci", EXIT_ICON);

        playButton.setOnAction(e -> {
            // Avvia la finestra Camera
            Stage cameraStage = new Stage();
            Camera cameraApp = new Camera();
            cameraApp.start(cameraStage);
            primaryStage.close();
        });

        settingsButton.setOnAction(e -> {
            // Qui puoi aprire una finestra di impostazioni (da implementare)
            System.out.println("Impostazioni cliccato!");
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        menuBox.getChildren().addAll(playButton, settingsButton, exitButton);

        StackPane root = new StackPane(background, menuBox);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Forts - Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createMenuButton(String text, String iconPath) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(48);
        icon.setFitHeight(48);

        Button button = new Button(text, icon);
        button.setFont(Font.font("Arial", 32));
        button.setStyle("-fx-background-color: rgba(30,30,30,0.8); -fx-text-fill: white; -fx-background-radius: 20;");
        button.setTextFill(Color.WHITE);
        button.setPrefWidth(300);
        button.setPrefHeight(70);
        button.setGraphicTextGap(20);
        button.setContentDisplay(ContentDisplay.LEFT); // <-- Allinea icona e testo a sinistra
        button.setAlignment(Pos.CENTER_LEFT);          // <-- Allinea il contenuto del bottone a sinistra
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}