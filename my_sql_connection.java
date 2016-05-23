import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author Elton Tang
 * @version 4/29/2016
 * ITMD_411_Final_Project_Elton_Tang
 *my_sql_connection.java
 *
 *Final Project
 */

public class my_sql_connection {
	static final String JDBC_Driver = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3306/411labs";
	static final String user = "db411";
	static final String pass = "411";
	static Connection conn = null;
		
	/**
	 * With the namespace already declared of the necessary Strings.
	 * The method simply does the result to connect to the database.
	 * @method sql_connection()
	 * @return
	 * 
	 * It will return the conn statement to access the database.
	 */
		public static Connection sql_connection(){
		
			try {
				Class.forName(JDBC_Driver);
				System.out.println("Currently connectiong to database");
				conn = DriverManager.getConnection(DB_URL,user,pass);
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("Successfully connected to database");
			return conn;
		}
		
		
		
		public static void main(String [] args){
			sql_connection();
		}
}
