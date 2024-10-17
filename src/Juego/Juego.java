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

    private Tortuga[]tortugas;



    // Variables y métodos propios de cada grupo
    // ...


    Juego() //este metodo es solo un constructor
    {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno);
        this.islas = new Isla[15];
        this.duende = new Duende();
        crearIslas();
        this.entorno.iniciar();

        this.tortugas= new Tortuga[5];
        crearTortugas();

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
    private void crearTortugas(){

        int indice=0;
        for (int i = 0; i < 5; i++) {
            double x = 50;
            double y = 50;
            tortugas[indice] = new Tortuga(50 * i , 50 * i );
            indice++;
        }
    }



    public void tick()
    {

        jugador.dibujar(entorno);
        jugador.aplicarGravedad(islas);

        for(Tortuga tortuga : tortugas){
            tortuga.dibujar(entorno);
        }

        duende.dibujar(entorno);
        duende.aplicarGravedad(islas);

        for(Isla isla : islas) {
            isla.dibujar(entorno);
        }
        //control movimiento con teclas
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            if(!jugador.colisionaDerecha(islas)) {
                jugador.moverDerecha(entorno,islas);
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            if(!jugador.colisionaIzquierda(islas)) {
                jugador.moverIzquierda(islas);
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            if(!jugador.colisionaArriba(islas)) {
                jugador.saltar();
            }
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            if(!jugador.colisionaAbajo(islas)) {
                jugador.moverAbajo(islas);
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