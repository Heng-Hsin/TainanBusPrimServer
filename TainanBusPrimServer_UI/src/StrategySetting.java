
public class StrategySetting {
	public static final String[] StrategyTypes={"LOW_SPEED","NEAR_STATION","SHORT_CROSSROAD","LOCK_BUSID","ADV_COMPENSATE","ADV_MULTI_BUS","ADV_MULTI_PHASE"};

	public static void Default_strategy_settings(){
	
		String[] allMasterAddr=MSDB.getAllMasters_Addr();
		
		for(String m: allMasterAddr){
			//System.out.println("Group "+m);
			
			 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
			 //String MasterIP=MSDB.getCrossRoad_IP(m);
			 for(String c:CrossRoadAddr){
				 					
				 for(String b:StrategyTypes){
					 MSDB.Default_strategy_settings(c,b);	
				 }
				 
			 }
											
		}
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
