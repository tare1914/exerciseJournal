
package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;


public class aparat�velse extends �velse{
	private Object aparat;
	public aparat�velse(String �Navn, String aNavn) {
		super(�Navn);
		this.aparat=aNavn;
	}
	
	public Object getAparat() {
		return this.aparat;
	}
	
	public void setAparat(String aparat) {
		this.aparat=aparat;
	}
}