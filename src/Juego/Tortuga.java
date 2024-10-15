public class Tortuga {
    private double x;
    private double y;

    private double ancho;
    private double alto;

    private boolean estaEnIsla;
    private Isla islaActual;

    public Tortuga() {
        this.x = 0;
        this.y = 0;
        this.ancho = 10;
        this.alto = 10;
        estaEnIsla = false;
        islaActual = null;
    }

    public void caer() {
        if (!estaEnIsla) {
            y++;
        }
    }

    public void aterrizarEnIsla(Isla isla) {
        if (!estaEnIsla) {
            y = isla.getY();
            estaEnIsla = true;
            islaActual = isla;
        }
    }

    public void actualizarPosicion(List<Isla> islas) {
        caer();
        for (Isla isla : islas) {
            if (isla.contienePunto(x, y)) {
                aterrizarEnIsla(isla);
                break;
            }
        }
        if (estaEnIsla) {
            moverEnIsla();
        }
    }
    public boolean estaEnIsla(Isla isla) {
        return islaActual == isla;
    }

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
    public double getAncho() {
        return this.ancho;
    }
    public double getAlto() {
        return this.alto;
    }

}
