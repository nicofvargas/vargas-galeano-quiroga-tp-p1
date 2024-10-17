package Juego;



import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Jugador jugador;
    private Isla[] islas;
    private Duende duende;
    private Tortuga tortuga;
    private Duende[] duendes;

    private Tortuga[] tortugas;


    // Variables y métodos propios de cada grupo
    // ...


    Juego() //este metodo es solo un constructor
    {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno);
        this.islas = new Isla[15];
        this.duende = new Duende(50, 50, 150);
        this.duendes = new Duende [4];
        crearIslas();
        this.entorno.iniciar();
        this.tortuga = new Tortuga(50, 50, 100);
        this.tortugas = new Tortuga[5];
        crearTortugas();
        crearDuende();
    }
    private void crearDuende() {
        double anchoDuende = 100;
        double altoDuende = 100;
        double xPosicion = 100;
        for (int i = 0; i < duendes.length; i++) {
            duendes[i] = new Duende(anchoDuende, altoDuende, xPosicion + (i * 50));
        }
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

    private void crearTortugas() {
        double ancho = 100;
        double alto = 100;
        double x = 50;

        for (int i = 0; i < 5; i++) {
            this.tortuga = new Tortuga(ancho, alto, x * 2 * i);
            System.out.println("entra");
        }


    }

        public void tick ()
        {

            jugador.dibujar(entorno);
            jugador.aplicarGravedad(islas);
            tortuga.dibujar(entorno);
            duende.dibujar(entorno);

            for (Isla isla : islas) {
                isla.dibujar(entorno);
            }
            //control movimiento con teclas
            if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
                if (!jugador.colisionaDerecha(islas)) {
                    jugador.moverDerecha(entorno, islas);
                }
            }
            if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
                if (!jugador.colisionaIzquierda(islas)) {
                    jugador.moverIzquierda(islas);
                }
            }
            if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
                if (!jugador.colisionaArriba(islas)) {
                    jugador.saltar();
                }
            }
            if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
                if (!jugador.colisionaAbajo(islas)) {
                    jugador.moverAbajo(islas);
                }
            }

            //duende.getX();
        }


        @SuppressWarnings("unused")
        public static void main (String[]args){

            Juego juego = new Juego();
        }
    }
