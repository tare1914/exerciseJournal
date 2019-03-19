package treningsdagbok;

import java.sql.*;
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
			
			while(choiceVar != 8){
				if (currentOkt==null){
					System.out.println("\nIngen �kt er valgt  ");
				} 
				else {
						System.out.println("N�v�rende valgt �kt er : "+currentOkt.getOktDato() +" ID: "+currentOkt.getOktID());
						//System.out.println(currentOkt);
				}
				
				String mainText = "***************************************\n" +
						"Velg hva du vil gj�re fra listen: \n" +
		                "1: Registrer ny trenings�kt \n" +
		                "2: Legg til �velse \n" +
		                "3: Lag ny �velsesgruppe \n" +
		                "4: Hent ut n siste �kter \n" +
		                "5: Se resultatlogg for en �velse \n" +
		                "6: Se �velsesgrupper og tilh�rende �velser \n" +
		                "7: Se rangering av �velser \n"+
		                "8: Avslutt";
		                ;
				System.out.println(mainText);
				choiceVar = scanner.nextInt();
				
				switch (choiceVar){
				
				case 1: //start new �kt
					Okt okt = new Okt(conn);
					okt.nyOkt(scanner);
					currentOkt = okt;
					break;
					
				case 2: //Legg til �velse
					if (currentOkt==null){
						System.out.println("Ingen �kt er valgt \n ");
					}else {
						Ovelse ovelse = new Ovelse(conn, currentOkt);
					}
					
					break;
					
				case 3: // legg til notat
					if (currentOkt==null){
						System.out.println("Ingen �kt er valgt \n ");
					}else {
						
						//notat.nyttNotat(scanner,currentOkt);
						}
					break;
				
				case 4:
					new OvelsesGruppe(conn,scanner);
					break;
					
				case 5:
					System.out.println("Angi hvor mange �kter du vil se: \n ");
					int n = scanner.nextInt();
					getNlast�kts(n,conn);
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
					return;
					
				
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
		String statQuery = "select �velse.�velsesnavn,avg(�velsei�kt.prestasjon) as sort from �velse "+
								"join �velsei�kt on �velse.�velsesnavn = �velsei�kt.�velsesnavn "+
									"group by �velse.�velsesnavn "+
										"order by sort desc;";
		try{
			Statement statement = myconn.createStatement();
			ResultSet rs = statement.executeQuery(statQuery);
			while(rs.next()){
				System.out.println("�velse: "+ rs.getString("�velsesnavn") + " Snittprestasjon: "+rs.getString("sort")  );
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
	
	public static void get�velsesResultat(Connection myconn,Scanner scanner ){
		// list opp �velser registrert i db
		// be brukeren velge hvilken han vil se logg for
		int maxID ;
		String st�rsteid =("select max(�ktid) from �kt;");
		//select something
		String alle�velser= String.format("select * from �velse ;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(st�rsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(�ktid)"));
			}else{
				System.out.println("Fins ikke �velses s� langt tilbake. Beklager");
				return;

			}
			Statement list�velser = myconn.createStatement();
			 myRs = list�velser.executeQuery(alle�velser);
			System.out.println("�velser registrert i databasen:\n");
			while (myRs.next()){
				String first = myRs.getString("�velsesnavn");
				System.out.println(first);
				//System.out.println("\n");
			}
			System.out.println("Hvilken �velse vil du se resultatlogg for?:\n");
			String �velsesvalg = scanner.next();
			System.out.println("Hvor langt tilbake vil du se loggen?:\n");
			int periode = scanner.nextInt();
			//build query
			String get�velseslogg= String.format("select * from �velse join �velsei�kt on �velse.�velsesnavn = �velsei�kt.�velsesnavn where( �velse.�velsesnavn='%s' and �ktid > '%d');",�velsesvalg,maxID-periode);
			
			 statement = myconn.createStatement();
			myRs = list�velser.executeQuery(get�velseslogg);
			while (myRs.next()){
				String first = "�kt ID: "+ myRs.getString("�ktid")+ "   "+ "Prestasjon: "+myRs.getString("prestasjon");
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
	
	public static void getNlast�kts(int n, Connection myconn){

		int maxID ;
		String st�rsteid =("select max(�ktid) from �kt;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(st�rsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(�ktid)"));
			}else{
				System.out.println("Ingen tidligere �kter i databasen");
				return;

			}
		
		
			String allegrupper = String.format("select * from �kt left join �ktnotat on �kt.�ktid=�ktnotat.�ktid  left join notat on notat.notatid=�ktnotat.notatid  where �kt.�ktid> '%d' ;",maxID-n);
		
			Statement list�kter = myconn.createStatement();
			 myRs = list�kter.executeQuery(allegrupper);
			
			while (myRs.next()){
				String notatt;
				if (myRs.getString("tekstfelt") == null){
					notatt = "";
				}else{
					notatt=myRs.getString("tekstfelt");
				}
				String first = "�kt ID: "+ myRs.getString("�ktid")+ "   "+ "Notat: "+ notatt;
				System.out.println(first);
				//System.out.println("\n");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
}
