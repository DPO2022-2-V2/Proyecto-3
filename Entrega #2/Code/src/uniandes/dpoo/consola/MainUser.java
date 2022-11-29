package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUser extends JPanel implements ActionListener {
    private UI mainUI;
    public MainUser(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);

        JButton comprarJugador = new JButton("Comprar Jugador");
        comprarJugador.addActionListener(this);
        this.add(comprarJugador, gbc);
        
        JButton venderJugador = new JButton("Vender Jugador");
        venderJugador.addActionListener(this);
        this.add(venderJugador, gbc);

        JButton anidirJugador = new JButton("Aniadir Jugador Titular");
        anidirJugador.addActionListener(this);
        this.add(anidirJugador, gbc);

        JButton removerJugador = new JButton("Remover Jugador Titular");
        removerJugador.addActionListener(this);
        this.add(removerJugador, gbc);

        JButton capTitular = new JButton("Seleccionar Capitan Equipo Titular");
        capTitular.addActionListener(this);
        this.add(capTitular, gbc);

        JButton rankingEquipos = new JButton("Ranking Equipos");
        rankingEquipos.addActionListener(this);
        this.add(rankingEquipos, gbc);

        JButton topJugadores = new JButton("Top Jugadores");
        topJugadores.addActionListener(this);
        this.add(topJugadores, gbc);

        JButton desempenioJugadores = new JButton("Desempenio Jugadores");
        desempenioJugadores.addActionListener(this);
        this.add(desempenioJugadores, gbc);

        JButton cerrarSesion = new JButton("Cerrar Sesion");
        cerrarSesion.addActionListener(this);
        this.add(cerrarSesion, gbc);

        JButton salirPrograma = new JButton("Salir Programa");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, gbc);

        JLabel saldoDisponibleUser = new JLabel("Saldo disponible: " + this.mainUI.getDineroDisponible());
        saldoDisponibleUser.setForeground(Color.RED);
        this.add(saldoDisponibleUser, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Comprar Jugador")) {
            this.mainUI.changeCenterPanel(new comprarEquiposDisponibles(mainUI));
        }
        if(b.getText().equals("Vender Jugador")) {
            this.mainUI.changeCenterPanel(new venderJugador(mainUI));
        }
        if(b.getText().equals("Ranking Equipos")) {
            this.mainUI.changeCenterPanel(new rankingEquipos(mainUI));
        }
        if(b.getText().equals("Seleccionar Capitan Equipo Titular")) {
            this.mainUI.changeCenterPanel(new aniadirQuitarCapitan(mainUI));
        }
        
        if(b.getText().equals("Top Jugadores")) {
            this.mainUI.changeCenterPanel(new rankingJugadores(mainUI));
        }
        if(b.getText().equals("Desempenio Jugadores")) {
            this.mainUI.changeCenterPanel(new desempenioJugadores(mainUI));
        }
        if(b.getText().equals("Cerrar Sesion")) {
            this.mainUI.cerrarSesion();
            this.mainUI.changeCenterPanel(new Main(mainUI));
        }
        if(b.getText().equals("Salir Programa")) {
            this.mainUI.salirPrograma();
        }

        if(b.getText().equals("Aniadir Jugador Titular")) {
        	this.mainUI.changeCenterPanel(new AniadirJugador(mainUI));
        }
        if(b.getText().equals("Remover Jugador Titular")) {
        	this.mainUI.changeCenterPanel(new RemoverJugador(mainUI));
        }
    }
}
