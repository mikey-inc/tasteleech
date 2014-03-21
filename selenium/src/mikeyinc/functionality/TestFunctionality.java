package mikeyinc.functionality;

import java.util.concurrent.TimeUnit;

import mikeyinc.utilities.Globals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestFunctionality {
	
	
	public void getNewTracks(WebDriver driver){
		
		String testTitle = "Checking if new tracks are fetched or not when 'Get New Tracks' is clicked'.";    	
    	
    	int rowCountBefore = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		System.out.println(rowCountBefore);
		
		driver.findElement(By.id("moreTracksButton")).click();		
    	Globals.waitForSeconds(10);
    	
    	String colorAfterClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");
    	System.out.println(colorAfterClick);
    	
    	//rgba(250, 167, 50, 1) is orange
    	//rgba(81, 163, 81, 1) is green, button can be either orange or green
    	if(colorAfterClick.equalsIgnoreCase("rgba(250, 167, 50, 1)") ||  colorAfterClick.equalsIgnoreCase("rgba(81, 163, 81, 1)")){
    		
    		int rowCountAfter = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
    		System.out.println(rowCountAfter);
    		
    		if(rowCountAfter > rowCountBefore){
    			System.out.println("Passed -> "+testTitle);
    		}else{
    			System.out.println("Error in =>> "+testTitle);
            	throw new RuntimeException();
    		}
    		
    		
    	}else{
    		System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
    	}
		
		
	}
	
	public void fbLogin(WebDriver driver){
		
		String testTitle = "Checking login with facebook and fetching of liked songs.";
		
		driver.findElement(By.partialLinkText("Click Here To Login and Get Started")).click();
		Globals.waitForSeconds(4);
		driver.findElement(By.partialLinkText("Click here to connect your Soundcloud account and complete Step 1")).click();
		String someTitle = null;
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Authorize access to your account on SoundCloud - Create, record and share your sounds for free")){
		    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		    	driver.findElement(By.partialLinkText("Sign in with Facebook")).click();
		    	//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		    	driver.findElement(By.id("email")).clear();
		    	driver.findElement(By.id("email")).sendKeys("dipuranjitsahoo@gmail.com");
		    	driver.findElement(By.id("pass")).clear();
		    	driver.findElement(By.id("pass")).sendKeys("ph99720no31323");
		    	driver.findElement(By.id("u_0_1")).click();
		    }		    
		}
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Tasteleech")){
		    	
		    	/*Waiting like this is not the most efficient way.
		    	 * I would like to have an improved option here like FluentWait.*/
		    	Globals.waitForSeconds(10);		    	
		    
		    	int rowCount=driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();		
				
				if(rowCount > 0){
					System.out.println("Passed -> "+testTitle);
				}else{		        
		        	System.out.println("Error in =>> "+testTitle);
		        	throw new RuntimeException();        
		        }											
		    }		    
		}		
	}
	
	
	public void checkSeedAddition(WebDriver driver){
		
		String testTitle = "Checking effect of clicking on seed songs and button color.";		
		
		String colorBeforeClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");		
		//rgba(218, 79, 73, 1) is red on windows
		//rgba(189, 54, 47, 1) is red on linux. I am not sure why it sees a different hue of red on linux.
		if(colorBeforeClick.equalsIgnoreCase("rgba(218, 79, 73, 1)") || colorBeforeClick.equalsIgnoreCase("rgba(189, 54, 47, 1)")){
			
			WebElement cellFour=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[1]/td[4]"));			
			driver.findElement(By.partialLinkText(cellFour.getText())).click();
			
			WebElement cellOne=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[2]/td[4]"));			
			driver.findElement(By.partialLinkText(cellOne.getText())).click();		
			
			WebElement cellTwo=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[3]/td[4]"));			
			driver.findElement(By.partialLinkText(cellTwo.getText())).click();		
			
			WebElement cellThree=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[4]/td[4]"));			
			driver.findElement(By.partialLinkText(cellThree.getText())).click();
			
			Globals.waitForSeconds(5);
							
			String colorAfterClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");	
			
			//rgba(91, 183, 91, 1) is green
			if(colorAfterClick.equalsIgnoreCase("rgba(91, 183, 91, 1)")){
				System.out.println("Passed -> "+testTitle);
			}else{		        
	        	System.out.println("Error in =>> "+testTitle);
	        	throw new RuntimeException();        
	        }
			
		}else{		        
        	System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();        
        }		
		
	}	

}
