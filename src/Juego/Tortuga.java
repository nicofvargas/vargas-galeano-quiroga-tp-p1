public class Tortuga {
    private double x;
    private double y;
    private boolean estaEnIsla;
    private Isla islaActual;

    public Tortuga() {
        x = 0; // comienza en x=0 (el cielo)
        y = 0; // coordenada y inicial no importa
        ancho = 10; // ancho predeterminado
        alto = 10; // alto predeterminado
        estaEnIsla = false;
        islaActual = null;
    }

    public void caer() {
        if (!estaEnIsla) {
            y++; // mueve hacia abajo (cae) si no está en una isla
        }
    }

    public void aterrizarEnIsla(Isla isla) {
        if (!estaEnIsla) {
            y = isla.getY(); // establece la coordenada y en la coordenada y de la isla
            estaEnIsla = true;
            islaActual = isla;
        }
    }
    public Tortuga() {
        x = 0; // comienza en x=0 (el cielo)
        y = 0; // coordenada y inicial no importa
        ancho = 10; // ancho predeterminado
        alto = 10; // alto predeterminado
        estaEnIsla = false;
        islaActual = null;
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

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
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
