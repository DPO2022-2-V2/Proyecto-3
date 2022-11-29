package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel implements ActionListener {
    private UI mainUI;
    public Main(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        JButton iniciarSesion = new JButton("Iniciar Sesion");
        iniciarSesion.addActionListener(this);
        this.add(iniciarSesion, gbc);

        JButton registrarse = new JButton("Registrarse");
        registrarse.addActionListener(this);
        this.add(registrarse, gbc);

        JButton salirPrograma = new JButton("Salir Programa");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Iniciar Sesion")) {
            this.mainUI.changeCenterPanel(new IniciarSesion(mainUI));
        }
        if(b.getText().equals("Registrarse")) {
            this.mainUI.changeCenterPanel(new Registrarse(mainUI));
        }
        if(b.getText().equals("Salir Programa")) {
            this.mainUI.salirPrograma();
        }
    }

}
