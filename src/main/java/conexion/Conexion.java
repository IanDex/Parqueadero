package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class Conexion {
	
    private static Connection cn = null;
    public static Connection getConnection() throws SQLException {
    	
    	try {
	    	if(System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
	    		cn = DriverManager.getConnection("jdbc:google:mysql://modified-wonder-87620:prueba-telefonicacorp/java_mysql?user=root");
	    	}
	    	
	    	else {
				Class.forName("com.mysql.jdbc.Driver");		
				System.out.println("Si");
				cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_mysql", "root", "");
	    	}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return cn;
    }
    
	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
