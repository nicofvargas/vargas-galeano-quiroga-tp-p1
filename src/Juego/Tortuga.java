package Juego;

import entorno.Entorno;

import java.awt.*;
public class Tortuga {
    private double x;
    private double y;

    private double ancho;
    private double alto;

    private boolean estaEnIsla;
    //voy a comentar todo lo que de error para hacer pruebas en las otras clases
    //private Isla islaActual;

    public Tortuga(double ancho, double alto,double x) {
        this.x = 100;
        this.y = 100;
        this.ancho = 100;
        this.alto = 100;
        estaEnIsla = false;
        //islaActual = null;
    }


    /*
        public void caer() {
            if (!estaEnIsla) {
                y++;
            }
        }

        public void aterrizarEnIsla(Isla isla) {
            if (!estaEnIsla) {
                y = isla.getY();
                estaEnIsla = true;
                islaActual = isla;
            }
        }

        public void actualizarPosicion(List<Isla> islas) {
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
        public boolean estaEnIsla(Isla isla) {
            return islaActual == isla;
        }
     */
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.green);
    }


    //getters
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
