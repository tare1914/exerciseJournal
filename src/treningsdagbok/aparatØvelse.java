
package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;


public class aparatØvelse extends Øvelse{
	private Object aparat;
	public aparatØvelse(String øNavn, String aNavn) {
		super(øNavn);
		this.aparat=aNavn;
	}
	
	public Object getAparat() {
		return this.aparat;
	}
	
	public void setAparat(String aparat) {
		this.aparat=aparat;
	}
}