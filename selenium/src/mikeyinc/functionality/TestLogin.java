package mikeyinc.functionality;

import java.util.concurrent.TimeUnit;

import mikeyinc.utilities.Globals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestLogin {
	
	public void googleLogin(WebDriver driver){
		
		String testTitle = "Checking login with google and fetching of liked songs.";
		
		driver.findElement(By.partialLinkText("Click Here To Login and Get Started")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to connect your Soundcloud account and complete Step 1")));
		driver.findElement(By.partialLinkText("Click here to connect your Soundcloud account and complete Step 1")).click();
		String someTitle = null;
		String loginMainHandle = null;
		//Globals.waitForSeconds(20);
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Authorize access to your account on SoundCloud - Create, record and share your sounds for free")){
		    	loginMainHandle = handle;
		    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		    	//driver.findElement(By.partialLinkText("Sign in with Facebook")).click();
		    	driver.findElement(By.className("google-plus-signin")).click();
		    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		    	//Globals.waitForSeconds(20);
		    	
		    	for (String holder : driver.getWindowHandles()) {
				    driver.switchTo().window(holder);
				    someTitle = driver.getTitle();
				    if(someTitle.equalsIgnoreCase("Sign in - Google Accounts")){		    	
				    	
						//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[5]")));
				    	//Globals.waitForSeconds(20);
				    	//int rowCount=driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();		
				    	driver.findElement(By.id("Email")).clear();
				    	driver.findElement(By.id("Email")).sendKeys("jenkinsCImikeyInc@gmail.com");
				    	driver.findElement(By.id("Passwd")).clear();
				    	driver.findElement(By.id("Passwd")).sendKeys("jenkins@gmail");
				    	driver.findElement(By.id("signIn")).click();
						        
				    }											
				 }		    
			}
		    	
		    	System.out.println("oops!!");
		    	
		    		    
		}
		
		
		//for (String handle : driver.getWindowHandles()) {
			Globals.waitForSeconds(5);
			 driver.switchTo().window(loginMainHandle);
			//System.out.println("1");
			    someTitle = driver.getTitle();
			    //System.out.println("2");
			    if(someTitle.equalsIgnoreCase("Authorize access to your account on SoundCloud - Create, record and share your sounds for free")){
			    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			    	driver.findElement(By.className("google-plus-signin")).click();
			    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			    	//Globals.waitForSeconds(20);
			    }	
		//}
		
		
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Tasteleech")){		    	
		    	
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[5]")));
		    	//Globals.waitForSeconds(20);
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


}
