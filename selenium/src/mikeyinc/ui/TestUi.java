package mikeyinc.ui;

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

}
