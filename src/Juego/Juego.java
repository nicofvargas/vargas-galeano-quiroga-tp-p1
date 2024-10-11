package Juego;



import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Jugador jugador;
    private Gravedad gravedad;
    private Isla[] islas;

    // Variables y métodos propios de cada grupo
    // ...


    Juego()
    {
        // Inicializa el objeto entorno
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno,gravedad);
        this.gravedad = new Gravedad();
        // Inicializar lo que haga falta para el juego
        // Isla
        double anchoIsla = 150;
        double altoIsla = 50;
        double separacion = 25;

        // Definimos la posición de las islas en el eje Y
        double[] posicionesY = {500,400,300,200,100};

        // Creamos las islas
        this.islas = new Isla[15]; // Inicializar la variable islas
        // ...
        int indice = 0;
        for (int i = 0; i < posicionesY.length; i++) {
            for (int j = 0; j < 5 - i; j++) {
                double x = ((200 + (anchoIsla*i)) / 2 + (j * (anchoIsla + separacion)));

                double y = posicionesY[i];
                this.islas[indice] = new Isla(x, y, anchoIsla, altoIsla);

                indice++;
            }

        }
        this.islas[14] = new Isla((800 - anchoIsla)/ 2, 50, anchoIsla, altoIsla);
        // ...




        // Inicia el juego!
        this.entorno.iniciar();
    }

    /**
     * Durante el juego, el método tick() será ejecutado en cada instante y
     * por lo tanto es el método más importante de esta clase. Aquí se debe
     * actualizar el estado interno del juego para simular el paso del tiempo
     * (ver el enunciado del TP para mayor detalle).
     */
    public void tick()
    {
        for (Isla isla : islas) {
            isla.dibujar(entorno);
        }
        jugador.dibujar(entorno);

        //control movimiento con teclas
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            jugador.moverDerecha(entorno);
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            jugador.moverIzquierda();
        }
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            jugador.saltar();
        }
    }


    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}
