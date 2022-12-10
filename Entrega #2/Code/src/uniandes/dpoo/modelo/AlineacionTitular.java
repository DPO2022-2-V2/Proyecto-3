package uniandes.dpoo.modelo;

import java.util.*;
import java.io.*;

public class AlineacionTitular implements Serializable {
	private Jugador capitan;
	private HashMap<String, Jugador> jugadores = new HashMap<String, Jugador>();
	private EquipoFantasia equipoPerteneciente;
	private int arqueros = 0;
	private int defensores = 0;
	private int mediocampistas = 0;
	private int delanteros = 0;

	public AlineacionTitular(EquipoFantasia equipo) {
		this.equipoPerteneciente = equipo;
	}

	public void elegirCapitanAlineacionTitular(Jugador jugadorSeleccionado) {
		if (jugadores.containsKey(jugadorSeleccionado.getNombre())) {
			this.capitan = jugadorSeleccionado;
			System.out.println("Capitan colocado satisfactoriamente");
		} else {
			System.out.println("El jugador seleccionado no esta en la alineacion");
		}
	}

	public void agregarJugadorAlineacionTitular(Jugador jugadorSeleccionado) {
		String posicionJugador = jugadorSeleccionado.getPosicion();
		if ((jugadores.size() < 11) & (jugadores.containsKey(jugadorSeleccionado.getNombre()) == false) & (

		(posicionJugador.equals("arquero") & getArqueros() < 1)
				|| (posicionJugador.equals("defensa") & getDefensores() < 4)
				|| (posicionJugador.equals("delantero") & getDelanteros() < 2)
				|| (posicionJugador.equals("medioCampista") & getMediocampistas() < 4)

		)) {
			jugadores.put(jugadorSeleccionado.getNombre(), jugadorSeleccionado);
			getEquipoPerteneciente().removeJugadorEquipoFantasia(jugadorSeleccionado);
			// getEquipoPerteneciente().addJugadorEquipoFantasia(null);

			jugadorSeleccionado.addAlineacionTitularPerteneciente(this);

			if (posicionJugador.equals("defensa")) {
				sumarDefensa();
			} else if (posicionJugador.equals("arquero")) {
				sumarArquero();
			} else if (posicionJugador.equals("delantero")) {
				sumarDelantero();
			} else if (posicionJugador.equals("medioCampista")) {
				sumarMedioCampista();
			}

		} else {
			System.out.println("ERROR: al agregar jugador");
		}
	}

	public void eliminarJugadorAlineacionTitular(Jugador jugadorSeleccionado) {
		if (jugadores.containsKey(jugadorSeleccionado.getNombre())) {
			jugadores.remove(jugadorSeleccionado.getNombre());
			// getEquipoPerteneciente().removeJugadorEquipoFantasia(null);
			getEquipoPerteneciente().addJugadorEquipoFantasia(jugadorSeleccionado);
			jugadorSeleccionado.removeAlineacionTitularPerteneciente(this);

			String posicionJugador = jugadorSeleccionado.getPosicion();
			if (posicionJugador.equals("defensa")) {
				restarDefensa();
			} else if (posicionJugador.equals("arquero")) {
				restarArquero();
			} else if (posicionJugador.equals("delantero")) {
				restarDelantero();
			} else if (posicionJugador.equals("medioCampista")) {
				restarMedioCampista();
			}
		} else {
			System.out.println("El jugador dado no esta en los jugadores del grupo titular");
		}
	}

	public boolean isCaptain(Jugador insJugador) { // VERRRRRRRRRRRRRRRR
		if (getCapitan() == null) {
			return false;
		} else if (this.capitan.getNombre().equals(insJugador.getNombre())) {
			return true;
		} else {
			return false;
		}
	}

	public void agregarPuntosEquipo(int puntosGanados, String posicionJugador) {
		EquipoFantasia equipo = getEquipoPerteneciente();
		if (getJugadores().size() == 11 & (

		(getArqueros() == 1)
				& (getDefensores() == 4)
				& (getDelanteros() == 2)
				& (getMediocampistas() == 4)

		)) {
			equipo.addPuntosEquipo(puntosGanados);
			equipo.sumarPuntosPosicion(puntosGanados, posicionJugador);
		} else {
			System.out.println("ERROR: No hay jugadores suficientes en el equipo de fantasia " + equipo.getNombre());
		}
	}

	// GETTERS & SETTERS
	public Jugador getCapitan() {
		return this.capitan;
	}

	public void setCapitan(Jugador capitan) {
		this.capitan = capitan;
	}

	public HashMap<String, Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(HashMap<String, Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public EquipoFantasia getEquipoPerteneciente() {
		return this.equipoPerteneciente;
	}

	public void sumarArquero() {
		this.arqueros += 1;
	}

	public void restarArquero() {
		this.arqueros -= 1;
	}

	public void sumarDefensa() {
		this.defensores += 1;
	}

	public void restarDefensa() {
		this.defensores -= 1;
	}

	public void sumarMedioCampista() {
		this.mediocampistas += 1;
	}

	public void restarMedioCampista() {
		this.mediocampistas -= 1;
	}

	public void sumarDelantero() {
		this.delanteros += 1;
	}

	public void restarDelantero() {
		this.delanteros -= 1;
	}

	public int getArqueros() {
		return arqueros;
	}

	public int getDefensores() {
		return defensores;
	}

	public int getMediocampistas() {
		return mediocampistas;
	}

	public int getDelanteros() {
		return delanteros;
	}

}
