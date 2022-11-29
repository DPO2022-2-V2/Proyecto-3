package uniandes.dpoo.modelo;

import java.util.*;

public class estadisticas {
    private ArrayList<EquipoFantasia> equiposFantasia;
    private ArrayList<EquipoReal> equiposReales;

    public estadisticas() {
        // TODO Auto-generated constructor stub
        equiposFantasia = new ArrayList<EquipoFantasia>();
        equiposReales = new ArrayList<EquipoReal>();

    }

    public void anadirEquipo(EquipoFantasia equipo) {
        equiposFantasia.add(equipo);
    }

    public void anadirEquipoReal(EquipoReal equipo) {
        equiposReales.add(equipo);
    }

    public ArrayList<EquipoFantasia> getRanking() {
        Collections.sort(equiposFantasia);
        return equiposFantasia;
    }

    public ArrayList<Jugador> getRankingJugadores() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (EquipoReal equipo : equiposReales) {
            jugadores.addAll(equipo.getJugadores().values());
        }
        Collections.sort(jugadores);
        return jugadores;
    }

    public int getPuntajeEquipoFantasia(EquipoFantasia equipoFantasia) {
        return equipoFantasia.getPuntosEquipo();
    }

    public ArrayList<EquipoFantasia> getEquiposFantasia() {
        return equiposFantasia;
    }

    public ArrayList<EquipoReal> getEquiposReales() {
        return equiposReales;
    }
}