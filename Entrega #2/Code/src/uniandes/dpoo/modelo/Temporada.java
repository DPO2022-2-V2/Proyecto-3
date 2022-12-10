package uniandes.dpoo.modelo;

import java.io.*;
import java.util.*;

import uniandes.dpoo.persistencia.ArchivoPartido;
import uniandes.dpoo.persistencia.ArchivoPersistensia;
import uniandes.dpoo.persistencia.ArchivoTemporada;

import java.time.*;
import java.time.format.*;

public class Temporada implements Serializable {
	private HashMap<String, EquipoReal> equipos = new HashMap<String, EquipoReal>();
	private HashMap<String, Profile> usuariosSistema = new HashMap<String, Profile>();
	private HashMap<String, Partido> partidos = new HashMap<String, Partido>();
	private Admin Administrador;
	private Profile usuarioUsandoSistema = null;
	private int presupuestoEquiposFantasia = 0;
	private ArrayList<ArrayList<Integer>> estadisticaPosicion = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> datosCompraVenta = new ArrayList<Integer>();
    private boolean terminoTemporada;
	
	public Temporada() {
		Administrador = new Admin("Admin", "Admin");
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
		datosCompraVenta.add(0);
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

		ArrayList<EquipoFantasia> mejoresEquiposFecha = getMejorEquipoFecha();
		for (EquipoFantasia instancia : mejoresEquiposFecha) {
			instancia.addPuntosEquipo(10);
			System.out.println(instancia.getNombre() + " - " + "se le sumaron puntos");
		}

		EquipoReal equipoPerdedor = null;
		EquipoReal equipoGanador = null;
		boolean ganaronEquipo = false;
		boolean habiaJugador = false;
		if (golesEquipoLocal < golesEquipoVisitante) {
			equipoGanador = instanciaPartido.getEquipoVisitante();
			equipoPerdedor = instanciaPartido.getEquipoLocal();
		} else {
			equipoGanador = instanciaPartido.getEquipoLocal();
			equipoPerdedor = instanciaPartido.getEquipoVisitante();
		}

		if (equipoPerdedor != null) {
			for (Profile profile : this.getUsuarios().values()) {
				if (profile instanceof User) {
					User user = (User) profile;
					for (EquipoFantasia equipoDeFantasia : user.getEquiposFantasia()) {
						ganaronEquipo = false;
						habiaJugador = false;
						for (String NombreJugador : equipoDeFantasia.getAlineacionTitular().getJugadores().keySet()) {
							if (equipoGanador.getJugadores().containsKey(NombreJugador)) {
								habiaJugador = true;
							}
							if (equipoPerdedor.getJugadores().containsKey(NombreJugador)) {
								ganaronEquipo = true;
								break;
							}
						}
						if (ganaronEquipo == false && habiaJugador == true) {
							equipoDeFantasia.addPuntosEquipo(25);
						}
					}
				}
			}
		} else {
			for (Profile profile : this.getUsuarios().values()) {
				if (profile instanceof User) {
					User user = (User) profile;
					for (EquipoFantasia equipoDeFantasia : user.getEquiposFantasia()) {
						habiaJugador = false;
						for (Jugador NombreJugador : equipoDeFantasia.getAlineacionTitular().getJugadores().values()) {
							if ((instanciaPartido.getEquipoVisitante().getJugadores().containsKey(NombreJugador.getNombre()) || instanciaPartido.getEquipoLocal().getJugadores().containsKey(NombreJugador.getNombre()))) {
								habiaJugador = true;
							} else if ((instanciaPartido.getEquipoVisitante().getJugadores().containsKey(NombreJugador.getNombre()) || instanciaPartido.getEquipoLocal().getJugadores().containsKey(NombreJugador.getNombre()))) {
								habiaJugador = false;
								break;
							}
						}
						if (habiaJugador == true) {
							equipoDeFantasia.addPuntosEquipo(10);
						}
					}
				}
			}
		}
		

	}

	public void setUserUsingApp(Profile usuario) {
		this.usuarioUsandoSistema = usuario;
		if (usuario == null && usuario instanceof User) {
			User currentUser = (User) this.usuarioUsandoSistema;
			currentUser.cerrarSesion();
		}
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
		String posicionJugador = jugador.getPosicion();
		if (posicionJugador.equals("arquero")) {
			datosCompraVenta.set(3, datosCompraVenta.get(3) + 1);
		} else if (posicionJugador.equals("defensa")) {
			datosCompraVenta.set(2, datosCompraVenta.get(2) + 1);
		} else if (posicionJugador.equals("delantero")) {
			datosCompraVenta.set(0, datosCompraVenta.get(0) + 1);
		} else if (posicionJugador.equals("medioCampista")) {
			datosCompraVenta.set(1, datosCompraVenta.get(1) + 1);
		}
	}

	public void venderJugadorEquipoFantasia(Jugador jugador) {
		getEquipoFantasia().venderJugador(jugador);
		String posicionJugador = jugador.getPosicion();
		if (posicionJugador.equals("arquero")) {
			datosCompraVenta.set(7, datosCompraVenta.get(7) + 1);
		} else if (posicionJugador.equals("defensa")) {
			datosCompraVenta.set(6, datosCompraVenta.get(6) + 1);
		} else if (posicionJugador.equals("delantero")) {
			datosCompraVenta.set(4, datosCompraVenta.get(4) + 1);
		} else if (posicionJugador.equals("medioCampista")) {
			datosCompraVenta.set(5, datosCompraVenta.get(5) + 1);
		}
	}

	public ArrayList<Integer> getDatosCompraVenta() {
		return this.datosCompraVenta;
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

	public boolean tieneEquipoConMismoNombreEquiposUser(String nombre) {
		User usuario = (User) getUsuarioUsandoSistema();
		if (usuario != null) {
			return usuario.tieneEquipoConMismoNombre(nombre);
		} else {
			return false;
		}
	}

	public boolean userTieneEquipoFantasia() {
		User usuario = (User) getUsuarioUsandoSistema();
		// EquipoFantasia val = usuario.getEquipoFantasia();
		Integer val = usuario.getEquiposFantasia().size();
		boolean res = false;
		if (val != 0) {
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

	public void setUsingEquipoFantasia(EquipoFantasia equipo) {	
		User usuario = (User) getUsuarioUsandoSistema();
		usuario.setUsingEquipoFantasia(equipo);
	}

	public ArrayList<EquipoFantasia> getUserEquiposFantasia() {
		User usuario = (User) getUsuarioUsandoSistema();
		return usuario.getEquiposFantasia();
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

	public boolean getTerminoTemporada() {
        return this.terminoTemporada;
    }

    public void setTerminoTemporada() {
        this.terminoTemporada = true;
    }

	public void terminarTemporada(){
		estadisticas estadisticas = new estadisticas();
		for (Profile profile : this.getUsuarios().values()) {
			if (profile instanceof User) {
				User user = (User) profile;
				for (EquipoFantasia instance : user.getEquiposFantasia()) {
					estadisticas.anadirEquipo(instance);
				}
			}
		}
		ArrayList<EquipoFantasia> rankingEquipos = estadisticas.getRanking();

		int i = -1;
		int max = -1;
		for (EquipoFantasia instancia : rankingEquipos) {
			if (instancia.getPuntosEquipo() != max) {
				max = instancia.getPuntosEquipo();
				i += 1;
			}
			if (i == 0) {
				instancia.addPuntosEquipoSinRestricciones(10);
			} else if (i == 1) {
				instancia.addPuntosEquipoSinRestricciones(7);
			} else if (i == 2) {
				instancia.addPuntosEquipoSinRestricciones(5);
			} else {
				break;
			}
		}

	}

	public ArrayList<EquipoFantasia> getMejorEquipoFecha() {
		estadisticas estadisticas = new estadisticas();
		for (Profile profile : this.getUsuarios().values()) {
			if (profile instanceof User) {
				User user = (User) profile;
				for (EquipoFantasia instance : user.getEquiposFantasia()) {
					estadisticas.anadirEquipo(instance);
				}
			}
		}
		ArrayList<EquipoFantasia> rankingEquipos = estadisticas.getRanking();
		ArrayList<EquipoFantasia> equiposMejoresEnFecha = new ArrayList<EquipoFantasia>();
		int i = 0;
		int maxPoints = rankingEquipos.get(0).getPuntosEquipo();
		while (i < rankingEquipos.size() && rankingEquipos.get(i).getPuntosEquipo() == maxPoints) {
			equiposMejoresEnFecha.add(rankingEquipos.get(i));
			i++;
		}

		return equiposMejoresEnFecha;
	}

	public ArrayList<EquipoFantasia> exportTopTeams() {
		estadisticas estadisticas = new estadisticas();
		for (Profile profile : this.getUsuarios().values()) {
			if (profile instanceof User) {
				User user = (User) profile;
				for (EquipoFantasia equipo : user.getEquiposFantasia()) {
					estadisticas.anadirEquipo(equipo);
				}
			}
		}
		ArrayList<EquipoFantasia> rankingEquipos = estadisticas.getRanking();
		return rankingEquipos;
	}
}
