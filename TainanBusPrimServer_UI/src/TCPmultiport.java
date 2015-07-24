import java.net.*; 
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.io.*;




public class TCPmultiport extends Thread {
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();	
	private  static Vector<String> udprecv=new Vector();
	private  static Vector outter=new Vector();
	private static Hashtable<String, byte[]> cc2Client = new Hashtable<String, byte[]>();	
	public static Hashtable<String, Date> IPCCommunication = new Hashtable<String, Date>();	
	public static String[] deviceIPaddress={}; 
	public static int[] deviceport={};
	public static int[] serverport={};
	public static String userInterfaceIP;
	//final static String userInterfaceIP="127.0.0.1";
	public static int userInterfacePort;
	public static int listenportforUser;
	public static int listenportforIPC;
	
	final static String tempdeviceIP="10.36.93.2";
	//final static String tempdeviceIP="127.0.0.1";
	final static int tempdeviceport=8909;
	public static String CrossRoadDB_userid;
	public static String CrossRoadDB_password;
	
	public static String Universal_path;
	public static String Universal_IPC_file;
	
	
	public TCPmultiport(){
		try{
			 Executor executor = Executors.newFixedThreadPool(4);  //number of threads ****
	         MSDB mssql =new MSDB(); 
	         if (Properties()){
	          
	         
	         
	         /*
	         for (int x=2000; x<2050; x++){ 
	 			 if(x<1005)
	        	executor.execute(new Work(x));
	 			 else if(x<1010)
	 			executor.execute(new Work2(x));
	 		 }
	         */
	         
	         //if(from.equals(userInterfaceIP) && Protocol.checkCKS(recv) && Protocol.ChkAddr(recv,"8888"))
	        
	        
	 		executor.execute(new Work3(listenportforUser));
	 		executor.execute(new Work2(listenportforIPC));
	 		executor.execute(new Work(1006));
	 		//executor.execute(new Work4(8888));
	 		
	 		
	 		 
	        
	         }else{
	        	 System.out.println("Error in Properties Files C://BusPrime.properties ");
	         }
	 		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] hexStringToByteArray(String s) {				//Convert String to byte array
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static String NowTime(){
		try{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
	    String time=dateFormat.format(date);	//2014/07/30 1058
	    return time;
		}catch(Exception e){
			e.printStackTrace();
		return null;
		}
	    
	}
	
	public static void LastContact(){
		
		try{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
	    String time=dateFormat.format(date);	//2014/07/30 1058
	   	    
	    System.out.println("NOW Time Date:"+time); 
	    //System.out.println("127.0.0.1 "+IPCCommunication.get("/127.0.0.1"));
	    
	    /*
	    String[] IPCIP =  MSDB.getIPCIP();
	    for(String a:IPCIP){
	    	MSDB.updateIPCOnOff(a,5);
	    }
	    */
	    	
	    
	    //Enumeration en=IPCCommunication.keys();	   	      		  
	      // display search result
	    System.out.println( IPCCommunication);
	    
	    
	    Set<String> keys = IPCCommunication.keySet(); 
	    
	    for(String key: keys){
	    	
	    	try{
	        	System.out.println(key +">>"+IPCCommunication.get(key));
		    	//System.out.println(IPCCommunication.get(key));	   	     	      
		    long diff = date.getTime() - IPCCommunication.get(key).getTime();	    
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			System.out.println("");
			
			String temp= key.toString();
			temp=temp.substring(1);
			
			if(diffDays>0 || diffHours>0 || diffMinutes>10){ // TC offline criteria
				System.out.println(" IP "+key+" is offline");
				MSDB.updateIPCOnOff(temp,5);
				
			}else{
				
				
				MSDB.updateIPCOnOff(temp,4);
				//System.out.println(" Key "+key+" changed <"+temp+">");
				
			}
	    	}catch(Exception vv){
	    		
	    	}
	    	
	
		
		
	    }
		
		}catch(Exception e){
			System.out.println("Error in Last Contact");
			e.printStackTrace();
		
		}
	    
	}
	
public static void LastContact2(){
		
		try{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
	    String time=dateFormat.format(date);	//2014/07/30 1058
	   	    
	    //System.out.println("NOW Time Date:"+time); 
	    //System.out.println("127.0.0.1 "+IPCCommunication.get("/127.0.0.1"));
	    
	   
	   /*
	    for(String a:Server_UI.IPCIP){
	    	MSDB.updateIPCOnOff(a,5);
	    }
	    */
	    
	    
	    //Enumeration en=IPCCommunication.keys();	   	      		  
	      // display search result
	 
	    Set<String> keys = IPCCommunication.keySet(); 
	    
	    for(String key: keys){
	    	
	    	try{
	    		//System.out.println(key +">>"+IPCCommunication.get(key));
		    	//System.out.println(IPCCommunication.get(key));	   	     	      
		    long diff = date.getTime() - IPCCommunication.get(key).getTime();	    
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
	        
			/*
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			System.out.println("");
			*/
			
			String temp= key.toString();
			temp=temp.substring(1);
			
			if(diffDays>0 || diffHours>0 || diffMinutes>10){ // TC offline criteria
				System.out.println("Something Wrong with IP "+key);
				MSDB.updateIPCOnOff(temp,5);
				
			}else{

				MSDB.updateIPCOnOff(temp,4);
				//System.out.println(" Key "+key+" changed <"+temp+">");
				
			}
	    		
	    	}catch(Exception ss){
	    		//ss.printStackTrace();
	    		
	    	}
	    
		
		
	    }
		
		}catch(Exception e){
			System.out.println("Error in Last Contact2");
			e.printStackTrace();
		
		}
	    
	}
	
	public static boolean Properties() {
		  
		   
	     Properties prop = new Properties();
		 InputStream input = null;
		 
			try {
				 					 
				input = new FileInputStream("C://TainanBusPrime.properties");
							
				prop.load(input);
			
					userInterfaceIP=prop.getProperty("userInterfaceIP");
					String STRuserInterfacePort=prop.getProperty("userInterfacePort");
					userInterfacePort= Integer.parseInt(STRuserInterfacePort);
					String STRlistenportforUser =prop.getProperty("listenportforUser");
					listenportforUser=Integer.parseInt(STRlistenportforUser);
					String STRlistenportforIPC =prop.getProperty("listenportforIPC");
					listenportforIPC=Integer.parseInt(STRlistenportforIPC);
					
					CrossRoadDB_userid=prop.getProperty("CrossRoadDB_userid");
					CrossRoadDB_password=prop.getProperty("CrossRoadDB_password");
					
					Universal_path=prop.getProperty("Universal_path");
					Universal_IPC_file=prop.getProperty("Universal_IPC_file");
					
					
					
					System.out.println(" userInterfaceIP "+userInterfaceIP);
					System.out.println(" userInterfacePort "+userInterfacePort);
					System.out.println(" listenportforUser "+listenportforUser);
					System.out.println(" listenportforIPC "+listenportforIPC);
		    			
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
	
	
	public static void main(String[] args) throws IOException { 
		
		MSDB mssql =new MSDB();
		
		TCPmultiport  multiport =new TCPmultiport();	
 		
	}
	
	public static class Work implements Runnable {    // TCP connection for back up
        private int id;  
      
        public Work (int id) {  
            this.id = id;  
        }  
      
        public void run() { 
			  
        	 ByteArrayOutputStream serverinput=new ByteArrayOutputStream();
        
        	 try{
        		
        		 ServerSocket ss = new ServerSocket(id);     // establish tcp server
        		 
        		System.out.println("TCP UP "+id);
        		 while(true) {
        			
        		 Socket sc  = ss.accept();                // receive 
           		  
           		 System.out.println(sc.getLocalSocketAddress().toString()+" "+new java.util.Date());   
                 OutputStream os = sc.getOutputStream();    // get stream。
                 InputStream in = sc.getInputStream(); 
                 int len=0;
                 byte[] buf=new byte[2048];   
                 
                
               
                 while( !(udprecv.isEmpty())){
                	 
           		   String first=udprecv.firstElement();
           		   byte[] tcpsend=hexStringToByteArray(first);
                   os.write(tcpsend);//Send to Client。
                   os.flush();
                   System.out.println("Sent by TCP "+bytesToHex(tcpsend)+" "+new java.util.Date());
                   udprecv.removeElementAt(0);
                                          
           		 }
                  
                 try{
                 while ((len = in.read(buf))>=0 )
                 {
              
                	 serverinput.write(buf, 0, len);    	        	 
                	 byte[] cmd= serverinput.toByteArray();
                    System.out.println("From TCP "+bytesToHex(cmd)+" "+sc.getLocalSocketAddress().toString());
                   
                    //MessageCreator bullet = new MessageCreator("02","003F", bytesToHex(serverinput.toByteArray()));
            		//byte[] cmd= serverinput.toByteArray();
                    
            	    //String udpip="10.36.93.90";
            		//String udpip="127.0.0.1";
            	    //int udpport=1001;
            		UDPSender cannon =new UDPSender(tempdeviceIP,tempdeviceport,cmd); 
            		System.out.println("Sent by UDP "+bytesToHex(cmd)+" "+tempdeviceIP+":"+tempdeviceport);
            		cannon.fire();
                    
            		//UDPSender.Send("10.36.90.73","1003","AABB0100A0000D5F8501AACC00");
            		
                    serverinput.reset();  
                    /*
                    byte[] tcpsend=serverinput.toByteArray();
                    os.write(tcpsend);//Send to Client。
                    os.flush();
                    System.out.println("Sent by TCP "+bytesToHex(tcpsend));
                    */
                    	
                    while( !(udprecv.isEmpty())){
               			String first=udprecv.firstElement();
               			byte[] tcpsend=hexStringToByteArray(first);
                       os.write(tcpsend);    //Send to Client。
                       os.flush();
                       System.out.println("Sent by TCP "+bytesToHex(tcpsend));
                       udprecv.removeElementAt(0);                                                                              
               		}
                    
                 }
        		 }catch(Exception e){
        			 e.printStackTrace();
        			 System.out.println("TCP Connection Lost");
        		 }
                                                           
                
                 os.close();                                // Close stream。
                 sc.close();                                // Shutdown TCP server。
           	
             }
        	 }catch(Exception e){
        		     	        		 
        		 e.printStackTrace();	 
        	 }        	  
        	 
		  } 
    } 
	
	public static class Work2 implements Runnable {  		//Listening Server for receiving IPC packets
        private int id;  
      
        public Work2 (int id) {  
            this.id = id;  
        }  
      
        public void run() { 
			  
        	try {
    	    	          	     
    	        DatagramSocket dsocket = new DatagramSocket(id);
    	        System.out.println("ListenPortForIPC UDP UP "+id);
    	     
    	        byte[] buffer = new byte[2048];

    	        // Create a packet to receive data into the buffer
    	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    	        
    	        while (true) {
    	          
    	          dsocket.receive(packet);
    	          
    	          byte[] newbuffer= new byte[packet.getLength()];
    	          
    	          for(int i =0;i<packet.getLength();i++)
    	          	newbuffer[i]=buffer[i];
    	          
    	          byte[] recv=newbuffer;
    	          //System.out.println(" UDP from "+packet.getAddress()+" "+bytesToHex(newbuffer)+" "+new java.util.Date());
    	          String from = packet.getAddress().toString().replace("/" , "");
    	          
    	          if( Protocol.checkCKS(recv) && Protocol.NotACKNAK(recv))    
    	          {
    	        	  
    	        	  recv=Protocol.pureMessage(recv);
    	        	     	        	  
    	        	 //String deviceReport= Protocol.zerof04(recv);
    	        	  
    	        	  try{
    	        		  if(IPCCommunication.containsKey(packet.getAddress().toString())==false){
        	        		  
    	    	        	  IPCCommunication.put(packet.getAddress().toString(), new java.util.Date());
    	    	        	  
    	    	        	  }else{
    	    	        		  IPCCommunication.remove(packet.getAddress().toString());
    	    	        		  
    	    	        		  IPCCommunication.put(packet.getAddress().toString(), new java.util.Date());
    	    	        	  }
    	        	  }catch(Exception e){
    	        		  System.out.println("IPCCommunication HashTable Error");
    	        		  e.printStackTrace();
    	        	  }
    	        	  
    	        	  
    	        	  try{
    	        		  
    	        		  if((recv[0]==(byte)0x5F) && (recv[1]==(byte)0xA0)){
        	        		  //System.out.println("Got 5FA0"+bytesToHex(recv)+ "From " +packet.getAddress().toString());
        	        		  if((recv[2]==(byte)0x01) && (recv[3]==(byte)0x01) ){
        	        		  MSDB.updateIPCStatus(from,9);
        	        		  MSDB.updateIPCOnOff(from,4);
        	        		  System.out.println("BusPrime On "+from);
        	        		  }
        	        		  if((recv[2]==(byte)0x01) && (recv[3]==(byte)0x00) ){
            	        		  MSDB.updateIPCStatus(from,8);
            	        		  MSDB.updateIPCOnOff(from,4);
            	        		  System.out.println("BusPrime Off "+from);
            	        		  }
        	        		  
        	        	  }else if((recv[0]==(byte)0x0F) && (recv[1]==(byte)0x04)){
        	        		  
        	        		  MSDB.update0F04Status(from,bytesToHex(recv));
        	        		  //System.out.println("0F04 "+bytesToHex(recv)+" from "+from);
        	        	  }
    	        		  
    	        	  }catch(Exception e){
    	        		  System.out.println("TCPmultiport IPC UDP receive Error ");
    	        		  e.printStackTrace();
    	        	  }
    	        	  
    	        	  
    	        	  
    	          }
    	          
    	          
    	          //udprecv.add(bytesToHex(newbuffer));
    	          //System.out.println(udprecv);
    	          
    	          
    	          //String msg = new String(buffer, 0, packet.getLength());
    	          //System.out.println(msg);  
    	         

    	          // Reset the length of the packet before reusing it.
    	          packet.setLength(buffer.length);
    	        }
    	      } catch (Exception e) {
    	    	  
    	        System.err.println(e);
    	      }
		  } 
    } 
	
	public static class Work3 implements Runnable {  //UDP socket for connecting to userinterface
        private int id;  
      
        public Work3 (int id) {  
            this.id = id;  
        }  
      
        public void run() { 
			  
        	try {
    	    	
          	     
    	        DatagramSocket dsocket = new DatagramSocket(id);
    	        System.out.println("UserInterface UDP UP "+id);
    	     
    	        byte[] buffer = new byte[2048];
    	        Vector<String> udpinner=new Vector();
    	        
    	        // Create a packet to receive data into the buffer
    	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    	        
    	        //String[] allroad=MSDB.getallRoadid();
    	        
    	        while (true) {
    	          
    	          dsocket.receive(packet);
    	          
    	          byte[] newbuffer= new byte[packet.getLength()];
    	          
    	          for(int i =0;i<packet.getLength();i++)
    	          	newbuffer[i]=buffer[i];
    	          
    	        
    	          System.out.println(" UDP from "+packet.getAddress()+" "+bytesToHex(newbuffer)+" "+new java.util.Date());
    	          String from = packet.getAddress().toString().replace("/" , "");
    	          byte[] recv=newbuffer;
    	          
    	          //5F87+UpdateType+CrossRoadID
    	          //update type 
    	          //1.Update TriggerPoint 2.Update Bus Route 3.Update SegementType 4.IPC program OTA 5.BusPrime Switch
    	          
    	          if(from.equals(userInterfaceIP) && Protocol.checkCKS(recv) && Protocol.ChkAddr(recv,"8888"))    // when running change to userInterfaceIP
    	          {  
    	        	  System.out.println("Command from UserInterface");    	        	  
    	        	  
    	        	      String seq=Protocol.GetSeq(recv);
    	        		  String recvstr=bytesToHex(recv);
            	          System.out.println(recvstr);
            	          byte[]message=Protocol.pureMessage(recv);
            	          int messagetype=-1;
            	          String msgrcver = "";
            	          String[] roaddirect = null;	
      	  				  String[] roadipport = null;
      	  				  String[] roadseg = null;
      	  				  String addr="";
      	  	
      	  				  
      	  				  
            	          try{
            	          	  
            	          String[] messageinfo = Protocol.fivef87(message);
            	          messagetype = Integer.valueOf(messageinfo[0]); 
            	          msgrcver = messageinfo[1];
            	          //System.out.println("msgrcver" +msgrcver);
            	          
            	          }catch(Exception e){
            	        	  
            	        	  System.out.println("Error in receiving fivef87 message");
            	          }
      	          
    	        	     if(messagetype>0){
    	        	    	 //System.out.println("messagetype "+messagetype);
    	        	    	 try{    	        	    		 
    	        	    	 
    	                   		byte[] cmd= MessageCreator.createpackage(seq,"8888", "0F805F87");  
    	                   		UDPSender.Send(userInterfaceIP,userInterfacePort,cmd);   	                   		
    	                   		//System.out.println("Sent by UDP "+bytesToHex(cmd)+" "+userInterfaceIP+":"+userInterfacePort);
    	                   		System.out.println("Message Type = "+messagetype);    	                   		
    	        	    	 	}catch(Exception e){
    	        	    	 		
    	        	    	 		System.out.println("Error in Sending Confirmation back to  workstation");
    	        	    	 	    	        	    	 		
    	        	    	 	} 
    	                   		
    	        	    	 
            	          switch(messagetype){
            	          
            	  		case 1:
            	  			 System.out.println("TriggerPoints Updated Sending out new TriggerPoints");
            	  			try{
            	  				 roaddirect=MSDB.getRoadDirect(msgrcver);	
            	  				 roadipport=MSDB.getRoadIPport(msgrcver);
            	  				 addr= MSDB.getRoadAddr(msgrcver);
            	  				System.out.println("RoadID "+msgrcver);
            	  				System.out.println("Roadaddr "+addr);
            	  				
            	  				 for(int j=0;j<roaddirect.length;j++){
            	  					
            	  					System.out.println("Direct "+roaddirect[j]);
            	  					String devicecmd=MSDB.fivef81(msgrcver, roaddirect[j]);
            	  				    System.out.println(devicecmd);
            	  					System.out.println("IPport "+roadipport[0]+":"+roadipport[1]);
            	  			            	  					
          	                   		byte[] dcmd= MessageCreator.createpackage(seq,addr, devicecmd);     
          	                   		
          	                   		int tempportint= Integer.parseInt(roadipport[1]);
          	                   	
          	                   		//UDPSender.Send(tempdeviceIP,tempdeviceport,dcmd); 
          	                   		UDPSender.Send(roadipport[0],tempportint,dcmd); 
          	                   		
          	                   		//System.out.println("Sent by UDP "+bytesToHex(dcmd));
          	                   		          		
            	  				 						}
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Triggerpoint Update ");
            	  			}						
            	  			 
            	  			break;
            	  			
            	  		case 2: //Update Bus Route Data
            	  			
            	  			
            	  			
            	  			
            	  			break;
            	  		case 3:   //no need for this part 
            	  			System.out.println("SegmentType Updated Sending out new SegmentType");			
            	  			
            	  			try{
          	  			           	  				 
           	  			     roadseg=MSDB.getRoadSegtype(msgrcver);            	  				
           	  				 roadipport=MSDB.getRoadIPport(msgrcver);
           	  				 addr= MSDB.getRoadAddr(msgrcver);
           	  				System.out.println("RoadID "+msgrcver);
           	  				System.out.println("Roadaddr "+addr);
           	  				
           	  				 for(int j=0;j<roadseg.length;j++){
           	  					
           	  					//System.out.println("Segtype "+roadseg[j]);
           	  					String devicecmd=MSDB.fivef82(msgrcver, roadseg[j]);
           	  				    //System.out.println(devicecmd);
           	  					//System.out.println("IPport "+roadipport[0]+" "+roadipport[1]);
           	  					
           	  					int tempportint= Integer.parseInt(roadipport[1]);
           	  				 
         	                   		byte[] dcmd = MessageCreator.createpackage(seq,addr, devicecmd);                		                      	                       		
         	                   		UDPSender.Send(roadipport[0],tempportint,dcmd); 
         	                   		//System.out.println("Sent by UDP "+bytesToHex(dcmd)+" "+tempdeviceIP+":"+tempdeviceport);
     		
         	                   		
           	  				 						}
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Segment Update");
            	  			}
           	  				 
            	  	    		
            	  	    	
            	  	    	            	  			
            	  			break;
            	  			
            	  		case 4:   //Use to be OTA program Update 
            	  				  //5FH+85H+Control
            	  			
            	  			System.out.println(" BusPrime OTA Update");	  
            	  			//MSDB mssql=new MSDB();
            	  			String IP="172.23.25.";		
            	  			String uploadport="8002";
            	  			String[] OTAfile=MSDB.getOTAFilePath(msgrcver);
            	  			
            	  			try{
            	  				//msgrcver   Roadid   AAAA 棕線   BBBB 綠線  FFFF 全線
            	  				
            	  				if(msgrcver.equalsIgnoreCase("AAAA")){
            	  					System.out.println("Brown Line OTA");
            	  					
            	  					
            	  					for(int i=1;i<31;i++){
    		        					
    		        					if((i!=55)&&(i!=47)&&(i!=46)&&(i!=45)&&(i!=50)){
    		        						String endvalue = Integer.toString(i);						
    		        						String tempip = IP+endvalue;	
    		        						//System.out.println(HttpMultipartUpload.Upload("127.0.0.1:81","C://","test.txt"));
    		        						boolean result=HttpMultipartUpload.Upload(tempip+":"+uploadport,OTAfile[0],OTAfile[1]);
    		        						
    		        						if(result){
    		        							MSDB.updateIPCOTA_Log(tempip,"Success");
    		        								System.out.println("OTA  Success "+tempip);
    		        	                    }else{
    		        	                    	MSDB.updateIPCOTA_Log(tempip,"Failed");
    		        	                    	System.out.println("OTA  Failed "+tempip);
    		        	                    }
    		        					}
    		        					
    		        				}
            	  					
            	  				}else if(msgrcver.equalsIgnoreCase("BBBB")){
            	  					System.out.println("Green Line OTA");
            	  					

            	  					for(int i=31;i<61;i++){
    		        					
            	  						if((i!=55)&&(i!=47)&&(i!=46)&&(i!=45)&&(i!=50)){
    		        						String endvalue = Integer.toString(i);						
    		        						String tempip = IP+endvalue;	
    		        						//System.out.println(HttpMultipartUpload.Upload("127.0.0.1:81","C://","test.txt"));
    		        						boolean result=HttpMultipartUpload.Upload(tempip+":"+uploadport,OTAfile[0],OTAfile[1]);
    		        						
    		        						if(result){
    		        							MSDB.updateIPCOTA_Log(tempip,"Success");
    		        								System.out.println("OTA  Success "+tempip);
    		        	                    }else{
    		        	                    	MSDB.updateIPCOTA_Log(tempip,"Failed");
    		        	                    	System.out.println("OTA  Failed "+tempip);
    		        	                    }
    		        					}
    		        					
    		        				}
            	  					
            	  				}else if(msgrcver.equalsIgnoreCase("FFFF")){
            	  					System.out.println("Both Lines OTA");
            	  					
            	  					for(int i=1;i<61;i++){
    		        					
            	  						if((i!=55)&&(i!=47)&&(i!=46)&&(i!=45)&&(i!=50)){
    		        						String endvalue = Integer.toString(i);						
    		        						String tempip = IP+endvalue;	
    		        						//System.out.println(HttpMultipartUpload.Upload("127.0.0.1:81","C://","test.txt"));
    		        						boolean result=HttpMultipartUpload.Upload(tempip+":"+uploadport,OTAfile[0],OTAfile[1]);
    		        						
    		        						if(result){
    		        							MSDB.updateIPCOTA_Log(tempip,"Success");
    		        								System.out.println("OTA  Success "+tempip);
    		        	                    }else{
    		        	                    	MSDB.updateIPCOTA_Log(tempip,"Failed");
    		        	                    	System.out.println("OTA  Failed "+tempip);
    		        	                    }
    		        					}
    		        					
    		        				}
            	  					
            	  					
            	  				}else{
            	  					System.out.println(msgrcver+ " OTA");
            	  					roadipport=MSDB.getRoadIPport(msgrcver);
            	  					System.out.println("IPport "+roadipport[0]);
            	  					boolean result=HttpMultipartUpload.Upload(roadipport[0]+":"+uploadport,OTAfile[0],OTAfile[1]);
            	  					
            	  					if(result){
	        							MSDB.updateIPCOTA_Log(roadipport[0],"Success");
	        								System.out.println("OTA  Success "+roadipport[0]);
	        	                    }else{
	        	                    	MSDB.updateIPCOTA_Log(roadipport[0],"Failed");
	        	                    	System.out.println("OTA  Failed "+roadipport[0]);
	        	                    }
            	  					
            	  				}
            	  					
            	  					/*
            	  				roadipport=MSDB.getRoadIPport(msgrcver);
           	  				 	addr= MSDB.getRoadAddr(msgrcver);
           	  				 	
           	  				 	System.out.println("IPport "+roadipport[0]);
                	  	    	
                	  	    	String[] filepath=MSDB.getCrossRoadOTAFilePath(msgrcver);
                	  	    	
                	  	    	//HttpMultipartTest temp = new HttpMultipartTest(filepath[0],filepath[1]);
                	  	    	
                	  	    	//HttpMultipartUpload.Upload(roadipport[0]+":81", filepath[1], filepath[0]) ;
                	  	    	
                	  	    		if(HttpMultipartUpload.Upload(roadipport[0]+":8002", filepath[1], filepath[0])){
                	  	    			
                	  	    			System.out.println("Individual BusPrime OTA Update SUCCESS "+roadipport[0]);
                	  	    			MSDB.updateCrossRoadOTA(msgrcver);
                	  	    			
                	  	    		}else{
                	  	    			System.out.println("Individual BusPrime OTA Update FAIL "+roadipport[0]);
                	  	    		}
                	  	    		*/
                	  	    		          	  	    		
                	  	    		//System.out.println(HttpMultipartTest.test("127.0.0.1:81"));
                	  	    		//System.out.println("Update OTA : "+roadipport[0]+":"+roadipport[1]);
                	  	    		//System.out.println(HttpMultipartTest.test(roadipport[0]+":"+roadipport[1]));
                	  	    		//Create Log function
            	  				
            	  			}catch(Exception e){
            	  				System.out.println("Error in Individual BusPrime OTA Update");	 
            	  				e.printStackTrace();
            	  			};
            	  			
            	  			
            	  	    	
            	  	    	            	  			
            	  		break;    
            	  			            	  		
            	  		
            	  		case 6:   //BusPrime Universal Off Switch   
            	  			
            	  			System.out.println("Universal BusPrime Off Switch");	
            	  			try{

            	  				 IP="172.23.25.";
            	  				

            	  				if(msgrcver.matches("AAAA")){  
            	  					System.out.println("Turn Off BusPrim Brown Line");
        	        				        	        				
        	        				for(int i=1;i<31;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8000AACCA4");
        	        				}
            	  					
            	  				}else if(msgrcver.matches("BBBB")){
            	  					System.out.println("Turn Off BusPrim Green Line");
            	  					
            	  					for(int i=31;i<61;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8000AACCA4");
        	        				}
            	  					
            	  					
            	  				}else if(msgrcver.matches("FFFF")){
            	  					System.out.println("Turn Off BusPrim EveryLine");
            	  					for(int i=1;i<61;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8000AACCA4");
        	        				}
            	  					
            	  				}else{
            	  					System.out.println("msgrcver "+msgrcver);
            	  					roadipport=MSDB.getRoadIPport(msgrcver);
               	  				 	
            	  					UDPSender.Send(roadipport[0],"20000","AABB01FFFF000D5F8000AACCA4");
               	  				 	
            	  				}
            	  				
               	  			
            	  				
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Universal BusPrime Off Switch");
            	  				
            	  			}
   	  			
            	  		break;
            	  		
            	  		case 5:   //BusPrime Universal On Switch   
            	  			
            	  			System.out.println("Universal BusPrime On Switch");	
            	  			try{
            	  				

            	  				 IP="172.23.25.";

            	  				if(msgrcver.matches("AAAA")){  
            	  					System.out.println("Turn On BusPrim Brown Line");
        	        				        	        				
        	        				for(int i=1;i<31;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8001AACCA5");
        	        				}
            	  					
            	  				}else if(msgrcver.matches("BBBB")){
            	  					System.out.println("Turn On BusPrim Green Line");
            	  					
            	  					for(int i=31;i<61;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8001AACCA5");
        	        				}
            	  					
            	  					
            	  				}else if(msgrcver.matches("FFFF")){
            	  					System.out.println("Turn On BusPrim EveryLine");
            	  					for(int i=1;i<61;i++){
        	        					String endvalue = Integer.toString(i);
        	        					//PingIP.OtherThread(IP+endvalue);
        	        					UDPSender.Send(IP+endvalue,"20000","AABB01FFFF000D5F8001AACCA5");
        	        				}
            	  					
            	  				}else{
            	  					
            	  					roadipport=MSDB.getRoadIPport(msgrcver);
               	  				 	
            	  					UDPSender.Send(roadipport[0],"20000","AABB01FFFF000D5F8001AACCA5");
               	  				 	
            	  				}
            	  				
               	  			
            	  				
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Universal BusPrime On Switch");
            	  				
            	  			}
            	  			

            	  		break;
            	  		
            	  		case 7:   //BusPrime Personal Off Switch   
            	  			
            	  			System.out.println("Personal BusPrime Off Switch");	
            	  			try{
		
            	  					 roadipport = MSDB.getRoadIPport(msgrcver);
                  	  				 addr = MSDB.getRoadAddr(msgrcver);
                  	  				System.out.println("RoadID "+msgrcver);
                  	  				System.out.println("Roadaddr "+addr);
                  	  				System.out.println("RoadIPport "+roadipport[0]+":"+roadipport[1]);
                  	  			//5F H+80 H+ControlStrategy+IsEnable
                  	  				String devicecmd="5F800100";
                  	  			byte[] dcmd= MessageCreator.createpackage(seq,addr, devicecmd);     
      	                   		
      	                   		int tempportint= Integer.parseInt(roadipport[1]);
      	                   	
      	                   		//UDPSender.Send(tempdeviceIP,tempdeviceport,dcmd); 
      	                   		UDPSender.Send(roadipport[0],tempportint,dcmd); 
 			
            	  				
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Personal BusPrime Off Switch");
            	  				
            	  			}
            	  			
            	  		break;
            	  		
            	  		case 8:   //BusPrime Personal On Switch   
            	  			
            	  			System.out.println("Personal BusPrime On Switch");	
            	  			try{
            	  				
            	  					 roadipport = MSDB.getRoadIPport(msgrcver);
                  	  				 addr = MSDB.getRoadAddr(msgrcver);
                  	  				System.out.println("RoadID "+msgrcver);
                  	  				System.out.println("Roadaddr "+addr);
                  	  				System.out.println("RoadIPport "+roadipport[0]+":"+roadipport[1]);
                  	  			//5F H+80 H+ControlStrategy+IsEnable
                  	  				String devicecmd="5F800101";
                  	  			byte[] dcmd= MessageCreator.createpackage(seq,addr, devicecmd);     
      	                   		
      	                   		int tempportint= Integer.parseInt(roadipport[1]);
      	                   	
      	                   		//UDPSender.Send(tempdeviceIP,tempdeviceport,dcmd); 
      	                   		UDPSender.Send(roadipport[0],tempportint,dcmd); 
 			
            	  				
            	  			}catch(Exception e){
            	  				e.printStackTrace();
            	  				System.out.println("Error in Personal BusPrime On Switch");
            	  				
            	  			}
            	  			
            	  		break;
            	  		
            	  		case 9:   //Universal BusPrime OTA Update
            	  			System.out.println("Universal BusPrime OTA Update");
            	 			
            	  			try{
            	  				String[] allcrossroadid=MSDB.getAllCrossroadIDOfGroup();
            	  				
            	  				
            	  				for(int hh=0;hh<allcrossroadid.length;hh++){
            	  					
           	  					 roadipport=MSDB.getRoadIPport(allcrossroadid[hh]);
                 	  				 addr= MSDB.getRoadAddr(allcrossroadid[hh]);
                 	  				System.out.println("RoadID "+allcrossroadid[hh]);
                 	  				System.out.println("Roadaddr "+addr);
                 	  				System.out.println("RoadIP "+roadipport[0]);
                 	  				
     	  				
                 	  				
                 	  				if(HttpMultipartUpload.Upload(roadipport[0]+":81", Universal_path, Universal_IPC_file)){
                	  	    			
                	  	    			System.out.println("Universal BusPrime OTA Update SUCCESS "+roadipport[0]);
                	  	    			                	  	    			
                	  	    			
                	  	    		}else{
                	  	    			System.out.println("Universal BusPrime OTA Update FAIL "+roadipport[0]);
                	  	    		}
                 	  				
            	  				}
            	  				
            	  				
            	  				
            	  			}catch(Exception e){
            	  				System.out.println("Error in Universal BusPrime OTA Update");	 
            	  				e.printStackTrace();
            	  			};
            	  			
            	  			
            	  			
            	  		
            	  		break;
            	  		
            	  		default:
            	  			
            				System.out.println("UserCommand error");	
            				
  	                   		byte[] cmd= MessageCreator.createpackage(seq,"8888", "0F815F87");                  		                      	                       		
  	                   		UDPSender.Send(userInterfaceIP,userInterfacePort,cmd); 
  	                   		System.out.println("Sent by UDP "+bytesToHex(cmd)+" "+userInterfaceIP+":"+userInterfacePort);
  	                   	
            	          }
    	        	     }else{
    	        	    	 
    	        	    
 	                   		byte[] cmd= MessageCreator.createpackage(seq,"8888", "0F815F87");               		                      	                       		
 	                   		UDPSender.Send(userInterfaceIP,userInterfacePort,cmd); 
 	                   		System.out.println("Sent by UDP "+bytesToHex(cmd)+" "+userInterfaceIP+":"+userInterfacePort);
 	                   		
    	        	    	 
    	        	     }

                   		
    	          }    	            	        

    	          // Reset the length of the packet before reusing it.
    	          packet.setLength(buffer.length);
    	          
    	        }
    	      } catch (Exception e) {
    	        System.err.println(e);
    	      }
		  } 
    } 

	
	public static class Work4 implements Runnable {  		//Collecting Remote DB data
        private int id;  
      
        public Work4 (int id) {  
            this.id = id;  
        }  
      
        public void run() { 
        	System.out.println("DB Collector UP");
        	//MSDB mssql=new MSDB();
			  while(true){				  
        	try {
        		
        		
        		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    Date date = new Date();        	    
        	    int int_minutes=date.getMinutes();
        	    //System.out.println("Minutes "+int_minutes);
        	    
        	    String temp_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
        	    String temp_connect="";
        		String temp_userid=CrossRoadDB_userid;
        		String temp_password=CrossRoadDB_password;
        	    
        	    if(int_minutes==55 ){  // int_minutes should be zero for on the hour performance
        	    	
        	    	System.out.println("It's "+date+" Start working");
        	    	String[] allcrossroadid=MSDB.getAllCrossroadIDOfGroup();
        	    	
        	    	for(int hh=0;hh<allcrossroadid.length;hh++){
        	    		
        	    		String[] roadipport=MSDB.getRoadIPport(allcrossroadid[hh]);
        	    		temp_connect="jdbc:microsoft:sqlserver://172.23.25.1:1433;DatabaseName=TaoyuanBusPrim";	
        	    		
        	    	}
        	    	//172.23.31.1 sa tybuspriM1234
        	    	
        	    	
            		boolean remoteconnection=MSDB.dbConnection(temp_driver,temp_connect,temp_userid,temp_password);
            		//System.out.println("Remote DBConnection "+MSDB.dbConnection(temp_driver,temp_driver,temp_connect,temp_userid,temp_password));
            		
            		
            		if(remoteconnection==true){
            			String temp_query="SELECT * FROM [TaoyuanBusPrim].[dbo].[BusA1_Log]";
            			ResultSet remotedata = MSDB.GetRemoteDBResultSet(temp_driver,temp_connect,temp_userid,temp_password,temp_query);
            			System.out.println("data "+remotedata);
            			 //MSDB.printRs(remotedata);
            			//ResultSet rs,String driver,String connect_string,String userid,String password,String tablename 
            			MSDB.InsertRs(remotedata,temp_driver,temp_connect,temp_userid,temp_password,"BusA1_Log");
            		
            		}
            		else{
            			System.out.println(" Cannot connect to remote DB ");
            		}
            		
        	    }
        	    	
        	    Thread.sleep(1000*60);
    	      } catch (Exception e) {
    	    	  System.out.println("Error in work 4");
    	        System.err.println(e);
    	      }
			  }
		  } 
    } 
	
	public static class Work5 implements Runnable {  		//Check IPC Status
        private int id;  
      
        public Work5 (int id) {  
            this.id = id;  
        }  
      
        public void run() { 
        	System.out.println("IPC Online Status ");
        	//MSDB mssql=new MSDB();
			  while(true){				  
        	try {
        		
        		
        		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    Date date = new Date();        	    
        	    int int_minutes=date.getMinutes();
        	    //System.out.println("Minutes "+int_minutes);
        	    
        	    String temp_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
        	    String temp_connect="";
        		
        	    String[] IPCIP =  MSDB.getIPCIP();
        	    
        	    if(int_minutes%5==0 ){  // every five minutes 
        	    	
        	    	System.out.println(date+" IPC Status Start Checking");
        	    	
        	    	
        	    	try{
        	    		Server_UI.btnNewButton_6.doClick();
        	    		
        	    		Thread.sleep(1000*30);
        	    		//PingIP.fullconnection
        	    		//PingIP.zeroconnection
        	    		
        	    		
        	    		for(String a:IPCIP){
        	    		
        	    			if(PingIP.fullconnection.contains(a)){
        	    				MSDB.updateIPCStatus(a,9);
        	    				MSDB.updateIPCOnOff(a,5);
        	    			}
        	    			
        	    			if(PingIP.zeroconnection.contains(a)){
        	    				MSDB.updateIPCStatus(a,7);
        	    				MSDB.updateIPCOnOff(a,4);
                    			
        	    			}
        	    			
        	    		}
        	    		
        	    	}catch(Exception e){
        	    		e.printStackTrace();
        	    		System.out.println(" IPC Status Checking Error");
        	    	}
        	    	
        	  
            		
        	    }
        	    	
        	    Thread.sleep(1000*55);
    	      } catch (Exception e) {
    	    	  System.out.println("Error in work 4");
    	        System.err.println(e);
    	      }
			  }
		  } 
    } 
	
	
	
}
