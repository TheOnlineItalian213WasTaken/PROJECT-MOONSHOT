package forts.game;

import java.lang.Math;

// Classe che gestisce le connessioni tra vertici di costruzione

public class Connection {
    private Vertex[] vertices; // I due vertici della connessione sono tenuti qui dentro

    private Material material;

    // Metodo costruttore 
    Connection(Vertex firstVertex, Vertex secondVertex, Material material){ 
        this.material = material;
        this.vertices = new Vertex[2];

        this.vertices[0] = firstVertex;
        this.vertices[1] = secondVertex;
        
    }

    // Metodi set e get
    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    // Metodi classe
    public Vertex findOtherVertex(Vertex baseVertex) { // Metodo d'utilità per trovare l'altro met
        Vertex otherVertex = null;
        int i, length;

        length = this.vertices.length;
        for(i = 0; i < length; i++) {
            if(!(this.vertices[i] == baseVertex)) { // TODO: Controlla se questo da errori, è probabile che il Java non funzioni così.
                continue;
            }

            otherVertex = this.vertices[i];
            
            break;
        }

        return otherVertex;
    }
    
}