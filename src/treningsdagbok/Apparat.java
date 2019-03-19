package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Apparat {
	String apparatNavn;
	String ovelsesNavn;
	String beskrivelse;
	
	public Apparat(Connection myConn, Scanner scanner, String apparatNavn) throws Exception {
		this.apparatNavn = apparatNavn;
		try {
			Statement statement = myConn.createStatement();
			String exists = String.format("select * from apparat where apparatnavn = '%s';", apparatNavn);
			
			ResultSet Rs = statement.executeQuery(exists);
			if (Rs.next()) {
				System.out.println("Apparat finnes allerede");
			}
			else {
				System.out.println("Lager nytt apparat");
				System.out.println("Angi beskrivelse for apparatet:");
				scanner.nextLine();
				beskrivelse = scanner.nextLine();
				System.out.println("Angi øvelsesnavn:");
				scanner.nextLine();
				ovelsesNavn = scanner.nextLine();
				
				String apparatInsert = String.format("insert into Apparat values('%s', '%s', '%s');", apparatNavn, ovelsesNavn, beskrivelse);
				statement.executeUpdate(apparatInsert);
			
				System.out.println("");
			}	
		} catch (Exception e) {
			throw e;
		} 
	}
}
