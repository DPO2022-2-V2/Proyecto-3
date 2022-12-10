package uniandes.dpoo.modelo;

import java.util.*;
import java.io.*;

public class Jugador implements Serializable, Comparable {
	private String nombre;
	private float precio;
	private String posicion;
	ArrayList<AlineacionTitular> alineacionTitularPerteneciente;
	private SistemaPuntos puntaje;
	private int puntajeAcumuladoJugador;
	private Temporada temporada;
	private boolean playedHour = false;

	public Jugador(String nombre, float precio, String posicion, Temporada tmp) {
		this.nombre = nombre;
		this.precio = precio;
		this.posicion = posicion;
		this.alineacionTitularPerteneciente = new ArrayList<AlineacionTitular>();
		this.puntaje = new SistemaPuntos(getPosicion());
		this.puntajeAcumuladoJugador = 0;
		this.temporada = tmp;
	}

	public void addAlineacionTitularPerteneciente(AlineacionTitular alineacion) {
		alineacionTitularPerteneciente.add(alineacion);
	}

	public void removeAlineacionTitularPerteneciente(AlineacionTitular alineacion) {
		alineacionTitularPerteneciente.remove(alineacion);
	}

	private ArrayList<AlineacionTitular> getAlineacionTitularPerteneciente() {
		return this.alineacionTitularPerteneciente;
	}

	public void asignarPuntosJugador(boolean gano, int minutosJugados, int golesAnotados, int golesRecibidos,
			int asistencias,
			int penaltiesAtajados, int penaltiesErrados, int tarjetasAmarillas,
			int tarjetasRojas, int autoGoles,
			int manos, int tirosLibres, int golTiroLibre) {
		getPuntaje().setMinutosJugados(minutosJugados);
		getPuntaje().setGolesAnotados(golesAnotados);
		getPuntaje().setAsistencias(asistencias);
		getPuntaje().setPenaltisDetenidos(penaltiesAtajados);
		getPuntaje().setPenaltisErrados(penaltiesErrados);
		getPuntaje().setTarjetasAmarillas(tarjetasAmarillas);
		getPuntaje().setTarjetasRojas(tarjetasRojas);
		getPuntaje().setAutoGoles(autoGoles);
		getPuntaje().setGolesRecibidos(golesRecibidos);
		getPuntaje().setNumeroFaltaMano(manos);
		getPuntaje().setTirosLibres(tirosLibres);
		getPuntaje().setGolTiroLibre(golTiroLibre);
		if (gano) {
			getPuntaje().setGanoEquipo(gano);
		}

		if (minutosJugados >= 60) {
			this.playedHour = true;
		}

		asignarPuntosAlineacionTitularPerteneciente();
	}

	private void asignarPuntosAlineacionTitularPerteneciente() {
		int puntosInicial = puntaje.calcularPuntuacion();
		int puntos = 0;
		puntos += puntosInicial;
		this.temporada.addPointsPosition(puntos, this.posicion); // ACA CAMBIO
		for (AlineacionTitular alineacionTitular : getAlineacionTitularPerteneciente()) {
			puntos = puntosInicial;
			if (alineacionTitular.isCaptain(this) == true & puntaje.isGanoEquipo() == true) {
				puntos += 5;
			}
			alineacionTitular.agregarPuntosEquipo(puntos, this.posicion);
		}
		setPuntajeAcumuladoJugador(getPuntajeAcumuladoJugador() + puntos);
	}

	// GETTERS & SETTERS

	public String getNombre() {
		return this.nombre;
	}

	public float getPrecio() {
		return this.precio;
	}

	public String getPosicion() {
		return this.posicion;
	}

	public boolean getPlayedHour() {
		return this.playedHour;
	}
	
	public int getPuntajeAcumuladoJugador() {
		return this.puntajeAcumuladoJugador;
	}

	public void setPuntajeAcumuladoJugador(int puntajeAcumuladoJugador) {
		this.puntajeAcumuladoJugador = puntajeAcumuladoJugador;
	}

	public SistemaPuntos getPuntaje() {
		return puntaje;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (this.getPuntajeAcumuladoJugador() > ((Jugador) o).getPuntajeAcumuladoJugador())
			return -1;
		if (this.getPuntajeAcumuladoJugador() == ((Jugador) o).getPuntajeAcumuladoJugador())
			return 0;
		else
			return 1;
	}

}
