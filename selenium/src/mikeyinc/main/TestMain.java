package mikeyinc.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import mikeyinc.functionality.TestFunctionality;
import mikeyinc.ui.TestUi;
import mikeyinc.utilities.Globals;
import mikeyinc.functionality.TestLogin;;

public class TestMain {
	
	
	
	public static void main(String[] args) {
		
		
		TestUi tu = new TestUi();
		TestFunctionality tf = new TestFunctionality();
		TestLogin tl = new TestLogin();
		
		
		/*Load home page.*/
		Globals.ffDriver.get(Globals.baseUrl);
		
		try{
			/*call each test one by one like this passing them the driver and/or url.*/		
			
			tu.testTitle(Globals.ffDriver);
			tu.playerLoad(Globals.ffDriver);
			tu.hideInstructions(Globals.ffDriver);
			tf.fbLogin(Globals.ffDriver);
			tf.checkSeedAddition(Globals.ffDriver);
			tf.getNewTracks(Globals.ffDriver);
			
			
			tf.playNextTrack(Globals.ffDriver);
			tf.playTrack(Globals.ffDriver);
			tf.ratingPlusTen(Globals.ffDriver);
			tf.ratingPlusOne(Globals.ffDriver);
			tf.ratingMinusTen(Globals.ffDriver);
			
			tf.skipColumnPlusButton(Globals.ffDriver);
			tf.skipColumnMinusButton(Globals.ffDriver);
			tf.skipColumnZeroButton(Globals.ffDriver);
			tf.genreColumnPlusButton(Globals.ffDriver);
			tf.genreColumnMinusButton(Globals.ffDriver);
			tf.prefListHideButton(Globals.ffDriver);
			
		}finally{
			/*If a failure occurs in any of the tests this will safely close the drivers.
			 * IMPORTANT: Always run the test in your local machine to ensure that the browser window is 
			 * closed. If a browser window is left unclosed it can cause memory leaks on the test server*/
			Globals.ffDriver.close();
			Globals.ffDriver.quit();
		}
		
		
		
		/*WebDriver disposableDriver = new FirefoxDriver();
		try{			
			disposableDriver.get("http://tasteleech.com");
			tl.googleLogin(disposableDriver);			
		}catch(Exception e){
			
		}finally{
			disposableDriver.close();
			disposableDriver.quit();
		}*/
		
		
		
		/*int rand = Globals.randInt(1,10);		
		if(rand == 5){
			WebDriver soundcloudDriver = new FirefoxDriver();
			try{			
				soundcloudDriver.get("http://tasteleech.com");
				tl.soundcloudLogin(soundcloudDriver);			
			}catch(Exception e){
				
			}finally{
				soundcloudDriver.close();
				soundcloudDriver.quit();
			}
		}else{
			System.out.println("INFO   -> Scoundcloud login skipped this time. It executes once in ten builds because of the captcha problem.");
		}*/
		
		
	}
	
	

}
