package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class bolaFuego {
    //atributos
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion;
    private String rutaCaminaDerecha="Images/BolaFuego/bolafuegoderecha.png";
    private String rutaCaminaIzq="Images/BolaFuego/bolafuegoizq.png";
    private Image caminaDerecha;
    private Image caminaIzq;

    //constructor
    public bolaFuego(Jugador jugador) {
        this.x=jugador.getX();
        this.y=jugador.getY();
        this.ancho=30;
        this.alto=30;
        this.velocidad=7;
        this.caminaDerecha= Herramientas.cargarImagen(rutaCaminaDerecha).getScaledInstance((int)this.ancho,(int)this.alto,Image.SCALE_SMOOTH);
        this.caminaIzq= Herramientas.cargarImagen(rutaCaminaIzq).getScaledInstance((int)this.ancho,(int)this.alto,Image.SCALE_SMOOTH);

        //este if lo pongo en el constructor para evitar que cambie la direccion de la bola de fuego al lanzarse
        if(jugador.getMiraDerecha()) {
            this.direccion=1;
        }
        else {
            this.direccion=-1;
        }
    }

    public void dibujar(Entorno entorno) {
        if (direccion==1) {
            entorno.dibujarImagen(caminaDerecha,this.x,this.y,0);
        }
        else{
            entorno.dibujarImagen(caminaIzq, this.x, this.y, 0);
        }
    }

    public void mover() {
        this.x+=velocidad*this.direccion;
    }

    //colisione islas
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

            if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - 10) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
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