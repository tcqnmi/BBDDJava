package dam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BD08_AltaRSMod {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/dam1" +
				 "?useUnicode=true&characterEncoding=UTF-8" +
				 "&autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "";
		
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Nombre: ");
		String nombre = teclado.nextLine();
		
		System.out.println("Localidad: ");
		String loc = teclado.nextLine();
		
		System.out.println("Estrellas: ");
		int estrellas = Integer.parseInt(teclado.nextLine());
		
		System.out.println("Precio: ");
		double precio = Double.parseDouble(teclado.nextLine());
		
		System.out.println("Wifi: (s/n) ");
		boolean wifi = teclado.nextLine().trim().toLowerCase().charAt(0) == 's';
		
	
		//Consulta sql select sin resultados pero con la estructura del registro
		String sql ="SELECT * \n"
				+ "FROM hoteles \n "
				+ "WHERE false \n";
			
		try (
			Connection cn = DriverManager.getConnection(url,user,password);
			Statement st = cn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(sql);
				
			
			){
		
				//Registro temporal para hacer el alta
			
				rs.moveToInsertRow();
				
				//Cargamos los parámetros con datos
				
				rs.updateString("Nombre", nombre);
				rs.updateString("Localidad",loc);
				rs.updateInt("Estrellas", estrellas);
				rs.updateDouble("Precio",precio);
				rs.updateBoolean("wifi", wifi);
				
				
				//Guardamos el registro temporal en la BD
				
				rs.insertRow();
				
				System.out.println("Se ha añadido un hotel a la tabla");
			
			}
			catch(SQLException e) {
				System.out.println("BD >"+e.getMessage());
			}
			catch(Exception e) {
				System.out.println("> "+e.getMessage());
			}
		
		
		
	}

}
