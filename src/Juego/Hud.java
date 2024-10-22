package Juego;

public class Hud {
    private int cronometro;
    private int duendesSalvados;
    private int duendesMuertos;
    private int enemigosEliminados;

    public Hud() {
        this.cronometro=0;
        this.duendesSalvados=0;
        this.duendesMuertos=0;
        this.enemigosEliminados=0;
    }
    //aca los metodos que actualizan cada valor
    public void setCronometro(int milisegundos) {
        this.cronometro=milisegundos/1000;
    }
    public void setDuendesSalvados(int salvado) {
        this.duendesSalvados+=salvado;
    }
    public void setDuendesMuertos(int muerto) {
        this.duendesMuertos+=muerto;
    }
    public void setEnemigosEliminados(int enemigosEliminado) {
        this.enemigosEliminados = enemigosEliminado;
    }
    //getters
    public int getCronometro() {
        return this.cronometro;
    }
    public int getDuendesSalvados() {
        return duendesSalvados;
    }
    public int getDuendesMuertos() {
        return duendesMuertos;
    }
    public int getEnemigosEliminados() {
        return enemigosEliminados;
    }

    //metodos para devolver los valores String ya que escribirTexto solo recibe por parametro un string, y dos double con los valores de X e Y
    public String cronometroStr() {
        return "Tiempo: " + getCronometro();
    }
    public String duendesSalvadosStr() {
        return "Duendes salvados: " + getDuendesSalvados();
    }
    public String duendesMuertosStr() {
        return "Duendes muertos: " + getDuendesMuertos();
    }
    public String enemigosEliminadosStr() {
        return "Enemigos eliminados: " + getEnemigosEliminados();
    }
}
