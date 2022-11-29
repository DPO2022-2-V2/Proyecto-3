package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAdmin extends JPanel implements ActionListener {
    private UI mainUI;
    public MainAdmin(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        JButton cargarTemporada = new JButton("Cargar Temporada");
        cargarTemporada.addActionListener(this);
        this.add(cargarTemporada, gbc);

        JButton cargarPartido = new JButton("Cargar Partido");
        cargarPartido.addActionListener(this);
        this.add(cargarPartido, gbc);

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
        if(b.getText().equals("Cargar Temporada")) {
            this.mainUI.changeCenterPanel(new CargarTemporada(mainUI));
        }
        if(b.getText().equals("Cargar Partido")) {
            this.mainUI.changeCenterPanel(new CargarPartido(mainUI));
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
