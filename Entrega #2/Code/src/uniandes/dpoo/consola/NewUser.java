package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUser extends JPanel implements ActionListener {
    private UI mainUI;
    JTextField txtNombreEquipo;

    public NewUser(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        txtNombreEquipo = new JTextField();
        txtNombreEquipo.setUI(new HintTextFieldUI("Nombre Equipo Fantasia", true));
        this.add(txtNombreEquipo, gbc);

        JButton cargarPartido = new JButton("Crear Equipo Fantasia");
        cargarPartido.addActionListener(this);
        this.add(cargarPartido, gbc);

        JButton cerrarSesion = new JButton("Cerrar Sesion");
        cerrarSesion.addActionListener(this);
        this.add(cerrarSesion, gbc);

        JButton salirPrograma = new JButton("Salir Programa");
        salirPrograma.addActionListener(this);
        this.add(salirPrograma, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if(b.getText().equals("Crear Equipo Fantasia")) {
            String nombreEquipo = txtNombreEquipo.getText();
            this.mainUI.crearEquipoFantasia(nombreEquipo);
            this.mainUI.changeCenterPanel(new MainUser(mainUI)); // cambiar a panel de usuario ya registrado y chequear si admin o user
        }
        if(b.getText().equals("Cerrar Sesion")) {
            this.mainUI.cerrarSesion();
            this.mainUI.changeCenterPanel(new Main(mainUI));
        }
        if(b.getText().equals("Salir Programa")) {
            this.mainUI.salirPrograma();
        }
    }
}
