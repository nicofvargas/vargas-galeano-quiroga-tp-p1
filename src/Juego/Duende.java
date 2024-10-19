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
    private boolean direccionElegida; // Para verificar si ya se eligió una dirección
    private int direccion;
    private boolean enElAire;
    private boolean estaEnIsla;
    private Isla islaActual;
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
        this.gravedad = 0.3;
        this.velocidadCaida = 0;
        this.direccionElegida = false;
        this.enElAire = false;
        this.estaEnIsla=false;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
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
    private void ajustarPosicionDuende(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }


            if (this.x + (this.ancho / 2) > isla.getX() - (isla.getAncho() / 2) &&
                    this.x - (this.ancho / 2) < isla.getX() + (isla.getAncho() / 2)) {


                this.y = isla.getY() - (isla.getAlto() / 2) - (this.alto / 2);
                break;
            }
        }
    }

    public void aplicarGravedad(Isla[] islas) {
        if(enElAire=true) {
            velocidadCaida+=gravedad;
            this.y+=velocidadCaida;
        }
        if(colisionaAbajo(islas)) {
            enElAire=false;
            velocidadCaida=0;
        }


    }
    //getters
    public double getX() {return this.x;}

    public double getY() {return this.y;}

    public double getAncho() {return this.ancho;}

   public double getAlto() {return this.alto;}

    public void patronDeMovimiento() {
        if (!direccionElegida) {
            this.direccion = (int) (Math.random() * 2) + 1;
            this.direccion = (this.direccion == 1) ? 1 : -1;
            this.direccionElegida = true;
        }
        this.x += velocidad * direccion;
    }
}




