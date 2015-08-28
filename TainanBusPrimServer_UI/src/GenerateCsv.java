import java.io.FileWriter;
import java.io.IOException;

public class GenerateCsv
{
   public static void main(String [] args)
   {
	   //generateCsvFile("c:\\test.csv"); 
   }
   
   public static void generateCsvFile(String sFileName,String Gopath,String Backpath)
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
	    while(attempts<4){
	    	try{
	    		// CrossRoad ID > GroupID> MasterIP Port > triggerpointid 123 >next 
	    		
		    	int seq_counter=0;
		    	String CrossRoadID=MSDB.getCrossRoad_BusLine_Seq(Goo[1],Integer.toString(seq_counter));
		    	
		    	
		    	seq_counter++;
		    	attempts=0;
		    }catch(Exception gg){
		    	
		    	attempts++;
		    	
		    	gg.printStackTrace();
		    	System.out.println("Error in generateCsvFile Row by Row ");
		    	
		    	}
	
    	}
	    
	    
	    //120.212264,22.997586,25,192.168.234.152:21201,004306860200,0,1
	    writer.append(",,,,,,");
	    writer.append(Backpath);	    
	    writer.append("\r\n");

			
	    //generate whatever data you want
			
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