package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class OvelseIOkt {
	
	Connection conn;
	
	public OvelseIOkt(Connection conn) {
		this.conn = conn;
	}
	
	public void addOvelseiokt(Connection conn, Scanner sc, int oktid, String ovelsesnavn) throws SQLException {
		System.out.println("Prestasjon?: \n");
		int prestasjon = sc.nextInt();
		Statement st = conn.createStatement();
		String addin = String.format("insert into ovelseiokt values('%d', '%s', '%d');",oktid, ovelsesnavn, prestasjon);
		st.executeUpdate(addin);
	}
		
	
	
}
