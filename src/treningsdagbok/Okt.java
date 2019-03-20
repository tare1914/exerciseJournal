package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Okt {
	
	private Connection conn;
	
	private int oktid;
	private Date dato;
	private int varighet;
	private int form;
	private String notat;
	
	public Okt(Connection conn) {
		this.conn = conn;
	}
	
	public int getOktID() {
		return oktid;
	}
	
	public Date getOktDato() {
		return dato;
	}
	
	public void nyOkt(Scanner sc) {
		System.out.println("Legg til ny økt\n");
		dato = new Date();
		
		System.out.println("Varighet i minutter: \n");
		varighet = Integer.parseInt(sc.next());
		
		System.out.println("Dagsform: \n");
		form = Integer.parseInt(sc.next());
		
		System.out.println("Notat: \n");
		notat = sc.next();
		
		
		String øktIns = String.format("insert into okt(dato, tid, varighet, form, notat) values(curdate(), curtime(), %d, %d, '%s');", varighet, form, notat);
		System.out.println("");
		
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(øktIns);
			Statement mySt = conn.createStatement();
			ResultSet rs = mySt.executeQuery("select max(id) from okt;");
			if (rs.next()) {
				oktid = Integer.parseInt(rs.getString(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
