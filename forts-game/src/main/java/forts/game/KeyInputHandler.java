package forts.game;

import javafx.scene.input.*;
import javafx.application.Platform;
import javafx.event.*;

// Classe che gestisce ogni evento relativo alla tastiera

public class KeyInputHandler implements EventHandler<KeyEvent> {
    static final double BASE_SPEED = 0.0001; // Velocità di base di quanto la telecamera si muoverà ogni tick
    Camera camera;

    KeyCode lastKey = KeyCode.UP;

    KeyInputHandler(Camera camera) {
        this.camera = camera;
    }

    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            movementStart(event);
        } else {
            movementEnd(event);
        }
    }

    public void movementStart(KeyEvent keyEvent) {
        Vector2 movementTarget;
        KeyCode key = keyEvent.getCode();

        System.out.println(key);

        if(key == KeyCode.UP) {
            movementTarget = new Vector2(0, BASE_SPEED);
        } else if(key == KeyCode.DOWN) {
            movementTarget = new Vector2(0, -BASE_SPEED);
        } else if(key == KeyCode.LEFT) {
            movementTarget = new Vector2(-BASE_SPEED, 0);
        } else if(key == KeyCode.RIGHT) {
            movementTarget = new Vector2(BASE_SPEED, 0);
        } else {
            return;
        }

        lastKey = key;

        if(keyEvent.isShiftDown()) {
            movementTarget.multiply(2);
        }

        camera.setCameraVelocity(movementTarget);
        System.out.println("" + movementTarget + this.camera.getCameraVelocity());
    }

    public void movementEnd(KeyEvent keyEvent) {
        Vector2 movementTarget;
        KeyCode key = keyEvent.getCode();

        if(lastKey == key) {
            movementTarget = new Vector2(0, 0);
        } else {
            return;
        }

        camera.setCameraVelocity(movementTarget);
    }
}
