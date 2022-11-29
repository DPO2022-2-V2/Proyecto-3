package uniandes.dpoo.modelo;

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class Partido implements Serializable {

	private EquipoReal equipoLocal;
	private EquipoReal equipoVisitante;
	private LocalDateTime fechaPartido;
	private int golesEquipoLocal = 0;
	private int golesEquipoVisitante = 0;
	private String id;

	public Partido(EquipoReal equipoLocal, EquipoReal equipoVisitante, LocalDateTime fechaPartido, String idDado) {
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.fechaPartido = fechaPartido;
		this.id = idDado;
	}

	public void AsignarPuntajePartidoJugadores(int golesEquipoLocal, int golesEquipoVisitante,
			ArrayList<String> datosPartido) {
		EquipoReal instanciaEquipoLocal = getEquipoLocal();
		EquipoReal instanciaEquipoVisitante = getEquipoVisitante();
		HashMap<String, Jugador> jugadoresEquipoLocal = instanciaEquipoLocal.getJugadores();
		HashMap<String, Jugador> jugadoresEquipoVisitante = instanciaEquipoVisitante.getJugadores();
		String linea;
		int jugadoresRegistrados = 0;

		setGolesEquipoLocal(golesEquipoLocal);
		setGolesEquipoVisitante(golesEquipoVisitante);

		for (int i = 2; i < datosPartido.size(); i++) {
			linea = datosPartido.get(i);
			String[] txtPartido = linea.split(";");

			String nombreInstanciaJugador = txtPartido[1];
			String posicionInstanciaJugador = txtPartido[2];
			int minutosJugadosInstanciaJugador = Integer.parseInt(txtPartido[3]);//
			int golesAnotadosInstanciaJugador = Integer.parseInt(txtPartido[4]);//
			int asistenciasInstanciaJugador = Integer.parseInt(txtPartido[5]);//
			int penaltiesAtajadosInstanciaJugador = Integer.parseInt(txtPartido[6]);//
			int penaltiesErradosInstanciaJugador = Integer.parseInt(txtPartido[7]);//
			int tarjetasAmarillasInstanciaJugador = Integer.parseInt(txtPartido[8]);//
			int tarjetasRojasInstanciaJugador = Integer.parseInt(txtPartido[9]);
			int autogolesInstanciaJugador = Integer.parseInt(txtPartido[10]);
			boolean gano = false;

			String nombreEquipoLocal = instanciaEquipoLocal.getNombre();
			String nombreEquipoVisitante = instanciaEquipoVisitante.getNombre();

			if (nombreEquipoLocal.equals(txtPartido[0])) {
				if (golesEquipoLocal > golesEquipoVisitante) {
					gano = true;
				}
				Jugador instanciaJugador = jugadoresEquipoLocal.get(nombreInstanciaJugador);
				instanciaJugador.asignarPuntosJugador(gano, minutosJugadosInstanciaJugador,
						golesAnotadosInstanciaJugador,
						golesEquipoVisitante, asistenciasInstanciaJugador, penaltiesAtajadosInstanciaJugador,
						penaltiesErradosInstanciaJugador, tarjetasAmarillasInstanciaJugador,
						tarjetasRojasInstanciaJugador, autogolesInstanciaJugador);

			} else if (nombreEquipoVisitante.equals(txtPartido[0])) {
				if (golesEquipoLocal < golesEquipoVisitante) {
					gano = true;
				}
				Jugador instanciaJugador = jugadoresEquipoVisitante.get(nombreInstanciaJugador);
				instanciaJugador.asignarPuntosJugador(gano, minutosJugadosInstanciaJugador,
						golesAnotadosInstanciaJugador,
						golesEquipoLocal, asistenciasInstanciaJugador, penaltiesAtajadosInstanciaJugador,
						penaltiesErradosInstanciaJugador, tarjetasAmarillasInstanciaJugador,
						tarjetasRojasInstanciaJugador, autogolesInstanciaJugador);
			}
			jugadoresRegistrados += 1;
		}
		if ((jugadoresEquipoLocal.size() + jugadoresEquipoVisitante.size()) > jugadoresRegistrados) {
			System.out.println("Menor cantidad de datos registrados");
		} else if ((jugadoresEquipoLocal.size() + jugadoresEquipoVisitante.size()) < jugadoresRegistrados) {
			System.out.println("Mayor cantidad de datos registrados");
		} else if ((jugadoresEquipoLocal.size() + jugadoresEquipoVisitante.size()) == jugadoresRegistrados) {
			System.out.println("Datos registrados de manera satisfactoria");
		}
	}

	// GETTERS
	public EquipoReal getEquipoLocal() {
		return equipoLocal;
	}

	public EquipoReal getEquipoVisitante() {
		return equipoVisitante;
	}

	public LocalDateTime getFechaPartido() {
		return fechaPartido;
	}

	public int getGolesEquipoLocal() {
		return golesEquipoLocal;
	}

	public int getGolesEquipoVisitante() {
		return golesEquipoVisitante;
	}

	private void setGolesEquipoLocal(int golesEquipoLocal) {
		this.golesEquipoLocal = golesEquipoLocal;
	}

	private void setGolesEquipoVisitante(int golesEquipoVisitante) {
		this.golesEquipoVisitante = golesEquipoVisitante;
	}

	public String getId() {
		return id;
	}

}
