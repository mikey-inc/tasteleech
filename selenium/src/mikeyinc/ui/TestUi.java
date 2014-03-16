package mikeyinc.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestUi {
	
	public void testTitle() {
		WebDriver driver = new FirefoxDriver();
       try{
		
        String baseUrl = "https://tasteleech.neocities.org";
        String expectedTitle = "Tasteleech";
        String actualTitle = "";

        
        driver.get(baseUrl);

       
        actualTitle = driver.getTitle();
        

        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
        	throw new RuntimeException();        
        }
       
       }finally{
    	System.out.println("grace for me");
        driver.close();
        driver.quit();
       }
        
    }

}
