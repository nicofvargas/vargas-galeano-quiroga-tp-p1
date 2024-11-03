package Juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
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
    private boolean[] posicionesXUsadas = new boolean[posicionesX.length];
    private Isla[] islasDelSpawn = new Isla[posicionesX.length];
    private Random random = new Random();
    private int condicionVictoria=10;
    private int condicionDerrota=10;
    private boolean gano;
    private boolean perdio;
    private enum EstadoJuego { INICIO, EN_JUEGO, FINALIZADO }
    private EstadoJuego estadoJuego = EstadoJuego.INICIO;
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
        this.tortugas = new Tortuga[3];
        islasDelSpawn[0]=islas[10];
        islasDelSpawn[1]=islas[7];
        islasDelSpawn[2]=islas[3];
        islasDelSpawn[3]=islas[5];
        islasDelSpawn[4]=islas[9];
        islasDelSpawn[5]=islas[14];
        this.casa = new Casa(islas);
        this.duende = new Duende(casa);
        this.duendes = new ArrayList<>();
        Duende.crearDuendesConDelay(duendes, maximoDuendes, casa);
        this.ultimo= ui.getCronometro();
        this.gano=false;
        this.perdio=false;
        estadoJuego = EstadoJuego.INICIO;

    }
    //aca obtengo una posicion aleatoria de X para tortugas
    public double getPosicionAleatoria() {
        verificarIslaDisponible();
        double[] posicionesDisponibles = posicionesDisponibles(posicionesXUsadas,posicionesX);
        return posicionesDisponibles[random.nextInt(posicionesDisponibles.length)];
    }
    //aca asigno a cada posicion del array el objeto isla para que correspondan por orden
    public double[] posicionesDisponibles(boolean[] posicionesXUsadas, double[] posicionesX) {
        //aca obtengo la cantidad de indices que va a tener el nuevo array
        int contador=0;
        for (int i=0; i<posicionesXUsadas.length;i++) {
            if(posicionesXUsadas[i]==false) {
                contador++;
            }
        }
        //aca asigno el tamaño del nuevo array y cargo los valores de X que pueden ser usados
        double[] nuevoArray = new double[contador];
        int indiceArray=0;
        for (int i=0; i<posicionesXUsadas.length; i++) {
            if(!posicionesXUsadas[i]) {
                nuevoArray[indiceArray]=posicionesX[i];
                indiceArray++;
            }
        }
        return nuevoArray;
    }
    //aca compruebo que la posicion no esta siendo usada

    private void verificarIslaDisponible() {
        for (int i=0; i<islasDelSpawn.length;i++) {
            if (islasDelSpawn[i].hayTortuga()) {
                posicionesXUsadas[i]=true;
            }
            else {
                posicionesXUsadas[i]=false;
            }
        }
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

    public void tick() {
        if (estadoJuego == EstadoJuego.INICIO) {
            menu.dibujar();
            if (menu.botonIniciarPresionado()) {
                estadoJuego = EstadoJuego.EN_JUEGO;
                gano = false;
                perdio = false;
                ui.iniciarCronometro(entorno);
            } else if (menu.botonSalirPresionado()) {
                System.exit(0);
            }
        }
        else if (estadoJuego == EstadoJuego.EN_JUEGO) {

            if (jugador != null && (jugador.colisionaAbajoTortu(tortugas) || jugador.colisionaArribaTortu(tortugas) ||
                    jugador.colisionaDerechaTortu(tortugas) || jugador.colisionaIzquierdaTortu(tortugas) ||
                    jugador.hayColisionVentanaAbajo(entorno))) {
                jugador = null;
            }

            if (jugador != null) {
                ui.actualizarCronometro(entorno);
                ui.dibujarFondo(entorno);
                jugador.dibujar(entorno);
                jugador.aplicarGravedad(islas);
                casa.dibujar(entorno);
                ui.dibujarCronometro(entorno);
                ui.dibujarDuendesSalvados(entorno);
                ui.dibujarDuendesMuertos(entorno);
                ui.dibujarEnemigosEliminados(entorno);

                // Dibuja y maneja las islas y tortugas
                for (Isla isla : islas) {
                    if (isla != null) {
                        isla.dibujar(entorno);
                        isla.hayTortuga(tortugas);
                    }
                }

                int tiempoactual = ui.getCronometro();
                int intervalo = 2;
                for (int i = 0; i < tortugas.length; i++) {
                    if (tortugas[i] == null && tiempoactual - ultimo >= intervalo) {
                        double posRandom = getPosicionAleatoria();
                        tortugas[i] = new Tortuga(posRandom);
                        ultimo = tiempoactual;
                    }
                    if (tortugas[i] != null) {
                        tortugas[i].aplicarGravedad(islas);
                        tortugas[i].actualizarPosicion(islas);
                        tortugas[i].dibujar(entorno);

                        if (bolaFuego != null && (tortugas[i].colisionaAbajoBolaFuego(bolaFuego) ||
                                tortugas[i].colisionaIzquierdaBolaFuego(bolaFuego) ||
                                tortugas[i].colisionaDerechaBolaFuego(bolaFuego))) {
                            tortugas[i] = null;
                            bolaFuego = null;
                            ui.setEnemigosEliminado();
                        }
                    }
                }


                for (int i = 0; i < duendes.size(); i++) {
                    Duende duende = duendes.get(i);
                    if (duende == null) {
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

                    if (duende.colisionaIzquierdaTortu(tortugas) || duende.colisionaDerechaTortu(tortugas) ||
                            duende.colisionaAbajoTortu(tortugas) || duende.colisionaArribaTortu(tortugas)) {
                        duendes.set(i, null);
                        ui.setDuendesMuerto();
                    }
                    if ((duende.colisionaAbajoJugador(jugador) || duende.colisionaArribaJugador(jugador) ||
                            duende.colisionaDerechaJugador(jugador) || duende.colisionaIzquierdaJugador(jugador)) &&
                            jugador.getY() > islas[5].getY()) {
                        duendes.set(i, null);
                        ui.setDuendesSalvado();
                    }
                }

                // Control de movimiento con teclas
                if (entorno.estaPresionada(entorno.TECLA_DERECHA) && !jugador.colisionaDerecha(islas)) {
                    jugador.moverDerecha(entorno, islas);
                }
                if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !jugador.colisionaIzquierda(islas)) {
                    jugador.moverIzquierda(entorno, islas);
                }
                if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && !jugador.colisionaArriba(islas)) {
                    jugador.saltar(islas);
                }
                if (entorno.estaPresionada('c') && bolaFuego == null) {
                    bolaFuego = new bolaFuego(jugador);
                }

                // Comprobación de colisiones de bola de fuego
                if (bolaFuego != null) {
                    bolaFuego.mover();
                    bolaFuego.dibujar(entorno);
                    if (bolaFuego.colisionaDerecha(islas) || bolaFuego.colisionaIzquierda(islas) ||
                            bolaFuego.hayColisionDer(entorno) || bolaFuego.hayColisionIzq()) {
                        bolaFuego = null;
                    }
                }

                // Verificación de victoria o derrota
                if (condicionVictoria == ui.getDuendesSalvados()) {
                    gano = true;
                    jugador = null;
                }
                if (condicionDerrota == ui.getDuendesMuertos()) {
                    perdio = true;
                    jugador=null;
                }
            }

            // Cambia a FINALIZADO si el jugador gana o pierde
            if (jugador == null) {
                estadoJuego = EstadoJuego.FINALIZADO;
            }
        }
        else if (estadoJuego == EstadoJuego.FINALIZADO) {
            if (gano) {
                menu.dibujarVictoria();
            } else {
                menu.dibujarDerrota();
            }
            if (menu.botonIniciarPresionado()) {
                reiniciarJuego();
            } else if (menu.botonSalirPresionado()) {
                System.exit(0);
            }
        }
    }

    private void reiniciarJuego() {
        estadoJuego = EstadoJuego.EN_JUEGO;
        gano = false;
        perdio = false;
        jugador = new Jugador(entorno);
        tortugas = new Tortuga[3];
        duendes.clear();
        Duende.crearDuendesConDelay(duendes, maximoDuendes, casa);
        ui.iniciarCronometro(entorno);
        ultimo=0;
        ui.reiniciarContadores();
    }



    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Juego juego = new Juego();
    }
}