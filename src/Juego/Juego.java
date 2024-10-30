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
    private boolean juegoiniciado=false;
    private int condicionVictoria=30;
    private int condicionDerrota=30;
    private boolean gano;
    private boolean perdio;
    private boolean puedeJugar=true;
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
        this.entorno.iniciar();
        this.ultimo= ui.getCronometro();
        this.gano=false;
        this.perdio=false;

    }
    //aca obtengo una posicion aleatoria de X para tortugas
    public double getPosicionAleatoria() {
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
            if(posicionesXUsadas[i]==false) {
                nuevoArray[indiceArray]=posicionesX[i];
                indiceArray++;
            }
        }
        return nuevoArray;
    }
    //aca compruebo que la posicion no esta siendo usada

    //aca verifico si posee tortuga y asigno los booleanos al array de booleanos
    private void verificarIslaDisponible() {
        for (int i=0; i<islasDelSpawn.length;i++) {
            if (islasDelSpawn[i].hayTortuga()) {
                posicionesXUsadas[i]=false;
            }
            else {
                posicionesXUsadas[i]=true;
            }
        }
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
                gano=false;
                perdio=false;
                ui.iniciarCronometro(entorno);
            }
            else if (menu.botonSalirPresionado()) {
                System.exit(0);
            }
        }
        else {

            if (jugador!=null && (jugador.colisionaAbajoTortu(tortugas) || jugador.colisionaArribaTortu(tortugas) || jugador.colisionaDerechaTortu(tortugas) || jugador.colisionaIzquierdaTortu(tortugas) || jugador.hayColisionVentanaAbajo(entorno))) {
                jugador=null;
            }

            if(jugador!=null) {
                ui.actualizarCronometro(entorno);
                ui.dibujarFondo(entorno);
                jugador.dibujar(entorno);
                jugador.aplicarGravedad(islas);
                casa.dibujar(entorno);
                ui.dibujarCronometro(entorno);
                ui.dibujarDuendesSalvados(entorno);
                ui.dibujarDuendesMuertos(entorno);
                ui.dibujarEnemigosEliminados(entorno);

                for(Isla isla: islas) {                     //Crea las islas
                    if(isla!=null) {                         //Verifica que no sea null
                        isla.dibujar(entorno);
                        //isla.colisionaArribaTortu(tortugas);// las dibuja
                    }
                }

                int tiempoactual = ui.getCronometro();
                int intervalo=2;
                for (int i=0; i <tortugas.length; i++) {                            // crea las tortugas
                    if(tortugas[i]==null) {                                         //Verifica que no sea null
                        if(tiempoactual - ultimo >= intervalo) {                     // tiempo de creacion
                            double posRandom= getPosicionAleatoria();               // Busca la posicion aleatoria
                            tortugas[i]= new Tortuga(posRandom);                   //crea
                            ultimo=tiempoactual;
                        }
                    }
                    if(tortugas[i]!=null) {
                        tortugas[i].aplicarGravedad(islas);                    //Verifica que no sea null
                        tortugas[i].actualizarPosicion(islas);                          //Verifica que este cayendo en una isla
                        tortugas[i].dibujar(entorno);                                    //crea
                        if(bolaFuego!=null) {                                           //Verifica que la bolaFuego no sea null
                            if(tortugas[i].colisionaAbajoBolaFuego(bolaFuego) ||
                                    tortugas[i].colisionaIzquierdaBolaFuego(bolaFuego) ||     //verifica que no choque con tortuga bolaFuego
                                    tortugas[i].colisionaDerechaBolaFuego(bolaFuego)) {
                                tortugas[i]=null;                                            // la mata
                                bolaFuego=null;                                              //Desaparece la bola
                                ui.setEnemigosEliminado();                                   // aumenta contador de eliminados
                            }
                        }
                    }
                }

                for (int i = 0; i<duendes.size();i++) {                 //Crea duende
                    Duende duende = duendes.get(i);
                    if (duende==null) {                                 //Verifica que no sea null
                        duendes.set(i, new Duende(casa));               // verifica que salga de la casa
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
                    if((duende.colisionaAbajoJugador(jugador) || duende.colisionaArribaJugador(jugador) || duende.colisionaDerechaJugador(jugador) || duende.colisionaIzquierdaJugador(jugador)) && jugador.getY()>islas[5].getY()) {
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
                if (entorno.estaPresionada('c') && bolaFuego==null) {
                    bolaFuego = new bolaFuego(jugador);
                }
                //aca compruebo el estado de bola defuego
                if(bolaFuego!=null) {
                    bolaFuego.mover();
                    bolaFuego.dibujar(entorno);
                    //aca compruebo si colisiona con alguna isla
                    if(bolaFuego.colisionaDerecha(islas) || bolaFuego.colisionaIzquierda(islas)) {
                        bolaFuego = null;
                    }
                    else if (bolaFuego.hayColisionDer(entorno) || bolaFuego.hayColisionIzq()) {
                        bolaFuego = null;
                    }
                    //aca cambio los booleanos de victoria o derrota
                    if(condicionVictoria==ui.getDuendesSalvados()) {
                        gano=true;
                    }
                    if(condicionDerrota==ui.getDuendesMuertos()) {
                        perdio=true;
                    }
                }
            }
            else {
                if(gano) {
                    menu.dibujarVictoria();
                    if (menu.botonIniciarPresionado()) {
                        gano=false;
                        perdio=false;
                        ui.iniciarCronometro(entorno);
                    }
                    else if (menu.botonSalirPresionado()) {
                        System.exit(0);
                    }

                }
                else {
                    menu.dibujarDerrota();
                    if (menu.botonIniciarPresionado()) {
                        gano=false;
                        perdio=false;
                        ui.iniciarCronometro(entorno);
                    }
                    else if (menu.botonSalirPresionado()) {
                        System.exit(0);
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