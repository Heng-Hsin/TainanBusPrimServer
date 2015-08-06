import java.net.*; 
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class DB_ThreadPool extends Thread {
	
	
	
	//final static String tempdeviceIP="127.0.0.1";
	
	public static String CrossRoadDB_userid;
	public static String CrossRoadDB_password;
	public static String DB_userid;
	public static String DB_password;
	public static String localDBIP;


	
	
	
	public static void main(String[] args) {
		DB_ThreadPool  multiport =new DB_ThreadPool();	
		
	}
	
	public static boolean Properties() {
		  
		   
	     Properties prop = new Properties();
		 InputStream input = null;
		 
			try {
				 					 
				input = new FileInputStream("C://TainanBusPrime.properties");
							
				prop.load(input);			
				
				 	localDBIP=prop.getProperty("LocalDB_IP");
				 	
					CrossRoadDB_userid=prop.getProperty("CrossRoadDB_userid");
					CrossRoadDB_password=prop.getProperty("CrossRoadDB_password");		
					
					 DB_userid=prop.getProperty("MSSQL_userid");
					 DB_password=prop.getProperty("MSSQL_password");
					
					System.out.println("localDBIP "+localDBIP);
					System.out.println("CrossRoadDB_userid "+CrossRoadDB_userid);
					System.out.println("CrossRoadDB_password "+CrossRoadDB_password);
					System.out.println("DB_userid "+DB_userid);
					System.out.println("DB_password "+DB_password);
					 
		    			return true;
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("Error in Properties");
				return false;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error in IO Properties");
						return false;
					}
				}
			}
	   		  		   
 }
	public DB_ThreadPool(){
		
		try{
			 Executor executor = Executors.newFixedThreadPool(6);  //number of threads ****
			 Properties();
			 String[] Work=Server_UI.MasterIP;
			 
			 for(String a:Work){
				 executor.execute(new Work(a));
			 }
	 		
	 		
	 		executor.execute(new Runnable() {
	 			@Override
	 			 public void run() {
	 				System.out.println(Thread.currentThread().getName() +
	 						" DB_ThreadPool Starting to end ");

	 			}
	 		});
	 		

			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public static class Work implements Runnable {  		//Collecting Remote DB data
        private String id;  
      
        public Work (String id) {  
            this.id = id;  
        }  
      
        public void run() { 
        	System.out.println(Thread.currentThread().getName() +" Begins Data Collecting " + id); 
        	
        	//WHERE Date between dateadd(hour, -1, getdate()) and getdate();
        	try{
        		//TainanBusPrim
				 String CrossRoadIP= id;
				 String temp_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
				 String temp_connect="jdbc:microsoft:sqlserver://"+CrossRoadIP+":1433;DatabaseName=TainanBusPrim";
				 String temp_userid=CrossRoadDB_userid.trim();
	        	 String temp_password=CrossRoadDB_password.trim();
	        	 //String temp_query=textArea_1.getText();
	        	 
	        	 String localIP= localDBIP;
	        	 String local_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
				 String local_connect="jdbc:microsoft:sqlserver://"+localIP+":1433;DatabaseName=TainanBusPrim";
				 String local_userid=DB_userid.trim();
	        	 String local_password=DB_password.trim();
	        	 //String local_table = textField_12.getText().trim();
	        	 String[] Query=new String[4];
	        	 String[] localtable=new String[4];
	        	 //String[] StartTime=new String[4];
	        	 String StartingTime="";
	        	 
	        	 Query[0]="SELECT * FROM [TainanBusPrim].[dbo].[BusA1_Log] WHERE [Time] between dateadd(hour, -1, getdate()) and getdate();";
				 localtable[0]="BusA1_Log";
				 Query[1]="SELECT * FROM [TainanBusPrim].[dbo].[BusStrategy_Log] WHERE [Time] between dateadd(hour, -1, getdate()) and getdate();";
				 localtable[1]="BusStrategy_Log";
				 Query[2]="SELECT * FROM [TainanBusPrim].[dbo].[SubPhaseLog] WHERE [TimeStarted] between dateadd(hour, -1, getdate()) and getdate();";
				 localtable[2]="SubPhaseLog";
				 Query[3]="SELECT * FROM [TainanBusPrim].[dbo].[BusTrigger_Log] WHERE [Time] between dateadd(hour, -1, getdate()) and getdate();";
				 localtable[3]="BusTrigger_Log";
				 
				 try{
					 
					 for(int o=0;o<4;o++){
						 
						 try{
							 StartingTime =MSDB.getGrabber_time(id , localtable[o]);
							 StartingTime="'"+StartingTime+"'";
							 
							 System.out.println("Grabbing Time for "+id+" Table "+localtable[o]);								 
							 Query[o]= Query[o].replace("dateadd(hour, -1, getdate())", StartingTime);							 
							 System.out.println("New Query "+Query[o]);
						 							 
						 }catch(Exception qw){
							 System.out.println("Error in Generating new Query "+qw.getMessage());
						 }
						 
						
					 }
					 
					 
				 }catch(Exception tn){
					 System.out.println("Grabbing Time Error "+id);
				 }
				
				 
				 
				boolean remoteconnection=MSDB.dbConnection(temp_driver,temp_connect,temp_userid,temp_password);
				//boolean localconnection=MSDB.dbConnection(temp_driver,temp_connect,temp_userid,temp_password);
				
				if(remoteconnection=false){
					JFrame frame= new JFrame();
                   JOptionPane.showMessageDialog(frame,"CrossRoad DB Connection Fail");
                   
				}else{
					
					for(int i=0;i<4;i++){
																		
						int attempt=0;
						while(attempt<3){
						try {
							ResultSet remotedata = MSDB.GetRemoteDBResultSet(temp_driver,temp_connect,temp_userid,temp_password,Query[i]);
							
							MSDB.InsertRs(remotedata,local_driver,local_connect,local_userid,local_password,localtable[i]);
							//System.out.println(" temp_driver "+temp_driver+" temp_connect "+temp_connect+" temp_userid "+temp_userid+" temp_password "+temp_password+" Query[i] "+Query[i]);
							attempt=3;
							System.out.println(id+" "+localtable[i]+" download complete");
							
							MSDB.Update_Grabber_log(id,localtable[i]);
							
						} catch (Exception e) {							
							attempt++;
							if(attempt>2){
								System.out.println(id+" Failed to download");
							}
						}						
					}  
					}
					
				
				}
				
				
        	}catch(Exception e){
        		e.printStackTrace();
        		System.out.println("Error in Collecting Remote DB data");
        		
        	}
        	System.out.println(Thread.currentThread().getName() + " Ends  Data Collecting " + id);  
        
		  } 
    }
	

}
