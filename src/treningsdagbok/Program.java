package treningsdagbok;

import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.Locale;
import java.util.Scanner;
import java.time.format.*;

public class Program {
	
private static Okt currentOkt;
	
	public static void main(String[] args) {

		
		//Locale.setDefault(Locale.US); //For � f� . og ikke , i double stirng format.
		
		try{
			Connection conn = DBConn.connect();
			int choiceVar = 0;
			Scanner scanner = new Scanner(System.in);
			
			while(choiceVar!=9){
				if (currentOkt==null){
					System.out.println("\nIngen okt er valgt  ");
				} 
				else {
						System.out.println("N�v�rende valgt okt er : "+currentOkt.getOktDato() +" ID: "+currentOkt.getOktID());
						TimeUnit.SECONDS.sleep(2);
				}
				
				String mainText = "***************************************\n" +
						"Velg hva du vil gj�re fra listen: \n" +
		                "1: Registrer ny treningsokt \n" + //yas
		                "2: Legg til fri�velse \n" +		//yas
		                "3: Legg til apparat�velse\n" +		//yas
		                "4: Se grupper og legg til ny \n" + //yas
		                "5: Hent ut n siste �kter \n" +		//yas
		                "6: Se resultatlogg for en �velse \n" +	//yas
		                "7: Se �velsesgrupper og tilh�rende �velser \n" + //yas
		                "8: Legg �velse i gruppe \n" + //yas
		                "9: Avslutt";
		                
				System.out.println(mainText);
				choiceVar = scanner.nextInt();
				
				switch (choiceVar){
				
				case 1: //start new okt1
					Okt okt = new Okt(conn);
					okt.nyOkt(scanner);
					currentOkt = okt;
					break;
					
				case 2: //Legg til �velse
					if (currentOkt==null){
						System.out.println("Ingen okt er valgt \n ");
					}
					else {
						FriOvelse friovelse = new FriOvelse(conn, currentOkt);
						friovelse.velgOvelse(scanner, conn, currentOkt);
					}
					
					break;
					
				case 3: // legg til apprat�velse
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
					get�velsesResultat(conn,scanner);
					break;
					
				case 7:
					get�velsesgruppe(conn, scanner);
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
	
	public static void get�velsesResultat(Connection myconn,Scanner scanner ){
		// list opp �velser registrert i db
		// be brukeren velge hvilken han vil se logg for
		int maxID ;
		String st�rsteid =("select max(id) from okt;");
		//select something
		String alle�velser= String.format("select * from ovelse ;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(st�rsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(id)"));
			}else{
				System.out.println("Fins ikke �velses s� langt tilbake. Beklager");
				return;

			}
			Statement list�velser = myconn.createStatement();
			 myRs = list�velser.executeQuery(alle�velser);
			System.out.println("�velser registrert i databasen:\n");
			while (myRs.next()){
				String first = myRs.getString("ovelsesnavn");
				System.out.println(first);
				//System.out.println("\n");
			}
			System.out.println("Hvilken �velse vil du se resultatlogg for?:\n");
			String �velsesvalg = scanner.next();
			System.out.println("Hvor langt tilbake vil du se loggen?:\n");
			int periode = scanner.nextInt();
			//build query
			String get�velseslogg= String.format("select prestasjon from ovelseiokt where(ovelsesnavn='%s' and oktid >= '%d');",�velsesvalg,maxID-periode);
			
			statement = myconn.createStatement();
			myRs = list�velser.executeQuery(get�velseslogg);
			while (myRs.next()){
				System.out.println(myRs.getString("prestasjon"));
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public static void get�velsesgruppe(Connection myconn,Scanner scanner){
		
		//het ut alle �velsesgrupper
		String muskelQuery =("select gruppenavn from ovelsesgruppe;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(muskelQuery);
			while (myRs.next()){
				String muskelgruppe = myRs.getString("gruppenavn");
				System.out.println("�velser i gruppen "+ muskelgruppe + " er:");
				String �velseQuery = String.format("select ovelsesnavn from ovelseigruppe where ovelseigruppe.gruppenavn = '%s';", muskelgruppe);
				//System.out.println(�velseQuery);
				Statement statement2 = myconn.createStatement();
				
				ResultSet qRs = statement2.executeQuery(�velseQuery);
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
		String st�rsteid =("select max(id) from okt;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(st�rsteid);
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
