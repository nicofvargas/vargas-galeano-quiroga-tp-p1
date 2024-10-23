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
    private boolean debeAterrizar;
    private boolean estaAterrizando;
    private int velocidad = 2;
    private boolean viva;
    //atributos usados en gravedad
    private boolean enElAire;
    private double velocidadCaida;
    private double gravedad;

    public Tortuga(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 50;
        this.alto = 50;
        estaEnIsla = false;
        islaActual = null;
        velocidadMovimiento = 0.8;
        moviendoDerecha = true;
        this.debeAterrizar = false;
        this.estaAterrizando = false;
        this.viva = true;
        this.enElAire=true;
        this.velocidadCaida=0;
        this.gravedad=0.3;
    }

    public void caer() {
        if (!estaEnIsla) {
            y += velocidad; // Ajusta la velocidad si es necesario
            if (y > 600) { // Suponiendo que 600 es el límite inferior
                y = 600; // Evitar que se mueva más allá de este límite
                morir(); // Lógica para morir si cae demasiado
            }
        }
    }

    public void aterrizarEnIsla(Isla isla) {
        if (!estaEnIsla && isla != null && !isla.hayTortuga()) {
            if (isla.contienePunto(this.x, this.y)) {
                // Ajustar la posición de la tortuga para que esté sobre la isla
                double alturaIsla = isla.getAlto();
                y = isla.getY() - (alturaIsla / 2) - (this.alto / 2) + 5; // Ajusta el +5 según sea necesario
                estaEnIsla = true;
                islaActual = isla;
                isla.establecerTortuga(true);
            }
        }
    }
    public void mover(double deltaX, double deltaY, Isla[] islas) {
        this.x += deltaX;
        this.y += deltaY;

        // Verificar si la tortuga está en posición de aterrizar
        for (Isla isla : islas) {
            if (isla.contienePunto(this.x, this.y) && !estaEnIsla) {
                aterrizarEnIsla(isla);
                break; // Salir del bucle una vez que aterrice
            }
        }
    }
    public void actualizarPosicion(Isla[] islas) {

        if (!estaEnIsla) {
            this.y += velocidad; // La tortuga cae
        }

        // Verificar colisión con islas
        for (Isla isla : islas) {
            if (isla != null && this.colisionConIsla(isla)) {
                this.aterrizarEnIsla(isla);
                break;
            }
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
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.green);
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
    public boolean getViva() { return this.viva; }
    public void setViva(boolean viva) { this.viva = viva; }

    public boolean estaViva() {
        return viva;
    }

    public void morir() {
        if(getY()>600) {
            viva = false;
        }
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

        if(bordeSuperiorTortu <= bordeInferiorBolaFuego && bordeSuperiorTortu>= bordeInferiorBolaFuego-10) {
            if(this.x+(this.ancho/2) > bolaFuego.getX()-(bolaFuego.getAncho()/2)  &&  this.x-(this.ancho/2) < bolaFuego.getX()+(bolaFuego.getAncho()/2)) {
                this.y=bordeInferiorBolaFuego+(this.alto/2);
                return true;
            }
        }
        return false;
    }

    public boolean colisionaAbajoBolaFuego(bolaFuego bolaFuego) {
        double bordeInferiorTortu = this.y + (this.alto / 2);
        double bordeSuperiorBolaFuego = bolaFuego.getY() - (bolaFuego.getAlto() / 2);

        if(bordeInferiorTortu>=bordeSuperiorBolaFuego && bordeInferiorTortu<=bordeSuperiorBolaFuego +10) {
            if(this.x+(this.ancho/2) > bolaFuego.getX()-(bolaFuego.getAncho()/2)  &&  this.x-(this.ancho/2) < bolaFuego.getX()+(bolaFuego.getAncho()/2)) {
                this.y=bordeSuperiorBolaFuego-(this.alto/2);
                return true;
            }
        }
        return false;
    }

    //colision con Islas

    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + 10) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeIzquierdoIsla - (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaIzquierda(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeIzquierdoPersonaje = this.x - (this.ancho / 2);
            double bordeDerechoIsla = isla.getX() + (isla.getAncho() / 2);

            if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - 10) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
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
            double bordeSuperiorPersonaje = this.y - (this.alto / 2);
            double bordeInferiorIsla = isla.getY() + (isla.getAlto() / 2);

            if(bordeSuperiorPersonaje <= bordeInferiorIsla && bordeSuperiorPersonaje>= bordeInferiorIsla-10) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeInferiorIsla+(this.alto/2);
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
            double bordeInferiorPersonaje = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +10) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeSuperiorIsla-(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }

    //gravedad
    public void aplicarGravedad(Isla[] islas) {
        if(enElAire=true) {
            velocidadCaida+=gravedad;
            if (velocidadCaida>10) {
                velocidadCaida=10;
            }
            this.y+=velocidadCaida;
        }
        if(colisionaAbajo(islas)) {
            enElAire=false;
            velocidadCaida=0;
        }
    }
}