package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class OvelsesGruppe {
	Connection connect;
	static String øvelsesgruppe;
	
	public OvelsesGruppe(Connection connect, Scanner scan) {
		this.connect = connect;
		String gruppetyper = String.format("select * from ovelsesgruppe;");
		
		try {
			Statement listgruppetyper = connect.createStatement();
			ResultSet myRs = listgruppetyper.executeQuery(gruppetyper);
			System.out.println("Gruppetyper registrert i databasen: \n");
			while (myRs.next()){
				String first = myRs.getString("gruppenavn");
				System.out.println(first);
				System.out.println("\n");
			}
		}catch(Exception e)	{
			e.printStackTrace();
		}
		
		System.out.println("Skriv navn på gruppetype:");
		gruppetyper = scan.next();
		
		try{
			String finnes = String.format("select * from ovelsesgruppe where gruppenavn= '%s';", gruppetyper);
			Statement statement = connect.createStatement();
			ResultSet myRs = statement.executeQuery(finnes);
			if (myRs.next())	{
				System.out.println("Gruppetype finnes allerede");
			}
			else	{
				
				String gruppeInsert = String.format("insert into ovelsesgruppe values('%s');", gruppetyper);
				System.out.println("");
				statement = connect.createStatement();
				statement.executeUpdate(gruppeInsert);
				
				}
			
		}
		catch(Exception e)	{
			e.printStackTrace();
			}
			
		}
		
		public OvelsesGruppe(Connection myconn, String øvelsesgruppe) { 
			this.øvelsesgruppe = øvelsesgruppe;
			
			try	{
			Statement statement = myconn.createStatement();
			
			System.out.println("*************************\n");
			String finnes = String.format("select * from ovelsesgruppe where gruppenavn = '%s';", øvelsesgruppe);
			
			ResultSet myRs = statement.executeQuery(finnes);
			if (myRs.next()){
				System.out.println("Gruppetypen finnes allerede");
			}
			else {
				System.out.println("Lager ny øvelsesgruppe");
				String gruppeInsert = String.format("insert into ovelsesgruppe values('%s');", øvelsesgruppe);
				System.out.println("");
				statement.executeUpdate(gruppeInsert);
					
				}
			}catch(Exception e){
				e.printStackTrace();
				
				
			}
		}
		
		public static void leggOv(Scanner sc, Connection conn) {
			System.out.println("Hvilken gruppe vil du legge til i?:");
			øvelsesgruppe = sc.next();
			System.out.println("Hvilken øvelse vil du legge inn?");
			String ovelse = sc.next();
			
			try {
			String ovelseInsert = String.format("insert into ovelseigruppe values('%s', '%s');", ovelse, øvelsesgruppe);
			Statement statement = conn.createStatement();
			statement.executeUpdate(ovelseInsert);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
