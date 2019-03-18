package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;

public class friØvelse extends Øvelse {
	private String beskrivelse;
	
	friØvelse(String øNavn, String beskrivelse){
		super(øNavn);
		this.beskrivelse=beskrivelse;
	}
	
	public String getBeskrivelse() {
		return beskrivelse;
	}
	
	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse=beskrivelse;
	}
}