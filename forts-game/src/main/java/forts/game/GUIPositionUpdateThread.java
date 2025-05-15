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
        while (true) {
            System.out.print(' ');
            updateVertices();
            updateConnections();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateVertices() {
        int i;
        ArrayList vertices;

        vertices = this.camera.getMainFort().getVertices();
        for (i = 0; i < vertices.size(); i++) {
            Vertex currVertex = (Vertex) vertices.get(i);
            currVertex.update(camera);
        }
    }

    public void updateConnections() {
        int i;
        ArrayList connections;

        connections = this.camera.getMainFort().getConnections();
        for (i = 0; i < connections.size(); i++) {
            Connection currConnection = (Connection) connections.get(i);
            currConnection.update(camera);
        }
    }
}
