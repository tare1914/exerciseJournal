package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class fri�velse extends �velse {
	private String beskrivelse;
	
	fri�velse(String �Navn, String beskrivelse){
		super(�Navn);
		this.beskrivelse=beskrivelse;
	}
	
	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse=beskrivelse;
	}
}