package principal;

import java.sql.Connection;

import conexion.Conexion_mysql;

public class Main {

	// PUNTO DE ENTRADA PRINCIPAL
	public static void main(String[] args) {
		
		// PRUEBA DE CONEXION A LA BD
		
		Connection cn = Conexion_mysql.conectar();

		if (cn != null) {

			System.out.println("La Base de datos se conecto correctamente.");

		} else {

			System.out.println("No se pudo conectar.");

		}

	}

}
