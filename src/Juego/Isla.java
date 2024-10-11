package Juego;

import entorno.Entorno;

import java.awt.*;

public class Isla {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    // Constructor que establece las posiciones y dimensiones de una isla
    public Isla(double x, double y, double ancho, double alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    // Método que dibuja la isla en el entorno
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.yellow);
    }
}

