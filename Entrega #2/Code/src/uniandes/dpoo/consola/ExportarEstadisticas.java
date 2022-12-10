package uniandes.dpoo.consola;

import javax.swing.*;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
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
    
        JButton estadisticaMercadoCompras = new JButton("Estadistica Mercado - Compras");
        estadisticaMercadoCompras.addActionListener(this);
        this.add(estadisticaMercadoCompras, gbc);

        JButton estadisticaMercadoVentas = new JButton("Estadistica Mercado - Ventas");
        estadisticaMercadoVentas.addActionListener(this);
        this.add(estadisticaMercadoVentas, gbc);

        JButton estadisticaDesempenioJugadores = new JButton("Reporte comparativo de puntos de los jugadores de un equipo");
        estadisticaDesempenioJugadores.addActionListener(this);
        this.add(estadisticaDesempenioJugadores, gbc);

        JButton progresoEquiposDeFantasia = new JButton("Reporte progreso de puntos de equipos");
        progresoEquiposDeFantasia.addActionListener(this);
        this.add(progresoEquiposDeFantasia, gbc);

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
        if(b.getText().equals("Estadistica Mercado - Compras")) {
            ArrayList<Integer> datos = mainUI.getDatosCompraVenta();
            PieChart chart = new PieChartBuilder().width(800).height(600).title("Estadistica Mercado - Compras").build();
            
            chart.addSeries("Delantero", datos.get(0));
            chart.addSeries("Medio Campista", datos.get(1));
            chart.addSeries("Defensa", datos.get(2));
            chart.addSeries("Arquero", datos.get(3));
            
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame displayChart = new SwingWrapper(chart).displayChart();     
                    displayChart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
               });
            t.start();
        }

        if(b.getText().equals("Estadistica Mercado - Ventas")) {
            ArrayList<Integer> datos = mainUI.getDatosCompraVenta();
            PieChart chart = new PieChartBuilder().width(800).height(600).title("Estadistica Mercado - Ventas").build();
            
            chart.addSeries("Delantero", datos.get(4));
            chart.addSeries("Medio Campista", datos.get(5));
            chart.addSeries("Defensa", datos.get(6));
            chart.addSeries("Arquero", datos.get(7));
            
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame displayChart = new SwingWrapper(chart).displayChart();     
                    displayChart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
               });
            t.start();
        }

        if(b.getText().equals("Reporte comparativo de puntos de los jugadores de un equipo")) {
            EquipoFantasia equipo = mainUI.getEquipoFantasia();
            ArrayList<Integer> datos = equipo.getPuntosPorTipoJugador();
            PieChart chart = new PieChartBuilder().width(800).height(600).title("Desempenio Jugadores Tu Equipo Fantasia").build();
            
            chart.addSeries("Delantero", datos.get(0));
            chart.addSeries("Medio Campista", datos.get(1));
            chart.addSeries("Defensa", datos.get(2));
            chart.addSeries("Arquero", datos.get(3));
            
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame displayChart = new SwingWrapper(chart).displayChart();     
                    displayChart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
               });
            t.start();
        }

        if(b.getText().equals("Reporte progreso de puntos de equipos")) {
            ArrayList<EquipoFantasia> rankingEquipos = mainUI.exportTopTeams();
            ArrayList<String> rankingEquiposNombres = new ArrayList<String>();
            ArrayList<Integer> rankingEquiposPuntos = new ArrayList<Integer>();
            for (EquipoFantasia equipo : rankingEquipos) {
                rankingEquiposNombres.add(equipo.getNombre());
                rankingEquiposPuntos.add(equipo.getPuntosEquipo());
            }

            CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Puntajes Equipos").xAxisTitle("Equipo").yAxisTitle("Puntaje").build();
            chart.addSeries("Fecha", rankingEquiposNombres, rankingEquiposPuntos);
            
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    JFrame displayChart = new SwingWrapper(chart).displayChart();     
                    displayChart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                }
               });
            t.start();
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


