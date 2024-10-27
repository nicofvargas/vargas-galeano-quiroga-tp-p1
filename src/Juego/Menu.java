package Juego;

import entorno.Entorno;
import java.awt.Color;

public class Menu {
    private Entorno entorno;
    private int botonIniciarX, botonIniciarY, botonSalirX, botonSalirY;
    private int anchoBoton = 200;
    private int altoBoton = 50;

    public Menu(Entorno entorno) {
        this.entorno = entorno;
        botonIniciarX = entorno.ancho() / 2;
        botonIniciarY = entorno.alto() / 2 - 50;
        botonSalirX = entorno.ancho() / 2;
        botonSalirY = entorno.alto() / 2 + 50;
    }

    public void dibujar() {

        entorno.cambiarFont("Arial", 36, Color.BLUE);
        entorno.escribirTexto("Al rescate de los Gnomos", entorno.ancho() / 2 - 200, entorno.alto() / 4);

        entorno.dibujarRectangulo(botonIniciarX, botonIniciarY, anchoBoton, altoBoton, 0, Color.GREEN);
        entorno.cambiarFont("Arial", 20, Color.WHITE);
        entorno.escribirTexto("Iniciar Partida", botonIniciarX - 60, botonIniciarY + 5);

        entorno.dibujarRectangulo(botonSalirX, botonSalirY, anchoBoton, altoBoton, 0, Color.RED);
        entorno.cambiarFont("Arial", 20, Color.WHITE);
        entorno.escribirTexto("Salir de la Partida", botonSalirX - 70, botonSalirY + 5);
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

