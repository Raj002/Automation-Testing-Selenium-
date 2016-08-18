package Project_Uses;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Multi_Browser
{
	WebDriver driver;
	
	@BeforeClass
	@Parameters({"browser"})
	public void Setup(String browser) throws Exception
	{
		if(browser.equals("FFX"))
		{
			driver=new FirefoxDriver();
			System.out.println("Test starts runnning in Firefox Browser");
		}
		else if(browser.equals("CRM"))
		{
			System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
			driver=new ChromeDriver();
			System.out.println("Test starts running in Chrome Browser");
		}
		else if(browser.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver", ".//Drivers//IEDriverServer.exe");
			driver=new InternetExplorerDriver();
			System.out.println("Test starts running in Internet Explorer driver");
		}
		/*else if(browser.equals("Htmlunit"))
		{
			driver=new HtmlUnitDriver();
			Logger log=Logger.getLogger("");
			log.setLevel(Level.OFF);
			System.out.println("Test starts running in HtmlUnit driver");			
		}*/
		
		else
		{
			throw new Exception("Browser is not correct");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("http://night.gbsindia.co.in/");
	}
	@Test(description="Super admin Login")
	public void TC01() throws InterruptedException
	{
		WebElement L=driver.findElement(By.xpath("(//li//a)[1]"));
		Actions a=new Actions(driver);
		a.doubleClick(L).perform();
		Thread.sleep(2000);
		a.moveToElement(driver.findElement(By.xpath("(//li//a)[2]"))).click().build().perform();
		
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Super Admin Login']")));
		driver.findElement(By.xpath("//input[@placeholder='Username']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("superadmin@gbs.com");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("1");
		driver.findElement(By.xpath("//button[@type='submit']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		WebElement w=driver.findElement(By.xpath("//a[@class='dropdown-toggle']"));
		a.doubleClick(w).click().build().perform();
		Thread.sleep(3000);
		a.moveToElement(driver.findElement(By.xpath("(//a)[4]"))).click().build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
	}
	
	@AfterClass
	public void Tear()
	{
		driver.quit();
	}
}
