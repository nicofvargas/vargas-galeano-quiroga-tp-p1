package Juego;

public class Gravedad {
    private double gravedad;
    private double velocidad;

    public Gravedad() {
        this.gravedad=15;
        this.velocidad=0; // se establece en 0 porque su valor se alterara dependiendo si esta en el piso o no
    }
    //este metodo va a ser llamado por todos los objetos del juego que tengan permitido caer
    public double aplicarGravedad(double y, boolean enElPiso) {
        if(!enElPiso) {
            velocidad+=gravedad;
            y+=velocidad;
        }
        else {
            velocidad=0; //aca se actualiza a 0 cada vez que el estado de enElPiso es true
        }
        return y; //esta es el return que es usado en todas las clases que puedan caer
    }
}
