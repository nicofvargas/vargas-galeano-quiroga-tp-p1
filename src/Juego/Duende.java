package Juego;

public class Duende {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double salud;
    private boolean colision;
    private boolean muere;
    private boolean rescatado;

    public Duende() {
        this.x = 100;
        this.y = 50;
        this.ancho = 50;
        this.alto = 50;
        this.velocidad = 2;
    }

    //getters
    public double getX() {
        return this.x;
    }

    public double getY() {return this.y;}

    public double getAncho() {return this.ancho;}

    public double getAlto() {return this.alto;}

    public void moverIzquierda(){
        this.x -= velocidad;
    }

    public void moverDerecha(){
        this.x += velocidad;
    }
    public void patronMov(){

    }
}
