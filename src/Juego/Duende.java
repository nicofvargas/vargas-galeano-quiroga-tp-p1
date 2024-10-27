package Juego;

import entorno.Entorno;

import java.awt.*;

import java.util.HashMap;

import java.util.Map;

import java.util.List;

public class Duende {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double gravedad;
    private double velocidadCaida;
    private int direccion;
    private boolean enElAire;
    private boolean estaEnIsla;
    private Isla islaActual;
    private  int maximoDuendes = 4;
    private Map<Isla ,Integer>direccionesPorIsla;
    //private boolean colision;
    //private boolean muere;
    //private boolean rescatado;

    public Duende(Casa casa) {
        this.x = casa.getX();
        this.y = casa.getY();
        this.ancho = 20;
        this.alto = 30;
        this.velocidad = 0.7;
        this.gravedad = 0.1;
        this.velocidadCaida = 0;
        this.enElAire = false;
        this.estaEnIsla=false;
        this.direccionesPorIsla = new HashMap<>();

    }

    public boolean duendeEnElAire(Isla[] islas) {
        if (!colisionaAbajo(islas)) {
            enElAire = true;
        }
        return false;
    }

    public static void crearDuendesConDelay(List<Duende> duendes, int maximoDuendes, Casa casa) {
        new Thread(() -> {
            while (duendesVivos(duendes) < maximoDuendes) {
                synchronized (duendes) {
                    for (int i = 0; i < duendes.size(); i++) {
                        if (duendes.get(i) == null) {
                            duendes.set(i, new Duende(casa));
                        }
                    }
                    // Si hay espacio para más duendes, agrega uno nuevo
                    if (duendes.size() < maximoDuendes) {
                        duendes.add(new Duende(casa));
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                }
            }
        }).start();
    }

    public static int duendesVivos(List<Duende> duendes) {
        int contador=0;
        for (Duende duende : duendes) {
            if (duende!=null) {
                contador++;
            }
        }
        return contador;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.red);
    }

    public boolean colisionaAbajo(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }
            double bordeInferiorDuende = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if (bordeInferiorDuende >= bordeSuperiorIsla && bordeInferiorDuende <= bordeSuperiorIsla + (velocidad + 8)) {
                if (this.x + (this.ancho / 2) > isla.getX() - (isla.getAncho() / 2) && this.x - (this.ancho / 2) < isla.getX() + (isla.getAncho() / 2)) {
                    this.y = bordeSuperiorIsla - (this.alto / 2);
                    islaActual = isla; // Actualiza la isla actual
                    return true;
                }
            }
        }
        return false;
    }

    public void aplicarGravedad(Isla[] islas) {
        if (enElAire) {
            velocidadCaida += gravedad;
            if (velocidadCaida > 10) {
                velocidadCaida = 10;
            }
            this.y += velocidadCaida;
            this.x+=velocidad*direccion;
        }
        if (colisionaAbajo(islas)) {
            enElAire = false;
            velocidadCaida = 0;
        }
    }

    public void patronDeMovimiento(Isla[] islas) {
        if (colisionaAbajo(islas)) {
            if (!direccionesPorIsla.containsKey(islaActual)) {
                direccion = (Math.random() < 0.5) ? -1 : 1;
                direccionesPorIsla.put(islaActual, direccion);
            } else {
                direccion = direccionesPorIsla.get(islaActual);
            }
            this.x += velocidad * direccion;
        }
    }

    //metodos de colision con tortuga
    public boolean colisionaDerechaTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoTortu = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoTortu && bordeDerechoPersonaje <= bordeIzquierdoTortu + 10) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeIzquierdoTortu - (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaIzquierdaTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeIzquierdoPersonaje = this.x - (this.ancho / 2);
            double bordeDerechoTortu = tortuga.getX() + (tortuga.getAncho() / 2);

            if (bordeIzquierdoPersonaje <= bordeDerechoTortu && bordeIzquierdoPersonaje >= bordeDerechoTortu - 10) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeDerechoTortu + (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaArribaTortu(Tortuga[] tortugas) {
        for(Tortuga tortuga : tortugas) {
            if(tortuga==null) {
                continue;
            }
            double bordeSuperiorPersonaje = this.y - (this.alto / 2);
            double bordeInferiorTortu = tortuga.getY() + (tortuga.getAlto() / 2);

            if(bordeSuperiorPersonaje <= bordeInferiorTortu && bordeSuperiorPersonaje>= bordeInferiorTortu-10) {
                if(this.x+(this.ancho/2) > tortuga.getX()-(tortuga.getAncho()/2)  &&  this.x-(this.ancho/2) < tortuga.getX()+(tortuga.getAncho()/2)) {
                    this.y=bordeInferiorTortu+(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean colisionaAbajoTortu(Tortuga[] tortugas) {
        for(Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }
            double bordeInferiorPersonaje = this.y + (this.alto / 2);
            double bordeSuperiorTortuga = tortuga.getY() - (tortuga.getAlto() / 2);

            if (bordeInferiorPersonaje >= bordeSuperiorTortuga && bordeInferiorPersonaje <= bordeSuperiorTortuga + 10) {
                if (this.x + (this.ancho / 2) > tortuga.getX() - (tortuga.getAncho() / 2) && this.x - (this.ancho / 2) < tortuga.getX() + (tortuga.getAncho() / 2)) {
                    this.y = bordeSuperiorTortuga - (this.alto / 2);
                    return true;
                }
            }
        }
        return false;
    }

    //colision con jugador
    public boolean colisionaDerechaJugador(Jugador jugador) {
        double bordeDerechoDuende = this.x + (this.ancho / 2);
        double bordeIzquierdoJugador = jugador.getX() - (jugador.getAncho() / 2);

        if (bordeDerechoDuende >= bordeIzquierdoJugador && bordeDerechoDuende <= bordeIzquierdoJugador + 10) {
            if (this.y + (this.alto / 2) > jugador.getY() - (jugador.getAlto() / 2) && this.y - (this.alto / 2) < jugador.getY() + (jugador.getAlto() / 2)) {
                this.x = bordeIzquierdoJugador - (this.ancho / 2);
                return true;
            }
        }
        return false;
    }


    public boolean colisionaIzquierdaJugador(Jugador jugador) {
        double bordeIzquierdoDuende = this.x - (this.ancho / 2);
        double bordeDerechoJugador = jugador.getX() + (jugador.getAncho() / 2);

        if (bordeIzquierdoDuende <= bordeDerechoJugador && bordeIzquierdoDuende >= bordeDerechoJugador - 10) {
            if (this.y + (this.alto / 2) > jugador.getY() - (jugador.getAlto() / 2) && this.y - (this.alto / 2) < jugador.getY() + (jugador.getAlto() / 2)) {
                this.x = bordeDerechoJugador + (this.ancho / 2);
                return true;
            }
        }
        return false;
    }


    public boolean colisionaArribaJugador(Jugador jugador) {
        double bordeSuperiorDuende = this.y - (this.alto / 2);
        double bordeInferiorJugador = jugador.getY() + (jugador.getAlto() / 2);

        if(bordeSuperiorDuende <= bordeInferiorJugador && bordeSuperiorDuende>= bordeInferiorJugador-10) {
            if(this.x+(this.ancho/2) > jugador.getX()-(jugador.getAncho()/2)  &&  this.x-(this.ancho/2) < jugador.getX()+(jugador.getAncho()/2)) {
                this.y=bordeInferiorJugador+(this.alto/2);
                return true;
            }
        }
        return false;
    }

    public boolean colisionaAbajoJugador(Jugador jugador) {
        double bordeInferiorDuende = this.y + (this.alto / 2);
        double bordeSuperiorJugador = jugador.getY() - (jugador.getAlto() / 2);

        if(bordeInferiorDuende>=bordeSuperiorJugador && bordeInferiorDuende<=bordeSuperiorJugador +10) {
            if(this.x+(this.ancho/2) > jugador.getX()-(jugador.getAncho()/2)  &&  this.x-(this.ancho/2) < jugador.getX()+(jugador.getAncho()/2)) {
                this.y=bordeSuperiorJugador-(this.alto/2);
                return true;
            }
        }

        return false;

    }
}






