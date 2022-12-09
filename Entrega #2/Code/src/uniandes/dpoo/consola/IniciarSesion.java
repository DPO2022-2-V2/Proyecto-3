package uniandes.dpoo.consola;

import javax.swing.*;

import uniandes.dpoo.modelo.Admin;
import uniandes.dpoo.modelo.Profile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IniciarSesion extends JPanel implements ActionListener {
    private UI mainUI;
    private JTextField usernameTxt;
    private JTextField passwordTxt;

    public IniciarSesion(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        usernameTxt = new JTextField();
        usernameTxt.setUI(new HintTextFieldUI("Username", true));
        this.add(usernameTxt, gbc);

        passwordTxt = new JTextField();
        passwordTxt.setUI(new HintTextFieldUI("Password", true));
        this.add(passwordTxt, gbc);

        JButton salirPrograma = new JButton("Iniciar Sesion");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Iniciar Sesion")) {
            String username = usernameTxt.getText();
            String password = passwordTxt.getText();
            Profile user = this.mainUI.iniciarSesion(username, password);
            if (user == null) {
                this.mainUI.changeCenterPanel(new Main(mainUI));
            } else if (user instanceof Admin) {
                if (this.mainUI.getTerminoTemporada()) {
                    this.mainUI.changeCenterPanel(new MainAdminTemporadaTerminada(mainUI));
                } else {
                    this.mainUI.changeCenterPanel(new MainAdmin(mainUI));
                }
            } else {
                if (this.mainUI.userTieneEquipoFantasia() == false) {
                    this.mainUI.changeCenterPanel(new NewUser(mainUI));
                } else {
                    this.mainUI.changeCenterPanel(new SeleccionarEquipo(mainUI)); // new MainUser(mainUI)
                }
            }
             // cambiar a panel de usuario ya registrado y chequear si admin o user
        }
    }
}
