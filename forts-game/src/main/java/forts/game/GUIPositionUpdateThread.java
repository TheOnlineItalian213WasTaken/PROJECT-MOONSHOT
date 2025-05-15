package forts.game;

import java.util.ArrayList;
import javafx.application.*;

// Thread che gestisce il ciclo con il quale

public class GUIPositionUpdateThread extends Thread {
    Camera camera;

    GUIPositionUpdateThread(Camera camera) {
        this.camera = camera;
    }

    public void run() {
        while(true) {
            System.out.println("UPDATE GUI: RUN");
            updateVertices();
        }
    }

    public void updateVertices() {
        int i;
        ArrayList vertices;

        vertices = this.camera.getMainFort().getVertices();
        for (i = 0; i < vertices.size(); i++) {
            Vertex currVertex = (Vertex) vertices.get(i);
            VertexPositionUpdateThread newThread = new VertexPositionUpdateThread(currVertex, this.camera);
            Platform.runLater(newThread);
        }
    }
}
