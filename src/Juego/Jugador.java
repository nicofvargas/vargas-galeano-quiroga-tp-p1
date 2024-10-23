package Juego;

import entorno.Entorno;

import java.awt.*;

public class Jugador {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    private double gravedad;
    private double velocidadCaida;
    private double velocidadSalto;
    private boolean enElAire;
    private String rutaJugador;
    private boolean miraDerecha;
    private boolean miraIzq;

    public Jugador(Entorno entorno) {
        this.x = 65;
        this.y = 456;
        this.ancho = 20;
        this.alto = 40;
        this.velocidad = 5;
        this.gravedad = 0.3;
        this.velocidadCaida = 0;
        this.velocidadSalto=0;
        this.enElAire=false; //esto cuando terminemos sera false solo lo pongo para pruebas como true
    }
    //getters
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public boolean getMiraDerecha() {
        return miraDerecha;
    }
    public boolean getMiraIzq() {
        return miraIzq;
    }
    public double getAncho() { return this.ancho; }
    public double getAlto() { return this.alto; }
    //metodo para dibujar
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
    }

    //metodos para mover
    public void moverDerecha(Entorno entorno, Isla[] islas) {
        this.x += velocidad;
        this.miraIzq=false;
        this.miraDerecha=true;
        if (hayColisionDer(entorno)) { //esto es para que no se pase del borde de ventana
            if(colisionaDerecha(islas)) { //esto comprueba si hay colision con isla vuelve para atras
                this.x -= velocidad;
            }
        }
    }
    public void moverIzquierda(Isla[] islas) {
        this.x -= velocidad;
        this.miraDerecha=false;
        this.miraIzq=true;
        if(hayColisionIzq()) {
            if(colisionaIzquierda(islas)) {
                this.x+=velocidad;
            }
        }
    }
    public void moverArriba(Isla[] islas) {
        this.y-=velocidad;
        if(colisionaArriba(islas)) {
            this.y+=velocidad;
        }
    }
    public void moverAbajo(Isla[] islas) {
        this.y+=velocidad;
        if(colisionaAbajo(islas)) {
            this.y-=velocidad;
        }
    }

    public void saltar(Isla[] islas) { //
        if (!enElAire) {
            velocidadCaida = -8;
            enElAire = true;
        }
    }

    //colision con bordes de ventana
    public boolean hayColisionIzq() {
        return this.x - this.ancho / 2 <= 0; //le resto el ancho dividido dos porque sino se pasa de la ventana ya que X es el medio del rectangulo
    }

    public boolean hayColisionDer(Entorno entorno) {
        return this.x + this.ancho / 2 >= entorno.ancho(); //aca lo mismo pero a la inversa le falta medio rectangulo para llegar a colisionar
    }

    //colision con objetos

    public boolean colisionaDerecha(Isla[] islas) {
        for (Isla isla : islas) {
            if (isla == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoIsla = isla.getX() - (isla.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + fixVelocidadColision()) {
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

            if (bordeIzquierdoPersonaje <= bordeDerechoIsla && bordeIzquierdoPersonaje >= bordeDerechoIsla - fixVelocidadColision()) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = bordeDerechoIsla + (this.ancho / 2);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean colisionaArriba(Isla[] islas) {
        for(Isla isla : islas) {
            if(isla==null) {
                continue;
            }
            double bordeSuperiorPersonaje = this.y - (this.alto / 2);
            double bordeInferiorIsla = isla.getY() + (isla.getAlto() / 2);

            if(bordeSuperiorPersonaje <= bordeInferiorIsla && bordeSuperiorPersonaje>= bordeInferiorIsla-fixVelocidadColision()) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeInferiorIsla+(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean colisionaAbajo(Isla[] islas) {
        for(Isla isla : islas) {
            if(isla==null) {
                continue;
            }
            double bordeInferiorPersonaje = this.y + (this.alto / 2);
            double bordeSuperiorIsla = isla.getY() - (isla.getAlto() / 2);

            if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +fixVelocidadColision()) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=bordeSuperiorIsla-(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }

    //metodos de colision con tortuga
    public boolean colisionaDerechaTortu(Tortuga[] tortugas) {
        for (Tortuga tortuga : tortugas) {
            if (tortuga == null) {
                continue;
            }

            double bordeDerechoPersonaje = this.x + (this.ancho / 2);
            double bordeIzquierdoTortu = tortuga.getX() - (tortuga.getAncho() / 2);

            if (bordeDerechoPersonaje >= bordeIzquierdoTortu && bordeDerechoPersonaje <= bordeIzquierdoTortu + fixVelocidadColision()) {
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

            if (bordeIzquierdoPersonaje <= bordeDerechoTortu && bordeIzquierdoPersonaje >= bordeDerechoTortu - fixVelocidadColision()) {
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

            if(bordeSuperiorPersonaje <= bordeInferiorTortu && bordeSuperiorPersonaje>= bordeInferiorTortu-fixVelocidadColision()) {
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
            if(tortuga==null) {
                continue;
            }
            double bordeInferiorPersonaje = this.y + (this.alto / 2);
            double bordeSuperiorTortuga = tortuga.getY() - (tortuga.getAlto() / 2);

            if(bordeInferiorPersonaje>=bordeSuperiorTortuga && bordeInferiorPersonaje<=bordeSuperiorTortuga +fixVelocidadColision()) {
                if(this.x+(this.ancho/2) > tortuga.getX()-(tortuga.getAncho()/2)  &&  this.x-(this.ancho/2) < tortuga.getX()+(tortuga.getAncho()/2)) {
                    this.y=bordeSuperiorTortuga-(this.alto/2);
                    return true;
                }
            }
        }
        return false;
    }


    //gravedad
    public void aplicarGravedad(Isla[] islas) {
        if(enElAire=true) {
            velocidadCaida+=gravedad;
            if (velocidadCaida>10) {
                velocidadCaida=10;
            }
            this.y+=velocidadCaida;
        }
        if(colisionaAbajo(islas) || colisionaArriba(islas)) {
            enElAire=false;
            velocidadCaida=0;
        }
    }


    public double fixVelocidadColision() {
        return 10;
    }

}
