package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class FriOvelse extends Ovelse {
	private String beskrivelse;
	
	FriOvelse(Connection conn, Okt currentokt){
		super(conn, currentokt);
	}
	
	public String getBeskrivelse() {
		return this.beskrivelse;
	}
	
	
	public void selectFri�velse(Scanner sc) {
		this.select�velse(sc);
		String fri�velseGet=String.format("select beskrivelse from friovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.beskrivelse=String.valueOf(st.executeQuery(fri�velseGet));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void nyFri�velse(Scanner sc) {
		this.nyOvelse(sc);
		
		System.out.println("beskrivelse: \n");
		this.beskrivelse=sc.next();
		
		
		String �velsesIns = String.format("insert into friovelse(ovelsesnavn, beskrivelse) values('%s','%s');", this.ovelsesnavn, this.beskrivelse);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(�velsesIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}