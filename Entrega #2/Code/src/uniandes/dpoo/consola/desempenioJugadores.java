package uniandes.dpoo.consola;

import javax.swing.*;
import uniandes.dpoo.modelo.EquipoReal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class desempenioJugadores extends JPanel implements ActionListener {
    private UI mainUI;
    // public ArrayList<ArrayList<Integer>> getEstadisticaPosicion() {
	// 	return this.controlador.getEstadisticaPosicion();
	// }
    public desempenioJugadores(UI mainUI) {
        this.mainUI = mainUI;

        String[][] arr = new String[4][1];

        arr[0][0] = "ARQUERO";
        arr[1][0] = "DEFENSA";
        arr[2][0] = "MEDIOCAMPISTA";
        arr[3][0] = "DELANTERO";

        this.setLayout(new GridLayout(2, 1));

        ArrayList<ArrayList<Integer>> estadisticasPosicion = this.mainUI.getEstadisticaPosicion();
        ArrayList<Integer> getBiggerNumbersEstadisticaPosicion = this.mainUI.getBiggerNumbersEstadisticaPosicion();

        JPanel mainPanel = new JPanel(new GridLayout(4, 0, 0, 0));
        
        for (int i = 0; i<4; i++) {
            Label l11 = new Label(arr[i][0]);
            mainPanel.add(l11);
            for (int j = 0; j < estadisticasPosicion.size(); j++) {
                ArrayList<Integer> fecha = estadisticasPosicion.get(j);
                int numeroPosicion = fecha.get(i);
                int opacidad = 0;
                if (getBiggerNumbersEstadisticaPosicion.get(i) != 0) {
                    opacidad = 100 * numeroPosicion / getBiggerNumbersEstadisticaPosicion.get(i);
                } 
                JPanel panel  = createSquareJPanel(new Color(0,255,0,opacidad), 50);

                panel.setBorder(BorderFactory.createLineBorder(Color.red));

                mainPanel.add(panel);
            }
        }
        final JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scroll);
        this.setOpaque(true);

        JButton salir = new JButton("Salir Menu");
        salir.addActionListener(this);
        this.add(salir);
    }

    private JPanel createSquareJPanel(Color color, int size)
    {
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(color);
        tempPanel.setMinimumSize(new Dimension(size, size));
        tempPanel.setMaximumSize(new Dimension(size, size));
        tempPanel.setPreferredSize(new Dimension(size, size));
        return tempPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Salir Menu")) {
            this.mainUI.changeCenterPanel(new MainUser(mainUI));
        }
        
    }
    
}
