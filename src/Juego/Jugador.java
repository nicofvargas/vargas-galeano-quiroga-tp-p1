package Juego;

import entorno.Entorno;

public class Jugador {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private boolean enElPiso;

    public Jugador(Entorno entorno) {
        this.x=100;
        this.y=100;
        this.ancho=50;
        this.alto=50;
        this.velocidad=2;
        this.enElPiso=false;
    }

    //movimiento

    public void moverIzq() {
        this.x-=velocidad;
    }
    public void moverDerecha() {
        this.x+=velocidad;
    }
    public void caer() {
        this.y+=velocidad;
    }
    public void saltar() {
        //falta completar
    }
}
