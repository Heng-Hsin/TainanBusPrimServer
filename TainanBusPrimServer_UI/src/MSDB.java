
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
import java.io.*;
import java.util.Date;

import javax.swing.table.DefaultTableModel;


public class MSDB
{	
	private static String DBDriver;
	private static String DB_connect_string;
	private static String DB_userid;
	private static String DB_password;
	private static String DB_database_name;
	
	//OracleDB("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@127.0.0.1:1521/xe","SYSTEM","753159852");	
	// MSSQL DB properties 
	
	private static String MSSQL_Driver;
	private static String MSSQL_connect_string;
	private static String MSSQL_userid;
	private static String MSSQL_password;
	// Oracle DB properties 
	private static String ORACLE_Driver;
	private static String ORACLE_connect_string;
	private static String ORACLE_userid;
	private static String ORACLE_password;
	
	public  MSDB(){
        Properties prop = new Properties();
	InputStream input = null;

	try {		
		input = new FileInputStream("C://TainanBusPrime.properties");
				
		prop.load(input);
               
        DBDriver=prop.getProperty("MSSQL_Driver");
        DB_connect_string=prop.getProperty("MSSQL_connect_string");
		DB_userid=prop.getProperty("MSSQL_userid");
		DB_password=prop.getProperty("MSSQL_password");
		DB_database_name=prop.getProperty("MSSQL_DatabaseName");
		
		
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
           
		//String driver,String address,String user,String password
		/*
		  this.DBDriver=driver;
		  this.DB_userid=user;
		  this.DB_connect_string=address;
		  this.DB_password=password;          
		*/  
                       /*
		         DBDriver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
			 DB_connect_string="jdbc:microsoft:sqlserver://10.122.1.38:1433;DatabaseName=taipei_db";
			 DB_userid="tts98217";
			 DB_password="tts98217";
                        */
           
                        
                       // DB_DRIVER=oracle.jdbc.driver.OracleDriver
                       // DB_URL=jdbc:oracle:thin:@192.168.200.9:1521:DBO_TAIPEI_DB
                       // DB_URL=jdbc:oracle:thin:@192.168.200.9:1521:orcl
                       // #DB_USER=dbo_taipei_db
                       // #DB_PWD=dbo_taipei_db
                        
                       /*
                        DBDriver="oracle.jdbc.driver.OracleDriver";
			 DB_connect_string="jdbc:oracle:thin:@192.168.200.9:1521:orcl";
			 DB_userid="dbo_taipei_db";
			 DB_password="dbo_taipei_db";
                        */
		
	}
	
	public static String[] GPStoByte(String coordinates){
		String[] position=coordinates.split("\\.");
		//System.out.println(coordinates);
		
		
		//for(String a:position)
		//System.out.println(a);
		
		int head = Integer.parseInt(position[0]);		
		String hexhead = Integer.toHexString(head).toUpperCase();
		//System.out.println(hexhead);
		
		int leg=Integer.parseInt(position[1]);
		String hexleg = Integer.toHexString(leg).toUpperCase();
		//System.out.println(hexleg);
		
		if(hexhead.length()<2)
			hexhead="0"+hexhead;
		
		//System.out.println(hexhead);
		
		while(hexleg.length()<6)
			hexleg="0"+hexleg;
		
		//System.out.println(hexleg);
		
		//String hex = "2A"; //The answer is 42  
		//int intValue = Integer.parseInt(hex, 16);  
		
		position[0]=hexhead;
		position[1]=hexleg;
		
		return position;
	}
	
	public static String BytetoGPS(byte[] data){
		String gps="";
		String raw=Protocol.bytesToHex(data);
		System.out.println(raw);
		int intValue = Integer.parseInt(raw, 16);
		//System.out.println(intValue);
		return gps;
	}
	
	public static String[] getTrigCrossRoadLatLon(String xroadid , String direct){
		
		Vector a =dbGetString("SELECT [StopLineLat],[StopLineLon] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
				+ "where [CrossRoadID]='"+xroadid+"' and [Direct]='"+direct+"' and [TriggerPointOrder]='"+0+"'");
		
		String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
public static String[] getBusLine_CrossRoadID(String BusLineID,int goBack){
		
		Vector a =dbGetString("SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab] "
				+ "where [BusLineID]='"+BusLineID+"' and [GoBack]='"+goBack+"'");
		
		String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
public static String[] getTrigLatLon(String xroadid , String direct, int order){
		
		Vector a =dbGetString("SELECT [Lat],[Lon] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
				+ "where [CrossRoadID]='"+xroadid+"' and [Direct]='"+direct+"' and [TriggerPointOrder]='"+order+"'");
		
		String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}

public static String[] getAllMasters_IP(){
	
	Vector a =dbGetString("SELECT [CrossRoadIP] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [isMaster]='1'");
	
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static String[] getAllMasters_Addr(){
	
	Vector a =dbGetString("SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [isMaster]='1'");
	
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static String[] getAll3G_IP(){
	
	Vector a =dbGetString("SELECT [CrossRoadIP] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [roadY] ='Y'  ");
	
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static String[] getCrossRoadAddr_Group(String GroupID){
	
	Vector a =dbGetString("SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] WHERE [GroupID] ='"+GroupID+"'  ");
	
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static String getTrigPtype(String xroadid , String direct, int order){
	String ptype="";
	
	Vector a =dbGetString("SELECT [PointType] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
			+ "where [CrossRoadID]='"+xroadid+"' and [Direct]='"+direct+"' and [TriggerPointOrder]='"+order+"'");
     ptype= a.toString().replace("[", "").replace("]", "").replace(" ", "");
      		
	return ptype;
}

public static String getGrabber_time(String CrossRoad_IP , String Table_name){
	String time="";
	
	Vector a =dbGetString("SELECT [Time] FROM ["+DB_database_name+"].[dbo].[DB_Grabber_log] "
			+ "where [CrossRoad_IP]='"+CrossRoad_IP+"' and [Table_Name]='"+Table_name+"'");
	time= a.toString().replace("[", "").replace("]", "");
      		
	return time;
}

public static String[] getLatestCommand(){
	
	Vector a =dbGetString("SELECT TOP 1 [OnOff],[Time],[admin] FROM ["+DB_database_name+"].[dbo].[OnOff_Tab] order by [Time] desc");
	
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static int GotTheCommand(String Time){
	int a=0;
			
	 a =dbUpdate(" UPDATE ["+DB_database_name+"].[dbo].[OnOff_Tab] SET [admin]='11' WHERE [Time]='"+Time+"';");	
	 
	 return a;
}
	
public static String[] getTrigID(String xroadid , String direct){
		
		Vector a =dbGetString("SELECT [TriggerPointID] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab]"
				+ " where [CrossRoadID]= '"+xroadid+"' and [Direct]='"+direct+"'"
				+ " order by [TriggerPointOrder]");
		
		String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
	public static String intTimeString(int hour,int min){
		String time;
		String sthour;
		String stmin;
		if(hour<10)
		 sthour="0" +Integer.toString(hour);
		else
		 sthour=Integer.toString(hour);
		
		if(min<10)
			 stmin="0" +Integer.toString(min);
			else
			 stmin=Integer.toString(min);
		
		time=sthour+stmin;
		
		return time;
	}
	
	public static String intHexStr(int value){
		
		 String HexStr="";
	     
	     if(value<16)
	    	 HexStr ="0"+Integer.toHexString(value);
	     else
	    	 HexStr =Integer.toHexString(value);
	     
	     HexStr=HexStr.toUpperCase();
		
	     return HexStr;
	}
	
	public static String DexStr2HexStr(String value){
		
		 String HexStr="";
	     
		 int foo = Integer.parseInt(value);
		 HexStr=intHexStr(foo);
	   
		
	     return HexStr;
	}
	
	
	public static String largeint(int value){   //for 2 byte array values  Green cycle offset
		
		String hexvalue="";		
		String hexfirst="";
		String hexsecond="";
		int second=0;
		int first=0;
		
		first=value/256;
		second=value%256;
		
		if(first<16)
			hexfirst="0" +Integer.toHexString(first);
		else 
			hexfirst=Integer.toHexString(first);
		
		if(second<16)
			hexsecond="0" +Integer.toHexString(second);
		else
			hexsecond=Integer.toHexString(second);
		
		hexvalue=hexfirst+hexsecond;
		hexvalue=hexvalue.toUpperCase();
		return hexvalue;
	}
	
	public static int[] timetoint(String time){
		int[] inttime=new int[2];
		
		String hour=time.substring(0, 2);
		
		String min=time.substring(2, 4);
		
		inttime[0]=Integer.parseInt(hour,10);
		inttime[1]=Integer.parseInt(min,10);
		
		return inttime;
	}
	
	public static String getGroupid(String deviceid){
		String groupid="";
		
		Vector a =dbGetString("SELECT GROUPID FROM [taipei_db].[dbo].[GRPDEVICE] where DEVICEID='"+deviceid+"';");
	     groupid= a.toString().replace("[", "").replace("]", "");
	      		
		return groupid;
	}
	
	public static String[] getTriggerCount(String xRoadID,String direct){
						
		Vector a =dbGetString("SELECT  [TriggerPointOrder]"
				+ "FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
				+ "where [CrossRoadID]='"+xRoadID+"' and [Direct]='"+direct+"'");
		
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
		//System.out.println(triggercount);
		
		
	}
	
	public static String[] getcommondayseg(String xroadid, String segtype ){
		
		Vector a =dbGetString("SELECT DEVICEID FROM [taipei_db].[dbo].[GRPDEVICE] ;");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
	public static String[] getalldeviceid(){
			
		Vector a =dbGetString("SELECT DEVICEID FROM [taipei_db].[dbo].[GRPDEVICE] ;");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
	public static String[] getallRoadid(){
		
		Vector a =dbGetString("SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [roadY]='Y'"
				+ "order by [CrossRoadID] ");
		
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s",""); 
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
   public static String[] getRoadIPport(String roadid){  
		
		Vector a =dbGetString("SELECT [CrossRoadIP],[CrossRoadPort] FROM "
				+ "["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab]where [CrossRoadID] ='"+roadid+"'");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s","");    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	
   public static String[] getRoadDirect(String roadid){  
		
 		Vector a =dbGetString("SELECT DISTINCT [Direct] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab]"
 				+ "where [CrossRoadID]='"+roadid+"'");
 		 String[] x= new String[a.size()];
          
 	      for(int i=0;i<a.size();i++){
 	    	x[i]= (String) a.get(i);
 	    	x[i]=x[i].replaceAll("\\s",""); 	    	
 	    	  //System.out.println(x[i]);
 	      }
 	      return x;
 	}
   
   public static String[] getFilePath(){  
		
		Vector a =dbGetString("SELECT [FileName],[FilePath]FROM ["+DB_database_name+"].[dbo].[OTA_Tab]");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s",""); 	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
   
   public static String[] getCrossRoadOTAFilePath(String crossroad){  
		
		Vector a =dbGetString("SELECT [FileName],[FilePath]FROM ["+DB_database_name+"].[dbo].[OTA_Tab] where [CrossRoadID]='"+crossroad+"'");
		 String[] x= new String[a.size()];
        
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s",""); 	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
   public static String[] getRoadSegtype(String roadid){  
		
		Vector a =dbGetString("SELECT DISTINCT [SegmentType] FROM ["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab]"
				+ " where [CrossRoadID] ='"+roadid+"'order by [SegmentType]");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s","");    	    	
	    	  //System.out.println(x[i]+"end");
	      }
	      return x;
	}
   
   public static String[] getOTAFilePath(String roadid){  
		
		Vector a =dbGetString("SELECT [FilePath],[FileName] FROM ["+DB_database_name+"].[dbo].[OTA_Tab] where [CrossRoadID]='"+roadid+"'");
		 String[] x= new String[a.size()];
        
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	x[i]=x[i].replaceAll("\\s","");    	    	
	    	  //System.out.println(x[i]+"end");
	      }
	      return x;
	}
	
   public static String getRoadAddr(String roadid){
		String addr="";
		int temp=0;
		Vector a =dbGetString("SELECT [IC_Addr] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab]"
				+ "where [CrossRoadID]='"+roadid+"'");
		
	     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");
	     //temp = Integer.parseInt(addr);
	     //addr=Integer.toHexString(temp);
	     //while(addr.length()<4)
	    	 //addr="0"+addr;
	     addr=addr.toUpperCase();
		return addr;
	}
	
	public static String[] faileddeviceid(String time){  // search starts from time      time ex:"2014-08-04"
		
		Vector a =dbGetString("SELECT distinct [DeviceID] FROM [taipei_db].[dbo].[TPCOMPARELOG]"
				+ "where result='N' and happentime >'"+time+"'");
		 String[] x= new String[a.size()];
         
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
	}
	

	public static int[] getGroupWeekSegmenttype(String groupid){
		
		
		  Vector a =dbGetInt(
	    		  "SELECT [SEGMENTTYPE] FROM [taipei_db].[dbo].[GRPTODWEEKDAYTAB] where groupid='"+groupid+"' order by weekday");      
	      Integer[] z = new Integer[a.size()];
	      int[] x = new int[a.size()];
	      a.toArray(z);
	      
	      for(int i=0;i<z.length;i++){
	    	x[i]=z[i].intValue();  	     	     	      
	      }
	      return x;
	}
	
	public static int[] getSpecSegmenttype(String groupid){
		
		
		  Vector a =dbGetInt(
	    		  "SELECT [SEGMENTTYPE] FROM [taipei_db].[dbo].[GRPSPTODTAB] where groupid='"+groupid+"' order by  [SEGMENTTYPE]");      
	      Integer[] z = new Integer[a.size()];
	      int[] x = new int[a.size()];
	      a.toArray(z);
	      
	      for(int i=0;i<z.length;i++){
	    	x[i]=z[i].intValue();  	     	     	      
	      }
	      return x;
	}
	
	public static String[] getUsedPlanid(String deviceid){  //getting the distinct planid used by the group
				String groupid=getGroupid(deviceid);
		  Vector a =dbGetString(
	    		  "SELECT distinct [PLANID] FROM [taipei_db].[dbo].[GRPTODTAB] where groupid ='"+groupid+"' order by planid");      
			String[] x= new String[a.size()];
	         
		      for(int i=0;i<a.size();i++){
		    	x[i]= (String) a.get(i);
		    	    	    	
		    	  //System.out.println(x[i]);
		      }
		      return x;
	      
	}
	
	public static String[] getAllCrossroadIDOfGroup(String groupid){  //getting the distinct planid used by the group
		
  Vector a =dbGetString(
		  "SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [GroupID] ='"+groupid+"' order by [CrossRoadID]");      
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
  
}
	
	public static String[] getAllCrossroadIDOfGroup(){  //getting the distinct planid used by the group
		
		  Vector a =dbGetString(
				  "SELECT [CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] order by [CrossRoadID]");      
			String[] x= new String[a.size()];
		     
		      for(int i=0;i<a.size();i++){
		    	x[i]= (String) a.get(i);
		    	    	    	
		    	  //System.out.println(x[i]);
		      }
		      return x;
		  
		}
	
	
	public static int getMaxSubPhaseCount(String deviceid,String planid){
		  int phasecount=0;
		
		  Vector a =dbGetInt(
	    		  "SELECT  max([PHASEID]) FROM [taipei_db].[dbo].[NOWTPBASICTAB] where deviceid='"+deviceid+"' and planid ='"+planid+"'");      
	      String z;
	      z=a.toString().replace("[","").replace("]", "");
	      phasecount = Integer.parseInt(z);
	     
	      return phasecount;
	}
	
	public static int getBusSegCount(String xroadID,String segtype){
		
		  int segcount=0;		
		  Vector a =dbGetInt(
	    		  "SELECT COUNT([SegmentType]) FROM ["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab]"
	    		  + " where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'");      
	      String z;
	      z=a.toString().replace("[","").replace("]", "");
	      segcount = Integer.parseInt(z);
	     
	      return segcount;
	}
	
	public static String[] getBusWeekdata(String xroadID,String segtype){  
		
  Vector a =dbGetString("SELECT DISTINCT [NumWeekDay],[WeekDay] FROM "
  		+ "["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab] "
  		+ "where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'");      
	String[] x= new String[a.size()];
     
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	x[i]=x[i].replaceAll("\\s+","");
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
  
}
	
	
	public static int[] getBusHour(String xroadID,String segtype){  
		
		  Vector a =dbGetString("SELECT [Hour] FROM "
		  		+ "["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab] "
		  		+ "where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'"
		  				+ "order by [Hour]");  
		  String s=a.toString().replace("[", "").replace("]", "").replace(" ", "");
		  //System.out.println(s);
		  String[] array = s.split(","); 
		 		 		 
	      int[] x = new int[a.size()];
	      
	      int foo = Integer.parseInt("1234");
	      for(int i=0;i<x.length;i++){
	    	x[i]=Integer.parseInt(array[i]);  	     	     	      
	      }
	      //for(int q:x)
			  //System.out.println(q);
	      return x;
		  
		}
	
	public static int[] getBusMin(String xroadID,String segtype){  
		
		  Vector a =dbGetString("SELECT [Min] FROM "
		  		+ "["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab] "
		  		+ "where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'"
		  				+ "order by [Hour]");      
		  String s=a.toString().replace("[", "").replace("]", "").replace(" ", "");
		  //System.out.println(s);
		  String[] array = s.split(","); 
		 		 		 
	      int[] x = new int[a.size()];
	      
	      int foo = Integer.parseInt("1234");
	      for(int i=0;i<x.length;i++){
	    	x[i]=Integer.parseInt(array[i]);  	     	     	      
	      }
	      //for(int q:x)
			  //System.out.println(q);
	      return x;
		}
	
	public static String[] getBusPlanid(String xroadID,String segtype){  
		
		  Vector a =dbGetString("SELECT [PlanID] FROM "
		  		+ "["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab] "
		  		+ "where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'"
		  				+ "order by [Hour]");      
		  String[] x= new String[a.size()];
        
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) ((String) a.get(i)).replace(" ", "");;
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
		}
	
	public static String[] getBusisEnable(String xroadID,String segtype){  
		
		  Vector a =dbGetString("SELECT [BusPrimEnable] FROM "
		  		+ "["+DB_database_name+"].[dbo].[TrafficSingal_Segment_Common_Tab] "
		  		+ "where [SegmentType]='"+segtype+"' and [CrossRoadID]='"+xroadID+"'"
		  				+ "order by [Hour]");      
		  String[] x= new String[a.size()];
          
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	    	    	
	    	  //System.out.println(x[i]);
	      }
	      return x;
		}

	public static int[] getTPBasic(String deviceid,String planid){  //getting the distinct planid used by the group
		
		  Vector a =dbGetInt(
	    		  "SELECT [MINGREEN],[MAXGREEN],[YELLOW],[ALLRED],[PEDFLASH],[PEDRED] FROM [taipei_db].[dbo].[NOWTPBASICTAB] where deviceid='"+deviceid+"' and planid='"+planid+"' order by phaseid");      
	      Integer[] z = new Integer[a.size()];
	      int[] x = new int[a.size()];
	      a.toArray(z);
	      
	      for(int i=0;i<z.length;i++){
	    	x[i]=z[i].intValue();  	     	     	      
	      }
	      return x;
	}

public static String[] getSegmentPlanid(String groupid,int segmenttype){
	
	
	 Vector a =dbGetString(
    		  "SELECT time,planid FROM [taipei_db].[dbo].[GRPTODTAB] where groupid='"+groupid+"' and segmenttype='"+segmenttype+"' order by segment ,time");           
	 
	 String[] x= new String[a.size()];
          
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
}

public static DefaultTableModel buildTableModel(ResultSet rs)
        throws SQLException {

    ResultSetMetaData metaData = rs.getMetaData();

    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }

    return new DefaultTableModel(data, columnNames);

}

public static String[] getSpecDate(String groupid,int segmenttype){
	
	
	 Vector a =dbGetString(
   		  "SELECT [SPECIALDATE1],[SPECIALDATE2] FROM [taipei_db].[dbo].[GRPSPTODTAB] where groupid='"+groupid+"' and [SEGMENTTYPE]='"+segmenttype+"' order by  [SPECIALDATE1]");           
	 
	 String[] x= new String[a.size()];
         
     for(int i=0;i<a.size();i++){
   	x[i]= (String) a.get(i);
   	    	    	
   	  //System.out.println(x[i]);
     }
     return x;
}

public static int updateIPCOTA_Log(String IP,String report){
	
	int a=0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    dateFormat.format(date);	//2014/07/30 1058
	String time=dateFormat.format(date);
			
	 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[OTA_Log] SET [OTA_Log]='"+report+"',[OTA_Time]='"+time+"'  WHERE [IPPort]='"+IP+"';");
	 if(a==0)
	 a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[OTA_Log] ([IPPort],[OTA_Log],[OTA_Time])VALUES('"+IP+"','"+report+"','"+time+"');");
	 
	 return a;
}

public static int updateWeekdaySegtype(String groupid,int weekday,int segmenttype){
	int a=0;
	
		
	 a =dbUpdate(" UPDATE [taipei_db].[dbo].[GRPTODWEEKDAYTAB] SET [SEGMENTTYPE]='"+segmenttype+"' WHERE [GROUPID]='"+groupid+"' and [WEEKDAY]='"+weekday+"';");
	 if(a==0)
	 a =a+dbUpdate("INSERT into [taipei_db].[dbo].[GRPTODWEEKDAYTAB] ([GROUPID],[WEEKDAY],[SEGMENTTYPE])VALUES('"+groupid+"','"+weekday+"','"+segmenttype+"');");
	 
	 return a;
}

public static int updateSpecdaySegtype(String groupid,int segmenttype,String date1,String date2){
	int a=0;
	
		
	 a =dbUpdate("UPDATE [taipei_db].[dbo].[GRPSPTODTAB] SET [SEGMENTTYPE]='"+segmenttype+"' WHERE [GROUPID]='"+groupid+"' and [SPECIALDATE1]='"+date1+"' and [SPECIALDATE2]='"+date2+"';");
	 if(a==0)
	 a =a+dbUpdate("INSERT into [taipei_db].[dbo].[GRPSPTODTAB] ([GROUPID],[SEGMENTTYPE],[SPECIALDATE1],[SPECIALDATE2]) VALUES ('"+groupid+"','"+segmenttype+"','"+date1+"','"+date2+"');");
	 
	 return a;
}

public static boolean check0F04status(String IP){
	
	String date="";
	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date datetemp = new Date();    
	 
	 //System.out.println("IP "+IP);
	try{
		Vector a =dbGetString("SELECT [TIMEIPCStatus] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [CrossRoadIP]='"+IP+"';");
		//System.out.println("Vector a "+a);
		date= a.toString().replace("[", "").replace("]", "");
		Date IPdate = dateFormat.parse(date);
		
		//System.out.println("check0F04status date "+ date);
		
		long diff = datetemp.getTime() - IPdate.getTime();	    		
		long diffSeconds = diff / 1000 % 60;
  		long diffMinutes = diff / (60 * 1000) % 60;
  		long diffHours = diff / (60 * 60 * 1000) % 24;
  		long diffDays = diff / (24 * 60 * 60 * 1000);
  		
  		/*
  		System.out.print(diffDays + " days, ");
  		System.out.print(diffHours + " hours, ");
  		System.out.print(diffMinutes + " minutes, ");
  		System.out.print(diffSeconds + " seconds.");
  		*/
  		
  		if(diffDays>0 || diffHours>0 || diffMinutes>1){
  			return true;
  		}else{
  			return false;
  		}
		
	}catch(Exception e){
		System.out.println("check0F04status Error ");
		e.printStackTrace();
		return false;
	}
	
	
      		
	
}

public static int update0F04Status(String IP,String status){
	int a=0;
	
		 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [Abnormalinfor]='"+status+"' ,[TIMEAbnormalinfor]=GETDATE() WHERE [CrossRoadIP]='"+IP+"' ;");
			
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static int update0F04Status_fromAddr(String CrossRoadID,String status){
	int a=0;
	
		 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [Abnormalinfor]='"+status+"' ,[TIMEAbnormalinfor]=GETDATE() WHERE [CrossRoadID]='"+CrossRoadID+"' ;");
			
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static int updateIPCStatus(String IP,int status){
	int a=0;
	
		if(status==7){
			 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [IPCStatus]='"+status+"'  WHERE [CrossRoadIP]='"+IP+"' ;");
		}else{
			 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [IPCStatus]='"+status+"' ,[TIMEIPCStatus]=GETDATE() WHERE [CrossRoadIP]='"+IP+"' ;");
		}
	
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static int updateIPCStatus_fromAddr(String CrossroadID,int status){
	int a=0;
	
		if(status==7){
			 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [IPCStatus]='"+status+"'  WHERE [CrossRoadID]='"+CrossroadID+"' ;");
		}else{
			 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [IPCStatus]='"+status+"' ,[TIMEIPCStatus]=GETDATE() WHERE [CrossRoadID]='"+CrossroadID+"' ;");
		}
	
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static int updateIPCOnOff(String IP,int status){
	int a=0;
	
		
	 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [OnOff]='"+status+"' ,[TIMEOnOff]=GETDATE() WHERE [CrossRoadIP]='"+IP+"' ;");
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static int updateIPCOnOff_fromAddr(String CrossRoadID,int status){
	int a=0;
		
	 a =dbUpdate("UPDATE ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] SET [OnOff]='"+status+"' ,[TIMEOnOff]=GETDATE() WHERE [CrossRoadID]='"+CrossRoadID+"' ;");
	 //if(a==0)
	 //a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab] ([IPCStatus]) VALUES ('"+IP+"');");
	 
	 return a;
}

public static void updateCrossRoadOTA(String crossroadID){
	
	int a=0;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     Date date = new Date();
	     dateFormat.format(date);	//2014/07/30 1058
 	System.out.println("Update Time "+dateFormat.format(date));
		String time=dateFormat.format(date);
	 a =dbUpdate(" UPDATE ["+DB_database_name+"].[dbo].[OTA_Tab] SET [UpdateTime] = '"+time+"'  WHERE [CrossRoadID]='"+crossroadID+"';");
		 	 
}



public static int deleteSegType(String groupid,int segmenttype){
	int a=0;
	try{
	  a =dbUpdate(" DELETE FROM  [taipei_db].[dbo].[GRPTODTAB]WHERE [GROUPID] = '"+groupid+"' and [SEGMENTTYPE]='"+segmenttype+"';");           
	}catch (Exception e) {  
		return a;  
	} 
	
    return a;
}

public static int deleteSpecSegType(String groupid,int segmenttype){
	int a=0;
	try{
	 a =dbUpdate(" DELETE FROM  [taipei_db].[dbo].[GRPSPTODTAB] WHERE [GROUPID]= '"+groupid+"' and [SEGMENTTYPE]='"+segmenttype+"';");           
	}catch (Exception e) {  
		return a;  
				}  
   return a;
   
}

public static int deleteTPBasicPlan(String deviceid,String planid){
	int a=0;
	try{
	 a =dbUpdate(" DELETE FROM  [taipei_db].[dbo].[NOWTPBASICTAB] WHERE [DEVICEID]= '"+deviceid+"' and [PLANID]='"+planid+"';");           
	}catch (Exception e) {  
		return a;  
				}  
   return a;
   
}

public static int deleteTPCFGPlan(String deviceid,String planid){
	int a=0;
	try{
	 a =dbUpdate(" DELETE FROM  [taipei_db].[dbo].[NOWTPCFGTAB] WHERE [DEVICEID]= '"+deviceid+"' and [PLANID]='"+planid+"';");           
	}catch (Exception e) {  
		return a;  
				}  
   return a;   
}

public static int setCompareClock(String time,int comparetype){
	int a=0;
	try{
	 a =dbUpdate(" DELETE  FROM  [taipei_db].[dbo].[COMPARECLOCK] ");  
         a =a+dbUpdate("INSERT into [taipei_db].[dbo].[COMPARECLOCK] ([STARTTIME],[COMPARETYPE]) VALUES ('"+time+"','"+comparetype+"');");
	}catch (Exception e) {  
		return a;  
				}  
   return a;
   
}

public static int Update_Grabber_log(String CrossRoadIP,String TableName){
	int a=0;
	try{
	 a =dbUpdate(" DELETE  FROM  ["+DB_database_name+"].[dbo].[DB_Grabber_log] Where [CrossRoad_IP]='"+CrossRoadIP+"'  and  [Table_Name]='"+TableName+"' ");  
         a =a+dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[DB_Grabber_log] ([CrossRoad_IP],[Table_Name],[Time]) VALUES ('"+CrossRoadIP+"','"+TableName+"',GETDATE());");
	}catch (Exception e) {  
		return a;  
				}  
   return a;
   
}

public static String[] getCompareClock(){
	
	try{Vector a =dbGetString("SELECT * FROM [taipei_db].[dbo].[COMPARECLOCK]");           
	 
	 String[] x= new String[a.size()];
          
      for(int i=0;i<a.size();i++){
    	x[i]= (String) a.get(i);
    	    	    	
    	  //System.out.println(x[i]);
      }
      return x;
	 
	}catch (Exception e) {  
		return null;  
				}  
      
   
}

public static int insertSegType(String groupid,byte[] command){
	//5FC6 0205 0000010600020700030A0004170003020607
	//2 5 0 0 1 6 0 2 7 0 3 10 0 4 23 0 3 2 6 7 
	int a=0 ;
	if(command[0]==(byte)0x5F && command[1]==(byte)0xC6 && command[3]!=(byte)0x00){
	int [] temp =Protocol.fivefc6(command);
	deleteSegType( groupid, temp[0]);
	String[] SegTime=new String[temp[1]*2];
	
		for(int i=0;i<temp[1];i++){
			String time="",planid="";
			
			if(temp[2+3*i]<10)
				 time="0"+Integer.toString(temp[2+3*i]);
			else
				time= Integer.toString(temp[2+3*i]);
			
			if(temp[3+3*i]<10)
				 time=time+"0"+Integer.toString(temp[3+3*i]);
			else
				time= time+Integer.toString(temp[3+3*i]);
			
			SegTime[i*2]=time;
			
			if(temp[4+3*i]<10)
				planid="0"+Integer.toString(temp[4+3*i]);
			else
				planid= Integer.toString(temp[4+3*i]);
			
			SegTime[1+i*2]=planid;
			
		}
		
		for(int s=0;s<temp[1];s++){
			a =a+dbUpdate("INSERT into [taipei_db].[dbo].[GRPTODTAB] ([GROUPID],[SEGMENTTYPE],[SEGMENT],[TIME],[PLANID])VALUES ('"+groupid+"','"+temp[0]+"','"+(s+1)+"','"+SegTime[s*2]+"','"+SegTime[1+s*2]+"');");
		}
				
		for(int m=0;m<temp[2+temp[1]*3];m++){
			a=a+updateWeekdaySegtype( groupid,temp[3+m+temp[1]*3],temp[0]);
		}
	 
	}
		
   return a;
}

public static boolean deleteBusData(){
	int a=0;
	
	try{
	  a =dbUpdate(" DELETE FROM ["+DB_database_name+"].[dbo].[BusDeviceData_Tab]");  
	 
		  return true;
	}catch (Exception e) {  
		return false;  
	}
	
	
    
}

public static String[] getBusLine(){  
	try{
	  Vector a =dbGetString("SELECT DISTINCT [BusLineID] FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab]");      
	  String[] x= new String[a.size()];
    
    for(int i=0;i<a.size();i++){
  	x[i]= (String) a.get(i);
  	    	    	
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getBusLine");
		return null;
	}
	}

public static String[] getIPCIP(){  
	try{
	  Vector a =dbGetString("SELECT [CrossRoadIP] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [roadY]='Y'");      
	  String[] x= new String[a.size()];
    
    for(int i=0;i<a.size();i++){
  	x[i]= (String) a.get(i);
  	    	    	
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getIPCIP");
		return null;
	}
	}

public static String[] getAllCrossRoadsIP(){  
	try{
	  Vector a =dbGetString("SELECT [IP] FROM ["+DB_database_name+"].[dbo].[GoogleMap_A_Tab]");      
	  String[] x= new String[a.size()];
    
    for(int i=0;i<a.size();i++){
  	x[i]= (String) a.get(i);
  	    	    	
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getAllCrossRoadsIP");
		return null;
	}
	}

public static String[] getBusLine_Trigger(String BusLine,String Goback){  
	try{
	  Vector a =dbGetString("SELECT [TriggerPointID] FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab] "
	  		+ " where [BusLineID] ='"+BusLine+"' and [GoBack] = '"+Goback+"'"
	  		+ " order by [BusLine_Order]");      
	  
	  String[] x= new String[a.size()];    
    for(int i=0;i<a.size();i++){
  	x[i]= (String) a.get(i);
  	    	    	
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getBusLine_Trigger");
		return null;
	}
	}

public static String[] getBusLine_CrossRoad_Direct_PassPhase(String BusLine,int Goback,String CrossRoadID){  
	try{
	  Vector a =dbGetString("SELECT [Direct],[BusSubPhaseID] FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab] "
	  		+ "where [BusLineID]='"+BusLine+"' and [GoBack]='"+Goback+"' and [CrossRoadID]='"+CrossRoadID+"'");      
	  
	  String[] x= new String[a.size()];    
    for(int i=0;i<a.size();i++){
  	x[i]= (String) a.get(i);
  	    	    	
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getBusLine_CrossRoad_Direct_PassPhase");
		return null;
	}
	}

public static String[] getTrigger_LatLon(String TriggerID){  
	try{
	  Vector a =dbGetString("SELECT [Lat],[Lon] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
	  		+ "where [TriggerPointID]='"+TriggerID+"'");      
	  
	  String[] x= new String[a.size()];    
    for(int i=0;i<a.size();i++){
    	String text=(String) a.get(i);    	
      	double value = Double.parseDouble(text);
      	value=value*100000;  		//��嚙踐�蕭�瞍脫�嚙踝蕭*100000
      	int int_value=(int) value;
      	text=String.valueOf(int_value);
      	x[i]= text;
      
  	  //System.out.println(x[i]);
    }
    return x;
	}catch(Exception e){
		System.out.println("Error in getTrigger_LatLon");
		return null;
	}
	}



public static String getCrossRoad_IPport(String CrossRoad){  
	try{
	  Vector a =dbGetString("SELECT [CrossRoadIP],[CrossRoadPort] FROM "
	  		+ "["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] where [CrossRoadID]='"+CrossRoad+"'");      
	  
	  String IP = (String) a.get(0); 
	  String port=(String) a.get(1); 
      String IPport=IP+":"+port;
      
  	  //System.out.println(x[i]);
      return IPport;
    }catch(Exception e){
		System.out.println("Error in getCrossRoad_IPport");
		return null;
	}
	}

public static String getCrossRoad_IP(String CrossRoadID){
	String IP="";
	try{
	
	Vector a =dbGetString("SELECT [CrossRoadIP] FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] "
			+ "where [CrossRoadID] ='"+CrossRoadID+"'");
	
	//SELECT [CrossRoadIP] FROM [TainanBusPrim].[dbo].[BusPrority_CrossRoad_Info_Tab] WHERE [CrossRoadID] ='0043'
	
	IP= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
	IP=IP.toUpperCase();
	return IP;
	}catch(Exception e){
		System.out.println("Error in getCrossRoad_IP");
		return null;
	}
}


public static String getTrigger_CrossRoadID(String TriggerID){
	String addr="";
	try{
	int temp=0;
	Vector a =dbGetString("SELECT[CrossRoadID] FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
			+ "where [TriggerPointID] ='"+TriggerID+"'");
	
     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
     addr=addr.toUpperCase();
	return addr;
	}catch(Exception e){
		System.out.println("Error in getTrigger_CrossRoadID");
		return null;
	}
}

public static String getBusLine_Name(String BusLineID){
	String addr="";
	try{
	int temp=0;
	Vector a =dbGetString("SELECT distinct [BusLine_Name] FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab] "
			+ "where [BusLineID]='"+BusLineID+"'");
	
     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
     addr=addr.toUpperCase();
	return addr;
	}catch(Exception e){
		System.out.println("Error in getBusLine_Name");
		return null;
	}
}


public static String getTrigger_BusLineOrder(String TriggerID){
	String addr="";
	try{
	int temp=0;
	Vector a =dbGetString("SELECT [BusLine_Order]  FROM ["+DB_database_name+"].[dbo].[BusLine_Info_Tab] "
			+ "where [TriggerPointID] ='"+TriggerID+"'");
	
     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
     addr=addr.toUpperCase();
	return addr;
	}catch(Exception e){
		System.out.println("Error in getTrigger_BusLineOrder");
		return null;
	}
}

public static String getCrossRoad_IPCStatus(String CrossRoadID){
	String addr="";
	try{
	int temp=0;
	Vector a =dbGetString("SELECT [IPCStatus]  FROM ["+DB_database_name+"].[dbo].[BusPrority_CrossRoad_Info_Tab] "
			+ "where [CrossRoadID] ='"+CrossRoadID+"'");
	//SELECT [IPCStatus] FROM [TainanBusPrim].[dbo].[BusPrority_CrossRoad_Info_Tab] WHERE [CrossRoadID] ='0043'
	
     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
     addr=addr.toUpperCase();
	return addr;
	}catch(Exception e){
		System.out.println("Error in getCrossRoad_IPCStatus");
		return null;
	}
}

public static String getTrigger_EndPoint(String TriggerID){
	String addr="";
	try{
	
	Vector a =dbGetString("SELECT [PointType]  FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab] "
			+ "where [TriggerPointID] ='"+TriggerID+"'");	
     addr= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
     addr=addr.toUpperCase();
     
     if(addr.equals("01"))
    	 addr="1";
     else
    	 addr="0";
     
	return addr;
	}catch(Exception e){
		System.out.println("Error in getTrigger_EndPoint");
		return null;
	}
}

public static boolean insertBusData(){
	try{
	if(deleteBusData()==true){
		
	String[] all_busline =getBusLine();
	
	String[] goback={"0","1"};			
	String emptyvalue="2500";
	
	int a=0;
	for(int i=0;i<all_busline.length;i++){
		
		String busline_ID=all_busline[i];
		
		for(int j=0;j<2;j++){
			
			String[] trigger_points= getBusLine_Trigger(busline_ID,goback[j]);
			
			
			
			for(int k=0;k<trigger_points.length;k++){				
				
				String[] Lat_Lon=getTrigger_LatLon(trigger_points[k]);				
				String cross_ID=getTrigger_CrossRoadID(trigger_points[k]);
				String IPport = getCrossRoad_IPport(cross_ID);
				String BusLine_Order= getTrigger_BusLineOrder(trigger_points[k]);
				String endpoint=getTrigger_EndPoint(trigger_points[k]);
				
				a =dbUpdate("INSERT into ["+DB_database_name+"].[dbo].[BusDeviceData_Tab] "
						+ "([BusLineID],[CrossRoadID],[TriggerPointID],[GoBack],[Lat],[Lon],[Temp],[IPport],[EndPoint],[BusLine_Order])"
						+ "VALUES ('"+busline_ID+"','"+cross_ID+"','"+trigger_points[k]+"','"+goback[j]+"','"+Lat_Lon[0]+"','"+Lat_Lon[1]+"','"
						+emptyvalue+"','"+IPport+"','"+endpoint+"','"+BusLine_Order+"')");
										}
									}
								}
	
	  if(a>0)
		  return true;
	  else
		  return false;
	}
	}catch(Exception e){
		System.err.println(e);
		System.out.println("Error in insertBusData");		
	}
			
   return false;
}

public static String fivef81(String xroadid, String direct){
	String command=xroadid;
	try{
	String[] xroadxy=getTrigCrossRoadLatLon( xroadid , direct);
	
	String[] xlat=GPStoByte(xroadxy[0]);
	
	String[] xlon=GPStoByte(xroadxy[1]);
	
	command=command+xlat[0]+xlat[1];
			
	
	command=command+xlon[0]+xlon[1];
	
	
	 String count[]=getTriggerCount(xroadid,direct);
	 
	 String trigcount ="0"+ count.length;
	 //System.out.println("test "+trigcount);
	 command=command+trigcount;
	 
	 String[] trigid= getTrigID(xroadid , direct);
	
 for(int i=0;i<count.length;i++){
		 
		 String tid=trigid[i];
		 String tdirect = "0"+direct;
		 String torder="0"+count[i];
		 String[] trigxy= getTrigLatLon(xroadid,direct, i);
		 String[] trigx=GPStoByte(trigxy[0]);
		 String[] trigy=GPStoByte(trigxy[1]);
		 
		 
		 String ptype=getTrigPtype(xroadid , direct, i);
		
		 command=command+tid+tdirect+torder+trigx[0]+trigx[1]+trigy[0]+trigy[1]+ptype;
	 }
 
	}catch(Exception e){
		System.err.println(e);
	}
	

	
    command="5F81"+command;
    command=command.toUpperCase();
    return command;
}

public static String fivef16(String deviceid,int day){     //day 0~6
		
	 String command="";
	 String grp=getGroupid(deviceid);
	 
	 int[] grpsegtype=getGroupWeekSegmenttype(grp);
	 int daysegtype=grpsegtype[day];
	 int totaldays = 0;
	 String weekday="";
	 for(int f=0;f<grpsegtype.length;f++){
		 if(daysegtype==grpsegtype[f]){
			 totaldays++;
			 weekday=weekday+"0"+(f+1);
		 }
	 }
	 weekday="0"+totaldays+weekday;
	 String Hexdaysegtype="";
	 
	 if(daysegtype<16)
		 Hexdaysegtype="0"+Integer.toHexString(daysegtype);
	 else
		 Hexdaysegtype=Integer.toHexString(daysegtype);
	 
     int segcount=(getSegmentPlanid(grp,daysegtype).length)/2;
     
     String Hexsegcount="";
     
     if(segcount<16)
    	 Hexsegcount ="0"+Integer.toHexString(segcount);
     else
    	 Hexsegcount =Integer.toHexString(segcount);
     
     command=Hexdaysegtype+Hexsegcount;
   
     
     String[] timetemp=getSegmentPlanid(grp,daysegtype);
    
     
     for(int i=0;i<timetemp.length;i++){
    	
    	 if(timetemp[i].length()>3){
    		 String hour=timetemp[i].substring(0,2);    		 
    		 String min=timetemp[i].substring(2,4);
    		
    		 String hexhour;
    		 int dechour=Integer.parseInt(hour);
    		 if(dechour==0)
    			 hexhour="00";
    		 else if(dechour<16) 
    			 hexhour= "0"+Integer.toHexString(dechour);
    		 
    		 else
    			 hexhour= Integer.toHexString(dechour);
    		     		
    		 
    		 String hexmin;
    		 
    		 int decmin=Integer.parseInt(min);
    		 if(decmin==0)
    			 hexmin="00";
    		 else if(decmin<16) 
    		  hexmin= "0"+Integer.toHexString(decmin);
    		 else
    			 hexmin= Integer.toHexString(decmin);
    		
    		 
    		 command=command+hexhour+hexmin;
    	 }
    	 else{
    		 String hexplan="";
       		 int plan=Integer.parseInt(timetemp[i]);
       		 if(plan<16)
       		 hexplan="0"+Integer.toHexString(plan);
       		 else
       		 hexplan=Integer.toHexString(plan);
       		 
       		 command=command+hexplan;
    	 }
    		 
     }
     
     command="5F16"+command+weekday;
     command=command.toUpperCase();
     return command;
}

public static String fivef82(String xroadid, String segtype){     
	
	 String command="";
	 
	 int count =getBusSegCount(xroadid,segtype);
	 
	 String hexcount=intHexStr(count);
	 
	 int[] hour=getBusHour(xroadid,segtype);
	 int[] min=getBusMin(xroadid,segtype);
	 String[] planID=getBusPlanid(xroadid,segtype);
	 String[] onoff=getBusisEnable(xroadid,segtype);
	 String turn="";
	 for(String v:onoff)
		 turn=turn+v;
	 
	 
	 String[] week=getBusWeekdata(xroadid,segtype);
	 
	 String daynum=DexStr2HexStr(week[0]);
	 String sstype=DexStr2HexStr(segtype);
	 String days=week[1];
	 
	 String ltcmd="";
	 
	for(int i=0;i<count;i++){
		
		ltcmd = ltcmd +intHexStr(hour[i])+intHexStr(min[i])+planID[i];
				
	}
    
    command="5F82"+xroadid+sstype+hexcount+ltcmd+daynum+days+turn;
    command=command.toUpperCase();
    return command;
}


public static int insertSpecSegType(String groupid,byte[] command){
	//5FC7 09070000050100060500010700030900071300031700055C0B025C0B02
	//9 7  0 0 5  1 0 6  5 0 1  7 0 3  9 0 7  19 0 3  23 0 5  92 11 2 92 11 2 
	int a=0 ;
	if(command[0]==(byte)0x5F && command[1]==(byte)0xC7 && command[3]!=(byte)0x00){
	int [] temp =Protocol.fivefc7(command);
      
	deleteSegType( groupid, temp[0]);
	deleteSpecSegType( groupid, temp[0]);
        
	String[] SegTime=new String[temp[1]*2];
	
		for(int i=0;i<temp[1];i++){
			String time="",planid="";
			
			if(temp[2+3*i]<10)
				 time="0"+Integer.toString(temp[2+3*i]);
			else
				time= Integer.toString(temp[2+3*i]);
			
			if(temp[3+3*i]<10)
				 time=time+"0"+Integer.toString(temp[3+3*i]);
			else
				time= time+Integer.toString(temp[3+3*i]);
			
			SegTime[i*2]=time;
			
			if(temp[4+3*i]<10)
				planid="0"+Integer.toString(temp[4+3*i]);
			else
				planid= Integer.toString(temp[4+3*i]);
			
			SegTime[1+i*2]=planid;
			
		}
		for(int s=0;s<temp[1];s++){
			a =a+dbUpdate("INSERT into [taipei_db].[dbo].[GRPTODTAB] ([GROUPID],[SEGMENTTYPE],[SEGMENT],[TIME],[PLANID])VALUES ('"+groupid+"','"+temp[0]+"','"+(s+1)+"','"+SegTime[s*2]+"','"+SegTime[1+s*2]+"');");
		}
				
		
		String date1="";
		String date2="";
		
		for(int q=6;q>0;q--){
			if(q>3){
				if(q==5 || q==4)
					date1=date1+"/";
				
				if(temp[temp.length-q]<10)			
					date1=date1+"0"+Integer.toString(temp[temp.length-q]);
				else
					date1=date1+Integer.toString(temp[temp.length-q]);
				
			
			}
			else{
				if(q==2 || q==1)
					date2=date2+"/";
				
					if(temp[temp.length-q]<10)
						date2=date2+"0"+Integer.toString(temp[temp.length-q]);
					else
						date2=date2+Integer.toString(temp[temp.length-q]);				
			}	
		
		
		}
		
			//
		
		a=a+ updateSpecdaySegtype(groupid,temp[0],date1,date2);
		
		
			
	}
		
   return a;
}

public static String fivef17(String deviceid,int specday){  ////specday 0~12 
	
	 String command="";
	 String grp=getGroupid(deviceid);
	 int[] grpsegtype = getSpecSegmenttype(grp);
	 

	
		 
	 //int daysegtype=grpsegtype[specday];
	 //System.out.println(daysegtype);
         int daysegtype=specday;
	 String[] dates=getSpecDate(grp,daysegtype);
	 
     String[] date1= dates[0].split("/");
     String[] date2= dates[1].split("/");
     
     int[] date1dec = new int[date1.length];
     int[] date2dec = new int[date2.length];
     
     for(int v=0;v<date1dec.length;v++){
    	 date1dec[v] = Integer.valueOf(date1[v]);
    	 date2dec[v] = Integer.valueOf(date2[v]);    	 			 
     }
     
     String hexdate1="",hexdate2="";
     
     
     for(int v=0;v<date1dec.length;v++){
    	 
    	 if(date1dec[v]<16)
    		 hexdate1=hexdate1+"0"+Integer.toHexString(date1dec[v]);
    	 else
    		 hexdate1=hexdate1+Integer.toHexString(date1dec[v]);
    	 
    	 if(date2dec[v]<16)
    		 hexdate2=hexdate2+"0"+Integer.toHexString(date2dec[v]);
    	 else
    		 hexdate2=hexdate2+Integer.toHexString(date2dec[v]);
    	    	 
     }
     
	 
	 String Hexdaysegtype="";
	 
	 if(daysegtype<16)
		 Hexdaysegtype="0"+Integer.toHexString(daysegtype);
	 else
		 Hexdaysegtype=Integer.toHexString(daysegtype);
	 
    int segcount=(getSegmentPlanid(grp,daysegtype).length)/2;
    
    String Hexsegcount="";
    
    if(segcount<16)
   	 Hexsegcount ="0"+Integer.toHexString(segcount);
    else
   	 Hexsegcount =Integer.toHexString(segcount);
    
    command=Hexdaysegtype+Hexsegcount;
  
    
    String[] timetemp=getSegmentPlanid(grp,daysegtype);
   
    
    for(int i=0;i<timetemp.length;i++){
   	
   	 if(timetemp[i].length()>3){
   		 String hour=timetemp[i].substring(0,2);    		 
   		 String min=timetemp[i].substring(2,4);
   		
   		 String hexhour;
   		 int dechour=Integer.parseInt(hour);
   		 if(dechour==0)
   			 hexhour="00";
   		 else if(dechour<16) 
   			 hexhour= "0"+Integer.toHexString(dechour);
   		 
   		 else
   			 hexhour= Integer.toHexString(dechour);
   		     		
   		 
   		 String hexmin;
   		 
   		 int decmin=Integer.parseInt(min);
   		 if(decmin==0)
   			 hexmin="00";
   		 else if(decmin<16) 
   		  hexmin= "0"+Integer.toHexString(decmin);
   		 else
   			 hexmin= Integer.toHexString(decmin);
   		
   		 
   		 command=command+hexhour+hexmin;
   	 }
   	 else{
   		String hexplan="";
   		 int plan=Integer.parseInt(timetemp[i]);
   		 if(plan<16)
   		 hexplan="0"+Integer.toHexString(plan);
   		 else
   		 hexplan=Integer.toHexString(plan);
   		 
   		 command=command+hexplan;
   	 }
   		 
    }
    
    command="5F17"+command+hexdate1+hexdate2;
    command=command.toUpperCase();
    return command;
	 
	 
}

public static String fivef14(String deviceid,String planid){     //select a planid to send to device
	 
	 String command="";
	 String hexplanid="";
	 String hextpbasic="";
	 String hexphasecount="";
	 String grp=getGroupid(deviceid);
	 
	 int decplanid=Integer.parseInt(planid);
	 
	 if(decplanid<16)
		 hexplanid="0"+Integer.toHexString(decplanid);
	 else
		 hexplanid=Integer.toHexString(decplanid);
	 
	 
	 
	 int maxphasecount=getMaxSubPhaseCount(deviceid,planid);
	 
			 
			 if(maxphasecount<16)
				 hexphasecount="0"+Integer.toHexString(maxphasecount);
			 else
				 hexphasecount=Integer.toHexString(maxphasecount);
			 
	 int[]tpbasic=getTPBasic(deviceid,planid);
	 
	 for(int j=0;j<tpbasic.length;j++){
		 if(j%6==1){
			 hextpbasic=hextpbasic+largeint(tpbasic[j]);
		 }else{			 
		 if(tpbasic[j]<16)
			 hextpbasic=hextpbasic+"0"+Integer.toHexString(tpbasic[j]);
		 else
			 hextpbasic=hextpbasic+Integer.toHexString(tpbasic[j]);
		 }
	 }
	 
	
    
    command="5F14"+hexplanid+hexphasecount+hextpbasic;
    command=command.toUpperCase();
    return command;
}

public static String fivef15(String deviceid,String planid){     //select a planid to send to device  PLANID IS IN HEX
	//PLANID, BASEDIRECTION , PHASEORDER,PHASECOUNT,CYCLETIME,OFFSET
	//6      0                  0         2           70        0 		
	//35 25 
	//DEVICEID	SERIALID	PLANID	PHASEORDER	CYCLETIME	OFFSET	BASEDIRECTION	PHASECOUNT	DATETIMEMOD	STATUS
	//S042201		06	00	70	0	0	2	2009/05/08 1056	Y
					
	 String command="";
	 String hexplanid="";
	 String hexdirect="";
	 String hexphaseorder="";
	 String hexphasecount="";
	 String hexcycle="";
	 String hexoff="";
	 String hexgreen="";
	 String grp=getGroupid(deviceid);
	 
	
	 
	 int[] tpcfggreen=downloadGREENTIME(deviceid ,planid);
     int[] tpcfg= downloadTPCFG(deviceid ,planid);
     

     
     for(int x=0;x<tpcfggreen.length;x++){
    	    	 hexgreen=hexgreen+largeint(tpcfggreen[x]);   	 	 
     }
     
     //intHexStr(int value)
	 
	 
	 try{
	
	 
	 if(tpcfg[1]<16)   ///?????????
		 hexdirect="0"+Integer.toHexString(tpcfg[1]);
	 else
		 hexdirect=Integer.toHexString(tpcfg[1]);
	 
	 if(tpcfg[2]<16)
		 hexphaseorder="0"+Integer.toHexString(tpcfg[2]);
	 else
		 hexphaseorder=Integer.toHexString(tpcfg[2]);	
	 			 
	 if(tpcfg[3]<16)
		 hexphasecount="0"+Integer.toHexString(tpcfg[3]);
	 else
		 hexphasecount=Integer.toHexString(tpcfg[3]);
	 
	 hexcycle=largeint(tpcfg[4]);  
	 	 
	 hexoff=largeint(tpcfg[5]);  
	 }catch (Exception e) {  
			return command;  
		} 
	 

   
   command="5F15"+planid+hexdirect+hexphaseorder+hexphasecount+hexgreen+hexcycle+hexoff;
   command=command.toUpperCase();
   return command;
}

public static int insertTPbasic(String deviceid,byte[] command){
	//5FC4 01 03 0A00D2040207030000C8030200000A00D203030703
	//1 3 10 210 4 2 7 3 0 200 3 2 0 0 10 210 3 3 7 3 
	
	int a=0 ;
	String hexplanid="";
	if(command[0]==(byte)0x5F && command[1]==(byte)0xC4 ){
	int [] temp =Protocol.fivefc4(command);
		
			if(temp[0]<16)
				hexplanid="0"+Integer.toHexString(temp[0]);
			else
				hexplanid=Integer.toHexString(temp[0]);
	
	  a=a+deleteTPBasicPlan( deviceid, hexplanid);
			 		
		for(int s=0;s<temp[1];s++){
						
			a =a+dbUpdate("INSERT into [taipei_db].[dbo].[NOWTPBASICTAB] ([DEVICEID],[PLANID],[PHASEID],[MINGREEN],[MAXGREEN],[YELLOW],[ALLRED],[PEDFLASH],[PEDRED])VALUES ('"+deviceid+"','"+hexplanid+"','"+(s+1)+"','"+temp[2+s*6]+"','"+temp[3+s*6]+"','"+temp[4+s*6]+"','"+temp[5+s*6]+"','"+temp[6+s*6]+"','"+temp[7+s*6]+"');");
		}
								
	}
		
   return a;
}

public static int insertTPCFG(String deviceid,byte[] command){
	
	//DEVICEID	SERIALID	PLANID	PHASEORDER	CYCLETIME	OFFSET	BASEDIRECTION	PHASECOUNT	DATETIMEMOD	STATUS
	//S049101		06	00	120	7	0	2	2009/01/24 2225	Y
	//   2 0 41 3 64 10 29 120 105 
	int a=0 ;
	String hexplanid="";
	String hexphaseorder="";
	
	if(command[0]==(byte)0x5F && command[1]==(byte)0xC5 ){
	int [] temp =Protocol.fivefc5(command);
	
	if(temp==null){
		System.out.println("嚙踐�蕭赯哨蕭���嚙踝蕭");
		return a;
	}
		
		
			if(temp[0]<16)
				hexplanid="0"+Integer.toHexString(temp[0]);
			else
				hexplanid=Integer.toHexString(temp[0]);
			
			if(temp[2]<16)
				hexphaseorder="0"+Integer.toHexString(temp[2]);
			else
				hexphaseorder=Integer.toHexString(temp[2]);
			
			hexplanid=hexplanid.toUpperCase();
			hexphaseorder=hexphaseorder.toUpperCase();
	
	  a=a+deleteTPCFGPlan( deviceid, hexplanid);

	  	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HHmm");
	     Date date = new Date();
	     dateFormat.format(date);	//2014/07/30 1058
	          							
			a =a+dbUpdate("INSERT into [taipei_db].[dbo].[NOWTPCFGTAB] ([DEVICEID],[PLANID],[PHASEORDER],[CYCLETIME],[OFFSET],[BASEDIRECTION],[PHASECOUNT],[DATETIMEMOD],[STATUS])VALUES ('"+deviceid+"','"+hexplanid+"','"+hexphaseorder+"','"+temp[temp.length-2]+"','"+temp[temp.length-1]+"','"+temp[1]+"','"+temp[3]+"','"+dateFormat.format(date)+"','"+"Y"+"');");
		
								
	}
		
   return a;
}

public static int insertTPbasicGreen(String deviceid,byte[] command){
		
	int a=0 ;
	String hexplanid="";
	if(command[0]==(byte)0x5F && command[1]==(byte)0xC5 ){
	int [] temp =Protocol.fivefc5(command);
	if(temp==null){
		System.out.println("����������T");
		return a;
	}
			if(temp[0]<16)
				hexplanid="0"+Integer.toHexString(temp[0]);
			else
				hexplanid=Integer.toHexString(temp[0]);
	
	 			 		
		for(int s=0;s<temp[3];s++){						
			a =a+dbUpdate("update [taipei_db].[dbo].[NOWTPBASICTAB] set [GREENTIME]='"+temp[4+s]+"' where deviceid='"+deviceid+"' and planid='"+hexplanid+"' and phaseid='"+(s+1)+"'");
		}
								
	}
		
   return a;
}

public static String[] getSpecialDaySegmenttype(String groupid){
	
	
	  Vector a =dbGetString("SELECT [SPECIALDATE1],[SPECIALDATE2],[SEGMENTTYPE] FROM [taipei_db].[dbo].[GRPSPTODTAB] where groupid='"+groupid+"' order by specialdate1");      
	   String[] x= new String[a.size()];
	      for(int i=0;i<a.size();i++){
	    	x[i]= (String) a.get(i);
	    	
	    	
	      }
      return x;
      
}



public static int[] downloadTPBASIC(String deviceID,String planID){
	
	Vector a =dbGetInt("select  MINGREEN,MAXGREEN,YELLOW,ALLRED,PEDFLASH,PEDRED FROM [taipei_db].[dbo].[NOWTPBASICTAB] WHERE DEVICEID= '"+deviceID+"' AND PLANID='"+planID+"' ORDER BY PHASEID");
	Integer[] z = new Integer[a.size()];
      int[] x = new int[a.size()];
      a.toArray(z);
      
      for(int i=0;i<z.length;i++){
    	x[i]=z[i].intValue();  	     	     	      
      }
      return x;
}

public static int[] downloadTPCFG(String deviceID,String planID){ // PLANID SHOULD BE IN HEX
	
	Vector a =dbGetInt("select PLANID, BASEDIRECTION , PHASEORDER,PHASECOUNT,CYCLETIME,OFFSET FROM [taipei_db].[dbo].[NOWTPCFGTAB] WHERE DEVICEID='"+deviceID+"' AND PLANID='"+planID+"'");
	Integer[] z = new Integer[a.size()];
      int[] x = new int[a.size()];
      a.toArray(z);
      //System.out.println(x[0]);
      String phasecount="";
		int phasecc=0;
		Vector b =dbGetString("SELECT PHASEORDER FROM [taipei_db].[dbo].[NOWTPCFGTAB] WHERE DEVICEID='"+deviceID+"' AND PLANID='"+planID+"';");
		phasecount= b.toString().replace("[", "").replace("]", "");    
		//System.out.println(phasecount);
		if(phasecount==null)
                    return null;
                phasecc= Integer.parseInt(phasecount, 16);
		
      for(int i=0;i<z.length;i++){
    	  
    	x[i]=z[i].intValue();  	    
    	if(i==2)
    		x[i]=phasecc;
    		
      }
      
      return x;
}



public static int[] downloadGREENTIME(String deviceID,String planID){   //PLANID SHOULD BE HEX
	
	Vector a =dbGetInt("select GREENTIME FROM [taipei_db].[dbo].[NOWTPBASICTAB] WHERE DEVICEID='"+deviceID+"' AND PLANID='"+planID+"' ORDER BY PHASEID");
	Integer[] z = new Integer[a.size()];
      int[] x = new int[a.size()];
      a.toArray(z);
      
      for(int i=0;i<z.length;i++){
    	x[i]=z[i].intValue();  	     	     	      
      }
      return x;
}
public static int updateSegCompareLog(String deviceid,int day,boolean result,String description,int centeraction, 
			int updateresult){
	int a = 0;
	String resultstr="";
	
	if(result)
		resultstr="Y";
	else
		resultstr="N";
	
String centersctstr="";
	
switch(centeraction) {  
case 2: 
	centersctstr="���蕭�嚙踝嚙踐�蕭謘餉爸嚙踐�蕭赯�"; 
    break; 
case 1: 
	centersctstr="嚙踐�蕭赯剝�嚙踐�蕭謘餉爸���蕭嚙�";  
    break; 
case 0: 
	centersctstr="���蕭�嚙踝謆蕭謘�";  
    break; 
default: 
	centersctstr="No Match Type"; 
}

String updateresultstr="";

switch(updateresult) {   
case 2: 
	updateresultstr="嚙踐�蕭謘蕭嚙踐�蕭嚙�"; 
    break; 
case 1: 
	updateresultstr="嚙踐�蕭謘��蕭嚙�";  
    break; 
case 0: 
	updateresultstr="���蕭���蕭謘�";  
    break; 
default: 
	updateresultstr="No Match Type"; 
}
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    dateFormat.format(date);	//2014/07/30 1058
	
	a =a+dbUpdate("INSERT into [taipei_db].[dbo].[SEGCOMPARELOG] ( [DEVICEID],[SEGMENTTYPE],[HAPPENTIME]"
			+ ",[RESULT],[DESCRIPTION],[CENTERACTION],[UPDATERESULT])"
			+ "VALUES('"+deviceid+"','"+day+"','"+dateFormat.format(date)+"','"+resultstr+"','"+description+"','"
			+centersctstr+"','"+updateresultstr+"');");
	
	return a;
}

public static int updateTPCompareLog(String deviceid,String planid,boolean result,String description,int centeraction, 
		int updateresult){
int a = 0;
String resultstr="";

if(result)
	resultstr="Y";
else
	resultstr="N";

String centersctstr="";

switch(centeraction) {  
case 2: 
centersctstr="���蕭�嚙踝嚙踐�蕭謘餉爸嚙踐�蕭赯�"; 
case 1: 
centersctstr="嚙踐�蕭赯剝�嚙踐�蕭謘餉爸���蕭嚙�";  
break; 
case 0: 
centersctstr="���蕭�嚙踝謆蕭謘�";  
break; 
default: 
centersctstr="No Match Type"; 
}

String updateresultstr="";

switch(updateresult) {   
case 2: 
updateresultstr="嚙踐�蕭謘蕭嚙踐�蕭嚙�"; 
break; 
case 1: 
updateresultstr="嚙踐�蕭謘��蕭嚙�";  
break; 
case 0: 
updateresultstr="���蕭���蕭謘�";  
break; 
default: 
updateresultstr="No Match Type"; 
}

DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = new Date();
dateFormat.format(date);	//2014/07/30 1058

a =a+dbUpdate("INSERT into [taipei_db].[dbo].[TPCOMPARELOG] ( [DEVICEID],[PlanID],[HAPPENTIME]"
		+ ",[RESULT],[DESCRIPTION],[CENTERACTION],[UPDATERESULT])"
		+ "VALUES('"+deviceid+"','"+planid+"','"+dateFormat.format(date)+"','"+resultstr+"','"+description+"','"
		+centersctstr+"','"+updateresultstr+"');");

return a;
}

	
public static Vector dbGetInt(String queryString)
{
   try {
 	  Class.forName(DBDriver);
      //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
      Connection con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
      //System.out.println("connected");
     
      /*
      System.out.println(" enter ID  ");
  	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
  	a = (String) br.readLine();
  	*/
      
      Statement statement = con.createStatement();  // before creating a query
      //String queryString = "select * from [taipei_db].[dbo].[TPCOMPARELOG] where [DEVICEID]='S"+a+"01'";
      //String queryString = "SELECT GROUPID FROM [taipei_db].[dbo].[GRPDEVICE] where DEVICEID='S580601';";
      ResultSet rs = statement.executeQuery(queryString);
      
      ResultSetMetaData metaDt = rs.getMetaData();
      int cols = metaDt.getColumnCount();
      //System.out.println("total columns " +cols);	// number of columns
      
      Vector columnNames = new Vector();
      Vector tpdata = new Vector();
      //Vector<Integer> tpdata = new Vector<Integer>();
      
    

      
      for(int i=1;i<=cols;i++){			// get the column names
          columnNames.addElement (metaDt.getColumnName(i));
          //System.out.println(metaDt.getColumnName(i));
      }
      
    
      
      if(!rs.next()){
 		 System.out.println("嚙踝��嚙踝蕭");  
 		 }
      else{
      int size =0;
      do  {
     	size ++;
     	
         
     	for(int i=1;i<=cols;i++){
     		
     		try{
     		tpdata.addElement(rs.getInt(i));
     		}
     		catch(Exception e){
     		String hex	=rs.getString(i);
     		int decimal=Integer.parseInt(hex,16);
     		tpdata.addElement(decimal);
     		
     		}     	      	
     	}
         //System.out.println(" Size "+size);
     	 } while (rs.next());
      }
      //System.out.println("Total rows "+size);
      return tpdata;
   } catch (Exception e) {
      e.printStackTrace();
      return null;
   }
	
   
   
   
}


public static ResultSet dbGetResult(String queryString){
   try {
 	  Class.forName(DBDriver);
      //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
      Connection con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
      //System.out.println("connected");
     
      /*
      System.out.println(" enter ID  ");
  	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
  	a = (String) br.readLine();
  	*/
      
      Statement statement = con.createStatement();  // before creating a query
      //String queryString = "select * from [taipei_db].[dbo].[TPCOMPARELOG] where [DEVICEID]='S"+a+"01'";
      //String queryString = "SELECT GROUPID FROM [taipei_db].[dbo].[GRPDEVICE] where DEVICEID='S580601';";
      ResultSet rs = statement.executeQuery(queryString);
      
      ResultSetMetaData metaDt = rs.getMetaData();
      int cols = metaDt.getColumnCount();
     

     
      //System.out.println("Total rows "+size);
      return rs;
   } catch (Exception e) {
      e.printStackTrace();
      return null;
   }
	
   
   
   
}
   public static Vector dbGetString(String queryString)
   {
      try {
    	  Class.forName(DBDriver);
         //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
         Connection con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
         //System.out.println("connected");
               
         
         Statement statement = con.createStatement(); 
         ResultSet rs = statement.executeQuery(queryString);
         
         ResultSetMetaData metaDt = rs.getMetaData();
         int cols = metaDt.getColumnCount();
         //System.out.println("total columns " +cols);	// number of columns
         
         Vector columnNames = new Vector();
         Vector tpdata = new Vector();
         //Vector<Integer> tpdata = new Vector<Integer>();

         
         for(int i=1;i<=cols;i++){			// get the column names
             columnNames.addElement (metaDt.getColumnName(i));
             //System.out.println(metaDt.getColumnName(i));
         }
         
       
         
         if(!rs.next()){
    		 System.out.println("嚙踝��嚙踝蕭2");  
    		 }
         else{
         int size =0;
         do  {
        	size ++;
        	
            
        	for(int i=1;i<=cols;i++){
        		tpdata.addElement(rs.getString(i));	
        		/*
        		try{tpdata.addElement(rs.getInt(i));}
        		catch(Exception e){
        			tpdata.addElement(rs.getString(i));	
        		}*/
        	      	
        	}
            //System.out.println(" Size "+size);
        	 } while (rs.next());
         }
         //System.out.println("Total rows "+size);
         return tpdata;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   
      
   }
   
   public static int dbUpdate(String queryString)
   {
      try {
    	  Class.forName(DBDriver);
     
         Connection con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
                
         Statement statement = con.createStatement();  // before creating a query
        
         try{
          statement.executeUpdate(queryString);
          int effect=statement.getUpdateCount();
               
        return effect;  }
         catch (Exception e) {
             e.printStackTrace();
             return 0;
          }
         
        
      } catch (Exception e) {
         e.printStackTrace();
         return 0;
      }
     
   }
   
   public static int dbUpdate(String driver,String connect_string,String userid,String password,String queryString)
   {
      try {
    	  Class.forName(driver);
     
         Connection con= DriverManager.getConnection(connect_string,userid,password);
                
         Statement statement = con.createStatement();  
        
         try{
          statement.executeUpdate(queryString);
          int effect=statement.getUpdateCount();
               
        return effect;  }
         catch (Exception e) {
             e.printStackTrace();
             return 0;
          }
         
        
      } catch (Exception e) {
         e.printStackTrace();
         return -1;
      }
     
   }
   
   public static boolean dbConnection()
   {
      try {
    	  Class.forName(DBDriver);
     
         Connection con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
        
         con.close();
         
        return true;

      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
     
   }
   public static boolean dbConnection(String driver,String connect_string,String userid,String password)
   {
      try {
    	  Class.forName(driver);
     
         Connection con= DriverManager.getConnection(connect_string,userid,password);
          
         con.close();
         
        return true;

      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
     
   }
   
   public static void convertToCsv(ResultSet rs,String s) throws SQLException, FileNotFoundException {
       PrintWriter csvWriter = new PrintWriter(new File(s)) ;
       ResultSetMetaData meta = rs.getMetaData() ; 
       int numberOfColumns = meta.getColumnCount() ; 
       String dataHeaders = "\"" + meta.getColumnName(1) + "\"" ; 
       for (int i = 2 ; i < numberOfColumns + 1 ; i ++ ) { 
               dataHeaders += ",\"" + meta.getColumnName(i) + "\"" ;
       }
       //csvWriter.println(dataHeaders) ;
       while (rs.next()) {
           String row = "\"" + rs.getString(1) + "\""  ; 
           for (int i = 2 ; i < numberOfColumns + 1 ; i ++ ) {
               row += ",\"" + rs.getString(i) + "\"" ;
           }
       csvWriter.println(row) ;
       }
       csvWriter.close();
   }
   
   public static void printRs(ResultSet rs) throws SQLException{
	      //Ensure we start with first row
	   ResultSetMetaData rsmd = rs.getMetaData();
	   int numberOfColumns = rsmd.getColumnCount() ; 
	   String[] columnheaders=new String[numberOfColumns];
	   System.out.println("Table Name "+rsmd.getTableName(1));
	   
	   
	   for(int i=0;i<numberOfColumns;i++){
		   System.out.println("ColumnName "+rsmd.getColumnName(i+1));
		   columnheaders[i] = rsmd.getColumnName(i+1);
	   }
		   	  	   
	      
	      while(rs.next()){
	    	  
	    	  for(String a:columnheaders){
	    		  String str_data = rs.getString(a);
	    		  System.out.print(" "+a+": " + str_data);
	    	  }
	    		  
	         
	    	  System.out.println();
	     }
	     
	   }
   
   public static void InsertRs(ResultSet rs,String driver,String connect_string,String userid,String password,String tablename ) throws SQLException{
	   //ResultSet rs,String driver,String connect_string,String userid,String password,String tablename   
	   //Ensure we start with first row
	   ResultSetMetaData rsmd = rs.getMetaData();
	   int numberOfColumns = rsmd.getColumnCount() ; 
	   String[] columnheaders=new String[numberOfColumns];
	   
	   for(int i=0;i<numberOfColumns;i++){
		   System.out.println("ColumnName "+rsmd.getColumnName(i+1));
		   columnheaders[i] = rsmd.getColumnName(i+1);
	   }
		   	  	   
	      
	      while(rs.next()){
	    	  
	    	  String rowdata="";
	    	  String rowheader="";
	    	  for(String a:columnheaders){
	    		  
	    		  String str_data = rs.getString(a);
	    		  //System.out.print(" "+a+": " + str_data);
	    		  
	    	  }
	    	  System.out.println();
	    	  
	    	  	for(int k=0;k<columnheaders.length;k++){
	    	  		
	    	  		rowdata=rowdata+"'"+rs.getString(columnheaders[k])+"'";
	    	  		rowheader=rowheader+columnheaders[k];
	    	  		
	    	  		if(k!=(columnheaders.length-1)){
	    	  			rowdata=rowdata+",";
	    	  			rowheader=rowheader+",";
	    	  		}

	    	  }
	    	  
	    	  String queryString="INSERT into "+ tablename+" ("+rowheader+") "+"VALUES("+rowdata+")";
	    	  //System.out.println(queryString);
	    	  dbUpdate(driver,connect_string,userid,password,queryString);
	    		  
	         
	    	  System.out.println();
	     }
	     
	   }
   
   public static void BusCSV(String BusLineID,String goback){    //major make over
	   
	   try{
		    
	       ResultSet myResultSet = dbGetResult("SELECT [Lat],[Lon],[Temp],[IPport],[TriggerPointID],[EndPoint] "
	       		+ "FROM ["+DB_database_name+"].[dbo].[BusDeviceData_Tab] where [BusLineID]='"+BusLineID+"' and "
	       		+ "[GoBack]='"+goback+"'order by [BusLine_Order]");	
	       System.out.println(myResultSet);
  
	       //convertToCsv(myResultSet,"C://"+DB_database_name+".csv");   
	       convertToCsv(myResultSet,"C://"+BusLineID+goback+".csv"); 
	       
	       HttpMultipartTest temp = new HttpMultipartTest(BusLineID+goback+".csv","C://");  
	       
	       System.out.println(temp.Upload("127.0.0.1:81"));
	       	       
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
	   
   }
   
   public static String fivef88(String BusLineID, int goback){     
		
		 String command="";
		 String[] StrGoBack={"00","01"};
		 String BusLineName=getBusLine_Name(BusLineID);
		 byte[] NameBytes = BusLineName.getBytes();	
		 
		 BusLineName = Protocol.bytesToHex(NameBytes);
		 		 
		 
		 while(BusLineName.length()<40){
			 BusLineName="00"+BusLineName;					 
		 }
		 
		 String[] Passed_CrossRoads = getBusLine_CrossRoadID(BusLineID,goback);
		 int CrossRoads_counts = Passed_CrossRoads.length;
		 	 				 
		 String Hex_CrossRoads_counts = intHexStr(CrossRoads_counts);
		 String CrossRoadID_Direct_PassPhase= "";
		 		 
		 for(int i=0;i<Passed_CrossRoads.length;i++){
			 			 			 
		 String[] Direct_PassPhase = getBusLine_CrossRoad_Direct_PassPhase( BusLineID, goback , Passed_CrossRoads[i]);
		 CrossRoadID_Direct_PassPhase = CrossRoadID_Direct_PassPhase+Passed_CrossRoads[i]+"0"+Direct_PassPhase[0]+Direct_PassPhase[1];
		 
		 }		
			    
	    command="5F88"+BusLineID+BusLineName+StrGoBack[goback]+Hex_CrossRoads_counts+CrossRoadID_Direct_PassPhase;
	    command=command.toUpperCase();
	    	    
	    return command;
	    		
	}
   
   public static ResultSet GetRemoteDBResultSet(String driver,String connect,String userid,String password,String queryString){
	   
	   try {
	 	  Class.forName(driver);
	      //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	      Connection con= DriverManager.getConnection(connect,userid,password);
	      //System.out.println("connected");
	     
	      /*
	      System.out.println(" enter ID  ");
	  	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
	  	a = (String) br.readLine();
	  	*/
	      
	      Statement statement = con.createStatement();  // before creating a query
	      //String queryString = "select * from [taipei_db].[dbo].[TPCOMPARELOG] where [DEVICEID]='S"+a+"01'";
	      //String queryString = "SELECT GROUPID FROM [taipei_db].[dbo].[GRPDEVICE] where DEVICEID='S580601';";
	      ResultSet rs = statement.executeQuery(queryString);
	      
	      ResultSetMetaData metaDt = rs.getMetaData();
	      int cols = metaDt.getColumnCount();
	     

	     
	      //System.out.println("Total rows "+size);
	      return rs;
	   } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	   }
			   	   
	   
	}
   
   

   public static void main(String[] args)
   {
      MSDB mssql=new MSDB();
     String[] tt= getAllMasters_IP();
     
     for(String t:tt)
         System.out.println(t);
      
    //System.out.println("/"+"127.0.0.1");
      
      //String deviceid="S021401";
      //System.out.println("DB connection "+dbConnection());
      /*
      int effected=dbUpdate("com.microsoft.jdbc.sqlserver.SQLServerDriver","jdbc:microsoft:sqlserver://10.122.1.38:1433;DatabaseName="+DB_database_name+""
    		  ,"sa","ceciits"," CREATE TABLE [dbo].[SubPhaseLog]("
    		  		+ "[CrossRoadID] [nvarchar](6) NULL,[SubPhaseID] [nvarchar](4) NULL,[TimeStarted] [datetime] NULL,"
    		  		+ "[SubPhaseInterval] [nvarchar](4) NULL,"
    		  		+ "[SubPhaseOriginal] [nvarchar](4) NULL,[Changed] [nvarchar](4) NULL)");
      System.out.println("Effected "+effected);
      */
      /*
      String[] temp=getTrigCrossRoadLatLon("0001","2");
      
      for(String t:temp)
      System.out.println(t);
      
      String[] a=GPStoByte("5.33");
      
      for(String x:a)
    	  System.out.println(x);
      
      byte[] tip=Protocol.hexStringToByteArray(a[0]);
      byte[] toe=Protocol.hexStringToByteArray(a[1]);
      
      BytetoGPS(tip);
      BytetoGPS(toe);
      
      getTriggerCount("0001","2");
      
      String[] z=getTrigID("0001","2");
      
      for(String c:z)
    	  System.out.println(c);
      
       z=getTrigLatLon("0001","2", 2);
      
       for(String c:z)
     	  System.out.println(c);
       */
      
      /*
      String[] path= getFilePath();
      for(String a:path)
    	  System.out.println(a);
            
      System.out.println(fivef88("0001", 0));
        */
      
       /*
       cmd=fivef82("0001", "1");
       MessageCreator ss=new MessageCreator("01","00A0", cmd);
       System.out.println(Protocol.bytesToHex(ss.create()));
       */
       
      
      // System.out.println(insertBusData());
      // BusCSV("T001","0");
      
      
       /*
       try{
    
       ResultSet myResultSet = dbGetResult("SELECT * FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab]");
       //Vector temp =dbGetString("SELECT * FROM ["+DB_database_name+"].[dbo].[BusPriority_TriggerPoint_Tab]");
       System.out.println(myResultSet);
       //convertToCsv(myResultSet,"Strick.csv");     
       convertToCsv(myResultSet,"C://"+DB_database_name+".csv");   
       
       
       }catch(Exception e){
    	   e.printStackTrace();
       }
       */
       
      //System.out.println(fivef17(deviceid,9));
      
      /*setCompareClock("14:00",1);
      String[] h=getCompareClock();
      for(String x:h)
          System.out.println(x);
      /*
      int[] tpcfggreen=downloadGREENTIME("S042201" ,"13");
       int[] tpcfg= downloadTPCFG("S021501" ,"13");
       System.out.print("TPCFG ");
       for(int i=0;i<tpcfg.length;i++)
     	  System.out.print(tpcfg[i]+" ");
       
       System.out.println();
       
       System.out.print("TPCFGREEN ");
       for(int i=0;i<tpcfggreen.length;i++)
      	  System.out.print(tpcfggreen[i]+" ");
     
       System.out.println();
      String plan="1";
      System.out.println(getGroupid(deviceid));
      
      int[] temp = downloadTPBASIC(deviceid, plan);
		
		System.out.println("TPBASIC ");
		for(int i =0; i< temp.length;i++)
		System.out.print(temp[i]+" ");
     
      
      int[] g=getGroupWeekSegmenttype(getGroupid(deviceid));
      for(int i=0;i<g.length;i++)
    	  System.out.print(g[i]+" ");
     
      System.out.println(" ");
      
     for(int j=0;j<g.length;j++){ 
     String[] h = getSegmentPlanid( getGroupid(deviceid),g[j]);  
      for(int i=0;i<h.length;i++)
    	  System.out.print(h[i]+" ");
     
      System.out.println(" ");
     }
     
   
     
     String[] s=getSpecialDaySegmenttype("GS0063");
     for(int h=0;h<s.length;h++){
    	 
    	 if(s[h].length()<3){
    		 int o=Integer.parseInt(s[h]);
    		 System.out.println(o);
    	 }
    	 else
    	 System.out.println(s[h]);
     }
     
     System.out.println(intTimeString(12,40));
     
     int[] sect= timetoint("0900");
     System.out.println(sect[0]+" "+sect[1]);
     
     for(int q=0;q<7;q++){
     String yyy= fivef16(deviceid,q);
     System.out.println(yyy);
     }
     
     byte[] command=Protocol.hexStringToByteArray("5FC601050000010600020700030A0004170003020607");
     System.out.println(insertSegType("GGGGG", command));
     byte[] command6=Protocol.hexStringToByteArray("5FC605050000010600020700030A0004170003020607");
     System.out.println(insertSegType("GGGGG", command6));
     
     byte[] command2=Protocol.hexStringToByteArray("5FC709070000050100060500010700030900071300031700055C0B065C0B08");
     System.out.println(insertSpecSegType("GGGGG",command2));
     //deleteSpecSegType("GGGGG",9);
     
     int[] specseg=getSpecSegmenttype("GGGGG");
     
     

     System.out.println("new "+fivef14("S035901","32"));
     
     byte[] command3=Protocol.hexStringToByteArray("5FC401030A00D2040207030000C8030200000A00D203030703");
     System.out.println(insertTPbasic("GGGGG",command3));
     
     System.out.println(fivef15("S042201","06"));
     
     byte[] command4=Protocol.hexStringToByteArray("5FC5000074032F0F2A6832");
     
     
     System.out.println(insertTPCFG("GGGGG", command4));
     System.out.println(insertTPbasicGreen("GGGGG", command4));
     
     
     updateTPCompareLog("GGGGG","0A",false,"FFFFFFFFF",1,1);

     //System.out.println(fivef15("S021501","08"));
     
      
      byte[] command10=Protocol.hexStringToByteArray("5FC708070000050100060500010700030900071300031700055C0B025C0B02");
      System.out.println(insertSpecSegType("GA0101", command10));
     */
   }
   
}