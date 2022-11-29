package uniandes.dpoo.consola;

import java.io.*;
import java.util.ArrayList;

import uniandes.dpoo.modelo.*;

public class Aplicacion {
	private Profile userInstance = null;
	private Temporada instanciaTemporda = null;

	public static void main(String[] args) {

		Aplicacion consola = new Aplicacion();
		consola.ejecutarOpcion();
	}

	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public void mostrarMenuUsuarioNoRegistrado() {
		System.out.println("\nOptions of the app\n");
		System.out.println("1. Iniciar sesion con usuario existente");
		System.out.println("2. Registrar nuevo usuario");
		System.out.println("3. Exit the program");
	}

	public void mostrarMenuUsuarioRegistrado() {
		System.out.println("\nOptions of the app\n");
		if (instanciaTemporda.userTieneEquipoFantasia() == false) {
			System.out.println("0. Crear equipo de fantasia");
			System.out.println("1. cerrar sesion");
			System.out.println("2. Exit the program");
		} else {
			System.out.println("1. Presupuesto Disponible");
			System.out.println("2. Comprar Jugador");
			System.out.println("3. Vender Jugador");
			System.out.println("4. Añadir jugador a equipo titular");
			System.out.println("5. Remover jugador de equipo titular");
			System.out.println("6. Ranking equipos");
			System.out.println("7. Top Jugadores");
			System.out.println("8. cerrar sesion");
			System.out.println("9. Exit the program");
		}
	}

	public void mostrarMenuAdmin() {
		System.out.println("\nOptions of the app\n");
		System.out.println("1. Cargar Temporada");
		System.out.println("2. Cargar Partido");
		System.out.println("3. cerrar sesion");
		System.out.println("4. Exit the program");
	}

	// modificando
	public void ejecutarOpcion() {
		System.out.println("Pedidos.com\n");
		this.instanciaTemporda = Temporada.recuperar();
		if (this.instanciaTemporda == null) {
			this.instanciaTemporda = new Temporada();
			Temporada.guardar(this.instanciaTemporda);
		}

		boolean continuar = true;
		while (continuar) {
			// try
			// {
			int opcion_seleccionada;
			if (userInstance == null) {
				mostrarMenuUsuarioNoRegistrado();
				opcion_seleccionada = Integer.parseInt(input("Please select an option"));
				if (opcion_seleccionada == 1) {
					iniciarSesion();
				} else if (opcion_seleccionada == 2) {
					registrarUsuario();
				} else if (opcion_seleccionada == 3) {
					System.out.println("Saliendo de la aplicación ...");
					continuar = salirAplicacion();
				} else {
					System.out.println("Por favor seleccione una opción válida.");
				}
			} else if (userInstance instanceof User) {
				mostrarMenuUsuarioRegistrado();
				opcion_seleccionada = Integer.parseInt(input("Please select an option"));
				if (instanciaTemporda.userTieneEquipoFantasia() == false) {
					if (opcion_seleccionada == 0) {
						crearEquipoDeFantasia();
					} else if (opcion_seleccionada == 1) {
						System.out.println("Cerrando sesion...");
						cerrarSesion();
					} else if (opcion_seleccionada == 2) {
						System.out.println("Saliendo de la aplicación ...");
						continuar = salirAplicacion();
					} else {
						System.out.println("Por favor seleccione una opción válida.");
					}
				} else {
					if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 1) {
						getPresupuestoDisponible();

					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 2) {
						comprarJugador();

					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 3) {
						venderJugador();

					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 4) {
						asignarJugadorAlineacionTitular();

					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 5) {
						eliminarJugadorAlineacionTitular();
					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 6) {
						mostrarRanking();
					} else if (instanciaTemporda.userTieneEquipoFantasia() == true & opcion_seleccionada == 7) {
						mostrarRankingJugadores();
					} else if (opcion_seleccionada == 8) {
						System.out.println("Cerrando sesion...");
						cerrarSesion();
					} else if (opcion_seleccionada == 9) {
						System.out.println("Saliendo de la aplicación ...");
						continuar = salirAplicacion();
					} else {
						System.out.println("Por favor seleccione una opción válida.");
					}
				}

			} else if (userInstance instanceof Admin) {
				mostrarMenuAdmin();
				opcion_seleccionada = Integer.parseInt(input("Please select an option"));
				if (opcion_seleccionada == 1) {
					cargarTemporada();
				} else if (opcion_seleccionada == 2) {
					cargarPartido();
				} else if (opcion_seleccionada == 3) {
					System.out.println("Cerrando sesion...");
					cerrarSesion();
				} else if (opcion_seleccionada == 4) {
					System.out.println("Saliendo de la aplicación ...");
					continuar = salirAplicacion();
				} else {
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
		}
	}

	public boolean salirAplicacion() {
		boolean continuar = false;
		Temporada.guardar(instanciaTemporda);
		return continuar;
	}

	public void cerrarSesion() {
		userInstance = null;
		instanciaTemporda.setUserUsingApp(null);
	}

	public void iniciarSesion() {
		String nombreUsuario = input("Escriba su nombre de usuario");
		String claveUsuario = input("Escriba su clave");
		userInstance = instanciaTemporda.iniciarSesion(nombreUsuario, claveUsuario);
		instanciaTemporda.setUserUsingApp((Profile) userInstance);
	}

	public void registrarUsuario() {
		String nombreUsuario = input("Escriba su nombre de usuario");
		String claveUsuario = input("Escriba su clave");
		instanciaTemporda.registrarUsuario(nombreUsuario, claveUsuario);
	}

	public void cargarTemporada() {
		String nombreArchivoJugadores = input("Escriba el nombre del archivo jugadores");
		String nombreArchivoFechas = input("Escriba el nombre del archivo fechas");
		int presupuesto = Integer.parseInt(input("Escriba dinero inicial para todos los equipos de fantasia"));

		try {
			instanciaTemporda.cargarDatosTemporada(nombreArchivoJugadores, nombreArchivoFechas, presupuesto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarPartido() {
		String nombreArchivoJugadores = input("Escriba el nombre del archivo");
		instanciaTemporda.cargarDatosPartido(nombreArchivoJugadores);
	}

	public void crearEquipoDeFantasia() {
		String nombreEquipo = input("Escriba el nombre de su equipo de fantasia");
		instanciaTemporda.crearEquipoFantasia(nombreEquipo);
	}

	public void comprarJugador() {
		Jugador jugadorSeleccionado = seleccionarJugadorGeneral();
		instanciaTemporda.comprarJugadorEquipoFantasia(jugadorSeleccionado);
	}

	public void venderJugador() {
		Jugador jugadorSeleccionado = getJugadorEquipoFantasia();
		instanciaTemporda.venderJugadorEquipoFantasia(jugadorSeleccionado);
	}

	public void getPresupuestoDisponible() {
		float dineroDisponible = instanciaTemporda.userPresupuestoDisponible();
		System.out.println("El presupuesto disponible de su equipo de fantasia es: " + dineroDisponible);
	}

	public Jugador seleccionarJugadorGeneral() {
		EquipoReal equipo = seleccionarEquipoReal();
		return seleccionarJugador(equipo);
	}

	public EquipoReal seleccionarEquipoReal() {
		EquipoReal equipo = null;
		ArrayList<EquipoReal> equiposReales = instanciaTemporda.getEquiposReales();
		System.out.println("Selecciona el equipo del cual el jugador pertenece");
		for (int i = 0; i < equiposReales.size(); i++) {
			String nombreEquipo = equiposReales.get(i).getNombre();
			System.out.println((i + 1) + ". " + nombreEquipo);
		}
		int opcion = Integer.parseInt(input("Escriba su opcion"));
		if (opcion > equiposReales.size() & opcion < 0) {
			equipo = seleccionarEquipoReal();
		} else {
			equipo = equiposReales.get(opcion - 1);
		}
		return equipo;
	}

	public Jugador seleccionarJugador(EquipoReal equipo) {
		Jugador jugador = null;
		ArrayList<Jugador> jugadoresEquipo = instanciaTemporda.getJugadoresEquipoReal(equipo);
		for (int i = 0; i < jugadoresEquipo.size(); i++) {
			String nombreJugador = jugadoresEquipo.get(i).getNombre();
			String posicionJugador = jugadoresEquipo.get(i).getPosicion();
			System.out.println((i + 1) + ". " + nombreJugador + " - " + posicionJugador);
		}
		int opcion = Integer.parseInt(input("Escriba su opcion"));

		if (opcion > jugadoresEquipo.size() & opcion < 0) {
			jugador = seleccionarJugador(equipo);
		} else {
			jugador = jugadoresEquipo.get(opcion - 1);
		}

		return jugador;
	}

	public void asignarJugadorAlineacionTitular() {
		Jugador jugador = getJugadorEquipoFantasia();
		instanciaTemporda.asignarJugadorAlineacionTitular(jugador);
	}

	public void eliminarJugadorAlineacionTitular() {
		Jugador jugador = seleccionarJugadorAlineacionTitular();
		instanciaTemporda.eliminarJugadorAlineacionTitular(jugador);
	}

	public Jugador seleccionarJugadorAlineacionTitular() {
		ArrayList<Jugador> jugadoresAlineacion = instanciaTemporda.getJugadoresAlineacionTitular();
		Jugador jugador = null;

		for (int i = 0; i < jugadoresAlineacion.size(); i++) {
			String nombreJugador = jugadoresAlineacion.get(i).getNombre();
			System.out.println((i + 1) + ". " + nombreJugador);
		}
		int opcion = Integer.parseInt(input("Escriba su opcion 2"));

		if (opcion > jugadoresAlineacion.size() & opcion < 0) {
			getJugadorEquipoFantasia();
		} else {
			jugador = jugadoresAlineacion.get(opcion - 1);
		}

		return jugador;
	}

	public ArrayList<Jugador> getTipoJugador() {
		ArrayList<ArrayList<Jugador>> jugadoresEquipo = instanciaTemporda.getJugadoresEquipoFantasia();
		ArrayList<Jugador> lista = null;

		ArrayList<Jugador> listaArquero = jugadoresEquipo.get(0);
		ArrayList<Jugador> listaDefensa = jugadoresEquipo.get(1);
		ArrayList<Jugador> listaMedioCampista = jugadoresEquipo.get(2);
		ArrayList<Jugador> listaDelantero = jugadoresEquipo.get(3);

		System.out.println("1. Arquero - " + listaArquero.size());
		System.out.println("2. Defensa - " + listaDefensa.size());
		System.out.println("3. Medio Campista - " + listaMedioCampista.size());
		System.out.println("4. Delantero - " + listaDelantero.size());
		int opcion = Integer.parseInt(input("Escriba su opcion"));
		if (opcion == 1) {
			lista = jugadoresEquipo.get(0);
		} else if (opcion == 2) {
			lista = jugadoresEquipo.get(1);
		} else if (opcion == 3) {
			lista = jugadoresEquipo.get(2);
		} else if (opcion == 4) {
			lista = jugadoresEquipo.get(3);
		} else {
			System.out.println("Seleccione una opcion valida");
			getTipoJugador();
		}
		if (lista.size() == 0) {
			System.out.println("jugadores en esa lista" + lista);
			ejecutarOpcion();
		}

		return lista;
	}

	public Jugador getJugadorEquipoFantasia() {
		Jugador jugador = null;
		ArrayList<Jugador> jugadoresEquipo = getTipoJugador();

		for (int i = 0; i < jugadoresEquipo.size(); i++) {
			String nombreJugador = jugadoresEquipo.get(i).getNombre();
			System.out.println((i + 1) + ". " + nombreJugador);
		}
		int opcion = Integer.parseInt(input("Escriba su opcion 2"));

		if (opcion > jugadoresEquipo.size() & opcion < 0) {
			getJugadorEquipoFantasia();
		} else {
			jugador = jugadoresEquipo.get(opcion - 1);
		}

		return jugador;
	}

	public void mostrarRanking() {
		estadisticas estadisticas = new estadisticas();
		for (Profile profile : this.instanciaTemporda.getUsuarios().values()) {
			if (profile instanceof User) {
				User user = (User) profile;
				estadisticas.anadirEquipo(user.getEquipoFantasia());
			}
		}
		ArrayList<EquipoFantasia> rankingEquipos = estadisticas.getRanking();
		for (EquipoFantasia equipo : rankingEquipos) {
			System.out.println(equipo.getNombre() + " - " + equipo.getPuntosEquipo());
		}

	}

	public void mostrarRankingJugadores() {
		estadisticas estadisticas = new estadisticas();
		for (EquipoReal equipo : this.instanciaTemporda.getEquiposReales()) {
			estadisticas.anadirEquipoReal(equipo);
		}
		ArrayList<Jugador> rankingJugadores = estadisticas.getRankingJugadores();
		for (Jugador jugador : rankingJugadores) {
			System.out.println(jugador.getNombre() + " - " + jugador.getPuntajeAcumuladoJugador());
		}
	}

}
