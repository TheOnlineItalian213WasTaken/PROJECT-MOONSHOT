package forts.game;

import java.lang.Math;

import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;
import java.io.Serializable;
import javafx.scene.*;
import javafx.scene.effect.ColorAdjust;

public abstract class Terrain implements Drawable {
    protected String spriteDirectory;
    private transient ImageView sprite;

    public void draw(Camera camera) {
        this.sprite = new ImageView(this.spriteDirectory); // Creazione iniziale dell'elemento grafico per il vertice
        this.sprite.setVisible(false);
        this.sprite.setPreserveRatio(false);

        camera.getTerrainPane().getChildren().add(sprite);

        this.update(camera);
        this.sprite.setVisible(true);
    }

    public void update(Camera camera) {
        Vector2 absolutePosition, relativePosition, sizeOffset;

        // Riposizionamento del gui dinamico in base alla posizione root del mondo e posizizone della telecamera
        sizeOffset = new Vector2((1250 * camera.getZoom() * 10), 0);
        absolutePosition = new Vector2();
        relativePosition = camera.calculateOffset(absolutePosition).subtract(sizeOffset);

        Platform.runLater(() -> {
            this.sprite.setFitWidth(2500 * camera.getZoom() * 10);
            this.sprite.setFitHeight(700 * camera.getZoom() * 10);

            this.sprite.setLayoutX(relativePosition.getX());
            this.sprite.setLayoutY(relativePosition.getY());
        });
    }
}
