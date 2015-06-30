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

public class DB_Updater {
	
	private static String DBDriver;
	private static String DB_connect_string;
	private static String DB_userid;
	private static String DB_password;
	
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
	
	   public static int dbUpdate(String queryString){
		   Connection con =null;
	      try {
	    	  Class.forName(DBDriver);
	     
	         con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
	                
	         Statement statement = con.createStatement();  // before creating a query
	        
	         try{
	          statement.executeUpdate(queryString);
	          int effect=statement.getUpdateCount();
	        
	          con.close();
	        return effect;  
	        
	        }
	         catch (Exception e) {
	             e.printStackTrace();
	             con.close();
	             return 0;
	          }
	         
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	         try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         return 0;
	      }
	     
	   }
	   
	   public static Vector dbGetString(String queryString){
		   Connection con =null;
		   try {
	    	  Class.forName(DBDriver);
	         //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	          con= DriverManager.getConnection(DB_connect_string,DB_userid,DB_password);
	         //System.out.println("connected");
	               	         
	         Statement statement = con.createStatement(); 
	         ResultSet rs = statement.executeQuery(queryString);
	         
	         ResultSetMetaData metaDt = rs.getMetaData();
	         int cols = metaDt.getColumnCount();
	         //System.out.println("total columns " +cols);	// number of columns
	         
	         Vector columnNames = new Vector();	         
	         Vector tpdata = new Vector();
	         
	         //System.out.println(tpdata);
	         //Vector<Integer> tpdata = new Vector<Integer>();
	         
	         for(int i=1;i<=cols;i++){			// get the column names
	             columnNames.addElement (metaDt.getColumnName(i));
	             //System.out.println(metaDt.getColumnName(i));
	         }
	         
	         if(!rs.next()){
	    		 System.out.println("µL¸ê®Æ");  
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
	         	con.close();
	         return tpdata;
	      } catch (Exception e) {
	         e.printStackTrace();
	         try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         return null;
	      }	   	      
	   }
	   
	   public static String[] get_HISTORYTIMINGPLANBASICTAB_PLANID(String DEVICEID){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT PLANID from HISTORYTIMINGPLANBASICTAB "
			  		+ "where DEVICEID ='"+DEVICEID+"' order by PLANID" );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("get_HISTORYTIMINGPLANBASICTAB_PLANID");
				return null;
			}
			}
	   
	   public static String[] get_HISTORYTIMINGPLANCFGTAB_PLANID(String DEVICEID){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT PLANID from HISTORYTIMINGPLANCFGTAB where "
			  		+ "DEVICEID ='"+DEVICEID+"' ORDER BY PLANID" );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("get_HISTORYTIMINGPLANCFGTAB_PLANID");
				return null;
			}
			}
	   
	   public static String[] get_HISTORYTODWEEKDAYTAB_SEGMENTTYPE(String DEVICEID){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT SEGMENTTYPE from HISTORYTODWEEKDAYTAB WHERE DEVICEID ='"+DEVICEID+"' "
			  		+ "order by SEGMENTTYPE " );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("Error in get_HISTORYTODWEEKDAYTAB_SEGMENTTYPE");
				return null;
			}
			}
	   
	   public static String[] get_HISTORYTODWEEKDAYTAB_WEEKDAY(String DEVICEID,String SEGMENTTYPE){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT WEEKDAY from HISTORYTODWEEKDAYTAB WHERE "
			  		+ "DEVICEID ='"+DEVICEID+"' AND SEGMENTTYPE ='"+SEGMENTTYPE+"' order by WEEKDAY" );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("ERROR IN get_HISTORYTODWEEKDAYTAB_WEEKDAY");
				return null;
			}
			}
	   	  	   
	   public static String[] get_HISTORYTODTAB_timeplan(String DEVICEID,String SEGMENTTYPE, String SEGMENT){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select TIME,PLANID  from HISTORYTODTAB WHERE DEVICEID ='"+DEVICEID+"' AND "
			  		+ "SEGMENTTYPE ='"+SEGMENTTYPE+"' AND SEGMENT ='"+SEGMENT+"'");      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("ERROR IN get_HISTORYTODTAB_SEGMENT");
				return null;
			}
			}
	   
	   public static String[] get_HISTORYTIMINGPLANBASICTAB_PHASEID(String DEVICEID,String PLANID){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT PHASEID from HISTORYTIMINGPLANBASICTAB  where "
			  		+ "DEVICEID ='"+DEVICEID+"' and PLANID ='"+PLANID+"' order BY PHASEID" );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("get_HISTORYTIMINGPLANBASICTAB_PLANID");
				return null;
			}
			}
	   
	   public static String[] get_HISTORYTODTAB_SEGMENT(String DEVICEID,String SEGMENTTYPE){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select DISTINCT SEGMENT from HISTORYTODTAB WHERE "
			  		+ "DEVICEID ='"+DEVICEID+"' AND SEGMENTTYPE ='"+SEGMENTTYPE+"' ORDER BY SEGMENT" );      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("get_HISTORYTIMINGPLANBASICTAB_PLANID");
				return null;
			}
			}
	   
	   public static String[] get_PLANBASIC_data(String DEVICEID,String PLANID,String PHASEID, String Time){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select * from  HISTORYTIMINGPLANBASICTAB where DEVICEID = '"+DEVICEID+"' "
			  		+ "and PLANID ='"+PLANID+"' and PHASEID='"+PHASEID+"' and MODTIME = to_date('"+Time+"','yyyy-MM-dd hh24:mi:ss')" );      
			  
			 String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		    			    	
		  	x[i]= (String) a.get(i);
		  	
		  	if(x[i] != null)
		  	x[i] = x[i].replace(".0", ""); 	   
		  	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("Error in get_EVERYTHING_PLAN");
				return null;
			}
			}
	   
	   public static String[] get_PLANCFG_data(String DEVICEID,String PLANID, String Time){  
			//select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB where DEVICEID ='S109900' AND PLANID ='01'
		   try{
			  Vector a = dbGetString("select * from  HISTORYTIMINGPLANCFGTAB where DEVICEID = '"+DEVICEID+"' "
			  		+ "and PLANID ='"+PLANID+"' and MODTIME = to_date('"+Time+"','yyyy-MM-dd hh24:mi:ss')" );      
			  
			 String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		    			    	
		  	x[i]= (String) a.get(i);
		  	
		  	if(x[i] != null)
		  	x[i] = x[i].replace(".0", ""); 	   
		  	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("Error in get_PLANCFG_data");
				return null;
			}
			}
	   
	   public static String[] get_TOD_data(String DEVICEID,String SEGMENTTYPE,String SEGMENT, String TIME){  
			
		   try{
			  Vector a = dbGetString("select TIME ,PLANID FROM HISTORYTODTAB WHERE DEVICEID='"+DEVICEID+"' "
			  		+ "AND SEGMENTTYPE='"+SEGMENTTYPE+"' AND SEGMENT='"+SEGMENT+"' AND "
			  		+ "MODTIME = to_date('"+TIME+"','yyyy-MM-dd hh24:mi:ss')" );      
			  
			 String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		    			    	
		  	x[i]= (String) a.get(i);
		  	
		  	if(x[i] != null)
		  	x[i] = x[i].replace(".0", ""); 	   
		  	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println("Error in get_PLANCFG_data");
				return null;
			}
			}
	   
	 	   
	   public static int update_PLANBASIC_data(String[] data){
		   int a=0;	
		   
		   try{
		   		//[DEVICEID],[PLANID],[PHASEID],[MAXGREEN],[MINGREEN],[YELLOW],[ALLRED],[PEDFLASH],[PEDRED]
			    //,[GREENTIME],[SERIALID],[STATUS],[MODTIME]
			  
			   
			 a =dbUpdate("UPDATE [TaoyuanBusPrim].[dbo].[TrafficSignal_Plan_Info_Tab] SET [MAXGREEN]='"+data[3]+"', [MINGREEN] ='"+data[4]+"', "
			 		+ "[YELLOW]='"+data[5]+"', [ALLRED]='"+data[6]+"',[PEDFLASH]='"+data[7]+"', [PEDRED]='"+data[8]+"' ,[GREENTIME]='"+data[9]+"',[SERIALID]='"+data[10]+"' "
			 		+ ",[STATUS] ='"+data[11]+"' ,[MODTIME] ='"+data[12]+"'  WHERE [DEVICEID] ='"+data[0]+"' and [PLANID]='"+data[1]+"' and [PHASEID] ='"+data[2]+"'");
			
			 
			 if(a==0){
			 a =a+dbUpdate("INSERT into [TaoyuanBusPrim].[dbo].[TrafficSignal_Plan_Info_Tab] ([DEVICEID],"
			 		+ "[PLANID],[PHASEID],[MAXGREEN],[MINGREEN],[YELLOW],[ALLRED],[PEDFLASH],[PEDRED],[GREENTIME],"
			 		+ "[SERIALID],[STATUS],[MODTIME])VALUES('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+
			 		"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"','"+data[12]+"')");
			 }
			 
			 
			 return a;
			 }catch(Exception e){
				 e.printStackTrace();
				 System.out.println("Error in update_PLANBASIC_data");
				 
			 }
		   return 0;
		}
	   
	   public static int update_PLANCFG_data(String[] data){
		   int a=0;	
		   
		   try{
			 
			   
			 a =dbUpdate("UPDATE [TaoyuanBusPrim].[dbo].[TrafficSignal_Plan_Cfg_Tab] SET [PHASEORDER]='"+data[2]+"', [CYCLETIME] ='"+data[3]+"', "
			 		+ "[OFFSET]='"+data[4]+"', [BASEDIRECTION]='"+data[5]+"',[PHASECOUNT]='"+data[6]+"', [STATUS]='"+data[7]+"' ,[SERIALID]='"+data[8]+"',[EDITOR]='"+data[9]+"' "
			 		+ ",[DESCRIPTION] ='"+data[10]+"' ,[MODTIME] ='"+data[11]+"'  WHERE [DEVICEID] ='"+data[0]+"' and [PLANID]='"+data[1]+"'");
			
			 
			 if(a==0){
			 a =a+dbUpdate("INSERT into [TaoyuanBusPrim].[dbo].[TrafficSignal_Plan_Cfg_Tab] ([DEVICEID],"
			 		+ "[PLANID],[PHASEORDER],[CYCLETIME],[OFFSET],[BASEDIRECTION],[PHASECOUNT],[STATUS],[SERIALID],[EDITOR],"
			 		+ "[DESCRIPTION],[MODTIME])VALUES('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+
			 		"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"','"+data[11]+"')");
			 }
			 
			 
			 return a;
			 }catch(Exception e){
				 e.printStackTrace();
				 System.out.println("Error in update_PLANCFG_data");
				 
			 }
		   return 0;
		}
	   
	   public static int update__Segment_Common_data(String[] data){
		   int a=0;	
		   
		   try{
			   
			  
			   
			   
			   /*
			 a =dbUpdate("UPDATE [TaoyuanBusPrim].[dbo].[TrafficSingal_Segment_Common_Tab] SET [Hour]='"+data[2]+"', [Min] ='"+data[3]+"', " 
			 		+ "[PlanID]='"+data[4]+"', [NumWeekDay]='"+data[5]+"',[WeekDay]='"+data[6]+"', [BusPrimEnable]='"+data[7]+"' ,[Changed]='"+data[8]+"'"
			 		+"', [Updated_Time]='"+data[9]+"'   WHERE [CrossRoadID] ='"+data[0]+"' and [SegmentType]='"+data[1]+"'");
			 if(a==0){
			 */
				 
			 a =a+dbUpdate("INSERT into [TaoyuanBusPrim].[dbo].[TrafficSingal_Segment_Common_Tab] ([CrossRoadID],"
			 		+ "[SegmentType],[Hour],[Min],[PlanID],[NumWeekDay],[WeekDay],[BusPrimEnable],[Changed],[Updated_Time]"
			 		+ ")VALUES('"+data[0]+"','"+data[1]+"','"+data[2]+"','"+data[3]+"','"+data[4]+"','"+data[5]+
			 		"','"+data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"')");
			 //}
			 
			 
			 return a;
			 }catch(Exception e){
				 e.printStackTrace();
				 System.out.println("Error in update_PLANCFG_data");
				 
			 }
		   return 0;
		}
	   
	   public static int delete_Segment_Common_data(String CrossRoadID, String SegmentType){
		   int a=0;	
		   
		   try{  				 
			 a =a+dbUpdate("delete  FROM [TaoyuanBusPrim].[dbo].[TrafficSingal_Segment_Common_Tab] "
			 		+ "where [CrossRoadID]='"+CrossRoadID+"' and [SegmentType]='"+SegmentType+"'");
			
			 
			 
			 return a;
			 }catch(Exception e){
				 e.printStackTrace();
				 System.out.println("Error in update_PLANCFG_data");
				 
			 }
		   return 0;
		}
	   
	   public static String[] get_HISTORYTIMINGPLANBASICTAB_DEVICEID(){  //needs to change into a device list
			
		   try{
			  Vector a = dbGetString("select DISTINCT DEVICEID from HISTORYTIMINGPLANBASICTAB ");      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println(" Error in get_HISTORYTIMINGPLANBASICTAB_DEVICEID ");
				return null;
			}
			}
	   
	   
	   
	   public static String[] get_HISTORYTIMINGPLANCFGTAB_DEVICEID(){  //needs to change into a device list
			
		   try{
			  Vector a = dbGetString("select DISTINCT DEVICEID from HISTORYTIMINGPLANCFGTAB ");      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println(" Error in get_HISTORYTIMINGPLANCFGTAB_DEVICEID ");
				return null;
			}
			}
	   
	   
	   public static String[] get_TOD_DEVICEID(){  //needs to change into a device list
			
		   try{
			  Vector a = dbGetString("SELECT DEVICEID FROM HISTORYTODTAB INTERSECT SELECT DEVICEID "
			  		+ "FROM HISTORYTODWEEKDAYTAB");      
			  
			  String[] x= new String[a.size()];    
		    for(int i=0;i<a.size();i++){
		  	x[i]= (String) a.get(i);
		  	    	    	
		  	  //System.out.println(x[i]);
		    }
		    return x;
			}catch(Exception e){
				System.out.println(" Error in get_HISTORYTIMINGPLANCFGTAB_DEVICEID ");
				return null;
			}
			}
	   
	   public static String get_Latest_HISTORYTIMINGPLANBASICTAB_row(String DEVICEID , String PLANID){
			String time="";
			try{
			
			
			Vector a =dbGetString("select  max(MODTIME) from HISTORYTIMINGPLANBASICTAB "
					+ "where DEVICEID ='"+DEVICEID+"' AND PLANID ='"+PLANID+"'");
			
			//time= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
			time= a.toString().replace("[", "").replace("]", "").replace(".0", "");    
			return time;
			}catch(Exception e){
				System.out.println("Error in get_Latest_HISTORYTIMINGPLANBASICTAB_row");
				return null;
			}
		}
	   
	   public static String get_Latest_HISTORYTIMINGPLANCFGTAB_row(String DEVICEID , String PLANID){
			String time="";
			try{
			
			
			Vector a =dbGetString("select  max(MODTIME) from HISTORYTIMINGPLANCFGTAB "
					+ "where DEVICEID ='"+DEVICEID+"' AND PLANID ='"+PLANID+"'");
			
			//time= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
			time= a.toString().replace("[", "").replace("]", "").replace(".0", "");    
			return time;
			}catch(Exception e){
				System.out.println("Error in get_Latest_HISTORYTIMINGPLANCFGTAB_row");
				return null;
			}
		}
	   
	   public static String get_Latest_HISTORYTODTAB_row(String DEVICEID , String SEGMENTTYPE,String SEGMENT){
			String time="";
			try{
						
			Vector a =dbGetString("select max(MODTIME) from HISTORYTODTAB WHERE DEVICEID ='"+DEVICEID+"' "
					+ "AND SEGMENTTYPE ='"+SEGMENTTYPE+"' AND SEGMENT ='"+SEGMENT+"'");
			
			//time= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
			time= a.toString().replace("[", "").replace("]", "").replace(".0", "");    
			return time;
			}catch(Exception e){
				System.out.println("Error in get_Latest_HISTORYTODTAB_row");
				return null;
			}
		}
	   
	   public static boolean Copy_HISTORYTIMINGPLANBASICTAB(){
			
			try{
				Switch2Oracle();
				String[] devices=get_HISTORYTIMINGPLANBASICTAB_DEVICEID();
				for(int i=0;i<devices.length;i++){
				String[] planid= get_HISTORYTIMINGPLANBASICTAB_PLANID(devices[i]);
							
							for(int j=0;j<planid.length;j++){				
							String[] phases = get_HISTORYTIMINGPLANBASICTAB_PHASEID(devices[i],planid[j]);	
							String time=get_Latest_HISTORYTIMINGPLANBASICTAB_row(devices[i],planid[j]);
							
									for(int k=0;k<phases.length;k++){
									String[] data = get_PLANBASIC_data(devices[i],planid[j],phases[k],time); 	
									Switch2MS();	
									update_PLANBASIC_data(data);
									Switch2Oracle();
									Thread.sleep(100);									
									}
							}
				}
			
				return true;
			}catch(Exception e){
				System.out.println("Error in get_Latest_HISTORYTIMINGPLANBASICTAB_row");
				return false;
			}
		}
	   
	   public static boolean Copy_HISTORYTIMINGPLANCFGTAB(){
			
			try{
				Switch2Oracle();
				String[] devices=get_HISTORYTIMINGPLANCFGTAB_DEVICEID();
				for(int i=0;i<devices.length;i++){
				String[] planid= get_HISTORYTIMINGPLANCFGTAB_PLANID(devices[i]);
							
							for(int j=0;j<planid.length;j++){				
							
							String time=get_Latest_HISTORYTIMINGPLANCFGTAB_row(devices[i],planid[j]);
																
									String[] data = get_PLANCFG_data(devices[i],planid[j],time); 	
									Switch2MS();	
									update_PLANCFG_data(data);
									Switch2Oracle();
									Thread.sleep(100);
							}
				}
			
				return true;
			}catch(Exception e){
				System.out.println("Error in get_Latest_HISTORYTIMINGPLANBASICTAB_row");
				return false;
			}
		}
	   
	   public static boolean Copy_TOD(){
			
			try{
				Switch2Oracle();
				String[] devices=get_TOD_DEVICEID();
				
				for(int i=0;i<devices.length;i++){
				String[] segmenttype= get_HISTORYTODWEEKDAYTAB_SEGMENTTYPE(devices[i]);
							
							for(int j=0;j<segmenttype.length;j++){				
								//delete_Segment_Common_data(devices[i], segmenttype[j]);
								String[] weekday = get_HISTORYTODWEEKDAYTAB_WEEKDAY(devices[i],segmenttype[j]);
								String numWeek="0"+weekday.length;
								String Week="";
								String Bus="";
								for(String b:weekday){
									Week =Week+"0"+b;
									Bus=Bus+"00";
								}
								String[] seg =  get_HISTORYTODTAB_SEGMENT(devices[i],segmenttype[j]); 
								
										for(int k=0;k<seg.length;k++){																			
									
									String time = get_Latest_HISTORYTODTAB_row(devices[i],segmenttype[j],seg[k]);
																		
									String[] part_data=get_TOD_data(devices[i],segmenttype[j],seg[k], time); //time planid
									
									String Hour = part_data[0].substring(0,2);
									String Min = part_data[0].substring(2,4);
									
									String planid = MSDB.DexStr2HexStr(part_data[1]);
									String segmenttype2 ="0"+segmenttype[j];
																			
											Switch2MS();	
											String data[]={devices[i],segmenttype2,Hour,Min,planid,numWeek,Week,Bus,"3",time};
											update__Segment_Common_data(data);
											
											
											Switch2Oracle();
											Thread.sleep(100);
										}
							}
				}
			
				return true;
			}catch(Exception e){
				System.out.println("Error in Copy_TOD");
				e.printStackTrace();
				return false;
			}
		}
	   
	   
	   public static String get_Latest_TrafficSingal_Segment_Common_Tab(String DEVICEID , String SEGMENTTYPE){
			String time="";
			try{
						
			Vector a =dbGetString("SELECT MAX([Updated_Time]) FROM [TaoyuanBusPrim].[dbo].[TrafficSingal_Segment_Common_Tab]"
					+ " where [CrossRoadID]='"+DEVICEID+"' and [SegmentType]='"+SEGMENTTYPE+"'");
			
			//time= a.toString().replace("[", "").replace("]", "").replace(" ", "");    
			time= a.toString().replace("[", "").replace("]", "").replace(".0", "");    
			return time;
			}catch(Exception e){
				System.out.println("Error in get_Latest_TrafficSingal_Segment_Common_Tab");
				return null;
			}
		}
	   
	   
	   
	   public static boolean Switch2Oracle() {
		   		   
		   try{
			   
			   DBDriver = ORACLE_Driver;
			   DB_connect_string = ORACLE_connect_string;
			   DB_userid = ORACLE_userid;
			   DB_password = ORACLE_password;
			   
			   return true;
		   }catch(Exception e){			   
			   e.printStackTrace();
			   System.out.println("Error in Switch2Oracle");
			   return false;			  
		   }
		   
		   
	   }
	   
	   public static boolean Switch2MS() {
   		   
		   try{
			   
			   DBDriver = MSSQL_Driver;
			   DB_connect_string = MSSQL_connect_string;
			   DB_userid = MSSQL_userid;
			   DB_password = MSSQL_password;
			   
			   return true;
		   }catch(Exception e){			   
			   e.printStackTrace();
			   System.out.println("Error in Switch2MS");
			   return false;			  
		   }
		   
		   
	   }
	
	   public static boolean Properties() {
		  
		   
		     Properties prop = new Properties();
			 InputStream input = null;
			 
				try {
					 					 
					input = new FileInputStream("C://BusPrime.properties");
								
					prop.load(input);
			 									                		                			                
			                MSSQL_Driver=prop.getProperty("MSSQL_Driver");
			                MSSQL_connect_string=prop.getProperty("MSSQL_connect_string");
			                MSSQL_userid=prop.getProperty("MSSQL_userid");
			                MSSQL_password=prop.getProperty("MSSQL_password");	                	               
			                
			                ORACLE_Driver=prop.getProperty("ORACLE_Driver");
			                ORACLE_connect_string=prop.getProperty("ORACLE_connect_string");
			                ORACLE_userid=prop.getProperty("ORACLE_userid");
			                ORACLE_password=prop.getProperty("ORACLE_password");
			                	                	          	    			
			    			System.out.println(prop.getProperty("MSSQL_Driver"));
			    			System.out.println(prop.getProperty("MSSQL_connect_string"));
			    			System.out.println(prop.getProperty("MSSQL_userid"));
			    			System.out.println(prop.getProperty("MSSQL_password"));
			    			
			    			System.out.println(prop.getProperty("ORACLE_Driver"));
			    			System.out.println(prop.getProperty("ORACLE_connect_string"));
			    			System.out.println(prop.getProperty("ORACLE_userid"));
			    			System.out.println(prop.getProperty("ORACLE_password"));
			    			
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
	
	   	   	   
	 public static void main(final String args[]) {
		 
		 
		 Properties();
		 
	while (true){		
				 
		try {
			/*
			  
			Switch2Oracle();
			
			
			String[] segtype = get_HISTORYTODWEEKDAYTAB_SEGMENTTYPE("S401500");
			System.out.println("Type");
			for(String a:segtype)
				System.out.println(a);
			
			String[] weekday = get_HISTORYTODWEEKDAYTAB_WEEKDAY("S401500","1");
			String numWeek="0"+weekday.length;
			System.out.println("numWeek "+numWeek);
			
			
			String Week="";
			for(String b:weekday)
				Week =Week+"0"+b;
			
			System.out.println("Week "+Week);
								
			
			System.out.println("Seg");
			
			String[] seg =  get_HISTORYTODTAB_SEGMENT("S401500","1");  
			
			for(String g:seg)
				System.out.println(g);
			
			String time= get_Latest_HISTORYTODTAB_row("S903600","1","1");
			
			System.out.println(time);
			
			String[] data=get_TOD_data("S903600","1","1", "2010-02-25 11:00:00");
			
			for(String z:data)
				System.out.println(z);
			*/
			
			//System.out.println(Copy_TOD());
			
			System.out.println(Copy_HISTORYTIMINGPLANCFGTAB());															
			System.out.println(Copy_HISTORYTIMINGPLANBASICTAB());
									
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Error in sql Updater");
		}
				
		
		try {
			System.out.println(" Wait for  five minutes");
			Thread.sleep(180000);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	 }
	

}
