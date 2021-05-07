package dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD01_Listados {

	public static void main(String[] args) {
		//Copiamos la cadena de conexión con el servidor
		String url = "jdbc:mysql://localhost:3306/dam1" +
				 "?useUnicode=true&characterEncoding=UTF-8" +
				 "&autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "";

		//Consulta SQL		
		String sql ="SELECT * FROM hoteles ORDER BY nombre";

		//Libera la máquina automáticamente, se cierra y libera recursos
		try(
				//Creamos una conexión con el servidor mysql (importamos el recurso de java.sql)
				Connection cn = DriverManager.getConnection(url, user, password);
				//Creamos un objeto statement para lanzar la consulta SQL
				Statement st = cn.createStatement();
				//Creamos un objeto ResultSet para procesar los resultados de la consulta Select
				ResultSet rs = st.executeQuery(sql);
		)
		{
			//Generamos el listado
			//Cabecera 
				
				//Tratamiento 
				String cab = String.format("%-15s %-15s %4s %8s %4s\n",
						"Hotel", "Localidad", "Estrellas", "Precio", "Wifi");
				System.out.println(cab);
				hr(cab.length());
			
			int cont = 0;
			
			//Cuerpo
			while(rs.next()) {
				//Acceso a los campos de la tabla
				String nom = rs.getNString("nombre");
				String loc = rs.getNString("localidad");
				int estrellas = rs.getInt("estrellas");
				double precio = rs.getDouble("precio");
				boolean wifi = rs.getBoolean("wifi");
				
				System.out.printf("%-15s %-15s %4d %8.2f€ %4s\n",
						nom, loc, estrellas, precio, wifi ? "Si":"No");
				cont++;
				
			}
			hr(cab.length());
			
			
			//Pie
		}
		catch(SQLException e) {
			System.out.println("BD >"+e.getMessage());
		}
		catch(Exception e) {
			System.out.println("> "+e.getMessage());
		}
	}
	
	public static void hr(int tam) {
		for(int i=1; i<=tam; i++)
			System.out.printf("=");
		System.out.println();
	}

}
