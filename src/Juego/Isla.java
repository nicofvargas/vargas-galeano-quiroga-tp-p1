package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class Isla {
    private double x;
    private double y;
    private int ancho;
    private int alto;
    private boolean tortugaEnIsla;
    private String rutaIsla="Images/Isla/isla.png";
    private Image imagenIsla;
    // Constructor que establece las posiciones y dimensiones de una isla
    public Isla(int x, int y, int ancho, int alto) {
        super();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.tortugaEnIsla = false;
        this.imagenIsla = Herramientas.cargarImagen(rutaIsla).getScaledInstance(this.ancho,this.alto,Image.SCALE_SMOOTH);

    }
    public boolean hayTortuga() {
        return tortugaEnIsla;
    }
    public void establecerTortuga(boolean estado) {
        this.tortugaEnIsla = estado;
    }

    // Método que dibuja la isla en el entorno
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenIsla,this.x,this.y,0);
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
        double margen = 10;
        return (px >= x - ancho / 2 - margen && px <= x + ancho / 2 + margen &&
                py >= y - alto / 2 - margen && py <= y + alto / 2 + margen);
    }
}