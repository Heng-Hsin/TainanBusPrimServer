import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class CrossRoadCenter_Sync {
	
	//[BusPriority_TriggerPoint_Tab]
	//[BusLine_Info_Tab]
	//[BusStrategy_Set]
	//[BusPrority_CrossRoad_Info_Tab]
	public static void DataVerify_start(){
		
		try{
			/*
			 System.out.println(" DB Name "+Server_UI.DB_database_name);
			 System.out.println(" DB Driver "+Server_UI.DBDriver);
			 System.out.println(" localDBIP "+Server_UI.localDBIP);
			 System.out.println(" Local DB_userid "+Server_UI.DB_userid);
			 System.out.println(" Local DB_password "+Server_UI.DB_password);
			 
			 System.out.println(" CrossRoadDB_userid "+Server_UI.CrossRoadDB_userid);
			 System.out.println(" CrossRoadDB_password "+Server_UI.CrossRoadDB_password);
			 */
			
			 String[] MasterIP =Server_UI.MasterIP;
			 String DB_name=Server_UI.DB_database_name;
			 String DB_driver=Server_UI.DBDriver;
			 
			 String MainDB_account=Server_UI.DB_userid;
			 String MainDB_password=Server_UI.DB_password;
			 
			 String RemoteDB_account=Server_UI.CrossRoadDB_userid;
			 String RemoteDB_password=Server_UI.CrossRoadDB_password;
			 
			 String MainDB_IP=Server_UI.localDBIP;
			 String DB_connect="jdbc:microsoft:sqlserver://XXXX:1433;DatabaseName="+DB_name;
			 
			 
			for(String IP:MasterIP){
				
				String GroupID=MSDB.getGroupID_fromIP(IP);
				System.out.println(" Master IP "+IP+" GroupID "+GroupID);
				 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(GroupID);
				 
				 String  Temp_connect=DB_connect.replace("XXXX", IP);					
				 
				 boolean remoteconnection=MSDB.dbConnection(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password);
				 
				 for(String CrossRoadID: CrossRoadAddr){
					 		 
					 
					 if(remoteconnection){
						// String R_BusLineID,BusLine_Name,CrossRoadID,BusLine_Order,GoBack,Direct,BusSubPhaseID,PlanID;
						System.out.println(" CrossRoad ID "+CrossRoadID+" Connection ok ");
						
						 String query="SELECT [BusLineID],[BusLine_Name],[CrossRoadID],[BusLine_Order],[GoBack],[Direct],[BusSubPhaseID],[PlanID]FROM [TainanBusPrim].[dbo].[BusLine_Info_Tab] where [CrossRoadID]='"+CrossRoadAddr+"'";
						 ResultSet remotedata = MSDB.GetRemoteDBResultSet(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password,query);
						 ResultSetMetaData rsmd = remotedata.getMetaData();
						 int columnsNumber = rsmd.getColumnCount();
						 System.out.println(" columnsNumber "+columnsNumber);
						 String[] columnheaders=new String[columnsNumber];
						   
						   for(int i=0;i<columnsNumber;i++){
							   System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   columnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   remotedata.first();		
						   //int row = remotedata.getRow();
						   //System.out.println(" ResultSet row "+row);
						   
						  
						 while(remotedata.next()){	
							 
					    	  for(String a:columnheaders){					    		  
					    		  String str_data = remotedata.getString(a);
					    		  System.out.println(" "+a+" : " + str_data);
					    		  
					    	  }

							 
						 }
						 
						 Thread.sleep(1000);
					 }else{
						 
						 System.out.println(CrossRoadID+" Connection Fail");
					 }
					 
					 Thread.sleep(1000);
				 }
				
			}
		}catch(Exception e){
			System.out.println("Error in DataVerify_start "+e.getMessage());
		}
				
		
	}
	
	
	
	
	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
