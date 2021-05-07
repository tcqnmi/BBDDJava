package dam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BD07_ModUnoaUnoRSMod {

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
			int contmod=0;
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
				
				System.out.printf("¿Deseas modificar este hotel (s/N)?");
				String resp = teclado.nextLine().trim().toLowerCase();
				
				if(resp.length()>0) {
					if(resp.charAt(0)=='s') {
						//Modificamos el registro
						
						String dato ="";
						
						System.out.printf("¿Nombre? [%s]",n);
						dato= teclado.nextLine().trim();
						if(dato.length()>0) {
							rs.updateString("Nombre",dato);
						
							
							System.out.printf("¿Localidad? [%s]",loc);
							dato= teclado.nextLine().trim();
							if(dato.length()>0) {
								rs.updateString("Localidad",dato);
							}
							
							System.out.printf("¿Estrellas [%2d]?",est);
							dato= teclado.nextLine().trim();
							if(dato.length()>0) {
								rs.updateInt("Estrellas",Integer.parseInt(dato));
							}
							
							System.out.printf("¿Precio [%.2f]?",precio);
							dato= teclado.nextLine().trim();
							if(dato.length()>0) {
								rs.updateDouble("Precio",Double.parseDouble(dato));
							}
							
							System.out.printf("¿Wifi? [%s]",wifi ? "Si":"No");
							dato= teclado.nextLine().trim();
							if(dato.length()>0) {
								rs.updateBoolean("Wifi",dato.charAt(0)=='s');
							}
							
							//Guardar los cambios en el resgistro actual, si no ponemos el rs.updateRow son temporales 
							
							rs.updateRow();
						}
						contmod++;
						System.out.println("Ok! Modificado");
					}
				}System.out.println("OK!");
				
			}
			//pie. Resumen de resultados
			System.out.printf("Encontrados %d hoteles / Modificados: %d\n",cont,contmod);
			
			
			
		}catch (SQLException e) {
			System.out.println("BBDD > "+e.getMessage());
		}catch (Exception e) {
			System.out.println("> "+e.getMessage());
		}
		
		
		

	}

}
