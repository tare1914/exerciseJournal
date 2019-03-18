
package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;


public class Apparat�velse extends �velse{
	private Object apparat;
	public Apparat�velse(String �Navn, String aNavn, Connection conn, Scanner sc) {
		super(�Navn, conn, sc);
		this.apparat=aNavn;
	}
	
	public Object getApparat() {
		return this.apparat;
	}
	
	public void setAparat(String apparat) {
		this.apparat=apparat;
	}
}