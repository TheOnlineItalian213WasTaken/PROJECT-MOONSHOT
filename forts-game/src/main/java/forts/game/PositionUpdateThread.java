package forts.game;

public class PositionUpdateThread extends Thread {
    Camera camera;

    PositionUpdateThread(Camera camera) {
        this.camera = camera;
    }

    public void run() {
        KeyInputHandler keyInputHandler = this.camera.getKeyInputHandler();
        while(true) {
            keyInputHandler.movementHandle(); // Gestione movimento
        }
    }
}
