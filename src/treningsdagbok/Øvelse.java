package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class �velse {
	private String navn;
	Scanner scanner;
	Connection myconn;
	
	public �velse(String �Navn, Connection conn, Scanner sc){
		this.navn=�Navn;
		this.myconn=conn;
		this.scanner=sc;
	}
	
	public String getNavn() {
		return this.navn;
	}
	
	public void setNavn(String navn) {
		this.navn=navn;
	}

}


