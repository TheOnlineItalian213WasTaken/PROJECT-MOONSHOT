package forts.game;

public class CameraPositionUpdateThread extends Thread {
    private double deltaTime = 0;
    private double lastTick;
    Camera camera;

    CameraPositionUpdateThread(Camera camera) {
        this.lastTick = System.nanoTime();
        this.camera = camera;
    }

    public void run() {
        while(true) {
            this.deltaTime = System.nanoTime() - this.lastTick;
            this.lastTick = System.nanoTime();
            movementHandle(); // Gestione movimento
        }
    }

    public void movementHandle() {
        this.camera.setPosition(this.camera.getPosition().add(this.camera.getCameraVelocity().multiply(this.deltaTime)));
    }
}
