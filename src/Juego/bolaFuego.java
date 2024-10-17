package Juego;

public class bolaFuego {
    private double x;
    private double y;
    private double ancho;
    private double alto;

    public bolaFuego(Jugador jugador) {
        this.x=jugador.getX();
        this.y=jugador.getY();
        this.ancho=30;
        this.alto=30;
    }
}
