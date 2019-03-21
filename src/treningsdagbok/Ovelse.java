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
	
	
	//velger en øvelse fra databasen
	public void selectØvelse(Scanner sc, Connection conn) {
		System.out.println("Velg Øvelse\n");
		this.ovelsesnavn=sc.next();
		String øvelseGet=String.format("select ovelsesnavn from ovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			st.executeQuery(øvelseGet);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//legger til en ny øvelse i databasen
	public void nyOvelse(Scanner sc, Connection conn) {
		System.out.println("Legg til ny øvelse\n");
		this.ovelsesnavn=sc.next();
		
		
		String øvelseIns = String.format("insert into ovelse(ovelsesnavn) values('%s');",this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(øvelseIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//public abstract void addToØvelsesgruppe(Øvelsesgruppe øgruppe);

}

