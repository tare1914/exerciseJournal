package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class �velse {
	private String navn;
	Scanner scanner;
	Connection myconn;
	
	public �velse(String �Navn){
		this.navn=�Navn;
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


