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
        this.x = 400;
        this.y = 44;
        this.ancho = 20;
        this.alto = 30;
        this.velocidad = 2;
        this.gravedad=0.3;
        this.velocidadCaida=0;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
    }

    public boolean colisionaIzquierda(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeIzquierdoDuende = this.x - (this.ancho / 2);
            double bordeDerechoIsla = isla.getX() + (isla.getAncho() / 2);

            if (bordeIzquierdoDuende <= bordeDerechoIsla && bordeIzquierdoDuende >= bordeDerechoIsla - velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeDerechoDuende = this.x + (this.ancho / 2);
            double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            if (bordeDerechoDuende >= bordeIzquierdoIsla && bordeDerechoDuende <= bordeIzquierdoIsla + velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeIzquierdoIsla - (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean colisionaAbajo(Isla[] islas) {
        for(Isla isla : islas) {
            if(isla==null) {
                continue;
            }
            double bordeInferiorDuende = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if(bordeInferiorDuende>=bordeSuperiorIsla && bordeInferiorDuende<=bordeSuperiorIsla +velocidad) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeSuperiorIsla-(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean colisionaArriba(Isla[] islas) {
        for(Isla isla : islas) {
            if(isla==null) {
                continue;
            }
            double bordeSuperiorDuende = this.y - (this.alto / 2);
            double bordeInferiorIsla = isla.getY() + (isla.getAlto() / 2);

            if(bordeSuperiorDuende <= bordeInferiorIsla && bordeSuperiorDuende>= bordeInferiorIsla-velocidad) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeInferiorIsla+(this.alto/2);
                    return true;
                }
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


