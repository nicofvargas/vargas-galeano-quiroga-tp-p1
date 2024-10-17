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

    public Jugador(Entorno entorno) {
        this.x = 100;
        this.y = 50;
        this.ancho = 50;
        this.alto = 50;
        this.velocidad = 10;
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
    //metodo para dibujar
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
    }

    //metodos para mover
    public void moverDerecha(Entorno entorno, Isla[] islas) {
        this.x += velocidad;
        if (hayColisionDer(entorno)) { //esto es para que no se pase del borde de ventana
            if(colisionaDerecha(islas)) { //esto comprueba si hay colision con isla vuelve para atras
                this.x -= velocidad;
            }
        }
    }
    public void moverIzquierda(Entorno entorno,Isla[] islas) {
        this.x -= velocidad;
        if(hayColisionIzq()) {
            if(colisionaIzquierda(islas)) {
                this.x+=velocidad;
            }
        }
    }
    public void moverArriba(Entorno entorno,Isla[] islas) {
        this.y-=velocidad;
        if(colisionaArriba(islas)) {
            this.y+=velocidad;
        }
    }
    public void moverAbajo(Entorno entorno,Isla[] islas) {
        this.y+=velocidad;
        if(colisionaAbajo(islas)) {
            this.y-=velocidad;
        }
    }

    public void saltar() { //
        if (!enElAire) {
            velocidadCaida = -10;
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

            if (bordeDerechoPersonaje >= bordeIzquierdoIsla && bordeDerechoPersonaje <= bordeIzquierdoIsla + velocidad) {
                if (this.y + (this.alto / 2) > isla.getY() - (isla.getAlto() / 2) && this.y - (this.alto / 2) < isla.getY() + (isla.getAlto() / 2)) {
                    this.x = (int) bordeIzquierdoIsla - (this.ancho / 2);
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
                    this.x = (int) bordeDerechoIsla + (this.ancho / 2);
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

            if(bordeSuperiorPersonaje <= bordeInferiorIsla && bordeSuperiorPersonaje>= bordeInferiorIsla-velocidad) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=(int) bordeInferiorIsla+(this.alto/2);
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

            if(bordeInferiorPersonaje>=bordeSuperiorIsla && bordeInferiorPersonaje<=bordeSuperiorIsla +velocidad) {
                if(this.x+(this.ancho/2) > isla.getX()-(isla.getAncho()/2)  &&  this.x-(this.ancho/2) < isla.getX()+(isla.getAncho()/2)) {
                    this.y=(int) bordeSuperiorIsla-(this.alto/2);
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
            this.y+=velocidadCaida;
        }
        if(colisionaAbajo(islas)) {
            enElAire=false;
            velocidadCaida=0;
        }

    }

}
