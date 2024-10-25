package Juego;
import java.util.Timer;
import java.util.TimerTask;

public class
Temporizador {

    private int segundosRestantes;
    private Timer timer;

    public Temporizador(int segundos) {
        this.segundosRestantes = segundos;
        this.timer = new Timer();
        iniciar();
    }

    private void iniciar() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                segundosRestantes--;
                if (segundosRestantes == 0) {
                    cancel();
                }
            }
        }, 0, 1000); //aca se ejecuta cada segundo
    }

    public int getSegundosRestantes() {
        return segundosRestantes; //con esto obtienen los segundos restantes
    }

    public void cancelar() {
        timer.cancel();
    }
}