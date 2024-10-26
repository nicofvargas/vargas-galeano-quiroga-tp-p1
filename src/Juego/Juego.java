package Juego;



import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Jugador jugador;
    private Isla[] islas;
    private Duende duende;
    private List<Duende> duendes;
    private Tortuga tortuga;
    private Tortuga[]tortugas;
    private bolaFuego bolaFuego;
    private Hud ui;
    private int maximoDuendes=4;

    // Variables y métodos propios de cada grupo
    // ...

    Juego() //este es solo un constructor
    {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno);
        islas=crearIslas(entorno);
        this.duende = new Duende();
        this.duendes = new ArrayList<>();
        Duende.crearDuendesConDelay(duendes, maximoDuendes);
        this.entorno.iniciar();
        this.tortugas = new Tortuga[5];
        crearTortugas();
        this.bolaFuego=null;
        this.ui = new Hud();
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
                x = random.nextDouble() * (350);
            } else {
                x = 450 + random.nextDouble() * (750 - 450);
            }
            double y =30;
            tortugas[i] = new Tortuga(x+10, y+10);
        }
    }
    public void verificarTortugasMuertas() {
        for (int i = 0; i < tortugas.length; i++) {
            // Verifica que tortugas[i] no sea null
            if (tortugas[i] != null && !tortugas[i].estaViva()) {
                tortugas[i] = crearNuevaTortuga(); // Reemplaza la tortuga muerta
            }
        }
    }
    private Tortuga crearNuevaTortuga() {
        Random random = new Random();
        double x;
        if (random.nextBoolean()) {
            x = random.nextDouble() * (350 - 50);
        } else {
            x = 450 + random.nextDouble() * (750 - 450);
        }
        double y = 50;
        return new Tortuga(x, y);

    }


    public void tick() {
        //aca controla si colisiona con una tortuga
        if (jugador!=null && (jugador.colisionaAbajoTortu(tortugas) || jugador.colisionaArribaTortu(tortugas) || jugador.colisionaDerechaTortu(tortugas) || jugador.colisionaIzquierdaTortu(tortugas))) {
            jugador=null;
        }

        if(jugador!=null) {
            jugador.dibujar(entorno);
            jugador.aplicarGravedad(islas);
            ui.dibujarCronometro(entorno);
            ui.dibujarDuendesSalvados(entorno);
            ui.dibujarDuendesMuertos(entorno);
            ui.dibujarEnemigosEliminados(entorno);
            //verificarTortugasMuertas(); esto lo comento por ahora no hace nada

            for (Tortuga tortuga : tortugas) {
                if (tortuga != null) {
                    tortuga.actualizarPosicion(islas);
                    tortuga.dibujar(entorno);

                }

            }

            for (Duende duende : duendes) {
                if(duende==null){
                    continue;
                }
                if(duende!=null){
                    duende.dibujar(entorno);
                    duende.aplicarGravedad(islas);
                    duende.patronDeMovimiento(islas);
                    duende.duendeEnElAire(islas);
                }

            }

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
            if (entorno.estaPresionada(entorno.TECLA_ESPACIO) && bolaFuego==null) {
                bolaFuego = new bolaFuego(jugador);
            }

            //aca compruebo el estado de bola de fuego
            if(bolaFuego!=null) {
                bolaFuego.mover(jugador);
                bolaFuego.dibujar(entorno);
                //aca compruebo si colisiona con alguna isla
                if(bolaFuego.colisionaDerecha(islas) || bolaFuego.colisionaIzquierda(islas)) {
                    bolaFuego = null;
                }
                //aca compruebo si colisiona con alguna tortuga
                else if (bolaFuego.colisionaDerechaTortu(tortugas) || bolaFuego.colisionaIzqTortu(tortugas)) {
                    bolaFuego = null;
                }
                else if (bolaFuego.hayColisionDer(entorno) || bolaFuego.hayColisionIzq()) {
                    bolaFuego = null;
                }
            }
        }


    }


    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}