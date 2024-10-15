package Juego;



import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Jugador jugador;
    private Gravedad gravedad;
    private Isla[] islas;
    private Duende duende;


    // Variables y métodos propios de cada grupo
    // ...


    Juego() //este metodo es solo un constructor
    {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno,gravedad);
        this.gravedad = new Gravedad();
        this.islas = new Isla[15];
        this.duende = new Duende();
        crearIslas();
        this.entorno.iniciar();


    }

    private void crearIslas() {
        double anchoIsla = 100;
        double altoIsla = 25;
        double separacion = 75;
        double[] posicionesY = {500, 400, 300, 200, 100};

        int indice = 0;
        for (int i = 0; i < posicionesY.length; i++) {
            for (int j = 0; j < 5 - i; j++) {
                double x = ((200 + (anchoIsla * i)) / 2 + (j * (anchoIsla + separacion)));
                double y = posicionesY[i];
                this.islas[indice] = new Isla(x, y, anchoIsla, altoIsla);
                indice++;
            }
        }
        this.islas[14] = new Isla((800 - anchoIsla) / 2, 50, anchoIsla, altoIsla); // Última isla
    }


    public void tick()
    {

        jugador.dibujar(entorno);


        //control movimiento con teclas
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            if(!jugador.colisionaDerecha(islas)) {
                jugador.moverDerecha(entorno,islas);
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            if(!jugador.colisionaIzquierda(islas)) {
                jugador.moverIzquierda();
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            if(!jugador.colisionaArriba(islas)) {
                jugador.moverArriba();
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            if(!jugador.colisionaAbajo(islas)) {
                jugador.moverAbajo();
            }
        }

        //duende.getX();
    }


    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}
