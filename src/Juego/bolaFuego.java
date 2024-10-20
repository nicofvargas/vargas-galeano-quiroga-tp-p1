package Juego;

import entorno.Entorno;

import java.awt.*;

public class bolaFuego {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion;
    private boolean activa;

    public bolaFuego(Jugador jugador) {
        this.x=jugador.getX();
        this.y=jugador.getY();
        this.ancho=30;
        this.alto=30;
        this.velocidad=7;
        this.activa=true;
        //este if lo pongo en el constructor para evitar que cambie la direccion de la bola de fuego al lanzarse
        if(jugador.getMiraDerecha()) {
            this.direccion=1;
        }
        else {
            this.direccion=-1;
        }
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
    }


    public void mover(Jugador jugador) {
        this.x+=velocidad*this.direccion;
    }

    //colisiones
    //islas
    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + velocidad) {
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

            if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }

    //tortugas
    public boolean colisionaDerechaTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeDerechoBolaFuego = this.x + (this.ancho / 2);
            double bordeIzquierdoTortuga = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeDerechoBolaFuego >= bordeIzquierdoTortuga && bordeDerechoBolaFuego <= bordeIzquierdoTortuga + velocidad) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeIzquierdoTortuga - (this.ancho / 2); //esto me parece que lo voy a cambiar asi la colision se ve mas real
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaIzqTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeIzqBolaFuego = this.x + (this.ancho / 2);
            double bordeDerechoTortuga = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeIzqBolaFuego >= bordeDerechoTortuga && bordeIzqBolaFuego <= bordeDerechoTortuga + velocidad) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeDerechoTortuga - (this.ancho / 2); //esto me parece que lo voy a cambiar asi la colision se ve mas real
                    return true;
                }
            }
        }
        return false;
    }

    //colision con ventana
    public boolean hayColisionIzq() {
        return this.x - this.ancho / 2 <= 0; //le resto el ancho dividido dos porque sino se pasa de la ventana ya que X es el medio del rectangulo
    }

    public boolean hayColisionDer(Entorno entorno) {
        return this.x + this.ancho / 2 >= entorno.ancho(); //aca lo mismo pero a la inversa le falta medio rectangulo para llegar a colisionar
    }

}
