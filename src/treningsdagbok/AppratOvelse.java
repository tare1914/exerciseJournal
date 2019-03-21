
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
	
	public void velgOvelse(Scanner sc, Connection conn) {
		System.out.println("Legge til ny eller bruke gammel? (n/m): \n");
		String ny = sc.next();
		if (ny.equals("n")) {
			this.nyApparat�velse(sc, conn);
		}
		else {
			this.selectApparat�velse(sc, conn);
		}
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
	
	public void selectApparat�velse(Scanner sc, Connection conn) {
		this.select�velse(sc, conn);
		String apparat�velseGet=String.format("select apparatnavn from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.apparat=String.valueOf(st.executeQuery(apparat�velseGet));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String kilo�velseGet=String.format("select kilo from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.kilo=Integer.parseInt(String.valueOf(st.executeQuery(kilo�velseGet)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		String sett�velseGet=String.format("select sett from apparatovelse where ovelsesnavn='%s'", this.ovelsesnavn);
		System.out.println("");
		
		try {
			Statement st=conn.createStatement();
			this.sett=Integer.parseInt(String.valueOf(st.executeQuery(sett�velseGet)));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void nyApparat�velse(Scanner sc, Connection conn){
		this.nyOvelse(sc, conn);
		
		System.out.println("apparatnavn: \n");
		this.apparat=sc.next();
		
		System.out.println("Kilo: \n");
		this.kilo= Integer.parseInt(sc.next());
		
		System.out.println("sett: \n");
		sett = Integer.parseInt(sc.next());
		
		System.out.println(this.ovelsesnavn);
		
		
		String �velsesIns = String.format("insert into apparatovelse(ovelsesnavn, kilo, sett, apparatnavn) values('%s' %d, %d, '%s');", this.ovelsesnavn, this.kilo, this.sett, this.apparat);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(�velsesIns);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}