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

public class MenuWindow extends Application {

    private static final String BACKGROUND_IMAGE = "sfondo.jpg"; // Cambia con il tuo sfondo
    private static final String PLAY_ICON = "buildVertexIcon.png";   // Cambia con la tua icona
    private static final String EXIT_ICON = "woodSprite.png";     // Cambia con la tua icona

    @Override
    public void start(Stage primaryStage) {
        // Sfondo
        ImageView background = new ImageView(new Image(BACKGROUND_IMAGE));
        background.setFitWidth(1280);
        background.setFitHeight(720);
        background.setPreserveRatio(false);

        // Layout centrale per i bottoni
        VBox menuBox = new VBox(30);
        menuBox.setAlignment(Pos.CENTER);

        // Bottone "Gioca"
        Button playButton = createMenuButton("Gioca", PLAY_ICON);
        // Bottone "Esci"
        Button exitButton = createMenuButton("Esci", EXIT_ICON);

        playButton.setOnAction(e -> {
            // Avvia la finestra Camera
            Stage cameraStage = new Stage();
            Camera cameraApp = new Camera();
            cameraApp.start(cameraStage);
            primaryStage.close();
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        menuBox.getChildren().addAll(playButton, exitButton);

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
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}