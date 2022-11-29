package uniandes.dpoo.modelo;

import java.io.*;
import java.util.*;

import uniandes.dpoo.persistencia.ArchivoPartido;
import uniandes.dpoo.persistencia.ArchivoPersistensia;
import uniandes.dpoo.persistencia.ArchivoTemporada;

import java.time.*;
import java.time.format.*;
import uniandes.dpoo.persistencia.*;

public class Temporada implements Serializable {
	private HashMap<String, EquipoReal> equipos = new HashMap<String, EquipoReal>();
	private HashMap<String, Profile> usuariosSistema = new HashMap<String, Profile>();
	private HashMap<String, Partido> partidos = new HashMap<String, Partido>();
	private Admin Administrador;
	private Profile usuarioUsandoSistema = null;
	private int presupuestoEquiposFantasia = 0;
	private ArrayList<ArrayList<Integer>> estadisticaPosicion = new ArrayList<ArrayList<Integer>>();
	
	public Temporada() {
		Administrador = new Admin("Admin", "Admin");
		usuariosSistema.put(Administrador.getNombre(), Administrador);
	}

	public static void guardar(Temporada obj) {
		ArchivoPersistensia per = new ArchivoPersistensia();
		try {
			per.guardar(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Temporada recuperar() {
		ArchivoPersistensia per = new ArchivoPersistensia();
		Temporada recuperar = null;
		try {
			recuperar = per.recuperar();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return recuperar;
	}
	// LOGIN SYSTEM

	public void registrarUsuario(String nombreUsuario, String claveUsuario) {
		if (usuariosSistema.containsKey(nombreUsuario) == false) {
			User instanciaUser = new User(nombreUsuario, claveUsuario);
			usuariosSistema.put(nombreUsuario, instanciaUser);
		} else {
			System.out.println("ERROR: El usuario ya existe");
		}
	}

	public Profile iniciarSesion(String nombreUsuario, String claveUsuario) {
		if (usuariosSistema.containsKey(nombreUsuario)) {
			Profile instanciaUser = usuariosSistema.get(nombreUsuario);
			if (claveUsuario.equals(instanciaUser.getclave())) {
				return instanciaUser;
			}

		}
		return null;
	}

	// CARGA DATOS

	public void cargarDatosTemporada(String nombreArchivoJugadores, String nombreArchivoFechas, int presupuesto)
			throws IOException {
		this.presupuestoEquiposFantasia = presupuesto;
		ArchivoTemporada ArchivoTemporada = new ArchivoTemporada();
		File archivoJugadores = new File("../Files_App/" + nombreArchivoJugadores);
		File archivoFechas = new File("../Files_App/" + nombreArchivoFechas);
		ArrayList<String>[] datos = ArchivoTemporada.cargarDatosTemporada(archivoJugadores, archivoFechas);
		String linea;

		ArrayList<String> datosJugadores = datos[0];
		ArrayList<String> datosFechas = datos[1];

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		for (int i = 1; i < datosJugadores.size(); i++) {
			linea = datosJugadores.get(i);
			EquipoReal instanciaEquipo;
			String[] partesTexto = linea.split(";");
			String equipoJugador = partesTexto[0];

			String txtJugador = partesTexto[1];
			String[] partesJugador = txtJugador.split("%");

			String nombreJugador = partesJugador[0];
			float precioJugador = Float.parseFloat(partesJugador[1]);
			String posicionJugador = partesJugador[2];

			if (getEquipos().containsKey(equipoJugador)) {
				instanciaEquipo = getEquipos().get(equipoJugador);
			} else {
				instanciaEquipo = new EquipoReal(equipoJugador, this);
				equipos.put(equipoJugador, instanciaEquipo);
			}
			instanciaEquipo.agregarJugador(nombreJugador, posicionJugador, precioJugador);
		}

		// CARGA FECHAS
		for (int j = 1; j < datosFechas.size(); j++) {
			linea = datosFechas.get(j);
			String[] txtFecha = linea.split(",");
			String idPartido = txtFecha[0];
			String timeDate = txtFecha[1] + " " + txtFecha[2];
			LocalDateTime dateTime = LocalDateTime.parse(timeDate, formatter);
			EquipoReal equipoLocal = getEquipos().get(txtFecha[3]);
			EquipoReal equipoVisitante = getEquipos().get(txtFecha[4]);
			getPartidos().put(idPartido, new Partido(equipoLocal, equipoVisitante, dateTime, idPartido));
		}

	}

	public void cargarDatosPartido(String nombreArhivoPartido) {
		ArchivoPartido ArchivoPartido = new ArchivoPartido();
		File archivoPartido = new File("../Files_App/" + nombreArhivoPartido);
		ArrayList<String> datosPartido = ArchivoPartido.cargarDatosPartido(archivoPartido);
		String[] txtPartido;
		String linea;

		ArrayList<Integer> newEstadisticasFecha = new ArrayList<Integer>();
		newEstadisticasFecha.add(0);
		newEstadisticasFecha.add(0);
		newEstadisticasFecha.add(0);
		newEstadisticasFecha.add(0);
		this.estadisticaPosicion.add(newEstadisticasFecha);
		
		linea = datosPartido.get(1);
		txtPartido = linea.split(";");
		HashMap<String, Partido> partidos = getPartidos();
		Partido instanciaPartido = partidos.get(txtPartido[0]);
		int golesEquipoLocal = Integer.parseInt(txtPartido[1]);
		int golesEquipoVisitante = Integer.parseInt(txtPartido[2]);

		instanciaPartido.AsignarPuntajePartidoJugadores(golesEquipoLocal, golesEquipoVisitante, datosPartido);

	}

	public void setUserUsingApp(Profile usuario) {
		this.usuarioUsandoSistema = usuario;
	}

	public ArrayList<ArrayList<Jugador>> getJugadoresEquipoFantasia() {
		return getEquipoFantasia().getJugadores();
	}

	public ArrayList<Jugador> getJugadoresAlineacionTitular() {
		Collection<Jugador> val = getAlineacionTitular().getJugadores().values();
		ArrayList<Jugador> listOfValues = new ArrayList<>(val);
		return listOfValues;
	}

	public ArrayList<EquipoReal> getEquiposReales() {
		Collection<EquipoReal> val = getEquipos().values();
		ArrayList<EquipoReal> listOfValues = new ArrayList<>(val);
		return listOfValues;
	}

	public ArrayList<Jugador> getJugadoresEquipoReal(EquipoReal equipo) {
		Collection<Jugador> val = equipo.getJugadores().values();
		ArrayList<Jugador> listOfValues = new ArrayList<>(val);
		return listOfValues;
	}

	// FUNCIONALIDADES
	public void crearEquipoFantasia(String nombreEquipoFantasia) {
		User usuario = (User) getUsuarioUsandoSistema();
		usuario.crearEquipoFantasia(nombreEquipoFantasia, getPresupuestoEquipoFantasia());
	}

	public void comprarJugadorEquipoFantasia(Jugador jugador) {
		getEquipoFantasia().comprarJugador(jugador);
	}

	public void venderJugadorEquipoFantasia(Jugador jugador) {
		getEquipoFantasia().venderJugador(jugador);
	}

	public void asignarJugadorAlineacionTitular(Jugador jugador) {
		getAlineacionTitular().agregarJugadorAlineacionTitular(jugador);
	}

	public void eliminarJugadorAlineacionTitular(Jugador jugador) {
		getAlineacionTitular().eliminarJugadorAlineacionTitular(jugador);
	}

	public void elegirCapitanAlineacionTitular(Jugador jugador) {
		getAlineacionTitular().elegirCapitanAlineacionTitular(jugador);
	}

	public boolean userTieneEquipoFantasia() {
		User usuario = (User) getUsuarioUsandoSistema();
		EquipoFantasia val = usuario.getEquipoFantasia();
		boolean res = false;
		if (val != null) {
			res = true;
		}
		return res;
	}

	public float userPresupuestoDisponible() {
		User usuario = (User) getUsuarioUsandoSistema();
		EquipoFantasia val = usuario.getEquipoFantasia();
		return val.getPresupuestoDisponible();
	}

	// GETTERS AND SETTERS

	public HashMap<String, EquipoReal> getEquipos() {
		return equipos;
	}

	public HashMap<String, Partido> getPartidos() {
		return partidos;
	}

	public Profile getUsuarioUsandoSistema() {
		return usuarioUsandoSistema;
	}

	public EquipoFantasia getEquipoFantasia() {
		User usuario = (User) getUsuarioUsandoSistema();
		EquipoFantasia equipoUsuario = usuario.getEquipoFantasia();
		return equipoUsuario;
	}

	public AlineacionTitular getAlineacionTitular() {
		User usuario = (User) getUsuarioUsandoSistema();
		EquipoFantasia equipoUsuario = usuario.getEquipoFantasia();
		AlineacionTitular alineacionUsuario = equipoUsuario.getAlineacionTitular();
		return alineacionUsuario;
	}

	public int getPresupuestoEquipoFantasia() {
		return this.presupuestoEquiposFantasia;
	}

	public HashMap<String, Profile> getUsuarios() {
		return this.usuariosSistema;
	}

	public void addPointsPosition(int puntos, String posicion) {
		ArrayList<ArrayList<Integer>> lst = this.estadisticaPosicion;
		ArrayList<Integer> lstFecha = lst.get(lst.size() - 1);
		if (posicion.equals("arquero")) {
			int puntajeFinal = lstFecha.get(0) + puntos;
			lstFecha.set(0, puntajeFinal);
		} else if(posicion.equals("defensa")) {
			int puntajeFinal = lstFecha.get(1) + puntos;
			lstFecha.set(1, puntajeFinal);
		} else if (posicion.equals("medioCampista")) {
			int puntajeFinal = lstFecha.get(2) + puntos;
			lstFecha.set(2, puntajeFinal);
		} else if (posicion.equals("delantero")) {
			int puntajeFinal = lstFecha.get(3) + puntos;
			lstFecha.set(3, puntajeFinal);
		}
		// Verificar si pone los valores dinamicamente
	}

	public ArrayList<Integer> getBiggerNumbersEstadisticaPosicion() {
		int arquero = 0;
		int defensor = 0;
		int medioCampista = 0;
		int delantero = 0;

		for (ArrayList<Integer> fecha: this.estadisticaPosicion) {
			if (fecha.get(0) > arquero) {
				arquero = fecha.get(0);
			}
			if (fecha.get(1) > defensor) {
				defensor = fecha.get(1);
			}
			if (fecha.get(2) > medioCampista) {
				medioCampista = fecha.get(2);
			}
			if (fecha.get(3) > delantero) {
				delantero = fecha.get(3);
			}
		}
		ArrayList<Integer> val = new ArrayList<Integer>();
		val.add(arquero);
		val.add(defensor);
		val.add(medioCampista);
		val.add(delantero);
		return val;
	}

	public ArrayList<ArrayList<Integer>> getEstadisticaPosicion() {
		return this.estadisticaPosicion;
	}


	public ArrayList<ArrayList<Integer>> getEstadisticaPorPosicion() {
		return this.estadisticaPosicion;
	}
}
