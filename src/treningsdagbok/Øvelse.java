package treningsdagbok;

import java.sql.*;
import java.util.Scanner;


public abstract class Øvelse {
	public Connection conn;
	public String ovelsesnavn;
	
	
	public Øvelse(Connection conn){
		this.conn=conn;
	}
	
	public String getovelsesnavn() {
		return this.ovelsesnavn;
	}
	
	
	//velger en øvelse fra databasen
	public void selectØvelse(Scanner sc) {
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
	public void nyØvelse(Scanner sc) {
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

