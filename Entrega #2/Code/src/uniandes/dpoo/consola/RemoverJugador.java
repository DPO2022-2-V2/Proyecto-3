package uniandes.dpoo.consola;

import uniandes.dpoo.modelo.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class RemoverJugador  extends JPanel implements ActionListener  {
    private UI mainUI;
    
    public RemoverJugador(UI ui) {
        this.setLayout(new GridLayout(0, 1));

        
        this.mainUI = ui;
        ArrayList<Jugador> jugadoresEquipo = mainUI.getJugadoresAlineacionTitular();

		JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

		for (Jugador jugador : jugadoresEquipo) {
            
            JButton equipoButton = new JButton(jugador.getNombre() + " - " + jugador.getPosicion());

            equipoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton)e.getSource();
                    if(b.getText().equals(jugador.getNombre() + " - " + jugador.getPosicion())) {
                        mainUI.eliminarJugadorAlineacionTitular(jugador);
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
    
