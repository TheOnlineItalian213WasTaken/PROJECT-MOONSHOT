package forts.game;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import java.util.ArrayList;

public class VertexCreationHandler {
    private Camera camera;
    private Vertex selectedVertex = null;

    public VertexCreationHandler(Camera camera) {
        this.camera = camera;
    }

    // Da chiamare quando un vertice viene cliccato
    public void onVertexClicked(Vertex vertex) {
        selectedVertex = vertex;
        // Attiva la modalità creazione: il prossimo click sulla scena sarà gestito da onSceneClicked
        camera.getRootScene().setOnMouseClicked(this::onSceneClicked);
    }

    // Da chiamare quando la scena viene cliccata
    private void onSceneClicked(MouseEvent event) {
        if (selectedVertex == null) return;

        // Calcola la posizione del click nel mondo
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        Vector2 worldPos = screenToWorld(mouseX, mouseY);

        // Cerca il vertice più vicino
        Vertex nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Object obj : camera.getMainFort().getVertices()) {
            Vertex v = (Vertex) obj;
            double dist = v.getPosition().subtract(worldPos).getMagnitude();
            if (dist < minDist) {
                minDist = dist;
                nearest = v;
            }
        }

        // Soglia di distanza per collegare invece di creare (puoi regolarla)
        double threshold = 200 * camera.getZoom();

        if (nearest != null && minDist < threshold) {
            // Crea solo la connessione
            Connection conn = new Connection(selectedVertex, nearest, new Wood());
            conn.draw(camera);
            camera.getMainFort().addConnection(conn);
        } else {
            // Crea un nuovo vertice e la connessione
            Vertex newVertex = new Vertex(worldPos);
            newVertex.draw(camera);
            camera.getMainFort().addVertex(newVertex);

            Connection conn = new Connection(selectedVertex, newVertex, new Wood());
            conn.draw(camera);
            camera.getMainFort().addConnection(conn);
        }

        // Reset stato
        selectedVertex = null;
        camera.getRootScene().setOnMouseClicked(null);
    }

    // Converte coordinate schermo in coordinate mondo
    private Vector2 screenToWorld(double x, double y) {
        Vector2 screen = new Vector2(x, y);
        Vector2 rootWorld = camera.getRootWorldPosition();
        double zoom = camera.getZoom();
        // Inverti la trasformazione fatta in Camera.calculateOffset
        double worldX = (x - rootWorld.getX()) / zoom + camera.getPosition().getX();
        double worldY = -((y - rootWorld.getY()) / zoom) + camera.getPosition().getY();
        return new Vector2(worldX, worldY);
    }
}
