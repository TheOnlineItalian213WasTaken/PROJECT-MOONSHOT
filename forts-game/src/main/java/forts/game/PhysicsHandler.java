package forts.game;

import java.util.ArrayList;

public class PhysicsHandler extends Thread {
    private Camera camera;
    private double deltaTime = 0;
    private double lastTick;

    private Vector2[] globalForces = {
        new Vector2(0, -9.81) // Gravità
    };

    PhysicsHandler(Camera camera) {
        this.lastTick = System.nanoTime();
        this.camera = camera;
    }

    public void run() {
        while(true) {
            this.deltaTime = System.nanoTime() - this.lastTick;
            this.lastTick = System.nanoTime();

            reset();
            calculateWeights();
            calculateFatigue();
            /* Con un po' più di tempo ed intelligenza forse questo sarebbe stato possibile, peccato
            initialMove();
            calculateConstraints(); */
        }
    }

    public void reset() {
        int i, size;

        size = camera.getMainFort().getVertices().size();
        for (i = 0; i < size; i++) {
            Vertex vertex = (Vertex) camera.getMainFort().getVertices().get(i);
           
            vertex.getActingForces().clear();
            vertex.getStartingForces().clear();

            vertex.setAcceleration(new Vector2());
        }

        size = camera.getMainFort().getConnections().size();
        for (i = 0; i < size; i++) {
            Connection connection = (Connection) camera.getMainFort().getConnections().get(i);
           
            connection.setHoldingWeight(0);
        }
    }

    public void calculateWeights() {
        int i, size;

        size = camera.getMainFort().getConnections().size();
        for (i = 0; i < size; i++) {
            Connection connection = (Connection) camera.getMainFort().getConnections().get(i);
           
            connection.getVertices()[0].disperseForces(connection.getWeight() / 2);
            connection.getVertices()[1].disperseForces(connection.getWeight() / 2);
        }
    }

    public void calculateFatigue() {
        int i, size;

        size = camera.getMainFort().getConnections().size();
        System.out.println(size);
        for (i = 0; i < size; i++) {
            Connection connection = (Connection) camera.getMainFort().getConnections().get(i);
            double finalFatigue;

            finalFatigue = (connection.getHoldingWeight() / connection.getMaterial().getWeightResistance()) * 1000;

            connection.getMaterial().setFatigue(finalFatigue);
            System.out.println(finalFatigue + "   " + connection.getMaterial().getFatigue());
        }
    }

    public void initialMove() {
        // Calcolo accelerazione
        int i, size;

        size = camera.getMainFort().getVertices().size();
        for (i = 0; i < size; i++) {
            Vertex vertex = (Vertex) camera.getMainFort().getVertices().get(i);
           
            for (Object forceObj : vertex.getActingForces()) {
                Vector2 force = (Vector2) forceObj;

                vertex.setAcceleration(vertex.getAcceleration().add(force));
                //System.out.println(vertex.getAcceleration());
            }

            //System.out.println(vertex.getAcceleration());
        }

        // Calcolo velocità
        size = camera.getMainFort().getVertices().size();
        for (i = 0; i < size; i++) {
            Vertex vertex = (Vertex) camera.getMainFort().getVertices().get(i);
            double clampedX, clampedY;
            Vector2 acceleration;
            Vector2 velocity = vertex.getVelocity();

            // Guard clause in caso che il vertice sia ancorato
            if(vertex.isAnchored()) {
                continue;
            }

            acceleration = vertex.getAcceleration();
            velocity = velocity.add(vertex.getAcceleration().multiply(deltaTime * 0.000000001));
            if(acceleration.getX() < 0) {
                clampedX = Math.clamp(velocity.getX(), acceleration.getX(), -acceleration.getX());
            } else {
                clampedX = Math.clamp(velocity.getX(), -acceleration.getX(), acceleration.getX());
            }

            if(acceleration.getY() < 0) {
                clampedY = Math.clamp(velocity.getY(), acceleration.getY(), -acceleration.getY());
            } else {
                clampedY = Math.clamp(velocity.getY(), -acceleration.getY(), acceleration.getY());
            }

            velocity = new Vector2(clampedX, clampedY);

            int j, length;
            Vector2 subtractingVector = new Vector2();
            ArrayList connections = vertex.getConnections();
            length = connections.size();
            for(j = 0; j < length; j++) { // Secondo ciclo itera ogni connessione per disperdere le forze e continuare la funzione ricorsiva
                Connection currConnection = (Connection) connections.get(j);
                Vertex otherVertex = currConnection.findOtherVertex(vertex);
                Vector2 subVector = vertex.getPosition().subtract(otherVertex.getPosition()).unit();

                double dotProduct = subVector.dotProduct(Vector2.yAxis);
                //System.out.println(dotProduct);
                subVector = subVector.multiply(velocity.getMagnitude() * dotProduct * 0.5);
                velocity = velocity.add(subVector);
            }
            System.out.println(subtractingVector);
            //velocity = velocity.add(subtractingVector);

            vertex.setVelocity(velocity);

            if(vertex.getPosition().getY() <= 0) {
                velocity.setX(0);
            }

            Vector2 targetPosition = vertex.getPosition().add(velocity.multiply(deltaTime * 0.00001));
            targetPosition.setY(Math.clamp(targetPosition.getY(), 0, Double.POSITIVE_INFINITY));

            vertex.setPosition(targetPosition);

            //System.out.println(vertex.getPosition() + "  " + velocity);
        }


    }

    public void calculateConstraints() {

    }
}
