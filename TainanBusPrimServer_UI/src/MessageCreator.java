


public class MessageCreator {
	final protected static String header ="AABB";
	final protected static String length ="0000";
	final protected static  String ender  ="AACC00";
	private static String Seq;
	private static String Addr;
	private static String Message;
			 
	public MessageCreator(String seq,String addr, String message){	// seq addr message
		this.Seq=seq;
		this.Addr=addr;
		this.Message=message;		
	}
   	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
public static byte[] CKS(byte[] bytes) {   // calculate message cks		
	int cks=bytes[0]^bytes[1];
	for(int j=2;j<bytes.length-1;j++)
		cks =cks^bytes[j];
	bytes[bytes.length-1]=(byte)cks;
	//System.out.println("CKS ="+bytes[bytes.length-1]);
	  return bytes;
		}

public static byte[] messagelength(byte[] bytes) {  //calculate message length
	
		bytes[5]=(byte)(bytes.length/256);
		bytes[6]=(byte)(bytes.length%256);
		//System.out.println("length ="+bytes[5]+bytes[6]);
	
	  return bytes;
	}

	public static byte[] create() {
						 		
		
		//AABB011035000D5F4401AACC44 verified message send UDP to IP = 10.122.1.36  port=31035				    	   
	    
	    String combinemessage=header+Seq+Addr+length+Message+ender;	    	    
	    
	    byte[] entiremessage =hexStringToByteArray(combinemessage);
																		
			entiremessage= messagelength(entiremessage);
			entiremessage= CKS(entiremessage);
			
			//System.out.println(bytesToHex(entiremessage));
			
		return entiremessage;								
	
	}
	
	public static byte[] createpackage(String seq,String addr,String msg){
		
		   String combinemessage=header+seq+addr+length+msg+ender;	    	    
		    
		    byte[] entiremessage =hexStringToByteArray(combinemessage);
																			
				entiremessage= messagelength(entiremessage);
				entiremessage= CKS(entiremessage);
				
				//System.out.println(bytesToHex(entiremessage));
				
			return entiremessage;				
		
	}
	
public static void main(final String args[]) {
   
 		
 		byte[] cmd=createpackage("01","FFFF", "5F1C02014E");  //1.Seq 2.Addr 3.封包內容
 		System.out.println("A.0 Packet  " + bytesToHex(cmd));    //包好的內容 拿這個跟你的比較
 		
 		byte[] cmd2=createpackage("01","2062", "5F190000000000");  //1.Seq 2.Addr 3.封包內容
 		System.out.println("4.0 Packet  " + bytesToHex(cmd2));    //包好的內容 拿這個跟你的比較
 		
 		
 		
 		//請看hexStringToByteArray(String s)   CKS(byte[] bytes)   messagelength(byte[] bytes)  
 		//createpackage(String seq,String addr,String msg)
 		//這4個function 其他的你應該不用參考
	
        
    }
	
	

}