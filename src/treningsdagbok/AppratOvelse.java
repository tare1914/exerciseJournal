
package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;


public class AppratOvelse extends Ovelse{
	private String apparat;
	private int kilo;
	private int sett;
	public AppratOvelse(Connection conn, Okt currentokt) {
		super(conn, currentokt);
	}
	
	public void velgOvelse(Scanner sc, Connection conn, Okt currentOkt) throws SQLException {
		System.out.println("Legge til ny eller bruke gammel? (n/m): \n");
		String ny = sc.next();
		if (ny.equals("n")) {
			this.nyApparatØvelse(sc, conn);
		}
		else {
			this.selectApparatØvelse(sc, conn);
		}
		OvelseIOkt iokt = new OvelseIOkt(conn);
		iokt.addOvelseiokt(conn, sc, currentOkt.getOktID(), this.ovelsesnavn);
	}
	
	public String getApparat() {
		return this.apparat;
	}
	
	public int getKilo() {
		return this.kilo;
	}
	
	public int getSett() {
		return this.sett;
	}
	
	public void setAparat(String apparat) {
		this.apparat=apparat;
	}
	
	public void selectApparatØvelse(Scanner sc, Connection conn) {
		this.selectØvelse(sc, conn);
		String apparatØvelseGet=String.format("select apparatnavn from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.apparat=String.valueOf(st.executeQuery(apparatØvelseGet));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String kiloØvelseGet=String.format("select kilo from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.kilo=Integer.parseInt(String.valueOf(st.executeQuery(kiloØvelseGet)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		String settØvelseGet=String.format("select sett from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.sett=Integer.parseInt(String.valueOf(st.executeQuery(settØvelseGet)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void nyApparatØvelse(Scanner sc, Connection conn){
		this.nyOvelse(sc, conn);
		
		System.out.println("apparatnavn: \n");
		this.apparat=sc.next();
		
		System.out.println("Kilo: \n");
		this.kilo= Integer.parseInt(sc.next());
		
		System.out.println("sett: \n");
		sett = Integer.parseInt(sc.next());
		
		System.out.println(this.ovelsesnavn);
		
		
		String øvelsesIns = String.format("insert into apparatovelse(ovelsesnavn, kilo, sett, apparatnavn) values('%s' %d, %d, '%s');", this.ovelsesnavn, this.kilo, this.sett, this.apparat);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(øvelsesIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}