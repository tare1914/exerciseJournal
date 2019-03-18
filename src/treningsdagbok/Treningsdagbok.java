package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Treningsdagbok {
	private Connection connect = null;
	
	public void readDataBase() throws Exception {
		try {
			// Loading the MySQL
			Class.forName("com.mysql.jdbc.Driver");
	        // Setup the connection with the DB
	        connect = DriverManager
	                .getConnection("jdbc:mysql://localhost/feedback?"
	                        + "user=sqluser&password=sqluserpw");
		}
	}
}
