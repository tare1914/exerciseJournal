package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Fri�velse extends �velse {
	private String beskrivelse;
	
	Fri�velse(String �Navn, String beskrivelse, Connection conn, Scanner sc){
		super(�Navn, conn, sc);
		this.beskrivelse=beskrivelse;
	}
	
	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse=beskrivelse;
	}
}