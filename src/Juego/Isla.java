package Juego;

import entorno.Entorno;

import java.awt.*;

public class Isla {
    private int x;
    private int y;
    private int ancho;
    private int alto;

    // Constructor que establece las posiciones y dimensiones de una isla
    public Isla(int x, int y, int ancho, int alto) {
        super();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
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
}

