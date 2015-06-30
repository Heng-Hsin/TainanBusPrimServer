
import static java.lang.Math.pow;

import java.util.ArrayList;

public class Protocol implements java.io.Serializable
{	
	private final static byte DLE = (byte) 0xAA;
	private final static byte STX = (byte) 0xBB;
	private final static byte ETX = (byte) 0xCC;
	private final static byte ACK = (byte) 0xDD;
	private final static byte NAK = (byte) 0xEE;		
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	
	public static String bytesToHex(byte[] bytes) {						//Convert byte array to hexString
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
			
	public static int byteToInt(byte[] bytes,int index1,int index2) { 	//convert 2 byte arrays to integers 
	    int number=0;
	    for(int i=index1;i<=index2;i++){
	    	
	    		if(bytes[i]>=0 && bytes[i]<=127 && i==index2)
	    			number=number+(int)bytes[i];
	    		else if(bytes[i]<=0 && i==index2)	    			
	    			number=number+(int)bytes[i]+256;	    	
	    		else if(bytes[i]>=0 && bytes[i]<=127)
	    			number=number+(int)bytes[i]*(int)pow(256, index2-i);
	    		else if(bytes[i]<=0)	    			
	    			number=number+((int)bytes[i]+256)*(int)pow(256, index2-i);
	    		else;
	}
	    return number;
	}
	
	
	public static int byteToInt2(byte bytes) { 				// convert single byte to number
	    int number=0;
	    
	    		if(bytes>=0 && bytes<=127)
	    			number=(int)bytes;
	    		else 	    			
	    			number=(int)bytes+256;	    		    		
	    		
	    return number;
	}
	
	public static int byteTime(byte bytes) { 				
	    int number=0;
	    
	    		if(bytes<10)
	    			number=(int)bytes;
	    		else 	    			
	    			number=(int)(bytes-6);	    		    		
	    		
	    return number;
	}
	
		
	
	public static byte[] intToByte(byte[] bytes,int value,int index1,int index2) { //inserting large integers into byte arrays 
		
		if(value<256)
			bytes[index2]=(byte)value;		
		else if(value>255){
			bytes[index1]=(byte)(value/255);
			bytes[index2]=(byte)(value%255);
						}
	    
	    return bytes;
	}

	public static boolean checkCKS(byte[] bytes) {   			// Check the messages CKS	
		int cks=bytes[0]^bytes[1];
		for(int j=2;j<bytes.length-1;j++)
			cks =cks^bytes[j];
		if((byte)cks==bytes[bytes.length-1])
			return true;
		else
			return false;		
			}
	
	public static boolean NotACKNAK(byte[] bytes) {   			// Check the messages CKS	
				
		if(DLE==bytes[0] && STX==bytes[1])
			return true;
		else
			return false;		
			}	
	
public static byte[] CKS(byte[] bytes) {   						// calculate message CKS		
	int cks=bytes[0]^bytes[1];
	for(int j=2;j<bytes.length-1;j++)
		cks =cks^bytes[j];
	bytes[bytes.length-1]=(byte)cks;
	//System.out.println("CKS ="+bytes[bytes.length-1]);
	  return bytes;
		}

public static String GetAddr(byte[] bytes){    				 // get the address in the message
	String addr="";
	byte[] addrArray={bytes[3],bytes[4]};
	addr=bytesToHex(addrArray);
	
	return addr;
}

public static boolean ChkAddr(byte[] bytes,String trueaddr){    				 // Compare address 
	
	byte[] addrArray={bytes[3],bytes[4]};
	String addr=bytesToHex(addrArray);
	if(addr.equals(trueaddr))
		return true;
	else
		return false;
	
}

public static String GetSeq(byte[] bytes){    				 // get the address in the message
	String addr="";
	byte[] addrArray={bytes[2]};
	addr=bytesToHex(addrArray);
	
	return addr;
}

public static byte[] ChangeAddr(byte[] bytes,String target){  //change the address of the message
	byte[] temp = hexStringToByteArray(target);
	bytes[3]=temp[0];
	bytes[4]=temp[1];
	
	return bytes;
}


public static byte alterbytebit(byte bitbyte,String control) {  //Change the bit values through the String 
	bitbyte=0;
	control=new StringBuilder(control).reverse().toString();
	
	for(int i=0;i<8;i++){
		if(control.substring(i,i+1).matches("1"))	
			bitbyte = (byte) (bitbyte|(1<<i));		//z = (byte) (z | (1 << 7));  //bit8 to 1 				
	}
	//System.out.println("Altered Byte "+byteToBit(bitbyte)+" val "+bitbyte);
  return bitbyte;
}


public static String byteToBit(byte b) {  							//show bytes bit value in String
    return ""  
            + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
            + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
            + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
            + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
} 



public static String ackOrNak(byte[] response){			//Process the ACK and NAK messages
	String report=null;
	String message =bytesToHex(response);
	String addr=message.substring(6,10);
	if(checkCKS(response)){
	if(response[1]==ACK && response[6]==(byte)0x08)			//Check if the ACK message LEN is 8 	
		report=report+"Device "+addr+" Has received message sequence "+response[3];	//System.out.println("Device "+addr+" Has received message sequence "+response[3]);
	else if(response[1]==NAK && response[6]==(byte)0x09){	//Check if the NAK message LEN is 9 
		
		System.out.println("Device "+addr+" Has an error "+response[3]);
		int error=response[7];
		String ckser ="CKS 嚙踝蕭嚙羯 ";
		String header ="嚙碼嚙踝蕭 嚙踝蕭嚙羯 ";
		String addrer ="ADDR 嚙踝蕭嚙羯 ";
		String lener ="LEN 嚙踝蕭嚙羯 ";		
		switch(error){
		case 1:
			report=report+ckser;					//System.out.println(ckser);
			break;
		case 2:
			report=report+header;					//System.out.println(header);
			break;
		case 3:
			report=report+ckser+header;				//System.out.println(ckser+header);
			break;
		case 4:
			report=report+addrer;					//System.out.println(lener);
			break;
		case 5:
			report=report+ckser+addrer;				//System.out.println(ckser+lener);
			break;
		case 6:
			report=report+header+addrer;				//System.out.println(header+lener);
			break;
		case 7:
			report=report+ckser+header+addrer;			//System.out.println(ckser+header+addrer);
			break;
		case 8:
			report=report+lener;						//System.out.println(lener);
			break;
		case 9:
			report=report+ckser+lener;						//System.out.println(ckser+lener);
			break;
		case 10:
			report=report+header+lener;						//System.out.println(header+lener);
			break;
		case 11:
			report=report+ckser+header+lener;				//System.out.println(ckser+header+lener);
			break;
		case 12:
			report=report+addrer+lener;				//System.out.println(addrer+lener);
			break;
		case 13:
			report=report+ckser+addrer+lener;		//System.out.println(ckser+addrer+lener);
			break;
		case 14:
			report=report+addrer+header+lener;				//System.out.println(addrer+header+lener);
			break;
		case 15:
			report=report+ckser+header+addrer+lener;		//System.out.println(ckser+header+addrer+lener);
			break;
			default:
				report=report+"error";			//System.out.println("error");						
		}
	}
	return report;
	}
	else
		return null;
}

public static int[] bitShowInt(int type){	//Return a int[] showing bit value of a integer For signalMap and Signal status 
	int[] map=new int[8];
	byte bitbyte= (byte)type;
	String signal=byteToBit(bitbyte);
	for(int i=0;i<8;i++){
		if(signal.substring(i,i+1).matches("1"))
			map[i]=1;
		else
			map[i]=0;		
	}
	return map;
}

public static String bitShowString(int type){			//For signalMap and Signal status 
	
	byte bitbyte= (byte)type;
	String signal=byteToBit(bitbyte);
	
	return signal;
}

public static byte[] pureMessage(byte[] message){
byte[] purebyte = new byte[message.length-10]; //AA BB 01 00 3F 00 0E 5FC41700 AACCCB
int count=0;

for(int i=7;i<message.length-3;i++){	
purebyte[count]=message[i];
count++;
}
return purebyte;
}

public static String zerof14(int settime){              //setting hardware response cycle   settime 0~5
	String command;
	command="0F14";
	command=command+"0"+Integer.toString(settime);
	return command;
}

public static String zerofc4(byte[] report){              
	
	if((report[0]==(byte)0x0F) && (report[1]==(byte)0xC4)){
		String reportStatus=null;
	if(report[2]==0)
		reportStatus="嚙緩嚙賡停嚙踝蕭^嚙踝蕭";
	else if(report[2]==1)
		reportStatus="嚙瘠嚙踝蕭^嚙踝蕭";
	else if(report[2]==2)
		reportStatus="嚙瘠嚙踝蕭2嚙稷嚙踝蕭";
	else if(report[2]==3)
		reportStatus="嚙瘠嚙踝蕭5嚙稷嚙踝蕭";
	else if(report[2]==4)
		reportStatus="嚙瘠嚙踝蕭嚙踝蕭嚙稷嚙踝蕭";
	else if(report[2]==5)
		reportStatus="嚙瘠5嚙踝蕭嚙踝蕭嚙稷嚙踝蕭";
	
	return reportStatus;
	}
	else
		return null;
}

/*
public static int fivef87(byte[] report){              
	
	if((report[0]==(byte)0x5F) && (report[1]==(byte)0x87)){
		int reportStatus=0;
	if(report[2]==1)
		reportStatus=1;
	else if(report[2]==2)
		reportStatus=2;
	else if(report[2]==3)
		reportStatus=3;
	
	
	return reportStatus;
	}
	else
		return 0;
}
*/

public static String[] fivef87(byte[] report){

	try{
	String[] ReportMSG = new String[2];
	if((report[0]==(byte)0x5F) && (report[1]==(byte)0x87)){
		ReportMSG[0]="0";
		
	
	ReportMSG[0] =Integer.toString(report[2]); ;
		
	byte[] destination = new byte[2];
	destination[0]= report[3];
	destination[1]= report[4];
	
	ReportMSG[1]=bytesToHex(destination);
	
	return ReportMSG;
	}
	else
		return null;
	}catch(Exception e){
		System.out.println("Error in fivef87");
		System.err.println(e);
		
		return null;
	}
}

public static String[] fivefA0(byte[] report){


	try{
	String[] ReportMSG = new String[2];
	
	if((report[0]==(byte)0x5F) && (report[1]==(byte)0xA0)){
		ReportMSG[0]="0";
		
	
	ReportMSG[0] =Integer.toString(report[2]); ;
		
	byte[] destination = new byte[2];
	destination[0]= report[3];
	destination[1]= report[4];
	
	ReportMSG[1]=bytesToHex(destination);
	
	return ReportMSG;
	}
	else
		return null;
	}catch(Exception e){
		System.out.println("Error in fivef87");
		System.err.println(e);
		
		return null;
	}
}

public static String zerof04(byte[] report){
	String hardwarestatus1,hardwarestatus2,reportstatus=null;
	try{
		if((report[0]==(byte)0x0F) && (report[1]==(byte)0x04)){
			hardwarestatus1=byteToBit(report[2]);
			hardwarestatus2=byteToBit(report[3]);
			
			for(int i=0;i<8;i++){
				if(hardwarestatus1.substring(i,i+1).matches("1")){
				if(i==0)
					reportstatus=reportstatus	+	" CPU Error";			//System.out.println("CPU Error");
				else if(i==1)
					reportstatus=reportstatus	+	" Memory Error";		//System.out.println("Memory Error");
				else if(i==2)
					reportstatus=reportstatus	+	" Timer Error";			//System.out.println("Timer Error");
				else if(i==3)
					reportstatus=reportstatus	+	" Watch dog timer Error";//System.out.println("Watch dog timer Error");
				else if(i==4)
					reportstatus=reportstatus	+	" Power Error";			//System.out.println("Power Error");
				else if(i==5)
					reportstatus=reportstatus	+	" I/O Error";			//System.out.println("I/O Error");
				else if(i==6)
					reportstatus=reportstatus	+	" Signal driver Error";	//System.out.println("Signal driver Error");
				else 
					reportstatus=reportstatus	+	" Signal head Error";	//System.out.println("Signal head Error");
				}
				
				if(hardwarestatus2.substring(i,i+1).matches("1")){
					if(i==0)
						reportstatus=reportstatus	+	" Communication Connect"; //System.out.println("Communication Connect");
					else if(i==1)
						reportstatus=reportstatus	+	" Cabinated Opened"; 	  //System.out.println("Cabinated Opened");
					else if(i==2)
						reportstatus=reportstatus	+	" Timing Plan Error";	  //System.out.println("Timing Plan Error");
					else if(i==3)
						reportstatus=reportstatus	+	" Signal Conflict Error"; //System.out.println("Signal Conflict Error");
					else if(i==4)
						reportstatus=reportstatus	+	" Signal Power Error"; //System.out.println("Signal Power Error");
					else if(i==5)
						reportstatus=reportstatus	+	" Timing Plan on Transition"; //System.out.println("Timing Plan on Transition");
					else if(i==6)
						reportstatus=reportstatus	+	" Controller Ready"; 		 //System.out.println("Controller Ready");
					else
						reportstatus=reportstatus	+	" Command Line Bad";		//System.out.println("Command Line Bad");
					}
			}
			return reportstatus;
			
		}else return null;
								
	}catch(Exception e){
		System.out.println("Error in zerof04");
		
			return null;
	}
	
	
}
public static String fivef10(int controlstrategy,int time){		///	controlstrategy 1,2,4,8,16,32,64,128 time 0~255
	String message=null;
	
	if(controlstrategy>128 || controlstrategy<0 || time>255 || time<0)
		System.out.println("嚙諸數設嚙緩嚙磕嚙碼嚙範嚙踝蕭");
	else{
		message="5F10";		
		message=message+Integer.toHexString(controlstrategy);
		message=message+Integer.toHexString(time);	
	}	
	return message;
}

public static String fivefc0(byte[] message){	/////not sure about this one
	
	String report="";
	
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC0){
				
		if(message[2]==1)
			report=report+" 嚙緩嚙褕梧蕭嚙踝蕭";		
		else if(message[2]==2)
			report=report+" 嚙褊態嚙踝蕭嚙踝蕭";		//System.out.println("嚙褊態嚙踝蕭嚙踝蕭");
		else if(message[2]==4)
			report=report+" 嚙踝蕭嚙篆嚙踝蕭嚙�";		//System.out.println("嚙踝蕭嚙篆嚙踝蕭嚙�");
		else if(message[2]==8)
			report=report+" 嚙踝蕭嚙踝蕭嚙踝蕭嚙�";		//System.out.println("嚙踝蕭嚙踝蕭嚙踝蕭嚙�");
		else if(message[2]==16)
			report=report+" 嚙褕相梧蕭嚙踝蕭";		//System.out.println("嚙褕相梧蕭嚙踝蕭");
		else if(message[2]==32)
			report=report+" 嚙磐嚙褕梧蕭嚙踝蕭";		//System.out.println("嚙磐嚙褕梧蕭嚙踝蕭");
		else if(message[2]==64)
			report=report+" 觸嚙褊梧蕭嚙踝蕭";		//System.out.println("觸嚙褊梧蕭嚙踝蕭");
		else if(message[2]==-128)
			report=report+" 觸嚙褊梧蕭嚙踝蕭";		//System.out.println("嚙磅嚙諂賂蕭嚙線嚙踝蕭嚙踝蕭");
		int time=0;
		
		if(message[3]>=0 && message[3]<=127)
			time=(int)message[3];		
		else			
			time=(int)message[3]+256;
			
		report=report+" "+Integer.toString(time);
		return report;
	}
	else 
		return null;
	
}

public static int[] fivefc4(byte[] message){///tp basic parameters SubphaseCount(MinGreen MaxGreen Yellow Allred PedGreenFlash PedRed)
	String report="";
	int[] tp = new int[((message[3])*6)];
	int[] tp2 = new int[2+((message[3])*6)];
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC4){
		
		tp2[0]=(int)message[2];
		tp2[1]=(int)message[3];
		
		for(int i=0;i<message[3];i++){
			int maxgreen=0;
			int mingreen=0;
			maxgreen=byteToInt(message,i*7+5,i*7+6);
			mingreen=byteToInt(message,i*7+4,i*7+4);
			/*																	
			System.out.println("Mingreen "+i+" = "+mingreen);						
			System.out.println("Maxgreen "+i+" = "+maxgreen);
			System.out.println("Yellow "+i+" = "+message[i*7+7]);
			System.out.println("AllRed "+i+" = "+message[i*7+8]);
			System.out.println("PedGreenFlash "+i+" = "+message[i*7+9]);
			System.out.println("PedRed "+i+" = "+message[i*7+10]);	
			*/
			tp[i*6]=mingreen;
			tp[i*6+1]=maxgreen;
			tp[i*6+2]=(int)message[i*7+7];
			tp[i*6+3]=(int)message[i*7+8];
			tp[i*6+4]=(int)message[i*7+9];
			tp[i*6+5]=(int)message[i*7+10];
			/*
			System.out.println("Mingreen tp"+i+" = "+tp[i*6]);						
			System.out.println("Maxgreen tp"+i+" = "+tp[i*6+1]);
			System.out.println("Yellow tp"+i+" = "+tp[i*6+2]);
			System.out.println("AllRed tp"+i+" = "+tp[i*6+3]);
			System.out.println("PedGreenFlash tp"+i+" = "+tp[i*6+4]);
			System.out.println("PedRed tp"+i+" = "+tp[i*6+5]);
			*/	
		}
		
		for(int z=0;z<tp.length;z++){
		tp2[z+2]=tp[z];
		}
		/*
		for(int i=0;i<tp.length;i++)
			report=report+" "+Integer.toString(tp[i]);		
		return report;
		*/
		return tp2;
	}
	else
	return null;
}
public static String fivef14(int[] command){		///	5F14 int message to byte message
//doesn't seem to need any function
	return null;
}

public static int[] fivefc5(byte[] message){
	
	int tpc[]= new int[6+message[5]];
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC5 && message[5]!= (byte)0x00){
		tpc[0]=message[2]; 		//planid
		tpc[1]=message[3];		//direct
		tpc[2]=byteToInt2(message[4]);		//phaseorder ****  phaseorder data in DB are in hex string
		tpc[3]=message[5]; 		//subphasecount
		try{
		for(int i=0;i<tpc[3];i++){
			tpc[i+4]=byteToInt(message,6+(i*2),7+(i*2)); 	//green			
		}
		}catch(Exception e){
			return null;
		}
		tpc[tpc.length-2]=byteToInt(message,message.length-4,message.length-3);		//cycletime
		tpc[tpc.length-1]=byteToInt(message,message.length-2,message.length-1);		//offset
		
	return tpc;
	}
	return null;
}



public static byte[] fivef15(int[] message){
	byte bytemessage[]= new byte[(message[3]*2)+10];
	
	bytemessage[0]=(byte) 0x5F;             //the int message does not include the header 5F15
	bytemessage[1]=(byte) 0x15;
	bytemessage[2]=(byte) message[0];		//Planid
	bytemessage[3]=(byte) message[1];		//Direct
	bytemessage[4]=(byte) message[2];		//Phaseorder
	bytemessage[5]=(byte) message[3];		//subphasecount
	
	for(int i=0;i<message[3];i++){
		bytemessage=intToByte(bytemessage, message[4+i],6+(i*2),7+(i*2));				
	}
	
	bytemessage=intToByte(bytemessage, message[message.length-2],bytemessage.length-4,bytemessage.length-3);	
	bytemessage=intToByte(bytemessage, message[message.length-1],bytemessage.length-2,bytemessage.length-1);	
		
	return bytemessage;
}


public static int[] fivefc3(byte[] message){ 	// signalmap and signal status need the bitsql function to interpret data
			
	if(message[0]==(byte)0x5F && message[1]==(byte)0xc3){    
	int [] report = new int[4+message[4]*message[5]];
	
	report[0]=byteToInt2(message[2]);	//PhaseOrder	
	report[1]=byteToInt2(message[3]);	//SignalMap
	report[2]=byteToInt2(message[4]);	//SignalCount
	report[3]=byteToInt2(message[5]);	//SubphaseCount
	int counter=0;
	for(int i=0;i<message[5];i++){
		for(int j=0;j<message[4];j++){
			report[4+counter]=byteToInt2(message[counter]);	//signal status
		counter++;
		}		
	}		
	return report;
	}
	else return null;
}


public static int[] fivef03(byte[] message){ // signalmap and signal status need the bitsql function to interpret data
	
	if(message[0]==(byte)0x5F && message[1]==(byte)0x03){    
	int [] report = new int[7+message[4]];
	
	report[0]=byteToInt2(message[2]);	//PhaseOrder	
	report[1]=byteToInt2(message[3]);	//SignalMap
	report[2]=byteToInt2(message[4]);	//SignalCount
	report[3]=byteToInt2(message[5]);	//SubphaseID
	report[4]=byteToInt2(message[6]);	//StepID
	
	if(message[6]==(byte)0x9F)
		System.out.println("All Red 3 seconds");
	else if(message[6]==(byte)0xBF)
		System.out.println("Constant Plan Flashing Yellow");
	else if(message[6]==(byte)0xDF)
		System.out.println("At site Controlling Flashing Yellow");
	else if(message[6]==(byte)0xFF)
		System.out.println("Error in Plan Flashing Yellow");
	else if(message[6]==(byte)0xAF)
		System.out.println("Error in Plan All Red");
	else if(message[6]==(byte)0xCF)
		System.out.println("Green Light Conflict Flashing Yellow");
	else if(message[6]==(byte)0xEF)
		System.out.println("Power Error");
	
	report[5]=byteToInt(message,7,8);		//stepsec
		
	int counter=0;	
		for(int j=0;j<message[4];j++){
			report[4+counter]=byteToInt2(message[counter]);	//signal status
		counter++;
		}					
	return report;
	}
	else return null;
}

public static int[] fivefc6(byte[] message){ 
	//5fc6 01 05 060001 090002 100001 130002 170003 07 01020304050607
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC6){    
	int [] report1 = new int[3+message[3]*3-1];
	
	report1[0]=byteToInt2(message[2]);	//SegmentType	
	report1[1]=byteToInt2(message[3]);	//SegmentCount
	
	int count1=0;
	for(int i =0;i<message[3];i++){
		report1[2+i*3]	= (message[i*3+4]); //hour
		report1[3+i*3]	= (message[i*3+5]); //min
		report1[4+i*3]	= (message[i*3+6]); //planid
		//System.out.println(report1[2+i*3]+" "+report1[3+i*3]+" "+report1[4+i*3]);
		count1++;
	}
	
	int count2=0;
	int [] report2 = new int[message[4+count1*3]+1];     //NumWeekDay
	
	report2[0]= message[4+count1*3];
			
	for(int i =1;i<=message[4+count1*3];i++){
		report2[i]	= byteToInt2(message[4+count1*3+i]);
		
		count2++;
	}
	int total=report1.length+report2.length;
	
	int [] reportend = new int[total]; 
	
	for(int i=0;i<total;i++){
		if(i<report1.length)
			reportend[i]=report1[i];
		else
			reportend[i]=report2[i-report1.length];
	}

	return reportend;

	}
	else return null;
}

public static int[] fivefc6second(byte[] message){ 
	//5fc6 01 05 060001 090002 100001 130002 170003 07 01020304050607
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC6){    
	int [] report1 = new int[3+message[3]*3-1];
	
	report1[0]=byteToInt2(message[2]);	//SegmentType	
	report1[1]=byteToInt2(message[3]);	//SegmentCount
	
	
	for(int i =0;i<message[3];i++){
		report1[2+i*3]	= (message[i*3+4]); //hour
		report1[3+i*3]	= (message[i*3+5]); //min
		report1[4+i*3]	= (message[i*3+6]); //planid
		//System.out.println(report1[2+i*3]+" "+report1[3+i*3]+" "+report1[4+i*3]);
		
	}
	


	return report1;

	}
	else return null;
}

public static int[] fivefc6plan(byte[] message){ 
	//5fc6 01 05 060001 090002 100001 130002 170003 07 01020304050607
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC6){    
	int [] report1 = new int[message[3]];
	
		
	
	for(int i =0;i<message[3];i++){
		
		report1[i]	= (message[i*3+6]); //planid
		//System.out.println(report1[2+i*3]+" "+report1[3+i*3]+" "+report1[4+i*3]);
		
	}

	return report1;

	}
	else return null;
}
public static int[] fivefc7(byte[] message){ 
	
	if(message[0]==(byte)0x5F && message[1]==(byte)0xC7){    
	int [] report1 = new int[3+message[3]*3-1];
	
	report1[0]=byteToInt2(message[2]);	//SegmentType	
	report1[1]=byteToInt2(message[3]);	//SegmentCount
	
	if(report1[1]==0){
	int[] rrr={report1[0],0};	
	return rrr;
	}
	int count1=0;
	for(int i =0;i<message[3];i++){
		report1[2+i*3]	= (message[i*3+4]); //hour
		report1[3+i*3]	= (message[i*3+5]); //min
		report1[4+i*3]	= (message[i*3+6]); //planid
		//System.out.println(report1[2+i*3]+" "+report1[3+i*3]+" "+report1[4+i*3]);
		count1++;
	}
	
	int count2=0;
	int [] report2 = new int[6];     
			
			
	for(int i =0;i<6;i++){
		report2[i]	= byteToInt2(message[report1.length+2+i]);
		//System.out.println(report2[i]);
	}
	int total=report1.length+report2.length;
	
	int [] reportend = new int[total]; 
	
	
	
	for(int i=0;i<total;i++){
		if(i<report1.length)
			reportend[i]=report1[i];
		else
			reportend[i]=report2[i-report1.length];
	}
	


	return reportend;

		
	
	}
	else return null;
}




public static void main(String[] args) { 
	
	//byte test[]={(byte)0x5F,(byte)0xC0,(byte)128,(byte)128};
	
	//hardwarecheck(test);
	
	/*byte test[]={(byte)0x5F,(byte)0xC4,(byte)0x01,(byte)0x03,(byte)0x0a,(byte)0x00,(byte)0xD2,(byte)0x04,(byte)0x02,
			(byte)0x07,(byte)0x03,(byte)0x00,(byte)0x00,(byte)0xC8,(byte)0x03,(byte)0x02,(byte)0x00,(byte)0x00,
			(byte)0x0A,(byte)0x00,(byte)0xD2,(byte)0x03,(byte)0x03,(byte)0x07,(byte)0x03};
	*/
	
	//fivefc0(test);
	//int tp[]=fivefc4(test);
	
	//byte test2[]={0,0};
	//int number3=byteToInt(test2,0,1);
	//byte bytes=(byte)128;
	//test2= intToByte(test2,8190,0,1);
	//System.out.println("number3 ="+number3);
	//System.out.println("test2 ="+test2[0]+" "+test2[1]);
	//int message[]={1,1,128,551,8,8,8,8};
	//byte[] byte3 = new byte[4+(1*7)];
	//byte3=fivef14( message);
	//for(int i=0;i<4+(1*7);i++)
	//System.out.println("byte ="+byte3[i]);
	
	//byte test[]={(byte)0x5F,(byte)0xC5,(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x03,(byte)0x00,(byte)0x0A,
			//(byte)0x00,(byte)0x0A,(byte)0x00,(byte)0x0A,(byte)0x00,(byte)0x0A,(byte)0x00,(byte)0x0A};
	
	int message[]={1,1,1,3,300,300,300,300,300};
	//message=fivefc5(test);
	
	//test=fivef15(message);
	byte test2[]=null;
	byte test3[]=null;
	byte test4[]=null;
	byte test5[]=null;
	byte test6[]=null;
	byte test7[]=null;
	byte test8[]=null;
	//System.out.println(zerofc4(test2));
	
	//System.out.println(fivef10(128,255));
	//byte s=(byte)64;
	
	//System.out.println(fivefc0(test2));
	
	//AADD01003F000841AADD01003F000841AABB01003F001C5FC401020C00D2030207030C00D203020703AACCCD
	//5FC501003803004F001D003500B40000
	test2=hexStringToByteArray("AABB01003F001C5FC401020C00D2030207030C00D203020703AACCCD");
	test3=hexStringToByteArray("AABB01003F001C5FC401020C00D2030207030C00D203020703AACB");
	test4=hexStringToByteArray("AABB01003F000E5FC41700AACCCB");
	test6=hexStringToByteArray("AABB01003F00145FC51700000001000C81AACC5C");
	test7=hexStringToByteArray("5FC401030A00D2040207030000C8030200000A00D203030703");
	test8=hexStringToByteArray("5fc7080506000b09000c10000b14000c17000d630701630701");
	
	
	
	//AABB01003F000E5FC41700AACCCB
	
	
	//int[] array=fivefc5(test2);
	GetAddr(test2);
	//byte[] bomb =new byte[10];
	
	
	
	
	
	//fivefc4(test2);
	//for(int i=0;i<array.length;i++)		
	//if(test2.equals(test3)==false)
	System.out.println("5FC601050600020900021000021300021700020701020304050607");
	//System.out.println(bytesToHex(pureMessage(test2)));
	System.out.println(bytesToHex(pureMessage(test6)));
	test6=pureMessage(test6);
	System.out.println("test4 "+test4);
	
	
	int [] temp =fivefc4(test7);
	
	//System.out.print("?? "+fivefc6(test7));
	
	//fivefc6(byte[] message);
	
	for(int i=0;i<temp.length;i++){
		System.out.print(temp[i]+" ");				
	}
	

	

	
	//System.out.println(Integer.toHexString(1));
	
	
}
}