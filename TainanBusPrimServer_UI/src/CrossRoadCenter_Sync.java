
public class CrossRoadCenter_Sync {
	
//[BusPriority_TriggerPoint_Tab]
//[BusLine_Info_Tab]
//[BusStrategy_Set]
//[BusPrority_CrossRoad_Info_Tab]
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] MasterIP =Server_UI.MasterIP;
		String[] allMasterAddr=MSDB.getAllMasters_Addr();
		
		for(String m: allMasterAddr){
			//System.out.println("Group "+m);
			
			 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
			 String MotherIP=MSDB.getCrossRoad_IP(m);
		}
	}

}
