package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Øvelse {
	private String navn;
	Scanner scanner;
	Connection myconn;
	
	public Øvelse(String øNavn){
		this.navn=øNavn;
		this.myconn=myconn;
		this.scanner=scanner;
	}
	
	public String getNavn() {
		return this.navn;
	}
	
	public void SetNavn(String navn) {
		this.navn=navn;
	}

}


