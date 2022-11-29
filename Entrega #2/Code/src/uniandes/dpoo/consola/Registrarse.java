package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registrarse extends JPanel implements ActionListener {
    private UI mainUI;
    private GridBagConstraints gbc;
    private JTextField usernameTxt;
    private JTextField passwordTxt;
    private Boolean haveError = false;

    public Registrarse(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        this.gbc = new GridBagConstraints();
        this.gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.gbc.fill = GridBagConstraints.BOTH;
        this.gbc.insets = new Insets(3, 1, 3, 0);
    
        usernameTxt = new JTextField();
        usernameTxt.setUI(new HintTextFieldUI("Username", true));
        this.add(usernameTxt, this.gbc);

        passwordTxt = new JTextField();
        passwordTxt.setUI(new HintTextFieldUI("Password", true));
        this.add(passwordTxt, this.gbc);

        JButton salirPrograma = new JButton("Registrarse");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, this.gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Registrarse")) {
            String username = usernameTxt.getText();
            String password = passwordTxt.getText();
            if (username.length() >= 4 && password.length() >= 4) {
                this.mainUI.register(username, password);
                this.mainUI.changeCenterPanel(new Main(mainUI));
            } else if (this.haveError == false) {
                JLabel error = new JLabel("ERROR: Invalid username or password");
                error.setForeground(Color.RED);
                this.add(error, this.gbc);
                this.haveError = true;
                this.validate();
                this.repaint();
            }
        }
    }
}
