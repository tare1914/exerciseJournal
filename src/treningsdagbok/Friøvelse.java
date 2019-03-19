package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Friøvelse extends Ovelse {
	private String beskrivelse;
	
	Friøvelse(Connection conn){
		super(conn);
	}
	
	public String getBeskrivelse() {
		return this.beskrivelse;
	}
	
	
	public void selectFriØvelse(Scanner sc) {
		this.selectØvelse(sc);
		String friØvelseGet=String.format("select beskrivelse from friovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.beskrivelse=String.valueOf(st.executeQuery(friØvelseGet));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void nyFriØvelse(Scanner sc) {
		this.nyØvelse(sc);
		
		System.out.println("beskrivelse: \n");
		this.beskrivelse=sc.next();
		
		
		String øvelsesIns = String.format("insert into friovelse(ovelsesnavn, beskrivelse) values('%s','%s');", this.ovelsesnavn, this.beskrivelse);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(øvelsesIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}