import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemLogger {

	  private static final Logger logger = LogManager.getLogger("Tainan");
	 
	    
	    public static void WriteLog(String info) {
	    	 logger.info(info);
	    }
}
