package mikeyinc.ui;

import mikeyinc.utilities.Globals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestUi {
	
	/*All methods can accept a driver and/or a url to work on.*/
	/*This way we don't have to create a driver for each test.*/
	public void testTitle(WebDriver driver) {
		/*Every test should have a title.*/
		/*Print this title for both success and failure as shown below.*/
		String testTitle = "Checking page title.";	
        
		/*------This block will execute your test logic--------*/
        String expectedTitle = "Tasteleech";
        String actualTitle = "";               
        actualTitle = driver.getTitle();
        
        /*Every test case can end with an if block to check for success or failure*/
        if (actualTitle.contentEquals(expectedTitle)){
        	/*On passing test print title.*/
            System.out.println("Passed -> "+testTitle);
        } else {
        	/*On test failure print title and throw runtime error.*/
        	System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();        
        }
        /*------/This block will execute your test logic--------*/       
        
    }
	
	
	public void hideInstructions(WebDriver driver){
		
		String testTitle = "Checking 'Hide Instructions' link'.";
		
		boolean instsDisplayedBefore = driver.findElement(By.id("collapseOne")).isDisplayed();		
		
		if(instsDisplayedBefore){
		
			driver.findElement(By.linkText("hide instructions")).click();		
			boolean instsDisplayedAfter = driver.findElement(By.id("collapseOne")).isDisplayed();
			Globals.waitForSeconds(2);
			if(!instsDisplayedAfter){
				System.out.println("Passed -> "+testTitle);
			}else{
				System.out.println("Error in =>> "+testTitle);
	        	throw new RuntimeException();
			}
		
		}
		
	}
	
	
	public void playerLoad(WebDriver driver){
		
		String testTitle = "Checking if song player is loaded or not.";
		
		driver.switchTo().frame("sc-widget");
		boolean playerVisible = driver.findElement(By.id("widget")).isDisplayed();
		driver.switchTo().defaultContent();
		if (playerVisible){
        	System.out.println("Passed -> "+testTitle);
        } else {        	
        	System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();        
        }
	}
	
	

}
