package forts.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SettingsWindow extends Application {
    private static String selectedBackground = "sfondo.png"; // default

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Impostazioni - Scegli Sfondo");

        // Lista degli sfondi disponibili
        String[] backgrounds = {"sfondo.png", "sfondo1.png"};
        String[] labels = {"Verde", "Deserto"};

        HBox previews = new HBox(30);
        previews.setAlignment(Pos.CENTER);

        ToggleGroup group = new ToggleGroup();
        RadioButton selectedRadio = null;

        for (int i = 0; i < backgrounds.length; i++) {
            VBox box = new VBox(8);
            box.setAlignment(Pos.CENTER);

            ImageView preview = new ImageView(new Image(backgrounds[i]));
            preview.setFitWidth(140);
            preview.setFitHeight(80);
            preview.setPreserveRatio(true);
            preview.setStyle("-fx-effect: dropshadow(gaussian, #222, 8, 0.5, 0, 2);"
                           + "-fx-border-color: #444; -fx-border-width: 2; -fx-background-radius: 10;");

            RadioButton radio = new RadioButton(labels[i]);
            radio.setToggleGroup(group);
            radio.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            // Nessun radio selezionato di default

            int idx = i;
            radio.setOnAction(e -> {
                selectedBackground = backgrounds[idx];
            });

            box.getChildren().addAll(preview, radio);
            previews.getChildren().add(box);
        }

        Label title = new Label("Scegli lo sfondo della finestra Camera:");
        title.setFont(Font.font("Arial", 22));
        title.setTextFill(Color.WHITE);

        Button apply = new Button("Applica");
        apply.setFont(Font.font("Arial", 18));
        apply.setStyle("-fx-background-color: rgba(30,30,30,0.8); -fx-text-fill: white; -fx-background-radius: 20;");
        apply.setPrefWidth(160);
        apply.setPrefHeight(40);
        apply.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected != null) {
                int idx = previews.getChildren().indexOf(selected.getParent());
                Camera.setBackgroundImage(backgrounds[idx]);
            }
            primaryStage.close();
        });

        VBox root = new VBox(30, title, previews, apply);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(520, 320);
        root.setStyle("-fx-background-color: rgba(20,20,20,0.92);"); // <-- Bordo tondo rimosso

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}