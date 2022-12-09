package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JButton progresoEquiposDeFantasia = new JButton("Progreso equipos de fantasia durante la temporada (por puntos)");
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
            this.mainUI.changeCenterPanel(new CargarTemporada(mainUI));
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


