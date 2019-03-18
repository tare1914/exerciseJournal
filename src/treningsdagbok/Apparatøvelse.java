
package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;


public class Apparatøvelse extends Øvelse{
	private Object apparat;
	public Apparatøvelse(String øNavn, String aNavn, Connection conn, Scanner sc) {
		super(øNavn, conn, sc);
		this.apparat=aNavn;
	}
	
	public Object getApparat() {
		return this.apparat;
	}
	
	public void setAparat(String apparat) {
		this.apparat=apparat;
	}
}