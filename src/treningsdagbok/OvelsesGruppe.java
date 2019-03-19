package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class OvelsesGruppe {
	Connection connect;
	String øvelsesgruppe;
	
	public OvelsesGruppe(Connection connect, Scanner scan) {
		this.connect = connect;
		String gruppetyper = String.format("select * from Øvelsesgruppe;");
		
		try {
			Statement listgruppetyper = connect.createStatement();
			ResultSet myRs = listgruppetyper.executeQuery(gruppetyper);
			System.out.println("Gruppetyper registrert i databasen: \n");
			while (myRs.next()){
				String first = myRs.getString("gruppetyper");
				System.out.println(first);
				System.out.println("\n");
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		
		System.out.println("Skriv navn på gruppetype:");
		gruppetyper = scan.next();
		
		try{
			String finnes = String.format("select * from Øvelsesgruppe where øvelsesgruppe= '%s';", øvelsesgruppe);
			Statement statement = connect.createStatement();
			ResultSet myRs = statement.executeQuery(finnes);
			if (myRs.next())	{
				System.out.println("Gruppetype finnes allerede");
			}
			else	{
				
				String gruppeInsert = String.format("insert into Øvelsesgruppe values('%s');", øvelsesgruppe);
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
			String finnes = String.format("select * from Øvelsesgruppe where øvelsesgruppe = '%s';", øvelsesgruppe);
			
			ResultSet myRs = statement.executeQuery(finnes);
			if (myRs.next()){
				System.out.println("Gruppetypen finnes allerede");
			}
			else {
				System.out.println("Lager ny øvelsesgruppe");
				String gruppeInsert = String.format("insert into Øvelsesgruppe values('%s');", øvelsesgruppe);
				System.out.println("");
				statement.executeUpdate(gruppeInsert);
					
				}
			}catch(Exception e){
				e.printStackTrace();
				
				
			}
		}

	}
