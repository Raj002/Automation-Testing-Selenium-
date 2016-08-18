package Grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GridTest 
{
	WebDriver driver;
	DesiredCapabilities cap = new DesiredCapabilities();
	
	@Parameters({"platform","browser","url"})
	@BeforeMethod
	public void Setup(String platform, String browser, String url) throws MalformedURLException
	{


		cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

		if(browser.equalsIgnoreCase("ie"))
		{
			cap = DesiredCapabilities.internetExplorer();
		}
		if(browser.equalsIgnoreCase("chrome"))
		{
			cap = DesiredCapabilities.chrome();
		}
		if(browser.equalsIgnoreCase("firefox"))
		{
			cap = DesiredCapabilities.firefox();
		}

		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(101, TimeUnit.SECONDS);
		driver.get(url);
	}

	@Test
	public void TC() throws InterruptedException
	{
		driver.switchTo().activeElement().sendKeys("selenium grid");
		Thread.sleep(4000); 
		String browserName = cap.getBrowserName(); 
		String browserVersion = cap.getVersion(); 
		// Get OS name. 
		String os = System.getProperty("os.name").toLowerCase();
		// Print test result with browser and OS version detail. 
		System.out.println("OS = " + os + ", Browser = " + browserName + " "+ browserVersion );

		/*driver.findElement(By.className("sbibod")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("sbibod")).sendKeys("selenium grid");
		Thread.sleep(4000);*/
	}
	@AfterMethod
	public void tear()
	{
		driver.quit();
	}

}
