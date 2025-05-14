package forts.game;

import java.util.ArrayList;

// Classe che immagazzina tutti i vertici e connessioni, essa può essere caricata da un file

public class Fort {
    private ArrayList vertices; // Tutti i vertici del forte
    private ArrayList connections; // Tutte le connessioni del forte 

    // Metodo costruttore
    Fort() {
        this.vertices = new ArrayList();
        this.connections = new ArrayList();
    }

    // Metodi set() e get()
    public ArrayList getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList vertices) {
        this.vertices = vertices;
    }

    public ArrayList getConnections() {
        return connections;
    }

    public void setConnections(ArrayList connections) {
        this.connections = connections;
    }

    // Metodi classe
    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }
}
