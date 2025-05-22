package forts.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.Serializable;


// Classe che immagazzina tutti i vertici e connessioni, essa può essere caricata da un file

public class Fort implements Serializable {
    private ArrayList<Vertex> vertices; // Tutti i vertici del forte
    private ArrayList<Connection> connections; // Tutte le connessioni del forte

    // Metodo costruttore
    public Fort() {
        this.vertices = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    // Metodi set() e get()
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    // Metodi classe
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addConnection(Connection c) {
        connections.add(c);
    }
}
