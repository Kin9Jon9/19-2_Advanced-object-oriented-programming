package connectDB;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;

public class DBA {
	
	public static Connection makeConnection() {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/gogaek?characterEncoding=UTF-8&serverTimezone=UTC";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "");
		}catch(ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}catch(SQLException ex) {
			System.out.println("SQLException : "+ex.getMessage());
		}
		
		return con;
	}
	
	public DBA(){
		makeConnection();
	}

}
