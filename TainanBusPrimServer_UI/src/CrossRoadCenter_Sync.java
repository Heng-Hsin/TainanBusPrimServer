import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;


public class CrossRoadCenter_Sync {
	
	//[BusPriority_TriggerPoint_Tab]
	//[BusLine_Info_Tab]
	//[BusStrategy_Set]
	//[BusPrority_CrossRoad_Info_Tab]
	
	public static void BusLine_Info_Tab_DataVerify_start(){
		
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
			 
			 String Comparing_TableName ="BusLine_Info_Tab";
			 int data_perRow =8;
			 
			for(String IP:MasterIP){
				
				String GroupID=MSDB.getGroupID_fromIP(IP);
				System.out.println(" Master IP "+IP+" GroupID "+GroupID);
				 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(GroupID);
				 
				 String  Temp_connect=DB_connect.replace("XXXX", IP);	
				 String  center_connect=DB_connect.replace("XXXX", MainDB_IP);
				 
				 boolean remoteconnection=MSDB.dbConnection(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password);
				 
				 for(String CrossRoadID: CrossRoadAddr){
					 		 
					 
					 if(remoteconnection){
						// String R_BusLineID,BusLine_Name,CrossRoadID,BusLine_Order,GoBack,Direct,BusSubPhaseID,PlanID;
						//System.out.println(" CrossRoad ID "+CrossRoadID+" Connection ok ");
						
						 String query="SELECT [BusLineID],[BusLine_Name],[CrossRoadID],[BusLine_Order],[GoBack],[Direct],[BusSubPhaseID],[PlanID]FROM [TainanBusPrim].[dbo].[BusLine_Info_Tab] where [CrossRoadID]='"+CrossRoadID+"' order by [BusLineID]";
						 
						 //System.out.println(CrossRoadID+" Query "+query);
						 Vector remoteVector = new Vector();
						 Vector centerVector = new Vector();
						 
						 ResultSet remotedata = MSDB.GetRemoteDBResultSet(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password,query);
						 ResultSet centerdata = MSDB.GetRemoteDBResultSet(DB_driver,center_connect,MainDB_account,MainDB_password,query);
						 
						 ResultSetMetaData Centersmd = centerdata.getMetaData();
						 ResultSetMetaData rsmd = remotedata.getMetaData();
						 int columnsNumber = rsmd.getColumnCount();
						 int centercolumnsNumber = Centersmd.getColumnCount();
						 
						 //System.out.println(" columnsNumber "+columnsNumber);
						 String[] columnheaders=new String[columnsNumber];
						 String[] Centercolumnheaders=new String[centercolumnsNumber];  
						 
						   for(int i=0;i<columnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   columnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   for(int i=0;i<centercolumnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   Centercolumnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   //remotedata.first();		
						   //int row = remotedata.getRow();
						   //System.out.println(" ResultSet row "+row);
						 
						 int remoterowcount=0;
						  while(remotedata.next()){	
							 
					    	  for(String a:columnheaders){					    		  
					    		  String str_data = remotedata.getString(a);
					    		  remoteVector.addElement(a+"_"+str_data);
					    		  //System.out.println(" "+a+" : " + str_data);					    		  
					    	  }
					    	  
					    	  remoterowcount++;					    	  
						 }
						  
						  //System.out.println("***RowCount "+remoterowcount);
						  
							 int centerrowcount=0;
							 while(centerdata.next()){	
								 
						    	  for(String a:Centercolumnheaders){					    		  
						    		  String str_data = centerdata.getString(a);
						    		  centerVector.addElement(a+"_"+str_data);
						    		  //System.out.println(" "+a+" : " + str_data);					    		  
						    	  }
						    	  
						    	  centerrowcount++;					    	  
							 }
							
							 
							  //System.out.println("***RowCount "+centerrowcount);
						  if(remoterowcount==centerrowcount ){
							  //System.out.println( CrossRoadID+" Exact Same Row Count ");							  
							  int centervector_size= centerVector.size();
							  int remotevector_size= remoteVector.size();
							  
							  if(centervector_size==remotevector_size ){
								  
								  int same_count=0;
								  String report="";
								  for(int v=0;v<remotevector_size;v++){
									 
									  if(centerVector.get(v).equals(remoteVector.get(v))){
										  //System.out.println("")
										  same_count++;
									  }else{
										  //System.out.println(CrossRoadID+" Center Data "+centerVector.get(v)+" Remote Data "+remoteVector.get(v));	
										  int rownumber=v/data_perRow;
										  String UnSyncedLine_center="*";
										  String UnSyncedLine_remote="#";
										  
										  for(int k=0;k<data_perRow;k++){
											  int vector_position=rownumber*data_perRow+k;
											  
											  if(k<data_perRow-1){
												  UnSyncedLine_center=UnSyncedLine_center+centerVector.get(vector_position)+",";
												  UnSyncedLine_remote=UnSyncedLine_remote+remoteVector.get(vector_position)+","; 
											  }else{
												  UnSyncedLine_center=UnSyncedLine_center+centerVector.get(vector_position)+"*";
												  UnSyncedLine_remote=UnSyncedLine_remote+remoteVector.get(vector_position)+"#"; 
											  }
											  
										  }
										  
										  report=report+CrossRoadID+" Center Data ( "+centerVector.get(v)+" ) "+UnSyncedLine_center+" Remote Data ( "+remoteVector.get(v)+" ) "+UnSyncedLine_remote+";";
										  
									  }	  									  
								  }
								  
								  if(same_count==remotevector_size){
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Synced","");
								  }else{
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data are not Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced",report);
								  }
								  
							  }else{
								  System.out.println( " IP "+IP+" CrossRoadID "+CrossRoadID+" Vector Size is Different ");	
								  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different");
							  }
							  
						  }else if(remoterowcount>centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Remote has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different RemoteDB has more data");
						  }
						  else if(remoterowcount<centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Center has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different CenterDB has more data");
						  }
							 						  
						 
						 Thread.sleep(1000);
					 }else{
						 
						 System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Connection Fail");
						 MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Failed To Connect");
					 }
					 
					 Thread.sleep(1000);
				 }
				
			}
		}catch(Exception e){
			System.out.println("Error in DataVerify_start "+e.getMessage());
		}
				
		
	}
	
	public static void BusPriority_TriggerPoint_Tab_DataVerify_start(){
		
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
			 
			 String Comparing_TableName ="BusPriority_TriggerPoint_Tab";
			 int data_perRow =9;
			 
			for(String IP:MasterIP){
				
				String GroupID=MSDB.getGroupID_fromIP(IP);
				System.out.println(" Master IP "+IP+" GroupID "+GroupID);
				 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(GroupID);
				 
				 String  Temp_connect=DB_connect.replace("XXXX", IP);	
				 String  center_connect=DB_connect.replace("XXXX", MainDB_IP);
				 
				 boolean remoteconnection=MSDB.dbConnection(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password);
				 
				 for(String CrossRoadID: CrossRoadAddr){
					 		 
					 
					 if(remoteconnection){
						// String R_BusLineID,BusLine_Name,CrossRoadID,BusLine_Order,GoBack,Direct,BusSubPhaseID,PlanID;
						//System.out.println(" CrossRoad ID "+CrossRoadID+" Connection ok ");
						
						 String query="SELECT  [CrossRoadID],[TriggerPointID],[Direct],[TriggerPointOrder],[Lat],[Lon],[PointType],[StopLineLat],[StopLineLon]FROM [TainanBusPrim].[dbo].[BusPriority_TriggerPoint_Tab]where [CrossRoadID]='"+CrossRoadID+"' order by [TriggerPointID],[Direct],[TriggerPointOrder] ";
						 
						 //System.out.println(CrossRoadID+" Query "+query);
						 Vector remoteVector = new Vector();
						 Vector centerVector = new Vector();
						 
						 ResultSet remotedata = MSDB.GetRemoteDBResultSet(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password,query);
						 ResultSet centerdata = MSDB.GetRemoteDBResultSet(DB_driver,center_connect,MainDB_account,MainDB_password,query);
						 
						 ResultSetMetaData Centersmd = centerdata.getMetaData();
						 ResultSetMetaData rsmd = remotedata.getMetaData();
						 int columnsNumber = rsmd.getColumnCount();
						 int centercolumnsNumber = Centersmd.getColumnCount();
						 
						 //System.out.println(" columnsNumber "+columnsNumber);
						 String[] columnheaders=new String[columnsNumber];
						 String[] Centercolumnheaders=new String[centercolumnsNumber];  
						 
						   for(int i=0;i<columnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   columnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   for(int i=0;i<centercolumnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   Centercolumnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   //remotedata.first();		
						   //int row = remotedata.getRow();
						   //System.out.println(" ResultSet row "+row);
						 
						 int remoterowcount=0;
						  while(remotedata.next()){	
							 
					    	  for(String a:columnheaders){					    		  
					    		  String str_data = remotedata.getString(a);
					    		  remoteVector.addElement(a+"_"+str_data);
					    		  //System.out.println(" "+a+" : " + str_data);					    		  
					    	  }
					    	  
					    	  remoterowcount++;					    	  
						 }
						  
						  //System.out.println("***RowCount "+remoterowcount);
						  
							 int centerrowcount=0;
							 while(centerdata.next()){	
								 
						    	  for(String a:Centercolumnheaders){					    		  
						    		  String str_data = centerdata.getString(a);
						    		  centerVector.addElement(a+"_"+str_data);
						    		  //System.out.println(" "+a+" : " + str_data);					    		  
						    	  }
						    	  
						    	  centerrowcount++;					    	  
							 }
							
							 
							  //System.out.println("***RowCount "+centerrowcount);
						  if(remoterowcount==centerrowcount ){
							  //System.out.println( CrossRoadID+" Exact Same Row Count ");							  
							  int centervector_size= centerVector.size();
							  int remotevector_size= remoteVector.size();
							  
							  if(centervector_size==remotevector_size ){
								  
								  int same_count=0;
								  String report="";
								  for(int v=0;v<remotevector_size;v++){
									 
									  if(centerVector.get(v).equals(remoteVector.get(v))){
										  //System.out.println("")
										  same_count++;
									  }else{
										  //System.out.println(CrossRoadID+" Center Data "+centerVector.get(v)+" Remote Data "+remoteVector.get(v));
										  
										  int rownumber=v/data_perRow;
										  String UnSyncedLine_center="*";
										  String UnSyncedLine_remote="#";
										  
										  for(int k=0;k<data_perRow;k++){
											  int vector_position=rownumber*data_perRow+k;
											  
											  if(k<data_perRow-1){
												  UnSyncedLine_center=UnSyncedLine_center+centerVector.get(vector_position)+",";
												  UnSyncedLine_remote=UnSyncedLine_remote+remoteVector.get(vector_position)+","; 
											  }else{
												  UnSyncedLine_center=UnSyncedLine_center+centerVector.get(vector_position)+"*";
												  UnSyncedLine_remote=UnSyncedLine_remote+remoteVector.get(vector_position)+"#"; 
											  }
											  
										  }
										  
										  report=report+CrossRoadID+" Center Data ( "+centerVector.get(v)+" ) "+UnSyncedLine_center+" Remote Data ( "+remoteVector.get(v)+" ) "+UnSyncedLine_remote+";";
									  }	  									  
								  }
								  
								  if(same_count==remotevector_size){
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Synced","");
								  }else{
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data are not Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced",report);
								  }
								  
							  }else{
								  System.out.println( " IP "+IP+" CrossRoadID "+CrossRoadID+" Vector Size is Different ");	
								  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different");
							  }
							  
						  }else if(remoterowcount>centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Remote has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different RemoteDB has more data");
						  }
						  else if(remoterowcount<centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Center has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different CenterDB has more data");
						  }
							 						  
						 
						 Thread.sleep(1000);
					 }else{
						 
						 System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Connection Fail");
						 MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Failed To Connect");
					 }
					 
					 Thread.sleep(1000);
				 }
				
			}
		}catch(Exception e){
			System.out.println("Error in DataVerify_start "+e.getMessage());
		}
				
		
	}
	
	public static void BusStrategy_Set_DataVerify_start(){
		
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
			 
			 String Comparing_TableName ="BusStrategy_Set_Tab";
			 
			for(String IP:MasterIP){
				
				String GroupID=MSDB.getGroupID_fromIP(IP);
				System.out.println(" Master IP "+IP+" GroupID "+GroupID);
				 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(GroupID);
				 
				 String  Temp_connect=DB_connect.replace("XXXX", IP);	
				 String  center_connect=DB_connect.replace("XXXX", MainDB_IP);
				 
				 boolean remoteconnection=MSDB.dbConnection(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password);
				 
				 for(String CrossRoadID: CrossRoadAddr){
					 		 
					 
					 if(remoteconnection){
						// String R_BusLineID,BusLine_Name,CrossRoadID,BusLine_Order,GoBack,Direct,BusSubPhaseID,PlanID;
						//System.out.println(" CrossRoad ID "+CrossRoadID+" Connection ok ");
						
						 String query="SELECT [CrossRoadID],[StrategyType],[Enable],[Param1],[Param2],[Param3],[Param4],[Param5],[Param6],[UpdateTime] FROM [TainanBusPrim].[dbo].[BusStrategy_Set_Tab] where [CrossRoadID]='"+CrossRoadID+"' order by [StrategyType]";
						 
						 //System.out.println(CrossRoadID+" Query "+query);
						 Vector remoteVector = new Vector();
						 Vector centerVector = new Vector();
						 
						 ResultSet remotedata=null;
						 ResultSet centerdata=null;
						 
						 try{
							  remotedata = MSDB.GetRemoteDBResultSet(DB_driver,Temp_connect,RemoteDB_account,RemoteDB_password,query);
						 }catch(Exception e){
							 System.out.println(" Remote data is empty");
						 }
						 
						 try{
							  centerdata = MSDB.GetRemoteDBResultSet(DB_driver,center_connect,MainDB_account,MainDB_password,query);
						 }catch(Exception e){
							 System.out.println(" Center data is empty");
						 }			

						 
						 if (!remotedata.isBeforeFirst() ) {    
							 System.out.println("remotedata is null"); 
							} 
						 
						 if (!centerdata.isBeforeFirst() ) {    
							 System.out.println("centerdata is null"); 
							} 
						 						
						 
						 ResultSetMetaData Centersmd = centerdata.getMetaData();
						 ResultSetMetaData rsmd = remotedata.getMetaData();
						 int columnsNumber = rsmd.getColumnCount();
						 int centercolumnsNumber = Centersmd.getColumnCount();
						 
						 //System.out.println(" columnsNumber "+columnsNumber);
						 String[] columnheaders=new String[columnsNumber];
						 String[] Centercolumnheaders=new String[centercolumnsNumber];  
						 
						   for(int i=0;i<columnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   columnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   for(int i=0;i<centercolumnsNumber;i++){
							   //System.out.println("ColumnName "+rsmd.getColumnName(i+1));
							   Centercolumnheaders[i] = rsmd.getColumnName(i+1);
						   }
						   
						   //remotedata.first();		
						   //int row = remotedata.getRow();
						   //System.out.println(" ResultSet row "+row);
						 
						 int remoterowcount=0;
						  while(remotedata.next()){	
							 
					    	  for(String a:columnheaders){					    		  
					    		  String str_data = remotedata.getString(a);
					    		  remoteVector.addElement(a+"_"+str_data);
					    		  //System.out.println(" "+a+" : " + str_data);					    		  
					    	  }
					    	  
					    	  remoterowcount++;					    	  
						 }
						  
						  //System.out.println("***RowCount "+remoterowcount);
						  
							 int centerrowcount=0;
							 while(centerdata.next()){	
								 
						    	  for(String a:Centercolumnheaders){					    		  
						    		  String str_data = centerdata.getString(a);
						    		  centerVector.addElement(a+"_"+str_data);
						    		  //System.out.println(" "+a+" : " + str_data);					    		  
						    	  }
						    	  
						    	  centerrowcount++;					    	  
							 }
							
							 
							  //System.out.println("***RowCount "+centerrowcount);
						  if(remoterowcount==centerrowcount ){
							  //System.out.println( CrossRoadID+" Exact Same Row Count ");							  
							  int centervector_size= centerVector.size();
							  int remotevector_size= remoteVector.size();
							  
							  if(centervector_size==remotevector_size ){
								  
								  int same_count=0;
								  String report="";
								  for(int v=0;v<remotevector_size;v++){
									 
									  if(centerVector.get(v).equals(remoteVector.get(v))){
										  //System.out.println("")
										  same_count++;
									  }else{
										  //System.out.println(CrossRoadID+" Center Data "+centerVector.get(v)+" Remote Data "+remoteVector.get(v));	
										  report=report+CrossRoadID+" Center Data "+centerVector.get(v)+" Remote Data "+remoteVector.get(v)+";";
									  }	  									  
								  }
								  
								  if(same_count==remotevector_size){
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Synced","");
								  }else{
									  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Data are not Synced  "+Comparing_TableName);
									  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced",report);
								  }
								  
							  }else{
								  System.out.println( " IP "+IP+" CrossRoadID "+CrossRoadID+" Vector Size is Different ");	
								  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different");
							  }
							  
						  }else if(remoterowcount>centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Remote has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different RemoteDB has more data");
						  }
						  else if(remoterowcount<centerrowcount){
							  System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Center has more data ");
							  MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Data Sizes are different CenterDB has more data");
						  }
							 						  
						 
						 Thread.sleep(1000);
					 }else{
						 
						 System.out.println(" IP "+IP+" CrossRoadID "+CrossRoadID+" Connection Fail");
						 MSDB.Update_DB_Compare_log(Comparing_TableName,IP,CrossRoadID,"Not Synced","Failed To Connect");
					 }
					 
					 Thread.sleep(1000);
				 }
				
			}
		}catch(Exception e){
			System.out.println("Error in DataVerify_start ");
			 e.printStackTrace();
		}
				
		
	}
	
	

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
