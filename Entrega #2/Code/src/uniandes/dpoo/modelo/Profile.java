package uniandes.dpoo.modelo;

import java.io.*;

public class Profile implements Serializable {
	protected String nombre;
	protected String clave;

	public String getNombre() {
		return this.nombre;
	}

	public String getclave() {
		return this.clave;
	}
}
