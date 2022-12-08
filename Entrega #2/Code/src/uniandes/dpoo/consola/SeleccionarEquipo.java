package uniandes.dpoo.consola;

import javax.swing.*;

import uniandes.dpoo.modelo.EquipoFantasia;
import uniandes.dpoo.modelo.EquipoReal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SeleccionarEquipo extends JPanel implements ActionListener {

    private UI mainUI;
    
    public SeleccionarEquipo(UI ui) {
        this.setLayout(new GridLayout(0, 1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        this.mainUI = ui;
        ArrayList<EquipoFantasia> equiposReales = mainUI.getUserEquiposFantasia();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        for (EquipoFantasia equipo : equiposReales) {
            JButton equipoButton = new JButton(equipo.getNombre());
            equipoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton)e.getSource();
                    if(b.getText().equals(equipo.getNombre())) {
                        mainUI.setUsingEquipoFantasia(equipo);
                        ui.changeCenterPanel(new MainUser(mainUI));
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
        // TODO Auto-generated method stub
        
    }
    
}
