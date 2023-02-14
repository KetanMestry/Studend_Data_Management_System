package finalProject;

import java.sql.*;
import java.util.Scanner;

public class StudentDataManagement {
	Scanner sc = new Scanner(System.in);
	ResultSet result;

	
	//Method to make connection to database
	PreparedStatement createConection(String querry) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		
		//Create connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentsDB","root","root");
				
		//Creating prepared Statement
		PreparedStatement pt = conn.prepareStatement(querry);
		
		return pt;
		//conn.close();
	}

	
	//Method to add entry to database
	void addEntry(PreparedStatement pt)  {	
		
		sc.nextLine();

		System.out.print("Enter name of student: ");
		String name = sc.nextLine();
		
		System.out.print("Enter age of student: ");
		int age = sc.nextInt();
		
		System.out.print("Enter class of student: ");
		int cls = sc.nextInt();
		
		try {
			pt.setString(1, name);
			pt.setInt(2, age);
			pt.setInt(3, cls);
			
			int i = pt.executeUpdate();
			System.out.println("Data inserted to database successfully");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	
	//Method to get and print
	void getAllStudData(PreparedStatement pt) {
		try {
			 result = pt.executeQuery();
			 System.out.println("\t  ________________________________________");

			 System.out.println("\t | Id\tName\t\tage\tclass\t  |");
			 System.out.println("\t |________________________________________|");
			 
			 while(result.next()) {
				 int id = result.getInt("stud_id");
				 String name = result.getString("stud_name");
				 int age = result.getInt("stud_age");
				 int cls = result.getInt("stud_class");
				 
				 System.out.println("\t |" + id + "\t" + name + "\t\t" + age + "\t" + cls + " \t  |");
			 }
			 System.out.println("\t |________________________________________|");

		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	
	//Method to get and print student data by student id
	void getStudDataById(PreparedStatement pt) {
		System.out.print("\t Enter student Id: ");
		int id = sc.nextInt();
		try {
			pt.setInt(1, id);
			result = pt.executeQuery();
			 System.out.println("\t  ________________________________________");

			 System.out.println("\t | Id\tName\t\tage\tclass\t  |");
			 System.out.println("\t |________________________________________|");
			 
			 while(result.next()) {
				 int studId = result.getInt("stud_id");
				 String name = result.getString("stud_name");
				 int age = result.getInt("stud_age");
				 int cls = result.getInt("stud_class");
				 
				 System.out.println("\t |" + studId + "\t" + name + "\t\t" + age + "\t" + cls + " \t  |");
			 }
			 System.out.println("\t |________________________________________|");

		}catch(SQLException e) {
			System.out.println(e);
		}
		
	}
	
	
	//Method to delete student data by student id
	void delStudDataById(PreparedStatement pt) {
		System.out.print("\t Enter student Id: ");
		int id = sc.nextInt();
		try {
			pt.setInt(1, id);
			int i = pt.executeUpdate();
			System.out.println("\t Data deleted successfully");
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		
	}
	
	//Method to delete student data by student name
	void delStudDataByName(PreparedStatement pt) {
		System.out.print("\t Enter student name: ");
		String name = sc.nextLine();
		try {
			pt.setString(1, name);
			int i = pt.executeUpdate();
			System.out.println("\t Data deleted successfully");
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		// Scanner object
		Scanner sc = new Scanner(System.in);
		
		//Object of class
		StudentDataManagement st = new StudentDataManagement();
		
		//Get prepared statement
		PreparedStatement pt;
		
		//variables to continue loop
		char repeat;
		char repeatGetData;
		char repeatDelData;
		
		
		//Main loop
		do {
			System.out.println("------------------------------");
			System.out.println("1. Enter Student data");
			System.out.println("2. Get Student data");
			System.out.println("3. Delete student data");
			System.out.print("Choose from above options.");
			
			int ch = sc.nextInt();

			switch(ch) {
			// Case 1
			case 1:
				String getDataQuerry = "INSERT INTO students(stud_name, stud_age, stud_class) VALUES (?,?,?)";
				pt = st.createConection(getDataQuerry);
				st.addEntry(pt);
			break;
			
			//Case 2
			case 2:
				//Fetch data loop
				do {
				System.out.println("****************************************************");
				System.out.println("\t 1. Get all students data");
				System.out.println("\t 2. Get student data by id");
				System.out.print("\t Choose from above options: ");
				int getDataCh = sc.nextInt();
				switch(getDataCh) {
				case 1:
					String getAllDataQuerry = "SELECT * FROM students";
					pt = st.createConection(getAllDataQuerry);
					st.getAllStudData(pt);
					break;
					
				case 2:
					String getDataByIdQuerry = "SELECT * FROM students WHERE stud_id = (?)";
					pt = st.createConection(getDataByIdQuerry);
					
					st.getStudDataById(pt);
					break;
					
				default:System.out.println("Enter valid options");
					
				}
				System.out.print("\t Get another student data? (Press y to continue): ");
				repeatGetData = sc.next().charAt(0);
				}while(repeatGetData == 'y' || repeatGetData == 'Y');
				System.out.println("****************************************************");
			break;//main switch case 2 break
			
			//case 3
			case 3:
				//Delete data loop
				do {
					System.out.println("****************************************************");
					System.out.println("\t 1. Delete all students data");
					System.out.println("\t 2. Delete student data by id");
					System.out.println("\t 2. Delete student data by name");
					System.out.print("\t Choose from above options: ");
					int delDataCh = sc.nextInt();
					switch(delDataCh) {
					case 1:
						String delAllDataQuerry = "TRUNCATE students";
						pt = st.createConection(delAllDataQuerry);
						pt.execute(delAllDataQuerry);
						break;
						
					case 2:
						String delDataByIdQuerry = "DELETE FROM students WHERE stud_id = ?";
						pt = st.createConection(delDataByIdQuerry);
						
						st.delStudDataById(pt);
						break;
						
					case 3:
						String delDataByNameQuerry = "DELETE FROM students WHERE stud_id = ?";
						pt = st.createConection(delDataByNameQuerry);
						
						st.delStudDataByName(pt);
						break;
						
					default:System.out.println("Enter valid options");
						
					}
					System.out.print("\t Delete another student data? (Press y to continue): ");
					repeatDelData = sc.next().charAt(0);
					}while(repeatDelData == 'y' || repeatDelData == 'Y');
					System.out.println("****************************************************");
				break;
			
			default:System.out.println("Enter valid option.");
			
			}

			System.out.print("Do you want to Continue?: (Press y to continue):");
			 repeat = sc.next().charAt(0);
			 
		}while(repeat == 'y' || repeat == 'Y');

		System.out.println("Bye");
		}

}
