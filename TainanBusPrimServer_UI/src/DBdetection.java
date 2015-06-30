
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;
import java.awt.Color;
import java.io.*;
import java.util.Date;


public class DBdetection extends Thread {
	
	public DBdetection(){
		
		 Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	MSDB mssql = new MSDB();
	            	
	        		while (true) {
	                    
	                    try {
	                    	
	                    	
	                    	if(mssql.dbConnection()){
	                    		
	                    		//System.out.println("DB detection");
	                    		Server_UI.lblNewLabel_5.setForeground(new Color(0,255,150));
	                    		
	                    		//Server_UI.lblNewLabel_5.setForeground(Color.YELLOW);
	                    		
	            				}else{
	            					Server_UI.lblNewLabel_5.setForeground(Color.RED);
	            				};
	                    	
	                        Thread.sleep(5000);
	                    } catch (InterruptedException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        });
	        thread.start();
		

		
	}

}
