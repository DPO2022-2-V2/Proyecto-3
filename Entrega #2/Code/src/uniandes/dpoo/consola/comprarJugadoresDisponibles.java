package uniandes.dpoo.consola;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;


import uniandes.dpoo.modelo.*;

public class comprarJugadoresDisponibles extends JPanel implements ActionListener {
    private UI mainUI;
    
    public comprarJugadoresDisponibles(UI ui, EquipoReal equipo) {
        this.setLayout(new GridLayout(0, 1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        this.mainUI = ui;
        ArrayList<Jugador> jugadoresReales = mainUI.getJugadoresEquipoReal(equipo);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        for (Jugador jugador : jugadoresReales) {
            
            JButton equipoButton = new JButton(jugador.getNombre() + " - " + jugador.getPrecio());


            equipoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton)e.getSource();
                    if(b.getText().equals(jugador.getNombre() + " - " + jugador.getPrecio())) {
                        mainUI.comprarJugadorEquipoFantasia(jugador);
                        mainUI.changeCenterPanel(new MainUser(mainUI));
                    }
                }
            });
            panel.add(equipoButton, gbc);
        }
        final JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
