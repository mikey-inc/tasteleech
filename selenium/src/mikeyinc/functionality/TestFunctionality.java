package mikeyinc.functionality;

import java.util.concurrent.TimeUnit;

import mikeyinc.utilities.Globals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestFunctionality {
	
	String songTitle = null;
	
	public void skipColumnZeroButton(WebDriver driver){
		String testTitle = "Checking skip column zero button.";
		String prefSongTitle = null;
		String songScoreBefore = null;
		String songScoreAfter = null;
		String playlistSongTitle = null;		
		WebElement scoreCell = null;
		int prefListRowCount = 0;
		
		/*Check score before clicing.*/
		prefListRowCount = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		//System.out.println("prefListRowCount "+prefListRowCount);
		for(int i = 1;i <= prefListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]"));
			prefSongTitle = titleCell.findElement(By.tagName("a")).getText();
			//System.out.println(prefSongTitle);
			if(prefSongTitle.equalsIgnoreCase(songTitle)){
				//System.out.println("found in prefList");				
				scoreCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[3]"));
				songScoreBefore = scoreCell.findElement(By.tagName("a")).getText();
				//System.out.println("song score "+songScoreBefore);
				break;
			}
		}
		
		/*Click zero button.*/
		int playListRowCount = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		for(int i = 1;i <= playListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[3]"));
			playlistSongTitle = titleCell.findElement(By.tagName("a")).getText();
			//System.out.println(prefSongTitle);
			if(playlistSongTitle.equalsIgnoreCase(songTitle)){
				//System.out.println("found in playlist");
				WebElement skipCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[4]"));			
				skipCell.findElement(By.linkText("0")).click();
				//System.out.println("clicked");
				Globals.waitForSeconds(1);
				break;
			}
		}
		
		/*Check score again.*/		
		for(int i = 1;i <= prefListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]"));
			prefSongTitle = titleCell.findElement(By.tagName("a")).getText();
			//System.out.println(prefSongTitle);
			if(prefSongTitle.equalsIgnoreCase(songTitle)){
				//System.out.println("found in prefList again");				
				scoreCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[3]"));
				songScoreAfter = scoreCell.findElement(By.tagName("a")).getText();
				//System.out.println("song score after"+songScoreAfter);
				break;
			}
		}
		
		int scoreBefore = Integer.parseInt(songScoreBefore);
		int scoreAfter = Integer.parseInt(songScoreAfter);
		if(scoreAfter > scoreBefore){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
		
	}
	
	public void skipColumnMinusButton(WebDriver driver){
		String testTitle = "Checking skip column minus button.";
		
		int prefListCountBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		WebElement titleCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[3]/td[3]"));
		songTitle = titleCell.findElement(By.tagName("a")).getText();
		//System.out.println(songTitle);
		
		WebElement skipColCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[3]/td[4]"));
		skipColCell.findElement(By.linkText("-")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[8]")));
		
		int prefListCountAfter = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		if(prefListCountAfter == prefListCountBefore + 1){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
	}
	
	public void skipColumnPlusButton(WebDriver driver){
		
		String testTitle = "Checking skip column plus button.";
		
		int prefListCountBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();		
		WebElement skipColCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[4]/td[4]"));
		skipColCell.findElement(By.linkText("+")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[7]")));
		
		int prefListCountAfter = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		if(prefListCountAfter == prefListCountBefore + 1){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
	}
	
	
	public void playTrack(WebDriver driver){
		
		String testTitle = "Checking if song is added to prefList when clicking Play on playlist.";
		
		/*Song player is present.*/
		Boolean isPresent = driver.findElements(By.id("playerDiv")).size() > 0;		
		
		int prefListCountBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();		
		
		WebElement playBtnCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[5]/td[5]"));
		playBtnCell.findElement(By.linkText("Play")).click();		
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[6]")));
		int prefListCountAfter = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		
		if(prefListCountAfter == prefListCountBefore + 1){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}	
		
	}
	
	
	public void getNewTracks(WebDriver driver){
		
		String testTitle = "Checking if new tracks are fetched or not when 'Get New Tracks' is clicked.";    	
    	
    	int rowCountBefore = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		//System.out.println(rowCountBefore);
		
		driver.findElement(By.id("moreTracksButton")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='playList']/tbody/tr[15]")));
    	Globals.waitForSeconds(1);
    	
    	String colorAfterClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");
    	//System.out.println(colorAfterClick);
    	
    	//rgba(250, 167, 50, 1) is orange
    	//rgba(81, 163, 81, 1) is green, button can be either orange or green
    	if(colorAfterClick.equalsIgnoreCase("rgba(250, 167, 50, 1)") ||  colorAfterClick.equalsIgnoreCase("rgba(81, 163, 81, 1)")){
    		
    		int rowCountAfter = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
    		//System.out.println(rowCountAfter);
    		
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
		//Globals.waitForSeconds(4);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to connect your Soundcloud account and complete Step 1")));
		driver.findElement(By.partialLinkText("Click here to connect your Soundcloud account and complete Step 1")).click();
		String someTitle = null;
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Authorize access to your account on SoundCloud - Create, record and share your sounds for free")){
		    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		    	driver.findElement(By.partialLinkText("Sign in with Facebook")).click();
		    	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
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
		    	
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[5]")));
		    	Globals.waitForSeconds(20);
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
		System.out.println("1");
		String testTitle = "Checking effect of clicking on seed songs and button color.";		
		
		String colorBeforeClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");		
		//rgba(218, 79, 73, 1) is red on windows
		//rgba(189, 54, 47, 1) is red on linux. I am not sure why it sees a different hue of red on linux.
		if(colorBeforeClick.equalsIgnoreCase("rgba(218, 79, 73, 1)") || colorBeforeClick.equalsIgnoreCase("rgba(189, 54, 47, 1)")){
			Globals.waitForSeconds(10);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[1]/td[4]")));
			System.out.println("1.1");
			WebElement cellFour=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[1]/td[4]"));
			driver.findElement(By.partialLinkText(cellFour.getText())).click();
			System.out.println("1.2");
			
			//driver.findElement(By.partialLinkText(cellFour.getText())).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[2]/td[4]")));
			System.out.println("4.1");
			WebElement cellOne=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[2]/td[4]"));		
			System.out.println("4.2");
			driver.findElement(By.partialLinkText(cellOne.getText())).click();		
			
			System.out.println("5.1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[3]/td[4]")));
			WebElement cellTwo=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[3]/td[4]"));	
			System.out.println("5.2");
			driver.findElement(By.partialLinkText(cellTwo.getText())).click();		
			System.out.println("2");
			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[4]/td[4]")));
			System.out.println("2.0");
			WebElement cellThree=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr[4]/td[4]"));
			System.out.println("2.1");
			driver.findElement(By.partialLinkText(cellThree.getText())).click();
			System.out.println("2.2");
			
			
			Globals.waitForSeconds(20);
			//WebDriverWait wait = new WebDriverWait(driver, 20);
			//wait.until(ExpectedConditions.textToBePresentInElement(By.linkText("//table[@id='prefList']/tbody/tr[4]/td[4]"), temp));
			//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@id='prefList']/tbody/tr[4]/td[4]")));
			String colorAfterClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");	
			//System.out.println("3");
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
