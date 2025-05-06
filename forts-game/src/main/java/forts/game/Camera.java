package forts.game;

public class Camera {
    private Vector2 position; // Variabile per tenere la posizione della telecamera
    private double zoom; // Variabile per tenere lo zoom della telecamera

    // Costruttore
    Camera(){
        this.position = new Vector2(0,0);
        this.zoom = 1;
    }

    // Metodi get() e set()
    public Vector2 getPosition(){
        return this.position;
    }

    public double getZoom(){
        return this.zoom;
    }

    public void setPosition(Vector2 newPosition){
        this.position = newPosition;
    }

    public void setZoom(double newZoom){
        this.zoom = newZoom;
    }

    public Vector2 calculateOffset(Vector2 baseVector){
        Vector2 finishedVector;

        finishedVector = (baseVector.multiply(this.zoom)).subtract(this.position);

        return finishedVector;
    }

}