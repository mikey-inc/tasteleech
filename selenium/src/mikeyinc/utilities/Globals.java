package mikeyinc.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Globals {
	
	/*Store all Global values here*/
	public static WebDriver ffDriver = new FirefoxDriver();
	public static String baseUrl = "http://tasteleech.com";
	public static int totalPlaylistResults = 0;
	
	
	
	public static void WaitForAjax(WebDriver driver)	
	{
		if (driver instanceof JavascriptExecutor) {
			//String ab = "true";
			//boolean ac = new Boolean(ab).booleanValue();
			
			String ab = ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0").toString();
			System.out.println(ab);
		}
	    //WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(15));
	    //wait.Until(d => (bool)(d as IJavaScriptExecutor).ExecuteScript("return jQuery.active == 0"));
	}
	
	
	/*Generate random number between a range.*/
	public static int generateRandom(int max){		
		int min = 1;
		int random = (int)(Math.random() * (max - min)) + min;
		return random;
	}
	
	
	/*Delay generator.*/
	public static void waitForSeconds(int seconds){
		
		seconds = seconds * 1000;
		
		try {
    	    Thread.sleep(seconds);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
		
	}

}
