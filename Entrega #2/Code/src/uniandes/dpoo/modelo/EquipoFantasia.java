package uniandes.dpoo.modelo;

import java.util.*;
import java.io.*;

public class EquipoFantasia extends Equipo implements Serializable, Comparable {
	private float presupuestoInicial;
	private float presupuestoDisponible;
	private int puntosEquipo = 0;
	private User duenio;
	private AlineacionTitular alineacionTitular;
	private ArrayList<Jugador> arqueros = new ArrayList<Jugador>();
	private ArrayList<Jugador> defensores = new ArrayList<Jugador>();
	private ArrayList<Jugador> mediocampistas = new ArrayList<Jugador>();
	private ArrayList<Jugador> delanteros = new ArrayList<Jugador>();

	public EquipoFantasia(String nombre, User duenio, int presupuesto) {
		this.nombre = nombre;
		this.duenio = duenio;
		this.alineacionTitular = new AlineacionTitular(this);
		this.presupuestoInicial = presupuesto;
		this.presupuestoDisponible = presupuesto;
	}

	// FUNCIONALIDADES
	public void comprarJugador(Jugador jugadorSeleccionado) {
		float precioJugador = jugadorSeleccionado.getPrecio();
		String posicionJugador = jugadorSeleccionado.getPosicion();
		if (getPresupuestoDisponible() >= precioJugador & (

		(posicionJugador.equals("arquero") & getArqueros().size() < 2
				& getArqueros().contains(jugadorSeleccionado) == false)
				|| (posicionJugador.equals("defensa") & getDefensores().size() < 5
						& getDefensores().contains(jugadorSeleccionado) == false)
				|| (posicionJugador.equals("delantero") & getDelanteros().size() < 3
						& getDelanteros().contains(jugadorSeleccionado) == false)
				|| (posicionJugador.equals("medioCampista") & getMediocampistas().size() < 5
						& getMediocampistas().contains(jugadorSeleccionado) == false)

		)) {

			setPresupuestoDisponible(getPresupuestoDisponible() - precioJugador);
			addJugadorEquipoFantasia(jugadorSeleccionado);

		} else {
			System.out.println("ERROR: No se pudo completar la transaccion");
		}
	}

	public void venderJugador(Jugador jugadorSeleccionado) {

		float precioJugador = jugadorSeleccionado.getPrecio();

		removeJugadorEquipoFantasia(jugadorSeleccionado);
		setPresupuestoDisponible(getPresupuestoDisponible() + (float) (precioJugador * 0.97));
	}

	public boolean cumpleRequisitos() {
		int sizeArqueros = getArqueros().size();
		int sizeDefensores = getDefensores().size();
		int sizeDelanteros = getDelanteros().size();
		int sizeMedioCampistas = getMediocampistas().size();
		boolean result = false;
		if (sizeArqueros != 2 & sizeDefensores != 5 & sizeDelanteros != 3 & sizeMedioCampistas != 5) {
			result = true;
		}
		return result;
	}

	public void addPuntosEquipo(int puntosGanados) {
		if ((getArqueros().size() == 1)
				& (getDefensores().size() == 1)
				& (getDelanteros().size() == 1)
				& getMediocampistas().size() == 1) {
			this.puntosEquipo += puntosGanados;
		}
	}

	// GETTERS & SETTERS

	public ArrayList<ArrayList<Jugador>> getJugadores() {
		ArrayList<ArrayList<Jugador>> list = new ArrayList<ArrayList<Jugador>>();
		list.add(getArqueros());
		list.add(getDefensores());
		list.add(getMediocampistas());
		list.add(getDelanteros());
		return list;
	}

	public void removeJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
		String posicionJugador = jugadorSeleccionado.getPosicion();
		if (posicionJugador.equals("defensa")) {
			getDefensores().remove(jugadorSeleccionado);
		} else if (posicionJugador.equals("arquero")) {
			getArqueros().remove(jugadorSeleccionado);
		} else if (posicionJugador.equals("delantero")) {
			getDelanteros().remove(jugadorSeleccionado);
		} else if (posicionJugador.equals("medioCampista")) {
			getMediocampistas().remove(jugadorSeleccionado);
		}
	}

	public void addJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
		String posicionJugador = jugadorSeleccionado.getPosicion();
		if (posicionJugador.equals("defensa")) {
			getDefensores().add(jugadorSeleccionado);
		} else if (posicionJugador.equals("arquero")) {
			getArqueros().add(jugadorSeleccionado);
		} else if (posicionJugador.equals("delantero")) {
			getDelanteros().add(jugadorSeleccionado);
		} else if (posicionJugador.equals("medioCampista")) {
			getMediocampistas().add(jugadorSeleccionado);
		}
	}

	public float getPresupuestoDisponible() {
		return presupuestoDisponible;
	}

	private void setPresupuestoDisponible(float presupuestoDisponible) {
		this.presupuestoDisponible = presupuestoDisponible;
	}

	public int getPuntosEquipo() {
		return puntosEquipo;
	}

	public AlineacionTitular getAlineacionTitular() {
		return alineacionTitular;
	}

	public ArrayList<Jugador> getArqueros() {
		return arqueros;
	}

	public ArrayList<Jugador> getDefensores() {
		return defensores;
	}

	public ArrayList<Jugador> getMediocampistas() {
		return mediocampistas;
	}

	public ArrayList<Jugador> getDelanteros() {
		return delanteros;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (this.getPuntosEquipo() > ((EquipoFantasia) o).getPuntosEquipo())
			return -1;
		if (this.getPuntosEquipo() == ((EquipoFantasia) o).getPuntosEquipo())
			return 0;
		else
			return 1;
	}

}
