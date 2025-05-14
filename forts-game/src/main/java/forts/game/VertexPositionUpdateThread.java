package forts.game;

public class VertexPositionUpdateThread extends Thread {
    Camera camera;
    Vertex currVertex;

    VertexPositionUpdateThread(Vertex currVertex, Camera camera) {
        this.currVertex = currVertex;
        this.camera = camera;
    }

    public void run() {
        currVertex.update(this.camera);
    }
}
