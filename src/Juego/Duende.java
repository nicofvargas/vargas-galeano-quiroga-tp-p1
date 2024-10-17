package Juego;

import entorno.Entorno;

import java.awt.*;

public class Duende {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double gravedad;
    private double velocidadCaida;
    //private double salud;
    //private boolean colision;
    //private boolean muere;
    //private boolean rescatado;

    public Duende() {
        this.x = 100;
        this.y = 100;
        this.ancho = 20;
        this.alto = 30;
        this.velocidad = 2;
        this.gravedad=0.3;
        this.velocidadCaida=0;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
    }

    public boolean colisionaAbajo(Isla[] islas) {
        for (Isla isla : islas) {
            if ((this.y + this.alto / 2 >= isla.getY() - isla.getAlto() / 2) &&
                    (this.y < isla.getY()) &&
                    (this.x + this.ancho / 2 > isla.getX() - isla.getAncho() / 2) &&
                    (this.x - this.ancho / 2 < isla.getX() + isla.getAncho() / 2)) {
                return true;
            }
        }
        return false;
    }

    public void aplicarGravedad(Isla[] islas) {
        if(!colisionaAbajo(islas)) {
            velocidadCaida+=gravedad;
            this.y+=velocidadCaida;
        }
        else {
            velocidadCaida=0;
        }
    }





    //getters
    //public double getX() {return this.x;}

   // public double getY() {return this.y;}

    //public double getAncho() {return this.ancho;}

   // public double getAlto() {return this.alto;}

   // public void moverIzquierda(){
        //this.x -= velocidad;
    //}

    //public void moverDerecha(){
        //this.x += velocidad;
   // }
    //public void patronDeMovimiento(){

   // }
}


