package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Tortuga {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    boolean estaEnIsla;
    private Isla islaActual;
    private double velocidadMovimiento;
    private boolean moviendoDerecha;
    private int velocidad = 2;

    //atributos usados en gravedad

    private String rutaCaminaDerecha="Images/Tortuga/transparent-mario-spiked-turtle-with-red-mushroom-on-head661f16350cb837.55097172.png";
    private String rutaCaminaIzq="Images/Tortuga/tortugaizq.png";
    private Image caminaDerecha;
    private Image caminaIzq;


    public Tortuga(double posRandom) {
        this.x = posRandom;
        this.y = 0;
        this.ancho = 40;
        this.alto = 50;
        estaEnIsla = false;
        islaActual = null;
        velocidadMovimiento = 0.8;
        moviendoDerecha = true;

        this.caminaDerecha= Herramientas.cargarImagen(rutaCaminaDerecha).getScaledInstance((int)this.ancho,(int)this.alto,Image.SCALE_SMOOTH);
        this.caminaIzq= Herramientas.cargarImagen(rutaCaminaIzq).getScaledInstance((int)this.ancho,(int)this.alto,Image.SCALE_SMOOTH);


    }

    public boolean colisionaConBolaFuego(bolaFuego bolaFuego) {
        double bordeIzquierdoTortuga = this.x - (this.ancho / 2);
        double bordeDerechoTortuga = this.x + (this.ancho / 2);
        double bordeSuperiorTortuga = this.y - (this.alto / 2);
        double bordeInferiorTortuga = this.y + (this.alto / 2);

        double bordeIzquierdoBola = bolaFuego.getX() - (bolaFuego.getAncho() / 2);
        double bordeDerechoBola = bolaFuego.getX() + (bolaFuego.getAncho() / 2);
        double bordeSuperiorBola = bolaFuego.getY() - (bolaFuego.getAlto() / 2);
        double bordeInferiorBola = bolaFuego.getY() + (bolaFuego.getAlto() / 2);

        return (bordeDerechoTortuga > bordeIzquierdoBola && bordeIzquierdoTortuga < bordeDerechoBola &&
                bordeInferiorTortuga > bordeSuperiorBola && bordeSuperiorTortuga < bordeInferiorBola);

    }

    public void aterrizarEnIsla(Isla isla) {
        if (!estaEnIsla && isla != null && !isla.hayTortuga()) {
            if (isla.contienePunto(this.x, this.y)) {
                // Ajustar la posición de la tortuga para que esté sobre la isla
                double alturaIsla = isla.getAlto();
                y = isla.getY() - (alturaIsla / 2) - (this.alto / 2);
                estaEnIsla = true;
                islaActual = isla;
                isla.establecerTortuga(true);
            }
        }

    }

//aca verifica tortuga, caiga , y este en una isla
    public void actualizarPosicion(Isla[] islas) {
        if (!estaEnIsla) {
            this.y += velocidad; // La tortuga cae
        }
        // Verificar colisión con islas
        for (Isla isla : islas) {
            if (isla != null && this.colisionConIsla(isla)) {
                this.aterrizarEnIsla(isla);
                break;
            }isla.establecerTortuga(false);
        }
        if (estaEnIsla && islaActual != null) {
            moverEnIsla();
        }
    }
    private boolean colisionConIsla(Isla isla) {
        return (this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2 &&
                this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2 &&
                this.y + this.alto / 2 >= isla.getY() - isla.getAlto() / 2 &&
                this.y - this.alto / 2 <= isla.getY() + isla.getAlto() / 2);
    }
//para que se mueva, se usa en aterrizar
    private void moverEnIsla() {
        if (moviendoDerecha) {
            x += velocidadMovimiento;
            if (x + ancho / 2 > islaActual.getX() + islaActual.getAncho() / 2) {
                moviendoDerecha = false;
            }
        } else {
            x -= velocidadMovimiento;
            if (x - ancho / 2 < islaActual.getX() - islaActual.getAncho() / 2) {
                moviendoDerecha = true;
            }
        }
    }

    public void dibujar(Entorno entorno) {
        if(moviendoDerecha) {
            entorno.dibujarImagen(caminaDerecha,this.x,this.y,0);
        }
        else {
            entorno.dibujarImagen(caminaIzq,this.x,this.y,0);
        }
    }
    public void dibujarCaminaDerecha(Entorno entorno) {
        entorno.dibujarImagen(caminaDerecha,this.x,this.y,0);
    }
    public void dibujarCaminaIzq(Entorno entorno) {
        entorno.dibujarImagen(caminaIzq,this.x,this.y,0);
    }

    // Getters y setters
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



    //metodos de colision con BolaFuego (LEER CLASE JUGADOR PUEDEn REUTILIZAR TODOS LOS METODOS DE COLISION EN TODAS LAS CLASES)
    public boolean colisionaDerechaBolaFuego(bolaFuego bolaFuego) {
        double bordeDerechoTortuga = this.x + (this.ancho / 2);
        double bordeIzquierdoBolaFuego = bolaFuego.getX() - (bolaFuego.getAncho() / 2);

        if (bordeDerechoTortuga >= bordeIzquierdoBolaFuego && bordeDerechoTortuga <= bordeIzquierdoBolaFuego + 10) {
            if (this.y + (this.alto / 2) > bolaFuego.getY() - (bolaFuego.getAlto() / 2) && this.y - (this.alto / 2) < bolaFuego.getY() + (bolaFuego.getAlto() / 2)) {
                this.x = bordeIzquierdoBolaFuego - (this.ancho / 2);
                return true;
            }
        }
        return false;
    }


    public boolean colisionaIzquierdaBolaFuego(bolaFuego bolaFuego) {
        double bordeIzquierdoTortu = this.x - (this.ancho / 2);
        double bordeDerechoBolaFuego = bolaFuego.getX() + (bolaFuego.getAncho() / 2);

        if (bordeIzquierdoTortu <= bordeDerechoBolaFuego && bordeIzquierdoTortu >= bordeDerechoBolaFuego - 10) {
            if (this.y + (this.alto / 2) > bolaFuego.getY() - (bolaFuego.getAlto() / 2) && this.y - (this.alto / 2) < bolaFuego.getY() + (bolaFuego.getAlto() / 2)) {
                this.x = bordeDerechoBolaFuego + (this.ancho / 2);
                return true;
            }
        }
        return false;
    }


    public boolean colisionaArribaBolaFuego(bolaFuego bolaFuego) {
        double bordeSuperiorTortu = this.y - (this.alto / 2);
        double bordeInferiorBolaFuego = bolaFuego.getY() + (bolaFuego.getAlto() / 2);

        if (bordeSuperiorTortu <= bordeInferiorBolaFuego && bordeSuperiorTortu >= bordeInferiorBolaFuego - 10) {
            if (this.x + (this.ancho / 2) > bolaFuego.getX() - (bolaFuego.getAncho() / 2) && this.x - (this.ancho / 2) < bolaFuego.getX() + (bolaFuego.getAncho() / 2)) {
                this.y = bordeInferiorBolaFuego + (this.alto / 2);
                return true;
            }
        }
        return false;
    }

    public boolean colisionaAbajoBolaFuego(bolaFuego bolaFuego) {
        double bordeInferiorTortu = this.y + (this.alto / 2);
        double bordeSuperiorBolaFuego = bolaFuego.getY() - (bolaFuego.getAlto() / 2);

        if (bordeInferiorTortu >= bordeSuperiorBolaFuego && bordeInferiorTortu <= bordeSuperiorBolaFuego + 10) {
            if (this.x + (this.ancho / 2) > bolaFuego.getX() - (bolaFuego.getAncho() / 2) && this.x - (this.ancho / 2) < bolaFuego.getX() + (bolaFuego.getAncho() / 2)) {
                this.y = bordeSuperiorBolaFuego - (this.alto / 2);
                return true;
            }
        }
        return false;
    }

    //colision con Islas

    public boolean colisionaAbajo(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }
            double bordeInferiorPersonaje = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if (bordeInferiorPersonaje >= bordeSuperiorIsla && bordeInferiorPersonaje <= bordeSuperiorIsla + 10) {
                if (this.x + (this.ancho / 2) > isla.getX() - (isla.getAncho() / 2) && this.x - (this.ancho / 2) < isla.getX() + (isla.getAncho() / 2)) {
                    this.y = bordeSuperiorIsla - (this.alto / 2);
                    return true;
                }
            }
        }
        return false;
    }
}