package forts.game;

import javafx.scene.input.MouseEvent;
import javafx.event.*;
import javafx.application.Platform;
import java.util.ArrayList;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class VertexCreationHandler implements EventHandler<MouseEvent> {
    private Camera camera;
    private Vertex selectedVertex = null;
    private Sound sound = new Sound();

    public VertexCreationHandler(Camera camera) {
        this.camera = camera;
    }

    public void handle(MouseEvent event) {
        onSceneClicked(event);
    }

    // Da chiamare quando un vertice viene cliccato
    public void onVertexClicked(Vertex vertex) {
        selectedVertex = vertex;
        // Attiva la modalità creazione: il prossimo click sulla scena sarà gestito da onSceneClicked
        camera.getRootScene().setOnMouseClicked(this::onSceneClicked);
    }

    // Da chiamare quando la scena viene cliccata
    private void onSceneClicked(MouseEvent event) {
        // Soglia di distanza per collegare invece di creare (puoi regolarla)
        double selectionThreshold = 200 * camera.getZoom();
        double buildThreshold = 400 * camera.getZoom();

        // Calcola la posizione del click nel mondo
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        Vector2 worldPos = this.camera.inverseOffset(new Vector2(mouseX, mouseY));

        if (selectedVertex == null) { // Se non è stato selezionato nessun vertice, cerca il più vicino, e se non esiste, creane uno
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

            if (nearest != null && minDist < selectionThreshold) {
                // Crea solo la connessione
                this.selectedVertex = nearest;
            } else if (nearest == null) {
                // Crea un nuovo vertice e la connessione
                Vertex newVertex = new Vertex(worldPos);
                newVertex.draw(camera);
                //if(worldPos.getY() <= 0) {
                    newVertex.setAnchored(true);
                //}
                camera.getMainFort().addVertex(newVertex);

                this.selectedVertex = newVertex;
            }

            return;
        }

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

        if (nearest != null && minDist < buildThreshold) {
            if (nearest == selectedVertex) {
                return;
            }

            // Crea solo la connessione
            for (Object obj : selectedVertex.getConnections()) {
                Connection connection = (Connection) obj;
                Vertex otherVertex = connection.findOtherVertex(selectedVertex);

                System.out.println(otherVertex.getPosition() + nearest.getPosition().toString());
                System.out.println(otherVertex.getPosition().subtract(nearest.getPosition()).getMagnitude());

                if (otherVertex == nearest) {
                    System.out.println("WAAAAAAAAAGH");
                    return;
                }
            }

            Material material;
            if (camera.isUseIronForConnections()) {
                material = new Iron();
                //camera.playIronConnectionSound(); // Riproduci il suono per intero
            } else {
                material = new Wood();
                //camera.playWoodConnectionSound(); // Riproduci il suono per intero
            }
            Connection conn = new Connection(selectedVertex, nearest, material);

            selectedVertex.getConnections().add(conn);
            nearest.getConnections().add(conn);

            conn.draw(camera);
            camera.getMainFort().addConnection(conn);
        } else {
            // Crea un nuovo vertice e la connessione
            Vertex newVertex = new Vertex(worldPos);
            newVertex.draw(camera);
            if (worldPos.getY() <= 0) {
                newVertex.setAnchored(true);
            }
            camera.getMainFort().addVertex(newVertex);

            Material material;
            if (camera.isUseIronForConnections()) {
                material = new Iron();
                camera.playIronConnectionSound(); // Riproduci il suono anche qui!
            } else {
                material = new Wood();
                camera.playWoodConnectionSound(); // Riproduci il suono anche qui!
            }
            Connection conn = new Connection(selectedVertex, newVertex, material);

            selectedVertex.getConnections().add(conn);
            newVertex.getConnections().add(conn);

            conn.draw(camera);
            camera.getMainFort().addConnection(conn);
        }
        System.out.println("cliccata scena");

        // Reset stato
        selectedVertex = null;
    }
}
