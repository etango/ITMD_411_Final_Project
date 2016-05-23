import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * @author Elton Tang
 * @version 4/29/2016
 * ITMD_411_Final_Project_Elton_Tang
 *sql_db.java
 *
 *Final Project
 */
public class sql_db {
	/**
	 * Here I am declaring my class for sql connection into the name space.
	 * Declaring a static connection and statement.
	 */
	
	static my_sql_connection obj = new my_sql_connection();
	static Connection con = null;
	static Statement statement = null;

	
	/**
	 * @method createLoginTable()
	 * Here I have a method that simply create a table for login. This will be used in the future to verify user that will using our ticket system.
	 */
	public static void createLoginTable(){
		final String createLoginTable = "CREATE TABLE etang_and_cxie_login_table(user_id INT AUTO_INCREMENT PRIMARY KEY, user_name VARCHAR(32), user_password VARCHAR(32)) " ; 
		
				  
		try {
		      con = obj.sql_connection();
		    
		      statement = con.createStatement();
		      
		      statement.executeUpdate(createLoginTable);
		      System.out.println("Created login table in given database was successful...");

			//end create table
		    //close connection/statement object  
		     statement.close();
		     con.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  

	}
	/**
	 * @method createInventoryTable()
	 * I am creating an inventory for IIT Help desk to sell laptops
	 */
	public static void createInventoryTable(){
		final String createInventoryTable = "CREATE TABLE etang_and_cxie_laptop_INVENTORY (laptop_id INT AUTO_INCREMENT PRIMARY KEY, model varchar(99), size varchar(10), processor varchar(10), ram varchar(10), graphics varchar(10), storage_space varchar(15),cost numeric(10,2), retail numeric(10,2),condition_laptop varchar(32))";
		  
				  
		try {
		      con = obj.sql_connection();
		    
		      statement = con.createStatement();
		      
		      statement.executeUpdate(createInventoryTable);
		      System.out.println("Created invenotry table in given database was successful...");

			//end create table
		    //close connection/statement object  
		     statement.close();
		     con.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  

	}
	
	/**
	 * @method createTicketTable()
	 * This is where the main core of the final project lie. THe table consists the ticket id, ticket name, desc, status, priority, and timestamp.
	 * The connection made to sql is done through another class I made to reduce hard codes in the overall program. 
	 * Also faster to type through the codes.
	 */
	public static void createTicketTable(){	
		
		
		// vars. for SQL Query create
		  final String createTicketTable = "CREATE TABLE etang_and_cxie_Ticket(ticket_id INT AUTO_INCREMENT PRIMARY KEY,ticket_name VARCHAR(30), ticket_description VARCHAR(200), ticket_status VARCHAR(30),ticket_priority VARCHAR(30),ticket_time timestamp )";
		  		
				  
		try {
		      con = obj.sql_connection();
		    
		      statement = con.createStatement();
		      
		      statement.executeUpdate(createTicketTable);
		      System.out.println("Created ticket table in given database was successful...");

			//end create table
		    //close connection/statement object  
		     statement.close();
		     con.close();
		    } catch (Exception e) {
		    	System.out.println(e.getMessage());  
		    }  

		
	}
	
	/**
	 * @insertLoginTable()
	 * I am just inserting dummies data here.
	 */
	public static void insertLoginTable(){
		 final String sql_insertLogin = "insert into etang_and_cxie_login_table(user_id,user_name,user_password) " + " values(1,'etango','qweasd963zxc'),(2,'cindylouwho','fourwordslowercase')" ; 
		
		try{
			con = obj.sql_connection();
			statement = con.createStatement();
			statement.executeUpdate(sql_insertLogin);
			System.out.println("Value was successfully inserted into login table");
		}
					
		
		catch(SQLException se){
			se.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	
	}
	
	/**
	 * @insertInvenotryTable()
	 * I am just inserting dummies data here.
	 */
	public static void insertInventoryTable(){
		final String sql_insertInventory = "insert into etang_and_cxie_laptop_inventory(laptop_id,model,size,processor,ram,graphics,storage_space,cost,retail,condition_laptop) " 
	+ " values(1,'HP','15 inches','i5 4th gen','6GB RAM','integrated','500GB HDD',105,300,'used'),(2,'ASUS','15 inches','i7 6th gen','16GB RAM','960 GTM','256GB SSD',300,999.99,'new')";
		
		try{
			con = obj.sql_connection();
			statement = con.createStatement();
			statement.executeUpdate(sql_insertInventory);
			System.out.println("Value was successfully inserted into inventory table");
		}
					
		
		catch(SQLException se){
			se.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @method createTicket
	 * @param ticketName
	 * @param ticketDesc
	 * @param priority
	 * 
	 * The method create ticket takes three argument. It takes in a ticket name, ticket description, and ticket priority.
	 * The purpose of this we can give the ticket a name or name of the user submitting the ticket. 
	 * The ticket description describes the problem.
	 * The ticket priority takes what is the priority of the of the ticket. It is urgent or not by high,medium, and low.
	 * 
	 * It takes the parameter and insert into the statement then run through executeUpdate to run the sql syntax that I have typed below.
	 * If the result returns a value of 0 then the ticket was not sucesssfully created.
	 * I have ticket id =0 just for declaring it so I can give it a key later.
	 * Next using getGeneratedKeys I get the id number that was just created for the current ticket.
	 * Then I display a notification of the ticket number that was created. 
	 */
	public void createTicket(String ticketName, String ticketDesc, String priority){
		
		try {
				
				con = obj.sql_connection();
				statement = con.createStatement();
	            int result = statement.executeUpdate("Insert into etang_and_cxie_Ticket(ticket_name, ticket_description,ticket_status,ticket_priority,ticket_time) values('"+ticketName+"','"+ticketDesc+"','open','"+priority+"',CURRENT_TIMESTAMP)", Statement.RETURN_GENERATED_KEYS);
	            int ticketid = 0;
	            if (result != 0) {
					System.out.println("Ticket Created Successfully!!!");
				} else {
					System.out.println("Ticket cannot be Created!!!");
				}
	            
	            String sql_select = "SELECT ticket_id FROM etang_and_cxie_Ticket WHERE ticket_name= '" + ticketName + "' and ticket_description= '" + ticketDesc + "'";
	            ResultSet rs = statement.getGeneratedKeys();
	            if (rs.next()){
	            	ticketid = rs.getInt(1);
	            }
	           
	            
	            
		        JOptionPane.showMessageDialog(null,"Ticket id: " + ticketid + " created");
		    } 
		       catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
		       }catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						if(statement!=null)
							statement.close();
					}catch(SQLException se2){
						
					}try{
						if(con!=null)
							con.close();
				}catch(SQLException se){
						se.printStackTrace();
					}
				}
	}
	
	/**
	 * @method closeTicket
	 * @param ticketid
	 * 
	 * The method purpose is take in a parameter which is the ticket id to determine which ticket will be closed.
	 * This is done by updating the ticket status to String closed. 
	 * I also include an error check to see if the ticket was succesfully closed or not.
	 */
	public void closeTicket(int ticketid){
		try{
			con = obj.sql_connection();
			statement = con.createStatement();
			String sql_update = "UPDATE etang_and_cxie_ticket " + "SET ticket_status = 'closed' WHERE ticket_id =" + ticketid;
			int result = statement.executeUpdate(sql_update);
	        if (result != 0) {
						System.out.println("Ticket closed Successfully!!!");
					} else {
						System.out.println("Ticket cannot be closed!!!");
					}
			JOptionPane.showMessageDialog(null, "Ticket #" + ticketid + " has been closed");
		
		}catch (SQLException se ){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
					
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
			
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * @updateTicket()
	 * @param id
	 * @param description
	 * 
	 * The method takes in an id and a description. It searches the ticket by the ticket id and then replace the older description with the new description that it recieves from the argument.
	 * There a check if the ticket was updated successfully or not.
	 * Then notifiy the user that the ticket number has been updated succesfully.
	 */
	public void updateTicket(int id, String description){
		try{
			con = obj.sql_connection();
			statement = con.createStatement();
			
			String sql_update = "UPDATE etang_and_cxie_ticket " + "SET ticket_description = '"+description+"' WHERE ticket_id =" + id;
			int result = statement.executeUpdate(sql_update);
	        if (result != 0) {
						System.out.println("Ticket Updated Successfully!!!");
						JOptionPane.showMessageDialog(null, "Ticket #" + id + " has been successfully updated.");
					} else {
						System.out.println("Ticket cannot be Updated!!!");
					}

			
		}catch (SQLException se ){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
				
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
				
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * @method deleteTicket
	 * @param ticketarray
	 * 
	 * This method takes the array list that is provided to delete which ever ticket was listed.
	 * There a confirmation message that prompts the user to click yes if they are sure they want to delete the ticket. If they say No, it will move on to the next ticket.
	 * If the user hit cancel it will cancel the entire process all together by using the break <-. 
	 */
	public void deleteTicket(ArrayList<Integer> ticketarray){

		try{
			con = obj.sql_connection();
			statement = con.createStatement();
			
			for (int i=0;i<ticketarray.size();i++){
				
				int x = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this ticket #" + ticketarray.get(i)); 
				if (x==0){
					String sql_delete = "DELETE FROM etang_and_cxie_ticket " + "WHERE ticket_id=" + ticketarray.get(i);
					int result = statement.executeUpdate(sql_delete);
					if (result != 0) {
						System.out.println("Ticket Updated Successfully!!!");
						JOptionPane.showMessageDialog(null, "Ticket ID: " + ticketarray.get(i) + " was successfully deleted.");
					} else {
						System.out.println("Ticket cannot be Updated!!!");
					}
				}
				else if (x==1){
					JOptionPane.showMessageDialog(null, "Ticket #" + ticketarray.get(i) + " was not deleted.");
				}
				else if (x==2){
					JOptionPane.showMessageDialog(null, "Current process of deleteing ticket has been canceled");
					break;
				}
			}
			
			con.close();
			statement.close();
		}catch(SQLException se ){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
					
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
				
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @method viewTicket
	 * @param view_cond
	 * @param ticketarray
	 * @return
	 * 
	 * The purpose of viewTicket is take 2 arguments. One is a list of array that supplies the ticketid if applicable depending if the user want to see specific tickets or all.
	 * Which view_cond will either say yes which means view all ticket or no that wants to view a specific ticket.
	 * 
	 * Then in the method I made an arrayList String so I can return the result back to whomever is calling this method.
	 * The result array is holding the value by expanding itself and using rs.getString(index of column). 
	 */
	public ArrayList<String> viewTicket(String view_cond, ArrayList<Integer> ticketarray){
			
		ArrayList<String> sql_result = new ArrayList <String>();
		if(view_cond == "yes"){
		try{
			
			con = obj.sql_connection();
			statement = con.createStatement();
			System.out.println("Connected to database");
			System.out.println("Creating Statement to retrieve data");
			
				String sql_select = "SELECT * FROM etang_and_cxie_Ticket";
				ResultSet rs = statement.executeQuery(sql_select);
								
				while(rs.next()){
					sql_result.add((rs.getString(1)));
					sql_result.add((rs.getString(2)));
					sql_result.add((rs.getString(3)));
					sql_result.add((rs.getString(4)));
					sql_result.add((rs.getString(5)));
				}
				
		
		}catch(SQLException se){
			se.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
					
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
				
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return sql_result;
		
		
	
		
	}else if(view_cond == "no"){
		try{
			
			con = obj.sql_connection();
			statement = con.createStatement();
			System.out.println("Connected to database");
			System.out.println("Creating Statement to retrieve data");
			
			for (int i=0;i<ticketarray.size();i++){
				String sql_select = "SELECT * FROM etang_and_cxie_Ticket WHERE ticket_id=" + ticketarray.get(i);
				ResultSet rs = statement.executeQuery(sql_select);
								
				while(rs.next()){
					sql_result.add((rs.getString(1)));
					sql_result.add((rs.getString(2)));
					sql_result.add((rs.getString(3)));
					sql_result.add((rs.getString(4)));
					sql_result.add((rs.getString(5)));
				}
				
			}
		
		}catch(SQLException se){
			se.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
					
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
				
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("returning arrazy size for sql_result 1 "+ sql_result.size());
		return sql_result;
		
		}
		System.out.println("returning arrazy size for sql_result 2"+ sql_result.size());
		return sql_result;
	}
	
	/**
	 * @method consoleSQL()
	 * This method is generating the information like duration of ticket since it has been created. This done by using the timestamp that was enter and then getting the differences between
	 * the current timestamp.
	 * The ratio was calculated by using the SUM method where ticket status is closed and dividing the by the total counts of tickets.
	 * The high priority is just use a where clause to find which ticket is high.
	 * 
	 */
	public void consoleSQL(){
		try{
			
			con = obj.sql_connection();
			statement = con.createStatement();
			System.out.println("Connected to database");
			System.out.println("Creating Statement to retrieve data");
			
				String sql_select = "select ticket_id, ticket_description, timestampdiff(minute,ticket_time,current_timestamp) FROM etang_and_cxie_Ticket";
				ResultSet rs = statement.executeQuery(sql_select);
				System.out.println("Ticket duration: ");
				while(rs.next()){
						String id = rs.getString(1);
						String description = rs.getString(2);
						String minute = rs.getString(3);
						System.out.println("Ticket ID: " + id + ", Ticket Description: "+description+" ,Ticket duration: " +minute+ " minutes");
				}
				
				sql_select ="select ticket_id,ticket_name, ticket_description ,ticket_priority FROM etang_and_cxie_Ticket WHERE ticket_priority = 'High' ";
				rs = statement.executeQuery(sql_select);
				
				System.out.println("\n");
				System.out.println("Ticket with the highiest priority status:");
				while(rs.next()){
					String id = rs.getString(1);
					String name = rs.getString(2);
					String desc = rs.getString(3);
					String priority = rs.getString(4);
					System.out.println("Ticket ID: " + id + ", Ticket Name: " + name + ", Ticket Description:  "+desc+" ,Ticket priority status: "+ priority );
				}
				
				System.out.println("\n");
				sql_select ="select SUM(case when ticket_status = 'closed' then 1 else 0 end)/count(*) as closed_ratio FROM etang_and_cxie_Ticket";
				rs = statement.executeQuery(sql_select);
				while(rs.next()){
					String closed_ratio = rs.getString(1);
					System.out.println("The ratio of closed tickets to open tickets is " + closed_ratio);
				}
				
				
		
		}catch(SQLException se){
			se.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement!=null)
					statement.close();
					
			}catch(SQLException se2){
				
			}try{
				if(con!=null)
					con.close();
				
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
}
	
	


