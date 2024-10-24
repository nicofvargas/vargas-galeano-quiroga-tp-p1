package Juego;

import entorno.Entorno;

import java.awt.*;

public class Isla {
    private double x;
    private double y;
    private int ancho;
    private int alto;
    private boolean tortugaEnIsla;
    private boolean estaEnIsla;

    // Constructor que establece las posiciones y dimensiones de una isla
    public Isla(int x, int y, int ancho, int alto) {
        super();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.tortugaEnIsla = false;
    }
    public boolean hayTortuga() {
        return tortugaEnIsla;
    }
    public void establecerTortuga(boolean estado) {
        this.tortugaEnIsla = estado;
    }

    // Método que dibuja la isla en el entorno
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.yellow);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getAncho() {
        return this.ancho;
    }
    public double getAlto() {
        return this.alto;
    }

    public boolean contienePunto(double px, double py) {
        double margen = 5;
        return (px >= x - ancho / 2 - margen && px <= x + ancho / 2 + margen &&
                py >= y - alto / 2 - margen && py <= y + alto / 2 + margen);
    }
}
