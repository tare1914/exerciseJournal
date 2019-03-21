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
			
			while(true){
				if (currentOkt==null){
					System.out.println("\nIngen okt er valgt  ");
				} 
				else {
						System.out.println("N�v�rende valgt okt er : "+currentOkt.getOktDato() +" ID: "+currentOkt.getOktID());
						TimeUnit.SECONDS.sleep(2);
				}
				
				String mainText = "***************************************\n" +
						"Velg hva du vil gj�re fra listen: \n" +
		                "1: Registrer ny treningsokt \n" +
		                "2: Legg til fri�velse \n" +
		                "3: Legg til apparat�velse\n" +
		                "4: Hent ut n siste okter \n" +
		                "5: Se resultatlogg for en �velse \n" +
		                "6: Se �velsesgrupper og tilh�rende �velser \n" +
		                "7: Se rangering av �velser \n"+
		                "8: Avslutt";
		                ;
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
						friovelse.velgOvelse(scanner, conn);
					}
					
					break;
					
				case 3: // legg til apprat�velse
					if (currentOkt==null){
						System.out.println("Ingen okt er valgt \n ");
					}
					else {
						AppratOvelse apparatovelse = new AppratOvelse(conn, currentOkt);
						apparatovelse.velgOvelse(scanner, conn);
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
					get�velsesgruppe( conn, scanner);
					break;
					
				case 8:
					getStatistikk(conn);
					break;
					
				case 9:
					System.exit(0);
					
				
				default:
					System.out.println("Ukjent nummer");
					break;
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		

	}
	public static void getStatistikk(Connection myconn){
		String statQuery = "select ovelse.ovelsesnavn,avg(ovelseiokt.prestasjon) as sort from ovelse "+
								"join ovelseiokt on ovelse.ovelsesnavn = ovelseiokt.ovelsesnavn "+
									"group by ovelse.ovelsesnavn "+
										"order by sort desc;";
		try{
			Statement statement = myconn.createStatement();
			ResultSet rs = statement.executeQuery(statQuery);
			while(rs.next()){
				System.out.println("�velse: "+ rs.getString("ovelsesnavn") + " Snittprestasjon: "+rs.getString("sort")  );
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
			String get�velseslogg= String.format("select * from ovelse join ovelseiokt on ovelse.ovelsesnavn = ovelseiokt.ovelsesnavn where( ovelse.ovelsesnavn='%s' and id > '%d');",�velsesvalg,maxID-periode);
			
			 statement = myconn.createStatement();
			myRs = list�velser.executeQuery(get�velseslogg);
			while (myRs.next()){
				String first = "okt ID: "+ myRs.getString("id")+ "   "+ "Prestasjon: "+myRs.getString("prestasjon");
				System.out.println(first);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public static void get�velsesgruppe(Connection myconn,Scanner scanner){
		
		//het ut alle �velsesgrupper
		String muskelQuery =("select muskelgruppe from �velsesgruppe;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(muskelQuery);
			while (myRs.next()){
				String muskelgruppe = myRs.getString("muskelgruppe");
				System.out.println("�velser i gruppen "+ muskelgruppe + " er:");
				String �velseQuery = 	"select * from �velsesgruppe "+ 
										"join �velseigruppe on �velsesgruppe.Muskelgruppe=�velseigruppe.Muskelgruppe " +
											"join �velse on �velseigruppe.�velsesnavn = �velse.�velsesnavn " +
												String.format("where �velsesgruppe.muskelgruppe = '%s';", muskelgruppe);
				//System.out.println(�velseQuery);
				Statement statement2 = myconn.createStatement();
				
				ResultSet qRs = statement2.executeQuery(�velseQuery);
				while (qRs.next()){
					System.out.println(qRs.getString("�velsesnavn"));
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
		
		
			String allegrupper = String.format("select * from okt left join oktnotat on okt.id=oktnotat.id  left join notat on notat.notatid=oktnotat.notatid  where okt.id> '%d' ;",maxID-n);
		
			Statement listokter = myconn.createStatement();
			 myRs = listokter.executeQuery(allegrupper);
			
			while (myRs.next()){
				String notatt;
				if (myRs.getString("tekstfelt") == null){
					notatt = "";
				}else{
					notatt=myRs.getString("tekstfelt");
				}
				String first = "okt ID: "+ myRs.getString("id")+ "   "+ "Notat: "+ notatt;
				System.out.println(first);
				//System.out.println("\n");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
}
