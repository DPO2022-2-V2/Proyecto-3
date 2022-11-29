package uniandes.dpoo.modelo;

import java.util.*;
import java.io.*;

public class EquipoReal extends Equipo implements Serializable {
	private HashMap<String, Jugador> jugadores = new HashMap<String, Jugador>();
	private Temporada temporada;

	public EquipoReal(String nombre, Temporada tmp) {
		this.nombre = nombre;
		this.temporada = tmp;
	}

	public void agregarJugador(String nombre, String posicion, float precio) {
		Jugador instancia_jugador = new Jugador(nombre, precio, posicion, this.temporada);
		getJugadores().put(nombre, instancia_jugador);
	}

	// GETTERS & SETTERS
	public HashMap<String, Jugador> getJugadores() {
		return jugadores;
	}
}
