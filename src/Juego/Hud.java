package Juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.*;
public class Hud {
    private String rutaFondo="Images/Fondo/fondo.jpg";
    private Image fondo;
    private int cronometro;
    private int duendesSalvados;
    private int duendesMuertos;
    private int enemigosEliminados;

    public Hud() {
        this.cronometro=0;
        this.duendesSalvados=0;
        this.duendesMuertos=0;
        this.enemigosEliminados=0;
        this.fondo = Herramientas.cargarImagen(rutaFondo).getScaledInstance(800,600,500);
    }
    //aca los metodos que actualizan cada valor
    public void setCronometro(Entorno entorno) {
        this.cronometro=entorno.tiempo()/1000;
    }
    public void dibujarFondo(Entorno entorno) {
        entorno.dibujarImagen(fondo,400,300,0);
    }
    public void setDuendesSalvado() {
        this.duendesSalvados++;
    }
    public void setDuendesMuerto() {
        this.duendesMuertos++;
    }
    public void setEnemigosEliminado() {
        this.enemigosEliminados++;
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

    //metodos para dibujar
    public void dibujarCronometro(Entorno entorno) {
        entorno.cambiarFont("arial",18,Color.red,Font.BOLD);
        entorno.escribirTexto(cronometroStr(),680,25);
    }
    public void dibujarDuendesSalvados(Entorno entorno) {
        entorno.cambiarFont("arial",13,Color.darkGray,Font.BOLD);
        entorno.escribirTexto(duendesSalvadosStr(),25,25);
    }
    public void dibujarDuendesMuertos(Entorno entorno) {
        entorno.escribirTexto(duendesMuertosStr(),230,25);
    }
    public void dibujarEnemigosEliminados(Entorno entorno) {
        entorno.escribirTexto(enemigosEliminadosStr(),420,25);
    }
}
