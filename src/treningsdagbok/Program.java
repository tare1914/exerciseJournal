package treningsdagbok;

import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.Locale;
import java.util.Scanner;
import java.time.format.*;

public class Program {
	
private static Okt currentOkt;
	
	public static void main(String[] args) {

		
		//Locale.setDefault(Locale.US); //For å få . og ikke , i double stirng format.
		
		try{
			Connection conn = DBConn.connect();
			int choiceVar = 0;
			Scanner scanner = new Scanner(System.in);
			
			while(choiceVar!=9){
				if (currentOkt==null){
					System.out.println("\nIngen okt er valgt  ");
				} 
				else {
						System.out.println("Nåværende valgt okt er : "+currentOkt.getOktDato() +" ID: "+currentOkt.getOktID());
						TimeUnit.SECONDS.sleep(2);
				}
				
				String mainText = "***************************************\n" +
						"Velg hva du vil gjøre fra listen: \n" +
		                "1: Registrer ny treningsokt \n" + //yas
		                "2: Legg til friøvelse \n" +		//yas
		                "3: Legg til apparatøvelse\n" +		//yas
		                "4: Se grupper og legg til ny \n" + //yas
		                "5: Hent ut n siste økter \n" +		//yas
		                "6: Se resultatlogg for en øvelse \n" +	//yas
		                "7: Se Øvelsesgrupper og tilhørende øvelser \n" + //yas
		                "8: Legg øvelse i gruppe \n" + //yas
		                "9: Avslutt";
		                
				System.out.println(mainText);
				choiceVar = scanner.nextInt();
				
				switch (choiceVar){
				
				case 1: //start new okt1
					Okt okt = new Okt(conn);
					okt.nyOkt(scanner);
					currentOkt = okt;
					break;
					
				case 2: //Legg til Øvelse
					if (currentOkt==null){
						System.out.println("Ingen okt er valgt \n ");
					}
					else {
						FriOvelse friovelse = new FriOvelse(conn, currentOkt);
						friovelse.velgOvelse(scanner, conn, currentOkt);
					}
					
					break;
					
				case 3: // legg til appratøvelse
					if (currentOkt==null){
						System.out.println("Ingen okt er valgt \n ");
					}
					else {
						AppratOvelse apparatovelse = new AppratOvelse(conn, currentOkt);
						apparatovelse.velgOvelse(scanner, conn, currentOkt);
					}
					break;
				
				case 4:
					new OvelsesGruppe(conn,scanner);
					break;
					
				case 5:
					System.out.println("Angi hvor mange okter du vil se: \n ");
					int n = scanner.nextInt();
					getNlastokts(n,conn);
					break;
					
				case 6:
					getØvelsesResultat(conn,scanner);
					break;
					
				case 7:
					getØvelsesgruppe(conn, scanner);
					break;
					
				case 8:
					OvelsesGruppe.leggOv(scanner, conn);
					break;
					
				case 9:
					System.exit(0);
					break;
				
				default:
					System.out.println("Ukjent nummer");
					break;
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		

	}
	
	public static void getØvelsesResultat(Connection myconn,Scanner scanner ){
		// list opp øvelser registrert i db
		// be brukeren velge hvilken han vil se logg for
		int maxID ;
		String størsteid =("select max(id) from okt;");
		//select something
		String alleøvelser= String.format("select * from ovelse ;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(størsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(id)"));
			}else{
				System.out.println("Fins ikke øvelses så langt tilbake. Beklager");
				return;

			}
			Statement listøvelser = myconn.createStatement();
			 myRs = listøvelser.executeQuery(alleøvelser);
			System.out.println("Øvelser registrert i databasen:\n");
			while (myRs.next()){
				String first = myRs.getString("ovelsesnavn");
				System.out.println(first);
				//System.out.println("\n");
			}
			System.out.println("Hvilken øvelse vil du se resultatlogg for?:\n");
			String øvelsesvalg = scanner.next();
			System.out.println("Hvor langt tilbake vil du se loggen?:\n");
			int periode = scanner.nextInt();
			//build query
			String getøvelseslogg= String.format("select prestasjon from ovelseiokt where(ovelsesnavn='%s' and oktid >= '%d');",øvelsesvalg,maxID-periode);
			
			statement = myconn.createStatement();
			myRs = listøvelser.executeQuery(getøvelseslogg);
			while (myRs.next()){
				System.out.println(myRs.getString("prestasjon"));
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public static void getØvelsesgruppe(Connection myconn,Scanner scanner){
		
		//het ut alle øvelsesgrupper
		String muskelQuery =("select gruppenavn from ovelsesgruppe;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(muskelQuery);
			while (myRs.next()){
				String muskelgruppe = myRs.getString("gruppenavn");
				System.out.println("Øvelser i gruppen "+ muskelgruppe + " er:");
				String øvelseQuery = String.format("select ovelsesnavn from ovelseigruppe where ovelseigruppe.gruppenavn = '%s';", muskelgruppe);
				//System.out.println(øvelseQuery);
				Statement statement2 = myconn.createStatement();
				
				ResultSet qRs = statement2.executeQuery(øvelseQuery);
				while (qRs.next()){
					System.out.println(qRs.getString("ovelsesnavn"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void getNlastokts(int n, Connection myconn){

		int maxID ;
		String størsteid =("select max(id) from okt;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(størsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(id)"));
			}else{
				System.out.println("Ingen tidligere okter i databasen");
				return;

			}
		
		
			String allegrupper = String.format("select * from okt where okt.id> '%d' ;",maxID-n);
			
			
			Statement listokter = myconn.createStatement();
			
			myRs = listokter.executeQuery(allegrupper);
			
			
			
			while (myRs.next()){
				String first = "okt ID: "+ myRs.getString("id")+ "   "+ "Notat: "+ myRs.getString("notat");
				System.out.println(first);
				
				String ovelseri = String.format("select ovelsesnavn from ovelseiokt where oktid = '%s';",myRs.getString("id"));
				Statement ovelserist = myconn.createStatement();
				ResultSet ovelsene = ovelserist.executeQuery(ovelseri);
				
				while (ovelsene.next()) {
					System.out.println(ovelsene.getString("ovelsesnavn"));
				}
				System.out.println("\n");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
}
