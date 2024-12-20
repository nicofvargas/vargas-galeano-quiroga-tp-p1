package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class Isla {
    private double x;
    private double y;
    private int ancho;
    private int alto;
    private boolean tortugaEnIsla;
    private String rutaIsla="Images/Isla/isla.png";
    private Image imagenIsla;
    // Constructor que establece las posiciones y dimensiones de una isla
    public Isla(int x, int y, int ancho, int alto) {
        super();
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagenIsla = Herramientas.cargarImagen(rutaIsla).getScaledInstance(this.ancho,this.alto,Image.SCALE_SMOOTH);

    }
    public boolean hayTortuga() {
        return tortugaEnIsla;
    }
    public void hayTortuga(Tortuga[] tortugas) {
        if(colisionaArribaTortu(tortugas)) {
            this.tortugaEnIsla=true;
        }
        else {
            this.tortugaEnIsla=false;
        }
    }
    //colision abajo
    public boolean colisionaArribaTortu(Tortuga[] tortugas) {
        for(Tortuga tortuga : tortugas) {
            if(tortuga==null) {
                continue;
            }
            double bordeSuperiorIsla = this.y - (this.alto / 2);
            double bordeInferiorTortu = tortuga.getY() + (tortuga.getAlto() / 2);

            if(bordeSuperiorIsla <= bordeInferiorTortu && bordeSuperiorIsla>= bordeInferiorTortu-10) {
                if(this.x+(this.ancho/2) > tortuga.getX()-(tortuga.getAncho()/2)  &&  this.x-(this.ancho/2) < tortuga.getX()+(tortuga.getAncho()/2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Método que dibuja la isla en el entorno
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenIsla,this.x,this.y,0);
    }

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

    public boolean contienePunto(double px, double py) {
        double margen = 10;
        return (px >= x - ancho / 2 - margen && px <= x + ancho / 2 + margen &&
                py >= y - alto / 2 - margen && py <= y + alto / 2 + margen);
    }
}