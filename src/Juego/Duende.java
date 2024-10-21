package Juego;

import entorno.Entorno;

import java.awt.*;

import java.util.HashMap;

import java.util.Map;


public class Duende {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double gravedad;
    private double velocidadCaida;
    private int direccion;
    private boolean direccionElegida;
    private boolean enElAire;
    private boolean estaEnIsla;
    private Isla islaActual;
    //private double salud;
    private Map<Isla ,Integer>direccionesPorIsla;
    //private boolean colision;
    //private boolean muere;
    //private boolean rescatado;

    public Duende() {
        this.x = 400;
        this.y = 44;
        this.ancho = 20;
        this.alto = 30;
        this.velocidad = 1;
        this.gravedad = 0.1;
        this.velocidadCaida = 0;
        this.enElAire = false;
        this.estaEnIsla=false;
        this.direccionesPorIsla = new HashMap<>();

    }

    public boolean duendeEnElAire(Isla[] islas) {
        if (!colisionaAbajo(islas)) {
            enElAire = true;
        }
        return false;
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
    }

    public boolean colisionaAbajo(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }
            double bordeInferiorDuende = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if (bordeInferiorDuende >= bordeSuperiorIsla && bordeInferiorDuende <= bordeSuperiorIsla + (velocidad + 8)) {
                if (this.x + (this.ancho / 2) > isla.getX() - (isla.getAncho() / 2) && this.x - (this.ancho / 2) < isla.getX() + (isla.getAncho() / 2)) {
                    this.y = bordeSuperiorIsla - (this.alto / 2);
                    islaActual = isla; // Actualiza la isla actual
                    return true;
                }
            }
        }
        return false;
    }

    public void aplicarGravedad(Isla[] islas) {
        if (enElAire) {
            velocidadCaida += gravedad;
            if (velocidadCaida > 10) {
                velocidadCaida = 10;
            }
            this.y += velocidadCaida;
            this.x+=velocidad*direccion;
        }
        if (colisionaAbajo(islas)) {
            enElAire = false;
            velocidadCaida = 0;
        }
    }

    public void patronDeMovimiento(Isla[] islas) {
        if (colisionaAbajo(islas)) {
            if (!direccionesPorIsla.containsKey(islaActual)) {
                direccion = (Math.random() < 0.5) ? -1 : 1;
                direccionesPorIsla.put(islaActual, direccion);
            } else {
                direccion = direccionesPorIsla.get(islaActual);
            }
            this.x += velocidad * direccion;
        }
    }
}







