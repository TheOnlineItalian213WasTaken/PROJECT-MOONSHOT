package forts.game;

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
            initialMove();
            calculateConstraints();
        }
    }

    public void reset() {
        for (Object obj : camera.getMainFort().getVertices()) {
            Vertex vertex = (Vertex) obj;
           
            vertex.getActingForces().clear();
            vertex.getStartingForces().clear();

            vertex.setAcceleration(new Vector2());
        }
    }

    public void calculateWeights() {
        int i, size;

        size = camera.getMainFort().getVertices().size();
        for (i = 0; i < size; i++) {
            Vertex vertex = (Vertex) camera.getMainFort().getVertices().get(i);
           
            for (Vector2 force : this.globalForces) {
                vertex.getStartingForces().add(force);
            }

            vertex.disperseForces(); // Se inizia a laggare, il colpevole è questa funzione qui
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
            velocity = velocity.add(vertex.getAcceleration().multiply(deltaTime * 0.00000000001));
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
            vertex.setVelocity(velocity);

            Vector2 targetPosition = vertex.getPosition().add(velocity.multiply(deltaTime * 0.00001));
            targetPosition.setY(Math.clamp(targetPosition.getY(), 0, Double.POSITIVE_INFINITY));

            vertex.setPosition(targetPosition);

            //System.out.println(vertex.getPosition() + "  " + velocity);
        }


    }

    public void calculateConstraints() {

    }
}
