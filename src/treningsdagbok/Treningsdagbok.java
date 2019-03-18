package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;
import java.util.Properties;

public class Treningsdagbok {
	protected Connection conn;
    public Treningsdagbok() {
    }
    public void connect() {
        try {
            Properties p = new Properties();
            p.put("user", "brukernavn");
            p.put("password", "passord");
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/brukernavn_database?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
