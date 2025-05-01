package forts.game;

// Classe che gestisce ogni singolo vertice su cui verranno applicate le forze all'interno del gioco

public class Vertex {
    String spriteDirectory = "buildVertexIcon.png"; // Directory della sprite per 

    Vector2 position; // Posizione nel mondo

    Connection[] connections; // Tutte le connessioni che originano con / terminano in questo vertica
    Vector2[] actingForces; // Tutte le forze che agiscono su questo vertice in un determinato momento (forze globali (es. gravità) sono escluse)

    Vector2 acceleration;
    Vector2 velocity;

    Vertex() {
        connections = new Connection[20]; // Massimo di 20 connessioni
        actingForces = new Vector2[20]; // Massimo di 20 forze che possono agire su un vertice  
    }

}
