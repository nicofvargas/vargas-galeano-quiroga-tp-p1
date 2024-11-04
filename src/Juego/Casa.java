package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class Casa {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private String rutaImagen="Images/Casa/casa.png";
    private Image imagenCasa;

    public Casa(Isla[] islas) {
        this.x=islas[0].getX();
        this.y=islas[0].getY()-10-islas[0].getAlto()-this.alto;
        this.ancho=100;
        this.alto=100;
        this.imagenCasa= Herramientas.cargarImagen(rutaImagen).getScaledInstance((int) this.ancho,(int) this.alto, Image.SCALE_SMOOTH);
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(imagenCasa,this.x,this.y,0);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
}
