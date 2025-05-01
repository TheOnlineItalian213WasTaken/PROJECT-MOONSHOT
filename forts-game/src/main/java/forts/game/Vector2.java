package forts.game;

import java.lang.Math;

// Classe necessaria per la gestione dei vettori di posizione all'interno del gioco.

public class Vector2 {
    private double x;
    private double y;
    private double magnitude; // Modulo del vettore 

    // Vettori d'utilità 
    private static final Vector2 yAxis = new Vector2(0, 1); // Vettore che rappresenta l'asse verticale
    private static final Vector2 xAxis = new Vector2(1, 0); // Vettore che rappresenta l'asse orizzontale

    // Metodi costruttori
    Vector2() { // Costruttore vuoto
        this.x = 0;
        this.y = 0;
        this.magnitude = 0;
    }

    Vector2(double x, double y) { // Costruttore da utilizzare nella maggior parte dei casi
        this.x = x;
        this.y = y;

        // Calcolo del modulo del vettore
        this.magnitude = Math.sqrt((Math.pow(x, 2)) + (Math.pow(y, 2)));
    }

    // Metodi set() e get()
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public static Vector2 getYaxis() {
        return yAxis;
    }

    public static Vector2 getXaxis() {
        return xAxis;
    }

    // Metodi classe
    public Vector2 add(Vector2 addingVector) { // Somma tra vettori
        Vector2 sumVector;
        double sumX, sumY;

        sumX = this.x + addingVector.getX();
        sumY = this.y + addingVector.getY();
        sumVector = new Vector2(sumX, sumY); // La somma dei vettori è la somma delle sue componenti

        return sumVector;
    }

    public Vector2 subtract(Vector2 subtractingVector) { // Sottrazione tra vettori
        Vector2 subVector;
        double subX, subY;

        subX = this.x - subtractingVector.getX();
        subY = this.y - subtractingVector.getY();
        subVector = new Vector2(subX, subY); // La sottrazione dei vettori è la sottrazione delle loro componenti

        return subVector;
    }
    
    public Vector2 multiply(Vector2 multiplicatingVector){ // Moltiplicazione tra vettori passando un vettore
        Vector2 mulVector;
        double mulX,mulY;

        mulX = this.x * multiplicatingVector.getX();
        mulY = this.y * multiplicatingVector.getY();
        mulVector = new Vector2(mulX,mulY); // La moltiplicazione dei vettori è la moltiplicazione delle loro componenti

        return mulVector;
    }
    public Vector2 multiply(double num){ // Moltiplicazione tra vettori passando un double
        Vector2 mulVector;
        double mulX,mulY;

        mulX = this.x * num;
        mulY = this.y * num;
        mulVector = new Vector2(mulX,mulY);

        return mulVector;
    }

    public Vector2 divide(Vector2 dividingVector){ // Divisione tra vettori passando un vettore
        Vector2 divVector;
        double divX,divY;

        divX = this.x / dividingVector.getX();
        divY = this.y / dividingVector.getY();
        divVector = new Vector2(divX,divY); // La divisione dei vettori è la divisione delle loro componenti

        return divVector;
    }
    public Vector2 divide(double num){ // Divisione tra vettori passando un double
        Vector2 divVector;
        double divX,divY;

        divX = this.x / num;
        divY = this.y / num;
        divVector = new Vector2(divX,divY);

        return divVector;
    }

    public Vector2 unit() { // Metodo per ritornare il vettore normalizzato del vettore su cui questo metodo è stato richiamato
        Vector2 unitVector;

        unitVector = this.divide(this.magnitude);

        return unitVector;
    }

    public double dotProduct(Vector2 secondVector) { // Ritorna un valore tra -1 e 1 in base alla differenza di direzione dei due vettori
        double theta;
        double dotProduct;
        
        theta = Math.atan2(secondVector.getY(), secondVector.getX()) - Math.atan2(this.y, this.x);
        dotProduct = this.magnitude * secondVector.getMagnitude() * Math.cos(theta);
        
        return dotProduct;
    }

    @Override
    public String toString() {
        String finalString = "(X: " + this.x + ", Y: " + this.y + ", Magnitude: " + this.magnitude + ")";
        return finalString;
    }
}
