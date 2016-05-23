import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.StringUtils;

import javax.swing.JPanel;
import javax.swing.JRadioButton;



/**
 * @author Elton Tang
 * @version 4/29/2016
 * ITMD_411_Final_Project_Elton_Tang_
 *ticketsGUI.java
 *
 *Final Project
 */
@SuppressWarnings("serial")
public class ticketsGUI extends JFrame implements ActionListener {
	
	/**
	 * I declared any variable that I wish to use globally in this class in the namespace.
	 * Also I established partly of the JFrame here so I can access this later when I want to insert row into the JTable.
	 */
	sql_db sql = new sql_db();
	static my_sql_connection sql_connection = new my_sql_connection();
	static Connection con = null;
	static Statement statement = null;
	JFrame mainFrame;
	JPanel p = new JPanel();
	Object columnNames [] = {"Ticket Number", "Ticket Name", "Ticket Description", "Ticket Status", "Ticket Priority Status"};
	DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	JTable table = new JTable(tableModel);
	JScrollPane scrollPane = new JScrollPane(table);
	

	
	//incl main menu objects
	private	JMenu file = new JMenu("File");
	private	JMenu tickets = new JMenu("Tickets");
	JScrollPane sp = null;
	//incl sub menu item objects
	JMenuItem ItemNew;
    JMenuItem ItemExit;
	
    JButton OpenTicket;
    JButton ViewTicket;
    JButton CreateTicket;
    JButton CloseTicket;
    JButton UpdateTicket;
    JButton DeleteTicket;
    JButton ClearTicket;
    JButton consoleSQL;
    //include more items below
	
    /**
     * @constructor ticketsGUI
     * This is my constructor, so when is called upon it executes other methods that I listed in the constructor.
     */
	public ticketsGUI() {
		createMenu();
	    prepareGUI();

	    sql.createLoginTable();
	    sql.createInventoryTable();
	    sql.createTicketTable();
	    sql.insertLoginTable();
	    sql.insertInventoryTable();
	   
	}
	


	/**
	 * @createMenu()
	 * Here I am desigining the template of the menu and adding event listener. So when the buttons are clicked it acknowledge and act depending on the statement near bottom of the this java file.
	 */
private void createMenu()
{
	
	file.setMnemonic('F');
	
	ItemNew = new JMenuItem("New");
	ItemNew.setMnemonic('N');
	file.add(ItemNew);
	
	ItemExit = new JMenuItem("Exit");
	ItemExit.setMnemonic('x');
	file.add(ItemExit);

	
	ViewTicket = new JButton("View a Ticket(s)");
	ViewTicket.setMnemonic('x');
	
	OpenTicket = new JButton("Open a Ticket(s)");
	OpenTicket.setPreferredSize(new Dimension(40,40));

	CreateTicket = new JButton("Create Ticket(s)");
	CloseTicket = new JButton("Close Ticket(s)");
	UpdateTicket = new JButton("Update Ticket(s)");
	DeleteTicket = new JButton("Delete Ticket(s)");
	ClearTicket = new JButton ("Clear Ticket(s)");
	consoleSQL = new JButton ("SQL Console Info.");
	
	//incl more sub menu items below
	
	//add listeners for each desired menu item 

	ViewTicket.addActionListener(this);
	OpenTicket.addActionListener(this);
	CloseTicket.addActionListener(this);
	UpdateTicket.addActionListener(this);
	DeleteTicket.addActionListener(this);
	ClearTicket.addActionListener(this);
	consoleSQL.addActionListener(this);
	

}

/**
 * @method insertJTable()
 * @param arrayList
 * 
 * This method is takes the parameter of array list that is passed to it and dump into a for loop that assign the values in each index of the array
 * to a String. Then I declare an Object data and place the String into the object then add it as a row to the jtable.
 */
private void insertJTable(ArrayList<String> arrayList){
	
		for (int i=0;i<arrayList.size();i++){
			
			String ticket_id = arrayList.get(i).toString();
			String ticket_name = arrayList.get(++i).toString();
			String ticket_description = arrayList.get(++i).toString();
			String ticket_status = arrayList.get(++i).toString();
			String ticket_priority = arrayList.get(++i).toString();
			
			Object[] data = {ticket_id, ticket_name, ticket_description, ticket_status, ticket_priority};
			tableModel.addRow(data);
		}
		scrollPane.setBounds(30,40,200,300);
		mainFrame.add(scrollPane);
		mainFrame.setVisible(true);
	}

/**
 * @method updateJTable
 * @param arrayList
 *This method is to be executed after a user updates the ticket. This will search if the ticket is being viewed on the JTable. If it is, it will remove it from the JTable
 *and add the new updated JTable in the original position. This is done with a for loop and an if statement.
 * 
 */
private void updateJTable(ArrayList<String> arrayList){
	
	int row=0;
	int check = 0;
		
	String ticket_id = arrayList.get(0).toString();
	String ticket_name = arrayList.get(1).toString();
	String ticket_description = arrayList.get(2).toString();
	String ticket_status = arrayList.get(3).toString();
	String ticket_priority = arrayList.get(4).toString();

	if(table.getRowCount() != 0){
		for(int i=0;i<table.getRowCount();i++){
			for (int j=0; j<table.getColumnCount();j++){
				if(table.getModel().getValueAt(i, j).equals(ticket_id)){
					System.out.println(table.getModel().getValueAt(i, j));
					row = i;
					tableModel.removeRow(row);
					Object[] data = {ticket_id, ticket_name, ticket_description, ticket_status, ticket_priority};
					tableModel.insertRow(row,data);
					check++;
				}
			}
		}
	}
	else{
		System.out.println("There no row being display on the JTable");
	}
	
	
	if (check ==0){
		Object[] data = {ticket_id, ticket_name, ticket_description, ticket_status, ticket_priority};
		tableModel.addRow(data);
		}
	else{
		System.out.println("row already been inserted");
	}
	
	scrollPane.setBounds(30,40,200,300);
	mainFrame.add(scrollPane);
	mainFrame.setVisible(true);
}

/**
 * @method removeRowJTable()
 * This search for the row that is displayed on the JTable.
 * If it is, it will remove the row from the JTable
 */
private void removeRowJTable(ArrayList<Integer> ticketarray) {
	int row=0;
	String[] ticket_id = new String[ticketarray.size()];
	System.out.println("Removing row");
	System.out.println("Array size:" + ticketarray.size());
	
	for (int i=0;i<ticketarray.size();i++){
		ticket_id[i] = ticketarray.get(i).toString();
		System.out.print("testing" + ticket_id[i] + "value of i" + i);
	}

	if(table.getRowCount() != 0){
		for(int k=0;k<ticketarray.size();k++){
			for(int i=0;i<table.getRowCount();i++){
				for (int j=0; j<table.getColumnCount();j++){
					if(table.getModel().getValueAt(i, j).equals(ticket_id[k].toString())){
						System.out.println(table.getModel().getValueAt(i, j));
						row = i;
						tableModel.removeRow(row);
						table.revalidate();
					}
				}
			}
		}
	}
	else{
		System.out.print("There no table being displayed");
	}
	
	scrollPane.setBounds(30,40,200,300);
	mainFrame.add(scrollPane);
	mainFrame.setVisible(true);
}

/**
 * @method clearJTable
 * I made this method to clear the JTable of rows that is currently present on the GUI. This will provides a clean field for the user if they wish to only see 
 * specific they want to see.
 */

private void clearJTable(){
	
	
	for (int i=tableModel.getRowCount()-1; i>=0 ;i--){
		tableModel.removeRow(i);
		table.revalidate();
	}
}
 
/**
 * @method prepareGUI()
 * 
 * This method is just setting up the GUI menu. The butotn and JTable are added to the GUI by using the .add feature.
 * Also declared the GridLayout so 7 rows and 1 column. The button goes to rows and jtable goes column.
 */
private void prepareGUI()
{ 
	
	mainFrame = new JFrame("IIT Help Desk");
	
    
	
	
	JMenuBar bar = new JMenuBar();
	bar.add(file);  //set menu orders
	bar.add(tickets);
	 //add menu bar component to frame
    mainFrame.setJMenuBar(bar); 
    
   
    p.setLayout(new GridLayout(7,1,0,3));
    p.add(ViewTicket);
    p.add(OpenTicket);
    p.add(CloseTicket);
    p.add(UpdateTicket);
    p.add(DeleteTicket);
    p.add(ClearTicket);
    p.add(consoleSQL);
   
    
    mainFrame.add(p,BorderLayout.WEST);
    
   // mainFrame.add(ViewTicket);
    //mainFrame.add(OpenTicket);
    //mainFrame.add(CloseTicket);
   // mainFrame.add(UpdateTicket);
   // mainFrame.add(DeleteTicket);
    

    //mainFrame.add(OpenTicket,BorderLayout.SOUTH);
    //mainFrame.getContentPane().add(ViewTicket);
   
 // this will terminate the application when user click close.
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(800,800); 
	mainFrame.setVisible(true);
}


@Override
/**
 * @actionPerfromed()
 * This method handles all the eventhandle when someone clicks the button. 
 * Each if and else if statement will act depending which button is clicked.
 * 
 * The genearl function of this it checks of error-trapping if the program request a number to be enter it checks for a value and check if it's positive or negative.
 * Also checks for String that enter if they are empty space or not.
 * When user hits enter it exit the current process. 
 * Overall this action performed calls methods from class sql_db and sends the the correct args that is needed to perform the neccessary task.
 */
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	

	 if(e.getSource() == ItemExit){
         System.exit(0);
     }

	 else if(e.getSource() == ViewTicket){
		 int x=0;
		 ArrayList <Integer> ticketarray = new ArrayList<Integer>();
		 int check=0;
		 
		String ticket_s = JOptionPane.showInputDialog(null, "Do you want to view all the current tickets? (yes/no)");
		while(x==0){
			if(ticket_s == null){
				JOptionPane.showMessageDialog(null, "Viewing ticket has been cancel");
				break;
			}
			else if(ticket_s.equalsIgnoreCase("yes")){
				x++;
				String view_yes = "yes";
				insertJTable(sql.viewTicket(view_yes,ticketarray));
			}else if(ticket_s.equalsIgnoreCase("no")){
				x++;
				String view_no = "no";
				String  ticketview = JOptionPane.showInputDialog(null,"How many ticket you wish to view?");
				if(ticketview == null){
					JOptionPane.showMessageDialog(null, "Viewing ticket has been cancel");
					break;
				}else {
					try{
						int view = Integer.parseInt(ticketview);
					}catch (NumberFormatException e1){
						JOptionPane.showMessageDialog(null, "Please enter a valid number!");
					}
				
				int view = Integer.parseInt(ticketview);
				
				if (view >0){
					
					for (int i=0;i<view;i++){
						String ticket_id = JOptionPane.showInputDialog(null, "Enter the ticket id you want to view");
						if(ticket_id == null){
							JOptionPane.showMessageDialog(null, "viewing a ticket has been cancel");
							break;
						}
						else{
							try{
								int id = Integer.parseInt(ticket_id);
							}catch (NumberFormatException e1){
								JOptionPane.showMessageDialog(null, "Please enter a valid number!");
							}
						
							int id = Integer.parseInt(ticket_id);
							if (id > 0){
								ticketarray.add(id);
									}		 
							else if (id < 0){
								JOptionPane.showMessageDialog(null, "Please enter a positive ID number");
							}else if (id == 0){
								JOptionPane.showMessageDialog(null, "Please enter an ID number that is greater than 0");
							}							
						}
					
					insertJTable(sql.viewTicket(view_no,ticketarray));
					
					}		
				}

				else if (view < 0 ){
					JOptionPane.showMessageDialog(null, "Please enter a positive number.");
				}
				
				else if (view == 0){
					JOptionPane.showMessageDialog(null, "Please enter a number greater than 0");
				}

				else{
					ticket_s = JOptionPane.showInputDialog(null, "Please enter yes or no if you want to view all the tickets.");
					x=0;
					}
				}
			}
		}
	}
		
	 else if(e.getSource() == OpenTicket){
		 
		 int x =0;
		 int check1 = 0;
		 int check2 = 0;
		 String ticketName = null;
		 String ticketDesc = null;
		 String priority = null;
		 while(x!=1){
			 priority = JOptionPane.showInputDialog(null, "Enter the prioritiy of your ticket. (High, Medium, Low) ");
			 
			 if(priority == null){
				 check1 ++;
				 check2 ++;
				 x++;
				 JOptionPane.showMessageDialog(null, "Opening a ticket has been cancel");
				 break;
			 }
			 else if(priority.equalsIgnoreCase("high")){
			 priority = "High";
			 x++;
			 }
			 else if(priority.equalsIgnoreCase("medium")){
				 priority = "Medium";
				 x++;
			 }
			 else if(priority.equalsIgnoreCase("low")){
				 priority = "Low";
				 x++;
			 }
			 else if(StringUtils.isEmptyOrWhitespaceOnly(priority)){
				 JOptionPane.showMessageDialog(null, "Please enter a word. (High,Medium,Low)");
			 }else{
				 JOptionPane.showMessageDialog(null, "Please enter a valid word. (Reminder, (High,Medium,Low)");
			 }
		 }
		 
		
		 
		 while (check1 != 1){
		 ticketName = JOptionPane.showInputDialog(null,"Enter your name");
		 
		 if(ticketName == null){
			 check2++;
			 JOptionPane.showMessageDialog(null, "Opening a ticket has been cancel");
			 break;
			 
		 }
		 else if(StringUtils.isEmptyOrWhitespaceOnly(ticketName)){
			 JOptionPane.showMessageDialog(null, "Please do not leave the field blank.");
		 }else{
			 check1++;
		 }
		 }
		 while (check2 != 1){
			 ticketDesc = JOptionPane.showInputDialog(null,"Enter a ticket description");
			 if(ticketDesc == null){
				 JOptionPane.showMessageDialog(null, "Opening a ticket has been cancel");
				 break;
			 }
			 else if(StringUtils.isEmptyOrWhitespaceOnly(ticketDesc)){
				 JOptionPane.showMessageDialog(null, "Please do not leave the field blank.");
			 }else{
				 check2++;
				 sql.createTicket(ticketName, ticketDesc, priority);
			 }
			 }
		
		 
		 


 }
	 else if (e.getSource() == CloseTicket){
		 
		 String ticketID = JOptionPane.showInputDialog(null, "Enter the ticket number you want to close");
		 if(ticketID == null){
			 JOptionPane.showMessageDialog(null, "Closing a ticket has been cancel");
		 }
		 else {
			 try{
				 int id = Integer.parseInt(ticketID);
			 }catch (NumberFormatException e1){
				 JOptionPane.showMessageDialog(null, "Please enter a valid ID number");
			 }
		 
			 int id = Integer.parseInt(ticketID);
			 if (id > 0){
				 ArrayList <Integer> ticketarray = new ArrayList<Integer>();
				 String view_no = "no";
				 ticketarray.add(id);
				 sql.closeTicket(id);
				 updateJTable(sql.viewTicket(view_no,ticketarray));
				 
			 }
			 else if (id < 0){
				 JOptionPane.showMessageDialog(null, "Please enter a positive ID number");
			 }else if (id == 0){
				 JOptionPane.showMessageDialog(null, "Please enter an ID number that is greater than 0");
			 }
		 }
	 }
	 else if(e.getSource() == DeleteTicket){
		 
		 ArrayList <Integer> ticketarray = new ArrayList<Integer>();
		 String Amount = null;
		 String TicketID = null;
		 int id = 0;
		 int amt = 0;
		 
		 int check=0;
		 while (check != 1){
		 Amount = JOptionPane.showInputDialog(null,"How many ticket you wish to delete from the Ticket Table");
		 if(Amount == null){
			 check++;
			 JOptionPane.showMessageDialog(null, "Deleting ticket(s) has been cancel");
			 break;
		 }else{
			 try{
				 amt = Integer.parseInt(Amount);
			 }catch (NumberFormatException e1){
				 JOptionPane.showMessageDialog(null, "Please enter a valid number!");
			 }
		 }
		 		amt = Integer.parseInt(Amount);
	
		 		if(amt <=0){
		 			JOptionPane.showMessageDialog(null, "Please enter a positive number! Exiting current process.");
		 		}
		 		else if(amt > 0){
		 			check++;
		 			int confirm = 0;
		 			for (int i=0; i<amt;i++){
		 				int check1=0;
		 				while (check1 != 1){
		 					TicketID = JOptionPane.showInputDialog(null,"Enter the ticket number you wish to delete");
		 					if(TicketID == null){
		 						check1++;
		 						confirm ++;
		 						amt = 0;
		 			 			JOptionPane.showMessageDialog(null, "Deleting ticket(s) has been cancel");
		 						break;
		 					}
		 					else{
		 						try{
		 							id = Integer.parseInt(TicketID);
		 						}catch (NumberFormatException e1){
		 							JOptionPane.showMessageDialog(null, "Please enter a valid number!");
		 						}
		 						id = Integer.parseInt(TicketID);
		 						if (id <=0){
		 							JOptionPane.showMessageDialog(null, "Please enter a positive number!");
		 						}
		 						else if(id>0){
		 							ticketarray.add(id);
		 							check1++;
		 						}
		 					}
				 }
			 }
		 			 if(confirm == 0){
		 				 sql.deleteTicket(ticketarray);
						 String view_no = "no";
						 System.out.println("ticketarray size in delete:" + ticketarray.size());
						 removeRowJTable(ticketarray);
						 System.out.println("sending to delete ticket");
		 			 }
		 			 else 
		 				 break;
		 }
	 }
	}
	 else if (e.getSource() == UpdateTicket){
		 String ticketID =null;
		 String ticketDescription=null; 
		 int check = 0;
		 while(check !=1){
			 ticketID = JOptionPane.showInputDialog(null, "Enter the ticket ID you wish to update.");
			 if(ticketID == null){
				 JOptionPane.showMessageDialog(null, "Updating ticket has been cancel.");
				 break;
			 }else{
				 try{
					 int id = Integer.parseInt(ticketID);
				 }catch (NumberFormatException e1){
					 JOptionPane.showMessageDialog(null, "Please enter a valid ID number");
				 }
				 int id = Integer.parseInt(ticketID);	 
				 if (id > 0){
				 
					 int check2=0;
					 while(check2 != 1){
						 ticketDescription = JOptionPane.showInputDialog(null, "Enter the updated Description.");
						 	if(ticketDescription == null){
						 		check++;
						 		check2++;
						 		JOptionPane.showMessageDialog(null, "Updating ticket has been cancel.");
						 		break;
						 	}
						 else if(StringUtils.isEmptyOrWhitespaceOnly(ticketDescription)){
							 JOptionPane.showMessageDialog(null, "Please do not leave the field blank.");
						 }else{
							 ArrayList <Integer> ticketarray = new ArrayList<Integer>();
							 String view_no = "no";
							 ticketarray.add(id);
							 sql.updateTicket(id, ticketDescription);
							 updateJTable(sql.viewTicket(view_no,ticketarray));
							 check++;
							 check2++;
						 }
					 }
				 }
				 else if (id < 0){
					 JOptionPane.showMessageDialog(null, "Please enter a positive ID number");
				 }else if (id == 0){
					 JOptionPane.showMessageDialog(null, "Please enter an ID number that is greater than 0");
				 }
			 }
		 	}
		}
	 
	 else if (e.getSource() == ClearTicket){
		 clearJTable();
	 }
	 else if (e.getSource() == consoleSQL){
		 sql.consoleSQL();
	 }
}



/**
 * 
 * @param args
 * Executes the constructor of the program
 */
public static void main(String args[])
{
	
	new ticketsGUI();
}

}
