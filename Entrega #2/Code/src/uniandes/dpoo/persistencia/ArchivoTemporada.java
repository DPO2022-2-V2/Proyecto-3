package uniandes.dpoo.persistencia;

import java.io.*;
import java.util.*;

public class ArchivoTemporada {
    /**
     * @param archivoJugadores
     * @param archivoFechas
     * @return
     * @throws IOException
     */
    public ArrayList<String>[] cargarDatosTemporada(File archivoJugadores, File archivoFechas) throws IOException {
        ArrayList<String> datosJugadores = new ArrayList<String>();
        ArrayList<String> datosFechas = new ArrayList<String>();
        ArrayList<String>[] datosTemporada = new ArrayList[2];
        BufferedReader readerJugadores = new BufferedReader(new FileReader(archivoJugadores));
        BufferedReader readerFechas = new BufferedReader(new FileReader(archivoFechas));

        String linea;
        while ((linea = readerJugadores.readLine()) != null) {
            datosJugadores.add(linea);
        }
        datosTemporada[0] = datosJugadores;
        while ((linea = readerFechas.readLine()) != null) {
            datosFechas.add(linea);
        }
        datosTemporada[1] = datosFechas;

        readerJugadores.close();
        readerFechas.close();

        return datosTemporada;
    }
}
