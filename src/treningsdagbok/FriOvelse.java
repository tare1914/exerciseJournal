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
	
	public void velgOvelse(Scanner sc, Connection conn, Okt currentOkt) throws SQLException {
		System.out.println("Legge til ny eller bruke gammel? (n/m): \n");
		String ny = sc.next();
		if (ny.equals("n")) {
			this.nyFriÿvelse(sc, conn);
		}
		else {
			this.selectFriOvelse(sc, conn);
		}
		OvelseIOkt iokt = new OvelseIOkt(conn);
		iokt.addOvelseiokt(conn, sc, currentOkt.getOktID(), this.ovelsesnavn);
	}
	
	public void selectFriOvelse(Scanner sc, Connection conn) {
		this.selectÿvelse(sc, conn);
		String friÿvelseGet=String.format("select beskrivelse from friovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.beskrivelse=String.valueOf(st.executeQuery(friÿvelseGet));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void nyFriÿvelse(Scanner sc, Connection conn) {
		this.nyOvelse(sc, conn);
		
		System.out.println("beskrivelse: (no space)\n");
		this.beskrivelse=sc.next();
		
		
		String ¯velsesIns = String.format("insert into friovelse(ovelsesnavn, beskrivelse) values('%s','%s');", this.ovelsesnavn, this.beskrivelse);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(¯velsesIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}