package Juego;

import entorno.Entorno;

import java.awt.*;

public class Jugador {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private boolean enElPiso;
    private boolean hayColision;
    private double distanciaSalto;
    private Gravedad gravedad;

    public Jugador(Entorno entorno, Gravedad gravedad) {
        this.x = 100;
        this.y = 50;
        this.ancho = 50;
        this.alto = 50;
        this.velocidad = 2;
        this.enElPiso = true;
        this.hayColision = false;
        this.distanciaSalto = 10;
        this.gravedad = gravedad;
    }

    //metodo para dibujar
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
    }

    //metodos para mover
    public void moverDerecha(Entorno entorno, Isla[] islas) {
        if (!hayColisionDer(entorno)) {
            this.x += velocidad;
        }
    }
    public void moverIzquierda() {
        if(!hayColisionIzq()) {
            this.x-=velocidad;
        }
    }
    public void moverArriba() {
        this.y-=velocidad;
    }
    public void moverAbajo() {
        this.y+=velocidad;
    }

    //colision con bordes de ventana
    public boolean hayColisionIzq() {
        return this.x - this.ancho / 2 <= 0; //le resto el ancho dividido dos porque sino se pasa de la ventana ya que X es el medio del rectangulo
    }

    public boolean hayColisionDer(Entorno entorno) {
        return this.x + this.ancho / 2 >= entorno.ancho(); //aca lo mismo pero a la inversa le falta medio rectangulo para llegar a colisionar
    }

    //colision con objetos

    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if ((this.x + this.ancho / 2 >= isla.getX() - isla.getAncho() / 2) &&
                    (this.x < isla.getX()) &&
                    (this.y + this.alto / 2 > isla.getY() - isla.getAlto() / 2) &&
                    (this.y - this.alto / 2 < isla.getY() + isla.getAlto() / 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean colisionaIzquierda(Isla[] islas) {
        for (Isla isla : islas) {
            if ((this.x - this.ancho / 2 <= isla.getX() + isla.getAncho() / 2) &&
                    (this.x > isla.getX()) &&
                    (this.y + this.alto / 2 > isla.getY() - isla.getAlto() / 2) &&
                    (this.y - this.alto / 2 < isla.getY() + isla.getAlto() / 2)) {
                return true;
            }
        }
        return false;
    }

    public boolean colisionaArriba(Isla[] islas) {
        for (Isla isla : islas) {
            if ((this.y - this.alto / 2 <= isla.getY() + isla.getAlto() / 2) &&
                    (this.y > isla.getY()) &&
                    (this.x + this.ancho / 2 > isla.getX() - isla.getAncho() / 2) &&
                    (this.x - this.ancho / 2 < isla.getX() + isla.getAncho() / 2)) {
                return true;
            }
        }
        return false;
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


}
