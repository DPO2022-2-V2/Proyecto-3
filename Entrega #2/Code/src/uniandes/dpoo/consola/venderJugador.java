package uniandes.dpoo.consola;

import uniandes.dpoo.modelo.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class venderJugador extends JPanel implements ActionListener {
    private UI mainUI;
    private JButton delantero;
    private JButton mCampista;
    private JButton defensa;
    private JButton arquero;
    
    public venderJugador(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        ArrayList<ArrayList<Jugador>> jugadoresEquipo = mainUI.getJugadoresEquipoFantasia();
        ArrayList<Jugador> listaArquero = jugadoresEquipo.get(0);
		ArrayList<Jugador> listaDefensa = jugadoresEquipo.get(1);
		ArrayList<Jugador> listaMedioCampista = jugadoresEquipo.get(2);
		ArrayList<Jugador> listaDelantero = jugadoresEquipo.get(3);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        arquero = new JButton("Arquero - " + listaArquero.size());
        arquero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b.getText().equals("Arquero - " + listaArquero.size())) {
                    seleccionarJugador(listaArquero);
                }
            }
        });
        this.add(arquero, gbc);

        defensa = new JButton("Defensa - " + listaDefensa.size());
        defensa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b.getText().equals("Defensa - " + listaDefensa.size())) {
                    seleccionarJugador(listaDefensa);
                }
            }
        });
        this.add(defensa, gbc);

        mCampista = new JButton("Medio Campista - " + listaMedioCampista.size());
        mCampista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b.getText().equals("Medio Campista - " + listaMedioCampista.size())) {
                    seleccionarJugador(listaMedioCampista);
                }
            }
        });
        this.add(mCampista, gbc);

        delantero = new JButton("Delantero - " + listaDelantero.size());
        delantero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton)e.getSource();
                if(b.getText().equals("Delantero - " + listaDelantero.size())) {
                    seleccionarJugador(listaDelantero);
                }
            }
        });
        this.add(delantero, gbc);
        
        
    }

    private void seleccionarJugador(ArrayList<Jugador> jugadores) {
        this.remove(delantero);
        this.remove(mCampista);
        this.remove(defensa);
        this.remove(arquero);
        this.setLayout(new GridLayout(0, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());


        for (Jugador jugador : jugadores) {
            
            JButton equipoButton = new JButton(jugador.getNombre() + " - " + jugador.getPrecio()*0.97);


            equipoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton)e.getSource();
                    if(b.getText().equals(jugador.getNombre() + " - " + jugador.getPrecio()*0.97)) {
                        mainUI.venderJugadorEquipoFantasia(jugador);
                        mainUI.changeCenterPanel(new MainUser(mainUI));
                    }
                }
            });
            panel.add(equipoButton, gbc);
        }


        final JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);
        validate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
