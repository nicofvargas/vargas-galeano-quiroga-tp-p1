package Juego;



import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

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
        islas=crearIslas(entorno);
        this.duende = new Duende();
        this.entorno.iniciar();
        this.tortugas = new Tortuga[5];
        crearTortugas();
    }

    public static Isla[] crearIslas(Entorno entorno) {
        int pisos=5;
        Isla[] islas=new Isla[pisos*(pisos+1)/2];
        int y=0;
        int x=0;
        int separacion=75;
        int indice=0;
        for(int i=1 ;i<=pisos; i++) {
            x=x-30;
            y=y+100;
            int expansion=-40*i;
            for(int j=1 ; j<=i; j++) {
                x=(entorno.ancho()-expansion)/(i+1)*j+expansion/2;
                islas[indice]= new Isla(x,y,100,30);
                indice++;
            }
        }
        return islas;
    }
    private void crearTortugas() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            double x;
            if (random.nextBoolean()) {
                x = random.nextDouble() * (350-50);
            } else {
                x = 450 + random.nextDouble() * (750 - 450);
            }
            double y = 50;
            tortugas[i] = new Tortuga(x, y);
        }
    }


    public void tick()
    {
        jugador.dibujar(entorno);

        for (Tortuga tortuga : tortugas) {
            if (tortuga != null) {
                tortuga.actualizarPosicion(islas);
                tortuga.dibujar(entorno);
            }
        }

        duende.dibujar(entorno);
        duende.aplicarGravedad(islas);
        duende.patronDeMovimiento();



        for(Isla isla: islas) {
            if(isla!=null) {
                isla.dibujar(entorno);
            }
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
                jugador.saltar(islas);
            }

        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            if(!jugador.colisionaAbajo(islas)) {
                jugador.moverAbajo(islas);
            }
        }
        jugador.aplicarGravedad(islas);

    }


    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}