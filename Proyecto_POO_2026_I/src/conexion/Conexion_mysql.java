package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion_mysql {

	private static final String URL = "jdbc:mysql://localhost:3306/restauranteSysGourmet_db";

	private static final String USUARIO = "root";

	private static final String PASSWORD = "mysql";

	public static Connection conectar() {

		Connection conexion = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);

			System.out.println("Conexión exitosa.");

		} catch (Exception e) {

			System.out.println("Error: " + e.getMessage());

		}

		return conexion;
	}

}
