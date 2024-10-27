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
    private Casa casa;
    private int maximoDuendes=4;
    private int ultimo;

    // Variables y métodos propios de cada grupo
    // ...

    Juego() //este es solo un constructor
    {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.jugador = new Jugador(entorno);
        islas=crearIslas(entorno);
        this.casa = new Casa(islas);
        this.duende = new Duende(casa);
        this.duendes = new ArrayList<>();
        Duende.crearDuendesConDelay(duendes, maximoDuendes, casa);
        this.entorno.iniciar();
        this.tortugas = new Tortuga[5];
        crearTortugas();
        this.bolaFuego=null;
        this.ui = new Hud();
        this.ultimo= ui.getCronometro();
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
            casa.dibujar(entorno);
            ui.setCronometro(entorno);
            ui.dibujarCronometro(entorno);
            ui.dibujarDuendesSalvados(entorno);
            ui.dibujarDuendesMuertos(entorno);
            ui.dibujarEnemigosEliminados(entorno);
            //pruebas lean e interpreten el comportamiento esto mismo puede usarse para tortuga y duende, obvio que con tiempos distintos y variables distintas
            int tiempoactual = ui.getCronometro();
            int intervalo=2;
            if(tiempoactual - ultimo >= intervalo) { //estemetodo no se va a usar lo puse solo para probar el temporizador
                bolaFuego = new bolaFuego(jugador);
                ultimo=tiempoactual;
            }
            for (Tortuga tortuga : tortugas) {
                if (tortuga != null) {
                    tortuga.actualizarPosicion(islas);
                    tortuga.dibujar(entorno);

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
                else if (bolaFuego != null) {
                    for (int i = 0; i < tortugas.length; i++) {
                        if (tortugas[i] != null && tortugas[i].colisionaConBolaFuego(bolaFuego)){
                            tortugas[i] = null;
                            bolaFuego = null;
                            ui.setEnemigosEliminado();
                            verificarTortugasMuertas();
                            break;
                        }
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