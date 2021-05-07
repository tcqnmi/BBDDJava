package dam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BD03_AltaPS {

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
		
	
		

		//Consulta SQL PARAMETRIZADA	(? son los parámetros)	
		String sql ="INSERT INTO hoteles  VALUES (default, ?, ? ,? ,? ,?)";
			
		try (
			Connection cn = DriverManager.getConnection(url,user,password);
			PreparedStatement ps = cn.prepareStatement(sql);
			
			){
				//Cargamos los parámetros con datos
				ps.setString(1, nombre);
				ps.setString(2, loc);
				ps.setInt(3,  estrellas);
				ps.setDouble(4, precio);
				ps.setBoolean(5, wifi);
				
				//Lanzamos la consulta
				
				int cont=ps.executeUpdate(); //executeUpdate para todo lo que no sea select
				System.out.println("Se han añadido ["+cont+"] hoteles a la tabla");
			
			}
			catch(SQLException e) {
				System.out.println("BD >"+e.getMessage());
			}
			catch(Exception e) {
				System.out.println("> "+e.getMessage());
			}
		
		
		
	}

}
