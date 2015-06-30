import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
 
public class PingIP {
	
	public static Vector<String> zeroconnection = new Vector<String>();
	public static Vector<String> fullconnection = new Vector<String>();
	
  public static void runSystemCommand(String ip) {
 
		try {
			String command="ping " + ip;
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
 
			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				System.out.println(s);
				if(s.contains("100%"))
					zeroconnection.addElement(ip);
				
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
  public static void Ping(String ip) {
	  
		try {
			String command="ping " + ip;
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
 
			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				//System.out.println(s);
				if(s.contains("100%") && zeroconnection.contains(ip)==false){
					zeroconnection.addElement(ip);
					if(fullconnection.contains(ip)){
						fullconnection.removeElement(ip);
					}
				}
				else if(s.contains("0%") && fullconnection.contains(ip)==false){
					fullconnection.addElement(ip);
					if(zeroconnection.contains(ip)){
						zeroconnection.removeElement(ip);
					}
				
				}
				
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
  
  public static boolean Ping2(String ip) {
	  
		try {
			String command="ping " + ip;
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
				//System.out.println(s);
				if(s.contains("100%"))
					return false;
				if(s.contains("0%"))
					return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
  
  public static void OtherThread(final String IP) {
	  Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {
            
                  
                  try {
                  	Ping( IP);
                  	//System.out.println("ONLINE "+fullconnection);
                  	//System.out.println("OFFLINE "+zeroconnection);
                  	
                	
              	    
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
              
          }
      });
      thread.start();
	  
  }
  

 
	public static void main(String[] args) {
		
		//final String ip = "127.0.0.1";
		 String ip[] ={"127.0.0.1","60.61.77.80","google.com","10.122.1.38"} ;
		//runSystemCommand( "google.com");
		//System.out.println("No Connection "+noconnection);
		
		//OtherThread("127.0.0.1") ;
		//System.out.println(Ping2("127.0.0.1"));
		//System.out.println(Ping2("yahoo.com"));
		 for(String a:ip)
			 OtherThread(a);
	
	}
}
