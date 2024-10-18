package Juego;

import entorno.Entorno;

import java.awt.*;
import java.util.Random;

public class Tortuga {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private boolean estaEnIsla;
    private Isla islaActual;
    private double velocidadMovimiento;
    private boolean moviendoDerecha;

    public Tortuga(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 50;
        this.alto = 50;
        estaEnIsla = false;
        islaActual = null;
        velocidadMovimiento = 0.8;
        moviendoDerecha = true;
    }

    public void caer() {
        if (!estaEnIsla) {
            y++;
        }
    }

    public void aterrizarEnIsla(Isla isla) {
        if (!estaEnIsla) {
            double alturaIsla = isla.getAlto();
            y = isla.getY() - (alturaIsla / 2) - (alto / 2);
            estaEnIsla = true;
            islaActual = isla;
        }
    }

    public void actualizarPosicion(Isla[] islas) {
        caer();
        for (Isla isla : islas) {
            if (isla.contienePunto(x, y)) {
                aterrizarEnIsla(isla);
                break;
            }
        }
        if (estaEnIsla) {
            moverEnIsla();
        }
    }

    private void moverEnIsla() {
        if (moviendoDerecha) {
            x += velocidadMovimiento;
            if (x + ancho / 2 > islaActual.getX() + islaActual.getAncho() / 2) {
                moviendoDerecha = false; // Cambia dirección
            }
        } else {
            x -= velocidadMovimiento;
            if (x - ancho / 2 < islaActual.getX() - islaActual.getAncho() / 2) {
                moviendoDerecha = true; // Cambia dirección
            }
        }
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.green);
    }

    // Getters
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