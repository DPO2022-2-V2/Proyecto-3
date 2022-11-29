package uniandes.dpoo.consola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CargarTemporada extends JPanel implements ActionListener {
    private UI mainUI;
    private JTextField nombreArchivoJugadores;
    private JTextField nombreArchivoFechas;
    private JTextField intSalarioInicial;

    public CargarTemporada(UI ui) {
        this.setLayout(new GridBagLayout());

        this.mainUI = ui;
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 1, 3, 0);
    
        nombreArchivoJugadores = new JTextField();
        nombreArchivoJugadores.setUI(new HintTextFieldUI("Archivo Jugadores", true));
        this.add(nombreArchivoJugadores, gbc);

        nombreArchivoFechas = new JTextField();
        nombreArchivoFechas.setUI(new HintTextFieldUI("Archivo Fechas", true));
        this.add(nombreArchivoFechas, gbc);

        intSalarioInicial = new JTextField();
        intSalarioInicial.setUI(new HintTextFieldUI("Salario Inicial", true));
        this.add(intSalarioInicial, gbc);

        JButton cargarPartido = new JButton("Cargar Temporada");
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
        if(b.getText().equals("Cargar Temporada")) {
            String fileJugadores = nombreArchivoJugadores.getText();
            String fileFechas = nombreArchivoFechas.getText();
            String salarioInicial = intSalarioInicial.getText();
            int intSalario = Integer.parseInt(salarioInicial);
            
            this.mainUI.cargarTemporada(fileJugadores, fileFechas, intSalario);
            this.mainUI.changeCenterPanel(new MainAdmin(mainUI)); // cambiar a panel de usuario ya registrado y chequear si admin o user
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
