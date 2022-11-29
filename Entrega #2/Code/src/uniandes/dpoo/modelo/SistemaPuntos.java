package uniandes.dpoo.modelo;

import java.io.*;

public class SistemaPuntos implements Serializable {

	private int minutosJugados;
	private int golesAnotados;
	private int autoGoles;
	private int asistencias;
	private int penaltisErrados;
	private int tarjetasAmarillas;
	private int tarjetasRojas;
	private int golesRecibidos;
	private int penaltisDetenidos;
	private String posicionJugador;
	private boolean ganoEquipo = false;

	public SistemaPuntos(String posicion) {
		this.posicionJugador = posicion;
	}

	public int calcularPuntuacion() {
		int puntuacion;
		String posicion = getPosicionJugador();
		puntuacion = 0;
		if (minutosJugados <= 60) {
			puntuacion += 1;
		} else if (minutosJugados > 60) {
			puntuacion += 2;
		}
		if (posicion.equals("delantero")) {
			puntuacion += golesAnotados * 4;
		} else if (posicion.equals("medioCampista")) {
			puntuacion += golesAnotados * 5;
		} else if (posicion.equals("arquero") || posicion.equals("defensa")) {
			puntuacion += golesAnotados * 6;
			if (golesRecibidos == 0) {
				puntuacion += 4;
			}
			if (posicion.equals("arquero") & penaltisDetenidos > 0) {
				puntuacion += 5;
			}
		}

		puntuacion += asistencias * 3;
		puntuacion -= penaltisErrados * 2;
		puntuacion -= tarjetasAmarillas * 1;
		puntuacion -= tarjetasRojas * 3;
		puntuacion -= autoGoles * 2;

		return puntuacion;

	}

	// GETTERS AND SETTERS
	public int getMinutosJugados() {
		return minutosJugados;
	}

	public void setMinutosJugados(int minutosJugados) {
		this.minutosJugados = minutosJugados;
	}

	public int getGolesAnotados() {
		return golesAnotados;
	}

	public void setGolesAnotados(int golesAnotados) {
		this.golesAnotados = golesAnotados;
	}

	public int getAutoGoles() {
		return autoGoles;
	}

	public void setAutoGoles(int autoGoles) {
		this.autoGoles = autoGoles;
	}

	public int getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}

	public int getPenaltisErrados() {
		return penaltisErrados;
	}

	public void setPenaltisErrados(int penaltisErrados) {
		this.penaltisErrados = penaltisErrados;
	}

	public int getTarjetasAmarillas() {
		return tarjetasAmarillas;
	}

	public void setTarjetasAmarillas(int tarjetasAmarillas) {
		this.tarjetasAmarillas = tarjetasAmarillas;
	}

	public int getTarjetasRojas() {
		return tarjetasRojas;
	}

	public void setTarjetasRojas(int tarjetasRojas) {
		this.tarjetasRojas = tarjetasRojas;
	}

	public int getGolesRecibidos() {
		return golesRecibidos;
	}

	public void setGolesRecibidos(int golesRecibidos) {
		this.golesRecibidos = golesRecibidos;
	}

	public int getPenaltisDetenidos() {
		return penaltisDetenidos;
	}

	public void setPenaltisDetenidos(int penaltisDetenidos) {
		this.penaltisDetenidos = penaltisDetenidos;
	}

	public String getPosicionJugador() {
		return this.posicionJugador;
	}

	public void setPosicionJugador(String posicionJugador) {
		this.posicionJugador = posicionJugador;
	}

	public boolean isGanoEquipo() {
		return ganoEquipo;
	}

	public void setGanoEquipo(boolean ganoEquipo) {
		this.ganoEquipo = ganoEquipo;
	}

}