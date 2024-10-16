package Juego;

public class bolaFuego {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private int direccion;
    private boolean activa;

    public bolaFuego(Jugador jugador) {
        this.x=jugador.getX();
        this.y=jugador.getY();
        this.ancho=30;
        this.alto=30;
        this.velocidad=7;
        this.activa=true;
    }

    public int setDireccion(Jugador jugador) {
        if (jugador.getMiraDerecha()) {
            return direccion=1;
        }
        else {
            return direccion=-1;
        }
    }

    public void mover(Jugador jugador) {
        this.x=velocidad*setDireccion(jugador);
    }

    //colisiones
    //islas
    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeIzquierdoIsla - (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaIzquierda(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeIzquierdoPersonaje = this.x - (this.ancho / 2);
            double bordeDerechoIsla = isla.getX() + (isla.getAncho() / 2);

            if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }

    //tortugas
    public boolean colisionaDerechaTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeDerechoBolaFuego = this.x + (this.ancho / 2);
            double bordeIzquierdoTortuga = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeDerechoBolaFuego >= bordeIzquierdoTortuga && bordeDerechoBolaFuego <= bordeIzquierdoTortuga + velocidad) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeIzquierdoTortuga - (this.ancho / 2); //esto me parece que lo voy a cambiar asi la colision se ve mas real
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaIzqTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeIzqBolaFuego = this.x + (this.ancho / 2);
            double bordeDerechoTortuga = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeIzqBolaFuego >= bordeDerechoTortuga && bordeIzqBolaFuego <= bordeDerechoTortuga + velocidad) {
                if (this.y + (this.alto / 2) > tortuga.getY() - (tortuga.getAlto() / 2) && this.y - (this.alto / 2) < tortuga.getY() + (tortuga.getAlto() / 2)) {
                    this.x = bordeDerechoTortuga - (this.ancho / 2); //esto me parece que lo voy a cambiar asi la colision se ve mas real
                    return true;
                }
            }
        }
        return false;
    }

}
