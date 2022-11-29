package uniandes.dpoo.persistencia;

import java.io.*;

import uniandes.dpoo.modelo.*;

public class ArchivoPersistensia {
    public void guardar(Temporada obj) throws FileNotFoundException, IOException {
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("../Persistencia/Temporada.txt"));
        salida.writeObject(obj);
        salida.close();
        System.out.println("se guardo el obj");
    }

    public Temporada recuperar() throws FileNotFoundException, IOException, ClassNotFoundException {
        boolean check = new File("../Persistencia/Temporada.txt").exists();
        Temporada user;
        if (check) {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("../Persistencia/Temporada.txt"));
            user = (Temporada) entrada.readObject();
            entrada.close();
        } else {
            user = null;
        }

        return user;
    }

}