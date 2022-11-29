package uniandes.dpoo.modelo;

import java.io.*;

public class Admin extends Profile implements Serializable {
	public Admin(String nombre, String clave) {
		this.nombre = nombre;
		this.clave = clave;
	}
}
