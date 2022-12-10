package uniandes.dpoo.controlador;

import java.io.IOException;
import java.util.ArrayList;

import uniandes.dpoo.modelo.*;

public class Controlador {
    private Temporada temporada;

    public Controlador() {
        this.temporada = Temporada.recuperar();
		if (this.temporada == null) {
			this.temporada = new Temporada();
			Temporada.guardar(this.temporada);
		}
    }

    public void salirPrograma() {
        Temporada.guardar(temporada);
        System.exit(1);
    }

    public void cerrarSesion() {
        this.temporada.setUserUsingApp(null);
    }

    public Profile iniciarSesion(String user, String password) {
        Profile userInstance = this.temporada.iniciarSesion(user, password);
		this.temporada.setUserUsingApp((Profile) userInstance);
        return userInstance;
    }

    public void register(String user, String password) {
        this.temporada.registrarUsuario(user, password);
    }

    public boolean userTieneEquipoFantasia() {
        return this.temporada.userTieneEquipoFantasia();
    }

    public void crearEquipoFantasia(String nombreEquipo) {
        this.temporada.crearEquipoFantasia(nombreEquipo);
    }

    public boolean tieneEquipoConMismoNombreEquiposUser(String nombre) {
		return temporada.tieneEquipoConMismoNombreEquiposUser(nombre);
	}

    public void cargarPartido(String filePartido) {
        this.temporada.cargarDatosPartido(filePartido);
    }

    public float getDineroDisponible() {
        return this.temporada.userPresupuestoDisponible();
    }

    public ArrayList<EquipoReal> getEquiposReales() {
        return this.temporada.getEquiposReales();
    }

    public ArrayList<Jugador> getJugadoresEquipoReal(EquipoReal equipo) {
        return this.temporada.getJugadoresEquipoReal(equipo);
    }

    public void comprarJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
        this.temporada.comprarJugadorEquipoFantasia(jugadorSeleccionado);
    }

    public ArrayList<ArrayList<Jugador>> getJugadoresEquipoFantasia() {
        return this.temporada.getJugadoresEquipoFantasia();
    }

    public void venderJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
        this.temporada.venderJugadorEquipoFantasia(jugadorSeleccionado);
    }

    public void asignarJugadorAlineacionTitular(Jugador jugador) {
        this.temporada.asignarJugadorAlineacionTitular(jugador);
    }

    public ArrayList<Jugador> getJugadoresAlineacionTitular() {
        return this.temporada.getJugadoresAlineacionTitular();
    }

    public void eliminarJugadorAlineacionTitular(Jugador jugador) {
        this.temporada.eliminarJugadorAlineacionTitular(jugador);
    }

    public ArrayList<EquipoFantasia> getRankingEquipos() {
        estadisticas estadisticas = new estadisticas();
        for (Profile profile : this.temporada.getUsuarios().values()) {
            if (profile instanceof User) {
                User user = (User) profile;
				for (EquipoFantasia instance : user.getEquiposFantasia()) {
					estadisticas.anadirEquipo(instance);
				}
            }
        }
        return estadisticas.getRanking();
    }

    public ArrayList<Jugador> getRankingJugadores() {
        estadisticas estadisticas = new estadisticas();
        for (EquipoReal equipo : this.temporada.getEquiposReales()) {
			estadisticas.anadirEquipoReal(equipo);
		}
        return estadisticas.getRankingJugadores();
    }

    public ArrayList<ArrayList<Integer>> getEstadisticaPosicion() {
		return this.temporada.getEstadisticaPosicion();
	}

    public ArrayList<Integer> getBiggerNumbersEstadisticaPosicion() {
		return this.temporada.getBiggerNumbersEstadisticaPosicion();
	}

    public void elegirCapitanAlineacionTitular(Jugador jugador) {
		this.temporada.elegirCapitanAlineacionTitular(jugador);
	}

    public void setUsingEquipoFantasia(EquipoFantasia equipo) {	
		this.temporada.setUsingEquipoFantasia(equipo);
	}

    public ArrayList<EquipoFantasia> getUserEquiposFantasia() {
		return this.temporada.getUserEquiposFantasia();
	}

    public boolean getTerminoTemporada() {
        return this.temporada.getTerminoTemporada();
    }

    public void setTerminoTemporada() {
        this.temporada.setTerminoTemporada();
    }

    public void terminarTemporada() {
        this.temporada.terminarTemporada();
    }

    public void cargarTemporada(String nombreArchivoJugadores, String nombreArchivoFechas, int presupuesto) {
        try {
            this.temporada.cargarDatosTemporada(nombreArchivoJugadores, nombreArchivoFechas, presupuesto);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<EquipoFantasia> exportTopTeams() {
        return this.temporada.exportTopTeams();
    }

    public ArrayList<Integer> getDatosCompraVenta() {
        return this.temporada.getDatosCompraVenta();
    }

    public EquipoFantasia getEquipoFantasia() {
        return this.temporada.getEquipoFantasia();
    }

    

    
}
