package treningsdagbok;

import java.sql.*;
import java.util.Scanner;


public abstract class �velse {
	public Connection conn;
	public String ovelsesnavn;
	
	
	public �velse(Connection conn){
		this.conn=conn;
	}
	
	public String getovelsesnavn() {
		return this.ovelsesnavn;
	}
	
	
	//velger en �velse fra databasen
	public void select�velse(Scanner sc) {
		System.out.println("Velg �velse\n");
		this.ovelsesnavn=sc.next();
		String �velseGet=String.format("select ovelsesnavn from ovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			st.executeQuery(�velseGet);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//legger til en ny �velse i databasen
	public void ny�velse(Scanner sc) {
		System.out.println("Legg til ny �velse\n");
		this.ovelsesnavn=sc.next();
		
		
		String �velseIns = String.format("insert into ovelse(ovelsesnavn) values('%s');",this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(�velseIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//public abstract void addTo�velsesgruppe(�velsesgruppe �gruppe);

}

