package forts.game;

public class CameraPositionUpdateThread extends Thread {
    Camera camera;

    CameraPositionUpdateThread(Camera camera) {
        this.camera = camera;
    }

    public void run() {
        while(true) {
            movementHandle(); // Gestione movimento
        }
    }

    public void movementHandle() {
        this.camera.setPosition(this.camera.getPosition().add(this.camera.getCameraVelocity()));
    }
}
