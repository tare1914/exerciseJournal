package treningsdagbok;

import java.sql.*;
import java.util.Scanner;


public class Ovelse {
	public Connection conn;
	public String ovelsesnavn;
	public Okt currentOkt;
	
	
	public Ovelse(Connection conn, Okt currentOkt){
		conn = this.conn;
		currentOkt = this.currentOkt;
	}
	
	public String getovelsesnavn() {
		return this.ovelsesnavn;
	}
	
	
	//velger en �velse fra databasen
	public void select�velse(Scanner sc, Connection conn) {
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
	public void nyOvelse(Scanner sc, Connection conn) {
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

