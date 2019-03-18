package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public abstract class Øvelse {
	private String navn;
	Scanner scanner;
	Connection myconn;
	
	public Øvelse(String øNavn, Connection conn, Scanner sc){
		this.navn=øNavn;
		this.myconn=conn;
		this.scanner=sc;
	}
	
	public String getNavn() {
		return this.navn;
	}
	
	public void setNavn(String navn) {
		this.navn=navn;
	}
	
	//public abstract void addToØvelsesgruppe(Øvelsesgruppe øgruppe);

}


