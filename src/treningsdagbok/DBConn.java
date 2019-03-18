package treningsdagbok;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.time.*;
import java.util.Properties;

public class DBConn {
	protected Connection conn;
    public DBConn() {
    }
    public void connect() {
        try {
            Properties p = new Properties();
            p.put("user", "philipae_datdat");
            p.put("password", "philipae_datdat");
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/philipae_datdatdat?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
