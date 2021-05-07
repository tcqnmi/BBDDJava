package dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BD06_BajaUnoaUnoRSMod {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/dam1" +
				 "?useUnicode=true&characterEncoding=UTF-8" +
				 "&autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "";
		
		Scanner teclado = new Scanner(System.in);

		//Filtramos la búsqueda por nombre (Empieza por)
		
		System.out.println("Hotel (empieza por): ");
		String buscar = teclado.nextLine().trim();
		
		//Consulta
		
		String sql ="SELECT * \n"
				+ "FROM HOTELES \n"
				+ "WHERE nombre LIKE '%s' \n"
				+ "ORDER by nombre \n";
		//Tengo que añadir el valor buscado a la cadena
		sql = String.format(sql, buscar+"%");
		
		
		//Hasta ahora usábamos preparedStatemet porque era optimizado para ser rápido, solo va hacia delante y de solo lectura
		//Ahora es sensible a cambios y "scrolleable"
		try (
				Connection cn = DriverManager.getConnection(url, user, password);
				//Statement que genera conjunto de datos modificables
				Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
								ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st.executeQuery(sql);
				
		){
			
			int cont=0;
			int contelim=0;
			while(rs.next()) {
				//Acceso a los campos
				String n = rs.getString("nombre");
				String loc = rs.getString("localidad");
				int est = rs.getInt("estrellas");
				double precio = rs.getDouble("precio");
				boolean wifi = rs.getBoolean("wifi");
				
				//Vista en detalle
				System.out.printf("Hotel: %s (%s)\n",n,loc);
				System.out.printf("%-10s: %d \n","Estrellas",est);
				System.out.printf("%-10s: %.2f\n","Precio",precio);
				System.out.printf("%-10s: %s\n","wifi",wifi ? "si":"no");
				cont++;
				
				//Tratamiento 1 a 1  ¿Eliminar?
				
				System.out.printf("¿Deseas elminar este hotel (s/N)?");
				String resp = teclado.nextLine().trim().toLowerCase();
				
				if(resp.length()>0) {
					if(resp.charAt(0)=='s') {
						//Eliminamos el registro
						rs.deleteRow();
						contelim++;
						System.out.println("Ok! Eliminado");
					}
				}System.out.println("OK!");
				
			}
			//pie. Resumen de resultados
			System.out.printf("Encontrados %d hoteles / Eliminados: %d\n",cont,contelim);
			
			
			
		}catch (SQLException e) {
			System.out.println("BBDD > "+e.getMessage());
		}catch (Exception e) {
			System.out.println("> "+e.getMessage());
		}
		
		
		

	}

}
