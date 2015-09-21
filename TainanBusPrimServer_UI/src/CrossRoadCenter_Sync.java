
public class CrossRoadCenter_Sync {
	
	public static void DataVerify_start(){
		
		
		String[] MasterIP =Server_UI.MasterIP;
		
		for(String IP:MasterIP){
			
			String GroupID=MSDB.getGroupID_fromIP(IP);
			System.out.println(" Master IP "+IP+" GroupID "+GroupID);
			 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(GroupID);
			 
			 for(String CrossRoadID: CrossRoadAddr){
				 System.out.println(" CrossRoad ID "+CrossRoadID);
				 
			 }
			
		}
		
	}
	
//[BusPriority_TriggerPoint_Tab]
//[BusLine_Info_Tab]
//[BusStrategy_Set]
//[BusPrority_CrossRoad_Info_Tab]
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
	}

}
