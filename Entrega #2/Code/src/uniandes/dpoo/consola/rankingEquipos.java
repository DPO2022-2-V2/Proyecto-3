package uniandes.dpoo.consola;

import javax.swing.*;

import uniandes.dpoo.modelo.EquipoFantasia;
import uniandes.dpoo.modelo.EquipoReal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class rankingEquipos extends JPanel implements ActionListener {
    private UI mainUI;
    
    public rankingEquipos(UI ui) {
        this.setLayout(new GridLayout(0, 1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        this.mainUI = ui;
        ArrayList<EquipoFantasia> rankingEquipos = mainUI.getRankingEquipos();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        for (EquipoFantasia equipo : rankingEquipos) {
            JLabel val = new JLabel(equipo.getNombre() + " - " + equipo.getPuntosEquipo());
            panel.add(val, gbc);
        }
        JButton salir = new JButton("Salir Menu");
        salir.addActionListener(this);
        panel.add(salir, gbc);
        final JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Salir Menu")) {
            this.mainUI.changeCenterPanel(new MainUser(mainUI));
        }
    }
}
