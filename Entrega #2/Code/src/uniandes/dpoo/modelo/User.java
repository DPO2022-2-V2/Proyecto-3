package uniandes.dpoo.modelo;

import java.io.*;

public class User extends Profile implements Serializable {
	private EquipoFantasia userEquipoFantasia = null;

	public User(String nombre, String clave) {
		this.nombre = nombre;
		this.clave = clave;
	}

	public void crearEquipoFantasia(String nombreEquipo, int presupuesto) {
		userEquipoFantasia = new EquipoFantasia(nombreEquipo, this, presupuesto);
	}

	public EquipoFantasia getEquipoFantasia() {
		return userEquipoFantasia;
	}
}
