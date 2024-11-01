package Juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.*;

public class Menu {
    private Entorno entorno;
    private int botonIniciarX, botonIniciarY, botonSalirX, botonSalirY;
    private int anchoBoton = 200;
    private int altoBoton = 50;
    private String rutaFondo ="images/Menu/fondo.png";
    private Image fondo;
    private int ancho, alto;

    public Menu(Entorno entorno) {

        Herramientas.loop("Musica/Menu/musicaMenu.wav");
        this.entorno = entorno;
        this.ancho = entorno.ancho();
        this.alto = entorno.alto();
        botonIniciarX = entorno.ancho() / 2;
        botonIniciarY = entorno.alto() / 2 - 50;
        botonSalirX = entorno.ancho() / 2;
        botonSalirY = entorno.alto() / 2 + 50;
        this.fondo = Herramientas.cargarImagen(rutaFondo).getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);


    }

    public void dibujar() {

        entorno.dibujarImagen(fondo, ancho / 2, alto / 2, 0);

        entorno.cambiarFont("Engravers MT", 50, Color.BLACK);
        entorno.escribirTexto("Al rescate de los Gnomos", entorno.ancho() / 2 -260 , entorno.alto() / 4);

        entorno.dibujarRectangulo(botonIniciarX, botonIniciarY, anchoBoton, altoBoton, 0, Color.GREEN);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Iniciar Partida", botonIniciarX - 60, botonIniciarY + 5);

        entorno.dibujarRectangulo(botonSalirX, botonSalirY, anchoBoton, altoBoton, 0,Color.red);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Salir de la Partida", botonSalirX - 75, botonSalirY + 5);
    }
    public void dibujarVictoria() {

        entorno.dibujarImagen(fondo, ancho / 2, alto / 2, 0);

        entorno.cambiarFont("Engravers MT", 50, Color.BLUE);
        entorno.escribirTexto("HAS GANADO", entorno.ancho() / 2-200 , entorno.alto() / 4);

        entorno.dibujarRectangulo(botonIniciarX, botonIniciarY, anchoBoton, altoBoton, 0, Color.GREEN);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Iniciar Partida", botonIniciarX - 60, botonIniciarY + 5);

        entorno.dibujarRectangulo(botonSalirX, botonSalirY, anchoBoton, altoBoton, 0,Color.red);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Salir de la Partida", botonSalirX - 75, botonSalirY + 5);
    }
    public void dibujarDerrota() {

        entorno.dibujarImagen(fondo, ancho / 2, alto / 2, 0);

        entorno.cambiarFont("Engravers MT", 50, Color.RED);
        entorno.escribirTexto("HAS PERDIDO", entorno.ancho() / 2 , entorno.alto() / 4);

        entorno.dibujarRectangulo(botonIniciarX, botonIniciarY, anchoBoton, altoBoton, 0, Color.GREEN);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Iniciar Partida", botonIniciarX - 60, botonIniciarY + 5);

        entorno.dibujarRectangulo(botonSalirX, botonSalirY, anchoBoton, altoBoton, 0,Color.red);
        entorno.cambiarFont("Engravers MT", 20, Color.WHITE);
        entorno.escribirTexto("Salir de la Partida", botonSalirX - 75, botonSalirY + 5);
    }

    public boolean botonIniciarPresionado() {
        return entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) &&
                entorno.mouseX() >= botonIniciarX - anchoBoton / 2 &&
                entorno.mouseX() <= botonIniciarX + anchoBoton / 2 &&
                entorno.mouseY() >= botonIniciarY - altoBoton / 2 &&
                entorno.mouseY() <= botonIniciarY + altoBoton / 2;
    }

    public boolean botonSalirPresionado() {
        return entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) &&
                entorno.mouseX() >= botonSalirX - anchoBoton / 2 &&
                entorno.mouseX() <= botonSalirX + anchoBoton / 2 &&
                entorno.mouseY() >= botonSalirY - altoBoton / 2 &&
                entorno.mouseY() <= botonSalirY + altoBoton / 2;
    }

}



