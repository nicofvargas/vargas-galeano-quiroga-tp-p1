package Juego;



import entorno.Entorno;
import entorno.InterfaceJuego;

import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;



public class Juego extends InterfaceJuego {
    // El objeto Entorno que controla el tiempo y otros
    private Entorno entorno;
    private Menu menu;
    private Jugador jugador;
    private Isla[] islas;
    private Duende duende;
    private List<Duende> duendes;
    private Tortuga tortuga;
    private Tortuga[]tortugas;
    private bolaFuego bolaFuego;
    private Hud ui;
    private Casa casa;
    private int maximoDuendes=4;
    private int ultimo;
    private double[] posicionesX={35,85,165,650,715,765};
    private Random random = new Random();
    private boolean juegoiniciado=false;
    // Variables y métodos propios de cada grupo
    // ...

    Juego() //este es solo un constructor
    {
        this.entorno = new Entorno(this, "Al rescate de los Gnomos", 800, 600);
        this.menu = new Menu(entorno);
        this.entorno.iniciar();
        this.jugador = new Jugador(entorno);
        this.ui = new Hud();
        this.bolaFuego=null;
        islas=crearIslas(entorno);
        this.casa = new Casa(islas);
        this.duende = new Duende(casa);
        this.duendes = new ArrayList<>();
        Duende.crearDuendesConDelay(duendes, maximoDuendes, casa);
        this.entorno.iniciar();
        this.ultimo= ui.getCronometro();
        this.tortugas = new Tortuga[3];




    }
    //aca obtengo una posicion aleatoria de X para tortugas
    public double getPosicionAleatoria() {
        return posicionesX[random.nextInt(posicionesX.length)];
    }
    //aca compruebo que la posicion no esta siendo usada
    public boolean estaSiendoUsada(Tortuga[] tortugas, double posRandom) {
        for (Tortuga tortuga : tortugas) {
            if(tortuga==null) {
                continue;
            }
            if(tortuga!=null && posRandom==tortuga.getX()) {
                return true;
            }
        }
        return false;
    }

    // a ver
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

    public void tick() {
        if (!juegoiniciado) {
            menu.dibujar();
            if (menu.botonIniciarPresionado()) {
                juegoiniciado = true;

            } else if (menu.botonSalirPresionado()) {
                System.exit(0);
            }
        } else{

        if (jugador!=null && (jugador.colisionaAbajoTortu(tortugas) || jugador.colisionaArribaTortu(tortugas) || jugador.colisionaDerechaTortu(tortugas) || jugador.colisionaIzquierdaTortu(tortugas))) {
            jugador=null;
        }

        if(jugador!=null) {
            ui.dibujarFondo(entorno);
            jugador.dibujar(entorno);
            jugador.aplicarGravedad(islas);
            casa.dibujar(entorno);
            ui.setCronometro(entorno);
            ui.dibujarCronometro(entorno);
            ui.dibujarDuendesSalvados(entorno);
            ui.dibujarDuendesMuertos(entorno);
            ui.dibujarEnemigosEliminados(entorno);

            for(Isla isla: islas) {
                if(isla!=null) {
                    isla.dibujar(entorno);
                }
            }
            //pruebas lean e interpreten el comportamiento esto mismo puede usarse para tortuga y duende, obvio que con tiempos distintos y variables distintas
            int tiempoactual = ui.getCronometro();
            int intervalo=2;



            for (int i=0; i <tortugas.length; i++) {
                if(tortugas[i]==null) {
                    if(tiempoactual - ultimo >= intervalo) {
                        double posRandom= getPosicionAleatoria();
                        if(!estaSiendoUsada(tortugas,posRandom)) {
                            tortugas[i]= new Tortuga(posRandom);
                            ultimo=tiempoactual;
                        }
                    }
                }
                if(tortugas[i]!=null) {
                    tortugas[i].actualizarPosicion(islas);
                    tortugas[i].dibujar(entorno);
                    if(bolaFuego!=null) {
                        if(tortugas[i].colisionaAbajoBolaFuego(bolaFuego) || tortugas[i].colisionaIzquierdaBolaFuego(bolaFuego) || tortugas[i].colisionaDerechaBolaFuego(bolaFuego)) {
                            tortugas[i]=null;
                            bolaFuego=null;
                        }
                    }
                }

            }

            for (int i = 0; i<duendes.size();i++) {
                Duende duende = duendes.get(i);
                if (duende==null) {
                    duendes.set(i, new Duende(casa));
                    continue;
                }
                if (duende.getX() < 0 || duende.getX() > entorno.ancho() || duende.getY() < 0 || duende.getY() > entorno.alto()) {
                    duendes.set(i, null);
                    ui.setDuendesMuerto();
                    continue;
                }
                duende.dibujar(entorno);
                duende.aplicarGravedad(islas);
                duende.patronDeMovimiento(islas);
                duende.duendeEnElAire(islas);
                //verifico colision con tortugas
                if (duende.colisionaIzquierdaTortu(tortugas) || duende.colisionaDerechaTortu(tortugas) || duende.colisionaAbajoTortu(tortugas) || duende.colisionaArribaTortu(tortugas)) {
                    duendes.set(i,null);
                    ui.setDuendesMuerto();
                }
                //colision con jugador
                if(duende.colisionaAbajoJugador(jugador) || duende.colisionaArribaJugador(jugador) || duende.colisionaDerechaJugador(jugador) || duende.colisionaIzquierdaJugador(jugador)) {
                    duendes.set(i, null);
                    ui.setDuendesSalvado();
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

            //aca compruebo el estado de bola defuego
            if(bolaFuego!=null) {
                bolaFuego.mover(jugador);
                bolaFuego.dibujar(entorno);
                //aca compruebo si colisiona con alguna isla
                if(bolaFuego.colisionaDerecha(islas) || bolaFuego.colisionaIzquierda(islas)) {
                    bolaFuego = null;
                }
                else if (bolaFuego.hayColisionDer(entorno) || bolaFuego.hayColisionIzq()) {
                    bolaFuego = null;
                }
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