package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.orangehrm.base.BaseClass;

public class DBConnection {
	private static final String DB_URL="jdbc:mysql://localhost:3306/orangehrm";
	private static final String DB_USERNAME="root";
	private static final String DB_PASSWORD="";
	public static final Logger logger=BaseClass.logger;
	
	private static Connection getDBConnection() {
		try {
			logger.info("Starting DB connection....");
			Connection conn=DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			logger.info("DB connection successfull");
			return conn;
		} catch (SQLException e) {
			logger.error("error while establish the db connection");
			e.printStackTrace();
		}
		return null;
	}
	//Get the employeedetails from the database and stored in a map.
	public static Map<String,String> getEmployeeDetails(String employee_id){
		String query="SELECT emp_firstname,emp_middle_name,emp_lastname FROM hs_hr_employee WHERE employee_id="+employee_id;
		Map<String,String> EmployeeDetails= new HashMap<>();
		
		try(Connection conn=getDBConnection();
				Statement stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(query)){
			logger.info("Executing query:"+query);
			if(rs.next()) {
				String firstName=rs.getString("emp_firstname");
				String middleName=rs.getString("emp_middle_name");
				String lastName=rs.getString("emp_lastname");
				
				//Store in a map
				EmployeeDetails.put("firstName", firstName);
				EmployeeDetails.put("middleName", middleName!=null? middleName:"" );
				EmployeeDetails.put("lastName", lastName);
				logger.info("Query executed successfully");
				logger.info("Employee data fetched");
			}
			else {
				logger.error("Data not found");
			}
		}
		catch(Exception e) {
			logger.info("errr while executing query");
			e.printStackTrace();
		}
		return EmployeeDetails;
	}
}
