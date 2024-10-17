package Juego;

import entorno.Entorno;

import java.awt.*;

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

    public Duende(double ancho, double alto,double x) {
        this.x = 100;
        this.y = 100;
        this.ancho = 100;
        this.alto = 100;
        this.velocidad = 2;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 50, Color.white);
    }




    //getters
    public double getX() {return this.x;}

    public double getY() {return this.y;}

    public double getAncho() {return this.ancho;}

    public double getAlto() {return this.alto;}

    public void moverIzquierda(){
        this.x -= velocidad;
    }

    public void moverDerecha(){
        this.x += velocidad;
    }
    public void patronDeMovimiento(){

    }
}


