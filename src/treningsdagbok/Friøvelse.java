package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class Friøvelse extends Øvelse {
	private String beskrivelse;
	
	Friøvelse(String øNavn, String beskrivelse, Connection conn, Scanner sc){
		super(øNavn, conn, sc);
		this.beskrivelse=beskrivelse;
	}
	
	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse=beskrivelse;
	}
}