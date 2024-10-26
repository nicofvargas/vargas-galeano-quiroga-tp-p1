package Juego;

import entorno.Entorno;

import java.awt.*;

public class Casa {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    public Casa(Isla[] islas) {
        this.x=islas[0].getX();
        this.y=islas[0].getY()-islas[0].getAlto()-this.alto;
        this.ancho=30;
        this.alto=30;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x,this.y,this.ancho,this.alto,0, Color.white);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
}
