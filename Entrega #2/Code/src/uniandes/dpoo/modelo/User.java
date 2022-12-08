package uniandes.dpoo.modelo;

import java.io.*;
import java.util.*;

public class User extends Profile implements Serializable {
	private EquipoFantasia userEquipoFantasia = null;
	private ArrayList<EquipoFantasia> userEquiposDeFantasia;

	public User(String nombre, String clave) {
		this.nombre = nombre;
		this.clave = clave;
		userEquiposDeFantasia = new ArrayList<EquipoFantasia>();
	}

	public void crearEquipoFantasia(String nombreEquipo, int presupuesto) {
		this.userEquipoFantasia = new EquipoFantasia(nombreEquipo, this, presupuesto);
		this.userEquiposDeFantasia.add(userEquipoFantasia);
	}

	public boolean tieneEquipoConMismoNombre(String nombre) {
		for (EquipoFantasia instance : userEquiposDeFantasia) {
			if (instance.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	public EquipoFantasia getEquipoFantasia() {
		return this.userEquipoFantasia;
	}

	public ArrayList<EquipoFantasia> getEquiposFantasia() {
		return this.userEquiposDeFantasia;
	}

	public void setUsingEquipoFantasia(EquipoFantasia equipo) {
		this.userEquipoFantasia = equipo;
	}

	public void cerrarSesion() {
		this.userEquipoFantasia = null;
	}
}
