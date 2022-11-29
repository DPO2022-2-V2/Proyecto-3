package uniandes.dpoo.persistencia;

import java.io.*;
import java.util.*;

public class ArchivoPartido {
    public ArrayList<String> cargarDatosPartido(File archivoPartido) {
        ArrayList<String> datosPartido = new ArrayList<String>();
        try (BufferedReader readerPartido = new BufferedReader(new FileReader(archivoPartido))) {
            String linea;
            while ((linea = readerPartido.readLine()) != null) {
                datosPartido.add(linea);
            }

            readerPartido.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datosPartido;
    }
}
