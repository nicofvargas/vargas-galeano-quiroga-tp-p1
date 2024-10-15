public class Tortuga {
    private double x;
    private double y;

    private double ancho;
    private double alto;

    private boolean estaEnIsla;
    private Isla islaActual;
    private double velocidadCaída;

    public Tortuga(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 10;
        this.alto = 10;
        estaEnIsla = false;
        islaActual = null;
        velocidadCaída = 1.0;
    }

    public void caer() {
        if (!estaEnIsla) {
            y += velocidadCaída;
        }
    }



    public void actualizarPosicion(Isla[] islas) {
        caer();
        boolean aterrizado = false;
        for (Isla isla : islas) {
            if (isla.contienePunto(x, y)) {
                aterrizarEnIsla(isla);
                aterrizado = true;
            }
        }
        if (estaEnIsla && aterrizado) {
            moverEnIsla();
        }
    }



    public boolean estaEnIsla(Isla isla) {
        return islaActual == isla;
    }


    Tortuga[] tortugas = new Tortuga[6];



    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.blue);
    }
    //getters
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getAncho() {return this.ancho;}
    public double getAlto() {return this.alto;}

}
