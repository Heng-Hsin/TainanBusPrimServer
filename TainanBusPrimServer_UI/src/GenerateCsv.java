import java.io.FileWriter;
import java.io.IOException;

public class GenerateCsv
{
   public static void main(String [] args)
   {
	   //generateCsvFile("c:\\test.csv"); 
   }
   
   public static void generateCsvFile(String sFileName,String Gopath,String Backpath,String port,String Range)
   {
	try
	{
	    FileWriter writer = new FileWriter(sFileName);
	    //PATH,110001,120.212079,22.996998,15,20,50
	    String[] Goo=Gopath.split(",");
	    String[] Baa=Backpath.split(",");
	    writer.append(Gopath);	         
	    writer.append("\r\n");
	    int attempts=0;
	    int seq_counter=0;
	    int routeCount=1;
	    
	    while(attempts<4){
	    	try{
	    		//RouteID> seq >>  CrossRoad ID > GroupID> MasterIP Port > triggerpointid 123 >next 
	    		//120.212264,22.997586,25,192.168.234.152:21201,004306860200,0,1	    				    			    	
		    	
		    	String CrossRoadID=MSDB.getCrossRoad_BusLine_Seq(Goo[1],Integer.toString(seq_counter));
		    	
		    	
		    	if (CrossRoadID != null && !CrossRoadID.isEmpty()){

		    	String GroupID=MSDB.getGroupID_CrossRoadID(CrossRoadID );
		    	String GroupIP =MSDB.getRoadIP(GroupID );
		    	String dic=MSDB.getDirect_BusRoute(Goo[1]);		    	
		    	String[] Triggerpoints=MSDB.getTrigID(CrossRoadID , dic);
		    	
		    	for(String a: Triggerpoints){
		    		String data="";
		    		String order=a.substring(a.length()-1);
		    		String[] Gps=MSDB.getGPS_TriggerPointID(a);
		    		
		    		if(order.equalsIgnoreCase("2")){
		    			String pointCount = Integer.toString(routeCount);
		    			data=Gps[0]+","+Gps[1]+","+Range+","+GroupIP+":"+port+","+a+","+"1"+","+pointCount;	    			
		    					
		    		}else{
		    			String pointCount = Integer.toString(routeCount);
		    			data=Gps[0]+","+Gps[1]+","+Range+","+GroupIP+":"+port+","+a+","+"0"+","+pointCount;		    			
		    					
		    		}
		    		System.out.println(data);
		    		routeCount++;
		    		writer.append(data);
	    			writer.append("\r\n");
		    		
		    	}
		    	
		    	seq_counter++;
		    	attempts=0;
		    	
		    	}else{
		    		System.out.println(" Empty CrossRoadID ");
		    		attempts++;
		    	}
		    	
		    }catch(Exception gg){

		    	attempts++;
		    	
		    	gg.printStackTrace();
		    	System.out.println("Error in generateCsvFile Row by Row ");
		    	
		    	}
	    	
	
    	}
	    
	    
	    //120.212264,22.997586,25,192.168.234.152:21201,004306860200,0,1
	    writer.append(",,,,,,");
	    writer.append("\r\n");
	    writer.append(Backpath);	    
	    writer.append("\r\n");
	     attempts=0;
	     seq_counter=0;
	     routeCount=1;
			
	     while(attempts<4){
		    	try{
		    		//RouteID> seq >>  CrossRoad ID > GroupID> MasterIP Port > triggerpointid 123 >next 
		    		//120.212264,22.997586,25,192.168.234.152:21201,004306860200,0,1	    				    			    	
			    	
			    	String CrossRoadID=MSDB.getCrossRoad_BusLine_Seq(Baa[1],Integer.toString(seq_counter));
			    	
			    	
			    	if (CrossRoadID != null && !CrossRoadID.isEmpty()){

			    	String GroupID=MSDB.getGroupID_CrossRoadID(CrossRoadID );
			    	String GroupIP =MSDB.getRoadIP(GroupID );
			    	String dic=MSDB.getDirect_BusRoute(Baa[1]);		    	
			    	String[] Triggerpoints=MSDB.getTrigID(CrossRoadID , dic);
			    	
			    	for(String a: Triggerpoints){
			    		String data="";
			    		String order=a.substring(a.length()-1);
			    		String[] Gps=MSDB.getGPS_TriggerPointID(a);
			    		
			    		if(order.equalsIgnoreCase("2")){
			    			String pointCount = Integer.toString(routeCount);
			    			data=Gps[0]+","+Gps[1]+","+Range+","+GroupIP+":"+port+","+a+","+"1"+","+pointCount;	    			
			    					
			    		}else{
			    			String pointCount = Integer.toString(routeCount);
			    			data=Gps[0]+","+Gps[1]+","+Range+","+GroupIP+":"+port+","+a+","+"0"+","+pointCount;			    					
			    		}
			    		
			    		System.out.println(data);
			    		routeCount++;
			    		writer.append(data);
		    			writer.append("\r\n");
			    		
			    	}
			    	
			    	seq_counter++;
			    	attempts=0;
			    	
			    	}else{
			    		System.out.println(" Empty CrossRoadID ");
			    		attempts++;
			    	}
			    	
			    }catch(Exception gg){

			    	attempts++;
			    	
			    	gg.printStackTrace();
			    	System.out.println("Error in generateCsvFile Row by Row ");
			    	
			    	}
		    	
		
	    	}
			
	     
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	     System.out.println("Error in generateCsvFile");
	} 
    }
}