import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpMultipartUpload {
    static String boundary = "abcde12345";
    static String prefix = "--";
    static String newLine = "\r\n";
    static boolean temp=false;    
    //static String filename="";
    //static String filepath="";
    
    HttpMultipartUpload(String filename,String filepath){
    	
    	//this.filename = filename;
    	//this.filepath = filepath;
    	
    }
    
    public static void main(final String args[]) {
    	
    	//MSDB mssql=new MSDB();
    	
    	//String[] filepath=mssql.getFilePath();
    	//HttpMultipartTest temp = new HttpMultipartTest("test.txt","C://");
    	//HttpMultipartTest temp = new HttpMultipartTest(filepath[0],filepath[1]);
    	
        System.out.println(Upload("127.0.0.1:81","C://","test.txt"));
        //System.out.println(temp);
        
    }

    static boolean Upload(String destination,String filepath,String filename) {
    	
    	temp=false;
    	
        try {
            //URL url = new URL("http://10.36.90.1:9001/uploadfile");
            URL url = new URL("http://"+destination+"/uploadfile");   
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-type", "multipart/form-data;boundary=" + boundary);
            ConfigHttpMultipart(connection.getOutputStream(),filepath,filename);
            InputStream ins = connection.getInputStream();
            byte[] b = readBuffer(ins);
            System.out.println(new String(b));
        } catch (MalformedURLException e) {
            System.out.println(" url error! ");
        } catch (IOException e) {
            System.out.println(" io error! ");
        }
        return temp;
    }

    private static void ConfigHttpMultipart(final OutputStream out,String filepath,String filename) {
        StringBuffer params = new StringBuffer();
        params.append(prefix + boundary + newLine);
        params.append("Content-Disposition: form-data; name=\"jsonData\"");
        params.append(newLine + newLine);
        String jsonData = "{\"test\":\"test message!\"}";
        params.append(jsonData);
        params.append(newLine);
        params.append(prefix + boundary + newLine);
        //params.append("Content-Disposition: form-data; name=\"signature\"; filename=\"BusPriority_IPC.bin\"");
        params.append("Content-Disposition: form-data; name=\"signature\"; filename=\""+filename+"\"");
        params.append(newLine);
        params.append("Content-Type: image/pjpeg");
        params.append(newLine + newLine);
        //File file = new File("C://BusPriority_IPC.bin");
        File file = new File(filepath+filename);
        
        try {
            InputStream in = new FileInputStream(file);
            out.write(params.toString().getBytes());
                     
            out.write(readBuffer(in));            
            out.write(newLine.getBytes());
            out.write((prefix + boundary + prefix + newLine).getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println(" no file! ");
        } catch (IOException e) {
            System.out.println(" io error! ");
        }
    }

    public static byte[] readBuffer(final InputStream ins) throws IOException {
        byte b[] = new byte[1024];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int len = 0;
        while ((len = ins.read(b)) != -1) {
                        
            stream.write(b, 0, len);
            //System.out.println(stream.toString());
            if(stream.toString().contains("true")){
            	temp=true;
            }
            	
        }
        
        return stream.toByteArray();
    }
}