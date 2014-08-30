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
	String playingSongTitle =null;
	

	
	
	public void prefListHideButton(WebDriver driver){
		
		String testTitle = "Checking Pref List hide button.";
		
		int prefListLastRowBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		WebElement hideCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+prefListLastRowBefore+"]/td[2]"));
		hideCell.findElement(By.tagName("a")).click();
		int prefListLastRowAfter = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		if(prefListLastRowBefore == prefListLastRowAfter + 1){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}		
	}
	
	
public void genreColumnMinusButton(WebDriver driver){
		
		String testTitle = "Checking genre column minus button.";
		String playlistSongTitle = null;
		float scoreBeforeFloat = 0.0f;
		float scoreAfterFloat = 0.0f;
		
		/*Check score before*/
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='playList']/tbody/tr[7]/td[3]")));
		WebElement songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[7]/td[3]"));
	
		songTitle = songCell.findElement(By.tagName("a")).getText();	
		songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[7]/td[1]"));
		String scoreBefore = songCell.findElement(By.tagName("a")).getText();
		scoreBeforeFloat = Float.parseFloat(scoreBefore);
		/*Click minus button*/
		
		wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='playList']/tbody/tr[7]/td[2]/p")));
		songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[7]/td[2]/p"));		

		songCell.findElement(By.linkText("-")).click();

		
		/*Check score again*/
		int playListRowCount = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		for(int i = 1;i <= playListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[3]"));
			playlistSongTitle = titleCell.findElement(By.tagName("a")).getText();
			
			if(playlistSongTitle.equalsIgnoreCase(songTitle)){
				
				WebElement skipCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[1]"));			
				String scoreAfter = skipCell.findElement(By.tagName("a")).getText();
				scoreAfterFloat = Float.parseFloat(scoreAfter);
				break;
			}
		}		
		
		if(scoreAfterFloat < scoreBeforeFloat){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
	}
	
	
	public void genreColumnPlusButton(WebDriver driver){
		
		String testTitle = "Checking genre column plus button.";
		String playlistSongTitle = null;
		float scoreBeforeFloat = 0.0f;
		float scoreAfterFloat = 0.0f;
		int playListRowCount = 5;//driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		
		/*Check score before*/
		WebElement songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+playListRowCount+"]/td[3]"));
		songTitle = songCell.findElement(By.tagName("a")).getText();		

		songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+playListRowCount+"]/td[1]"));
		String scoreBefore = songCell.findElement(By.tagName("a")).getText();
		scoreBeforeFloat = Float.parseFloat(scoreBefore);
		/*Click plus button*/
		songCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+playListRowCount+"]/td[2]/p"));

		songCell.findElement(By.linkText("+")).click();

		Globals.waitForSeconds(1);

		/*Check score again*/
		playListRowCount = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		for(int i = 1;i <= playListRowCount; i++){

			playlistSongTitle=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[3]")).findElement(By.tagName("a")).getText();;
			
			

			if(playlistSongTitle.equalsIgnoreCase(songTitle)){

				WebElement skipCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[1]"));			
				String scoreAfter = skipCell.findElement(By.tagName("a")).getText();
				scoreAfterFloat = Float.parseFloat(scoreAfter);

				break;
			}
		}
		
		
		
		if(scoreAfterFloat > scoreBeforeFloat){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("scoreBefore "+scoreBeforeFloat);
			System.out.println("scoreAfter "+scoreAfterFloat);
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
	}
	
	
	public void skipColumnZeroButton(WebDriver driver){
		String testTitle = "Checking skip column zero button.";
		String prefSongTitle = null;
		String songScoreBefore = null;
		String songScoreAfter = null;
		String playlistSongTitle = null;		
		WebElement scoreCell = null;
		int prefListRowCount = 0;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		
		/*Check score before clicing.*/
		prefListRowCount = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();

		for(int i = 1;i <= prefListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]"));
			prefSongTitle = titleCell.findElement(By.tagName("a")).getText();

			if(prefSongTitle.equalsIgnoreCase(songTitle)){
	
				scoreCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[3]"));
				songScoreBefore = scoreCell.findElement(By.tagName("a")).getText();

				break;
			}
		}
		
		/*Click zero button.*/
		int playListRowCount = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();
		for(int i = 1;i <= playListRowCount; i++){
			
			playlistSongTitle=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[3]")).findElement(By.tagName("a")).getText();

			if(playlistSongTitle.equalsIgnoreCase(songTitle)){

				WebElement skipCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr["+i+"]/td[4]"));			
				skipCell.findElement(By.linkText("0")).click();

				Globals.waitForSeconds(1);
				break;
			}
		}
		
		/*Check score again.*/		
		for(int i = 1;i <= prefListRowCount; i++){
			WebElement titleCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[1]"));
			prefSongTitle = titleCell.findElement(By.tagName("a")).getText();

			if(prefSongTitle.equalsIgnoreCase(songTitle)){
		
				scoreCell=driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+i+"]/td[3]"));
				songScoreAfter = scoreCell.findElement(By.tagName("a")).getText();

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
		WebDriverWait wait = new WebDriverWait(driver, 20);
		int prefListCountBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();	
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='playList']/tbody/tr[4]/td[4]/a")));
		driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[4]/td[4]")).findElement(By.linkText("+")).click();

		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[7]")));
		
		int prefListCountAfter = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		if(prefListCountAfter > prefListCountBefore){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Score--Before:"+prefListCountBefore+"::After:"+prefListCountAfter);
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
	}
	
	
	public void ratingMinusTen(WebDriver driver){
		
		String testTitle = "Checking ratings minus ten button.";
		
		int songScoreAfter = 0;
		
		driver.findElement(By.xpath("//button[@onclick='setCurrentTrackScore(-10);']")).click();
		Globals.waitForSeconds(2);//score came out zero once...replace with wait statement.
		WebDriverWait wait = new WebDriverWait(driver, 20);
	
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[7]")));
		int rowNum = Globals.findPrefListSongRowByTitle(playingSongTitle, driver);
		
		
		if(rowNum > 0){

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr["+rowNum+"]")));

			songScoreAfter = Integer.parseInt(driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+rowNum+"]/td[3]")).findElement(By.tagName("a")).getText());
		}
		
		if(songScoreAfter == -10){
			System.out.println("Passed -> "+testTitle);
		}else{			
			System.out.println("Error in =>> "+testTitle);
			System.out.println("Error: Song score is =>> "+songScoreAfter);
        	throw new RuntimeException();
		}
		
	}
	
	
	
	public void ratingPlusOne(WebDriver driver){
		
		String testTitle = "Checking ratings plus one button.";
		
		int songScoreAfter = 0;

		driver.findElement(By.xpath("//button[@onclick='setCurrentTrackScore(1);']")).click();

		int rowNum = Globals.findPrefListSongRowByTitle(playingSongTitle, driver);
		
		
		if(rowNum != 0){
			WebElement plusTennedSongTitleCell = driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+rowNum+"]/td[3]"));
			songScoreAfter = Integer.parseInt(plusTennedSongTitleCell.findElement(By.tagName("a")).getText());
		}
		
		if(songScoreAfter == 1){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error: Song score is =>> "+songScoreAfter);
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
		
	}
	
	
	
	public void ratingPlusTen(WebDriver driver){
		
		String testTitle = "Checking ratings plus ten button.";
		
		int songScoreAfter = 0;

		driver.findElement(By.xpath("//button[@onclick='setCurrentTrackScore(10);']")).click();

		int rowNum = Globals.findPrefListSongRowByTitle(playingSongTitle, driver);
		
		
		if(rowNum != 0){
			WebElement plusTennedSongTitleCell = driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+rowNum+"]/td[3]"));
			songScoreAfter = Integer.parseInt(plusTennedSongTitleCell.findElement(By.tagName("a")).getText());
		}
		
		if(songScoreAfter == 10){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error: Song score is =>> "+songScoreAfter);
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
		
	}
	
	
	public void playNextTrack(WebDriver driver){
		
		String testTitle = "Checking next songe is played(or added to pref list) when 'Play Next Track' is clicked.";
		
		WebElement songToBeAdded = driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[1]/td[3]"));
		String songToBeAddedTitle = songToBeAdded.findElement(By.tagName("a")).getText();
		
		driver.findElement(By.id("nextTrackButton")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[12]")));
		
		int rowNum = Globals.findPrefListSongRowByTitle(songToBeAddedTitle, driver);
		if(rowNum > 0){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
		
	}
	
	
	public void playTrack(WebDriver driver){
		
		driver.switchTo().frame("sc-widget");
		driver.findElement(By.cssSelector("button.playButton.playing")).click();
		driver.switchTo().defaultContent();
		
		String testTitle = "Checking if song is added to prefList when clicking Play on playlist.";
		
		/*Song player is present.*/
	
		
		int prefListCountBefore = driver.findElements(By.xpath("//table[@id='prefList']/tbody/tr")).size();
		
		WebElement playingSongTitleCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[5]/td[3]"));

		playingSongTitle = playingSongTitleCell.findElement(By.tagName("a")).getText();

		
		WebElement playBtnCell=driver.findElement(By.xpath("//table[@id='playList']/tbody/tr[5]/td[5]"));

		playBtnCell.findElement(By.tagName("a")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 50);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[13]")));

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

		
		driver.findElement(By.id("moreTracksButton")).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='playList']/tbody/tr["+(rowCountBefore+1)+"]")));
    	Globals.waitForSeconds(1);
    	

    	
    	int rowCountAfter = driver.findElements(By.xpath("//table[@id='playList']/tbody/tr")).size();

		
		if(rowCountAfter > rowCountBefore){
			System.out.println("Passed -> "+testTitle);
		}else{
			System.out.println("Error: Current rows in playlist =>> "+rowCountAfter);
			System.out.println("Error in =>> "+testTitle);
        	throw new RuntimeException();
		}
    	
    	//rgba(250, 167, 50, 1) is orange
    	//rgba(81, 163, 81, 1) is green, button can be either orange or green
    	
		
		
	}
	
	public void fbLogin(WebDriver driver){
		
		String testTitle = "Checking login with facebook and fetching of liked songs.";
		
		driver.findElement(By.partialLinkText("Click Here To Login and Get Started")).click();

		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to connect your Soundcloud account and complete Step 1")));
		driver.findElement(By.partialLinkText("Click here to connect your Soundcloud account and complete Step 1")).click();
		String someTitle = null;
		for (String handle : driver.getWindowHandles()) {
			
		    driver.switchTo().window(handle);
		    someTitle = driver.getTitle();
		    if(someTitle.equalsIgnoreCase("Authorize access to your account on SoundCloud - Create, record and share your sounds for free")){
		    	driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		    	driver.findElement(By.partialLinkText("Sign in with Facebook")).click();
		    	driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
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
		    	
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr[5]")));

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

			WebDriverWait wait = new WebDriverWait(driver, 20);								
			WebElement cell;
			for(int i = 0; i < 5 ; i++){				
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='prefList']/tbody/tr["+(i+1)+"]/td[4]")));
				cell = driver.findElement(By.xpath("//table[@id='prefList']/tbody/tr["+(i+1)+"]/td[4]"));
				cell.findElement(By.tagName("a")).click();				
			}
										
			Globals.waitForSeconds(10);
			
			String colorAfterClick = driver.findElement(By.id("moreTracksButton")).getCssValue("background-color");	
			
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
