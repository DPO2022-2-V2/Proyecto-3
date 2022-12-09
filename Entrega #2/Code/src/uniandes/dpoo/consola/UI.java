package uniandes.dpoo.consola;
import uniandes.dpoo.controlador.Controlador;
import uniandes.dpoo.modelo.EquipoFantasia;
import uniandes.dpoo.modelo.EquipoReal;
import uniandes.dpoo.modelo.Jugador;
import uniandes.dpoo.modelo.Profile;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
//import org.knowm.xchart.XYChart;

public class UI extends JFrame implements ActionListener, Serializable {
    private JLabel tituloInterfaz;
    private JPanel centerPanel;
    private Controlador controlador;
    public UI() {
        this.controlador = new Controlador();

        this.setTitle("FutbolDeFantasia");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(550, 500);
        this.setLayout(new BorderLayout());

        tituloInterfaz = new JLabel("Futbol de Fantasia", SwingConstants.CENTER);
        tituloInterfaz.setFont(new Font("Dialog", Font.PLAIN, 26));

        centerPanel = new Main(this);
    
        this.add(tituloInterfaz, BorderLayout.PAGE_START);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        UI fPrincipal = new UI();
        fPrincipal.setVisible(true);
    }

    public void changeCenterPanel(JPanel panel) {
        this.remove(this.centerPanel);
        this.add(panel);
        this.centerPanel = panel;
        this.validate();
        this.repaint();
    }   

    public void salirPrograma() {
        this.controlador.salirPrograma();
    }

    public void cerrarSesion() {
        this.controlador.cerrarSesion();
    }

    // Iniciar Sesion | Register
    public Profile iniciarSesion(String user, String password) {
        return this.controlador.iniciarSesion(user, password);
    }

    public boolean userTieneEquipoFantasia() {
        return this.controlador.userTieneEquipoFantasia();
    }

    public void crearEquipoFantasia(String nombreEquipo) {
        this.controlador.crearEquipoFantasia(nombreEquipo);
    }

    public void register(String user, String password) {
        this.controlador.register(user, password);
    }

    public void cargarPartido(String filePartido) {
        this.controlador.cargarPartido(filePartido);
    }

    public void cargarTemporada(String nombreArchivoJugadores, String nombreArchivoFechas, int presupuesto) {
        this.controlador.cargarTemporada(nombreArchivoJugadores, nombreArchivoFechas, presupuesto);
    }

    public float getDineroDisponible() {
        return this.controlador.getDineroDisponible();
    }

    public ArrayList<EquipoReal> getEquiposReales() {
        return this.controlador.getEquiposReales();
    }

    public ArrayList<Jugador> getJugadoresEquipoReal(EquipoReal equipo) {
        return this.controlador.getJugadoresEquipoReal(equipo);
    }

    public void comprarJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
        this.controlador.comprarJugadorEquipoFantasia(jugadorSeleccionado);
    }

    public ArrayList<ArrayList<Jugador>> getJugadoresEquipoFantasia() {
        return this.controlador.getJugadoresEquipoFantasia();
    }

    public void venderJugadorEquipoFantasia(Jugador jugadorSeleccionado) {
        this.controlador.venderJugadorEquipoFantasia(jugadorSeleccionado);
    }

    public void asignarJugadorAlineacionTitular(Jugador jugador) {
        this.controlador.asignarJugadorAlineacionTitular(jugador);
    }

    public ArrayList<Jugador> getJugadoresAlineacionTitular() {
        return this.controlador.getJugadoresAlineacionTitular();
    }

    public void eliminarJugadorAlineacionTitular(Jugador jugador) {
        this.controlador.eliminarJugadorAlineacionTitular(jugador);
    }

    public ArrayList<EquipoFantasia> getRankingEquipos() {
        return this.controlador.getRankingEquipos();
    }

    public ArrayList<Jugador> getRankingJugadores() {
        return this.controlador.getRankingJugadores();
    }

    public ArrayList<ArrayList<Integer>> getEstadisticaPosicion() {
		return this.controlador.getEstadisticaPosicion();
	}

    public ArrayList<Integer> getBiggerNumbersEstadisticaPosicion() {
		return this.controlador.getBiggerNumbersEstadisticaPosicion();
	}

    public void elegirCapitanAlineacionTitular(Jugador jugador) {
		this.controlador.elegirCapitanAlineacionTitular(jugador);
	}

    public boolean tieneEquipoConMismoNombreEquiposUser(String nombre) {
		return controlador.tieneEquipoConMismoNombreEquiposUser(nombre);
	}

    public void setUsingEquipoFantasia(EquipoFantasia equipo) {	
		this.controlador.setUsingEquipoFantasia(equipo);
	}

    public ArrayList<EquipoFantasia> getUserEquiposFantasia() {
		return this.controlador.getUserEquiposFantasia();
	}

    public boolean getTerminoTemporada() {
        return this.controlador.getTerminoTemporada();
    }

    public void terminarTemporada() {
        this.controlador.terminarTemporada();
    }

    public void setTerminoTemporada() {
        this.controlador.setTerminoTemporada();
    }

    // Other

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

}
