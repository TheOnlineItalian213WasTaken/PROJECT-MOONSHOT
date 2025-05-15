package forts.game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Test extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // Livello 1 (Sfondo Rosso)
        Pane layer1 = new Pane();
        Pane layer2 = new Pane();
        Pane layer3 = new Pane();
        Button button = new Button("Ciao");
        Pane root = new Pane();
        layer1.setLayoutX(0);
        layer1.setLayoutY(0);
        layer1.setStyle("-fx-background-color: red;");
        layer1.setPrefSize(300, 250);

        button.setLayoutX(50);
        button.setLayoutY(50);
        button.setStyle("-fx-background-color: blue;");
        button.setPrefSize(75, 50);
        layer2.setLayoutX(50);
        layer2.setLayoutY(50);
        layer2.setStyle("-fx-background-color: green;");
        layer2.setPrefSize(150, 125);
        layer2.getChildren().add(button);

        layer3.setLayoutX(0);
        layer3.setLayoutY(0);
        layer3.setPrefSize(300, 250);
        layer3.setStyle("-fx-background-color: blue;");

        root.getChildren().addAll(layer1, layer2, layer3);


        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Tre Livelli in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}