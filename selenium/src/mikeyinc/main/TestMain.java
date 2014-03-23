package mikeyinc.main;

import mikeyinc.functionality.TestFunctionality;
import mikeyinc.ui.TestUi;
import mikeyinc.utilities.Globals;

public class TestMain {
	
	
	
	public static void main(String[] args) {
		
		
		TestUi tu = new TestUi();
		TestFunctionality tf = new TestFunctionality();
		
		/*Load home page.*/
		Globals.ffDriver.get(Globals.baseUrl);
		
		try{
			/*call each test one by one like this passing them the driver and/or url.*/		
			
			tu.testTitle(Globals.ffDriver);
			tf.fbLogin(Globals.ffDriver);
			tf.checkSeedAddition(Globals.ffDriver);
			tf.getNewTracks(Globals.ffDriver);
			//tf.playTrack(Globals.ffDriver);
			//tf.skipColumnPlusButton(Globals.ffDriver);
			//tf.skipColumnMinusButton(Globals.ffDriver);
			//tf.skipColumnZeroButton(Globals.ffDriver);
			
			
		}finally{
			/*If a failure occurs in any of the tests this will safely close the drivers.
			 * IMPORTANT: Always run the test in your local machine to ensure that the browser window is 
			 * closed. If a browser window is left unclosed it can cause memory leaks on the test server*/
			Globals.ffDriver.close();
			Globals.ffDriver.quit();
		}
		
	}
	
	

}
