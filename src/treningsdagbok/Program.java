package treningsdagbok;

import java.sql.*;
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
			
			while(choiceVar != 8){
				if (currentOkt==null){
					System.out.println("\nIngen økt er valgt  ");
				} 
				else {
						System.out.println("Nåværende valgt økt er : "+currentOkt.getOktDato() +" ID: "+currentOkt.getOktID());
						//System.out.println(currentOkt);
				}
				
				String mainText = "***************************************\n" +
						"Velg hva du vil gjøre fra listen: \n" +
		                "1: Registrer ny treningsøkt \n" +
		                "2: Legg til øvelse \n" +
		                "3: Lag ny øvelsesgruppe \n" +
		                "4: Hent ut n siste økter \n" +
		                "5: Se resultatlogg for en øvelse \n" +
		                "6: Se Øvelsesgrupper og tilhørende øvelser \n" +
		                "7: Se rangering av øvelser \n"+
		                "8: Avslutt";
		                ;
				System.out.println(mainText);
				choiceVar = scanner.nextInt();
				
				switch (choiceVar){
				
				case 1: //start new økt
					Okt okt = new Okt(conn);
					okt.nyOkt(scanner);
					currentOkt = okt;
					break;
					
				case 2: //Legg til Øvelse
					if (currentOkt==null){
						System.out.println("Ingen økt er valgt \n ");
					}else {
						Ovelse ovelse = new Ovelse(conn, currentOkt);
					}
					
					break;
					
				case 3: // legg til notat
					if (currentOkt==null){
						System.out.println("Ingen økt er valgt \n ");
					}else {
						
						//notat.nyttNotat(scanner,currentOkt);
						}
					break;
				
				case 4:
					new OvelsesGruppe(conn,scanner);
					break;
					
				case 5:
					System.out.println("Angi hvor mange økter du vil se: \n ");
					int n = scanner.nextInt();
					getNlastØkts(n,conn);
					break;
				case 6:
					getØvelsesResultat(conn,scanner);
					break;
				case 7:
					getØvelsesgruppe( conn, scanner);
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
		String statQuery = "select øvelse.øvelsesnavn,avg(øvelseiøkt.prestasjon) as sort from øvelse "+
								"join øvelseiøkt on øvelse.øvelsesnavn = øvelseiøkt.øvelsesnavn "+
									"group by øvelse.øvelsesnavn "+
										"order by sort desc;";
		try{
			Statement statement = myconn.createStatement();
			ResultSet rs = statement.executeQuery(statQuery);
			while(rs.next()){
				System.out.println("Øvelse: "+ rs.getString("øvelsesnavn") + " Snittprestasjon: "+rs.getString("sort")  );
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
	}
	
	public static void getØvelsesResultat(Connection myconn,Scanner scanner ){
		// list opp øvelser registrert i db
		// be brukeren velge hvilken han vil se logg for
		int maxID ;
		String størsteid =("select max(øktid) from økt;");
		//select something
		String alleøvelser= String.format("select * from Øvelse ;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(størsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(øktid)"));
			}else{
				System.out.println("Fins ikke øvelses så langt tilbake. Beklager");
				return;

			}
			Statement listøvelser = myconn.createStatement();
			 myRs = listøvelser.executeQuery(alleøvelser);
			System.out.println("Øvelser registrert i databasen:\n");
			while (myRs.next()){
				String first = myRs.getString("øvelsesnavn");
				System.out.println(first);
				//System.out.println("\n");
			}
			System.out.println("Hvilken øvelse vil du se resultatlogg for?:\n");
			String øvelsesvalg = scanner.next();
			System.out.println("Hvor langt tilbake vil du se loggen?:\n");
			int periode = scanner.nextInt();
			//build query
			String getøvelseslogg= String.format("select * from øvelse join øvelseiøkt on øvelse.øvelsesnavn = øvelseiøkt.øvelsesnavn where( øvelse.øvelsesnavn='%s' and øktid > '%d');",øvelsesvalg,maxID-periode);
			
			 statement = myconn.createStatement();
			myRs = listøvelser.executeQuery(getøvelseslogg);
			while (myRs.next()){
				String first = "Økt ID: "+ myRs.getString("øktid")+ "   "+ "Prestasjon: "+myRs.getString("prestasjon");
				System.out.println(first);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public static void getØvelsesgruppe(Connection myconn,Scanner scanner){
		
		//het ut alle øvelsesgrupper
		String muskelQuery =("select muskelgruppe from øvelsesgruppe;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(muskelQuery);
			while (myRs.next()){
				String muskelgruppe = myRs.getString("muskelgruppe");
				System.out.println("Øvelser i gruppen "+ muskelgruppe + " er:");
				String øvelseQuery = 	"select * from øvelsesgruppe "+ 
										"join øvelseigruppe on øvelsesgruppe.Muskelgruppe=øvelseigruppe.Muskelgruppe " +
											"join øvelse on øvelseigruppe.øvelsesnavn = øvelse.øvelsesnavn " +
												String.format("where øvelsesgruppe.muskelgruppe = '%s';", muskelgruppe);
				//System.out.println(øvelseQuery);
				Statement statement2 = myconn.createStatement();
				
				ResultSet qRs = statement2.executeQuery(øvelseQuery);
				while (qRs.next()){
					System.out.println(qRs.getString("øvelsesnavn"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		
	}
	
	public static void getNlastØkts(int n, Connection myconn){

		int maxID ;
		String størsteid =("select max(øktid) from økt;");
		try{
			Statement statement = myconn.createStatement();
			ResultSet myRs = statement.executeQuery(størsteid);
			if (myRs.next()){
				maxID = (myRs.getInt("max(øktid)"));
			}else{
				System.out.println("Ingen tidligere økter i databasen");
				return;

			}
		
		
			String allegrupper = String.format("select * from økt left join Øktnotat on økt.øktid=øktnotat.øktid  left join notat on notat.notatid=øktnotat.notatid  where økt.øktid> '%d' ;",maxID-n);
		
			Statement listøkter = myconn.createStatement();
			 myRs = listøkter.executeQuery(allegrupper);
			
			while (myRs.next()){
				String notatt;
				if (myRs.getString("tekstfelt") == null){
					notatt = "";
				}else{
					notatt=myRs.getString("tekstfelt");
				}
				String first = "Økt ID: "+ myRs.getString("øktid")+ "   "+ "Notat: "+ notatt;
				System.out.println(first);
				//System.out.println("\n");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
}
