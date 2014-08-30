package mikeyinc.utilities;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	/*If zero is returned then song was not found.*/
	public static int findPrefListSongRowByTitle(String songTitle, WebDriver driver){
		WebDriverWait wait;		
		int row = 0;
		//WebDriver driver = ffDriver;
		String prefSongTitle = null;		
		int prefListRowCount = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		//System.out.println("from globals pref count "+prefListRowCount);
		//WebElement titleCell
		for(int i = 1;i <= prefListRowCount; i++){
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]/a")));
			prefSongTitle=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]")).findElement(By.tagName("a")).getText();
			//prefSongTitle = titleCell.findElement(By.tagName("a")).getText();
			//System.out.println(prefSongTitle);
			if(prefSongTitle.equalsIgnoreCase(songTitle)){
				//System.out.println("found in globals prefList");				
				row = i;
				break;
			}
		}
		
		return row;
	}
	
	/*Generate random number between a range. Ranges are inclusive.*/
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
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
