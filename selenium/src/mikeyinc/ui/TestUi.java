package mikeyinc.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestUi {
	
	public void testTitle() {
       
		WebDriver driver = new FirefoxDriver();
        String baseUrl = "https://tasteleech.neocities.org";
        String expectedTitle = "Tasteleech22";
        String actualTitle = "";

        
        driver.get(baseUrl);

       
        actualTitle = driver.getTitle();
        

        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
        	//throw new RuntimeException();
            try{
            	throw new Exception();
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
       
        
        driver.close();
        driver.quit();
        
        
    }

}
