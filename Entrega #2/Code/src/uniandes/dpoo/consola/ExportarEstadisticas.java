package uniandes.dpoo.consola;

import javax.swing.*;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import uniandes.dpoo.modelo.EquipoFantasia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ExportarEstadisticas extends JPanel implements ActionListener {
    private UI mainUI;
    public ExportarEstadisticas(UI ui) {
        this.mainUI = ui;
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        JButton estadisticaMercado = new JButton("Estadistica Mercado");
        estadisticaMercado.addActionListener(this);
        this.add(estadisticaMercado, gbc);

        JButton estadisticaDesempenioJugadores = new JButton("Estadistica Desempenio Jugadores Equipo");
        estadisticaDesempenioJugadores.addActionListener(this);
        this.add(estadisticaDesempenioJugadores, gbc);

        JButton provenenciaDePuentos = new JButton("Provenencia de puntos equipos reales");
        provenenciaDePuentos.addActionListener(this);
        this.add(provenenciaDePuentos, gbc);

        JButton progresoEquiposDeFantasia = new JButton("Progreso equipos de fantasia");
        progresoEquiposDeFantasia.addActionListener(this);
        this.add(progresoEquiposDeFantasia, gbc);

        JButton gastosEquiposDeFantasia = new JButton("Estadistica gastos equipos de fantasia");
        gastosEquiposDeFantasia.addActionListener(this);
        this.add(gastosEquiposDeFantasia, gbc);

        JButton cerrarSesion = new JButton("Cerrar Sesion");
        cerrarSesion.addActionListener(this);
        this.add(cerrarSesion, gbc);

        JButton salirPrograma = new JButton("Salir Programa");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Estadistica Mercado")) {
            
        }
        if(b.getText().equals("Estadistica Desempenio Jugadores Equipo")) {
            
        }
        if(b.getText().equals("Provenencia de puntos equipos reales")) {
            
        }
        if(b.getText().equals("Progreso equipos de fantasia")) {
            ArrayList<EquipoFantasia> rankingEquipos = mainUI.exportTopTeams();
            ArrayList<String> rankingEquiposNombres = new ArrayList<String>();
            ArrayList<Integer> rankingEquiposPuntos = new ArrayList<Integer>();
            for (EquipoFantasia equipo : rankingEquipos) {
                rankingEquiposNombres.add(equipo.getNombre());
                rankingEquiposPuntos.add(equipo.getPuntosEquipo());
            }

            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Puntajes Equipos | Fecha #" + rankingEquiposPuntos.size()).xAxisTitle("Equipo").yAxisTitle("Puntaje").build();
            chart.addSeries("Fecha #" + rankingEquiposPuntos.size(), rankingEquiposNombres, rankingEquiposPuntos);
            
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame displayChart = new SwingWrapper(chart).displayChart();     
                    displayChart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
               });
            t.start();
        }
        
        if(b.getText().equals("Estadistica gastos equipos de fantasia")) {
            
        }
        
        if(b.getText().equals("Cerrar Sesion")) {
            this.mainUI.cerrarSesion();
            this.mainUI.changeCenterPanel(new Main(mainUI));
        }
        if(b.getText().equals("Salir Programa")) {
            this.mainUI.salirPrograma();
        }
    }
}


