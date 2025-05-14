package forts.game;

public class CameraPositionUpdateThread extends Thread {
    Camera camera;

    CameraPositionUpdateThread(Camera camera) {
        this.camera = camera;
    }

    public void run() {
        KeyInputHandler keyInputHandler = this.camera.getKeyInputHandler();
        while(true) {
            keyInputHandler.movementHandle(); // Gestione movimento
        }
    }
}
