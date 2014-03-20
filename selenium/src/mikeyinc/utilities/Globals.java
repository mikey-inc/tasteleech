package mikeyinc.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Globals {
	
	/*Store all Global values here*/
	public static WebDriver ffDriver = new FirefoxDriver();
	public static String baseUrl = "http://tasteleech.com";
	
	public static void waitForSeconds(int seconds){
		
		seconds = seconds * 1000;
		
		try {
    	    Thread.sleep(seconds);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
		
	}

}
