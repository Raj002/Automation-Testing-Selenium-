package SmokeTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Execute_Failed_Test_Cases.Retry;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class Report_Demo 
{
	ExtentReports report = new ExtentReports("C:\\SeleniumReport_scrnshots\\Final_Report.html");
	ExtentTest logger;
	
	WebDriver driver;
	
	@BeforeMethod
	public void Setup(Method method)
	{
		logger = report.startTest(method.getName());
		logger.log(LogStatus.INFO,"Test Execution is starting" );
		driver = new FirefoxDriver();
		
		logger.log(LogStatus.INFO, "Opening URL");
		driver.get("https://wordpress.com/");
		driver.manage().window().maximize();
		logger.log(LogStatus.INFO, "Application is opening up");
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}
	
	@Test (retryAnalyzer=Retry.class, description = "Creating blog via clicking Login option")
	public void TC1() throws InterruptedException
	{
		WebDriverWait wait=new WebDriverWait(driver,20);
		
		driver.findElement(By.xpath("//*[@id='wpcom-home']/header/div/ul/li/a")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='wp-submit']"))).isDisplayed();
		driver.findElement(By.xpath("//a[text()='Register']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='primary']/span/span/div/div/header/h1"))).isDisplayed();
		driver.findElement(By.xpath("//*[@id='primary']/span/span/div/div/div/div/div[3]/div/a[1]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[starts-with(@id,'search-component')]"))).isDisplayed();
		driver.findElement(By.xpath("//input[starts-with(@id,'search-component')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[starts-with(@id,'search-component')]")).sendKeys("raj.wordpress.com");
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='primary']/span/span/div/div/div/div/div[2]/div[2]/div[1]/div[1]/h3"), "r"));
		
		driver.findElement(By.xpath("(//button[text()='Select'])[1]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Select Free Plan']")));
		
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Selenium"));
		driver.findElement(By.xpath("//button[text()='Select Free Plan']")).click();
		Thread.sleep(3000);
	}
	
	@Test (retryAnalyzer = Retry.class, description = "Creating website")
	public void TC2() throws InterruptedException
	{
		WebDriverWait wait=new WebDriverWait(driver,20);
		driver.findElement(By.id("learn-more-blog")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='Get Started']")).click();
		
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("an"));
		logger.log(LogStatus.PASS, "Title contains the gettile");
	}
	
	
	public String TakeScreenShot(String Testcase) throws IOException
	{
		File scrshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String dest = "C:\\SeleniumReport_scrnshots\\"+ Testcase +".png"+"";
		File destination = new File(dest);
		FileUtils.copyFile(scrshot, destination);
		
		return dest;
	}
	
	@AfterMethod
	public void Tear(ITestResult testresult,Method method)
	{
		String testname = method.getName();
		try
		{
			if(testresult.getStatus()==ITestResult.FAILURE)
			{
				System.out.println(testresult.getStatus());
				
				logger.log(LogStatus.INFO, "ScreenShot below");
				
				String image = logger.addScreenCapture(TakeScreenShot(testname));
				
				logger.log(LogStatus.FAIL, "Method Execution", image);
			}
		}
		catch(Exception e)
		{
			Assert.fail("Failed to take screenshot");
		}
		finally
		{
			System.out.println("" + testname + " Test has been completed");
			
			report.endTest(logger);
			
			report.flush();
			driver.quit();
		}
	}
	
	@AfterSuite
	public void DisplayReport()
	{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("C:\\SeleniumReport_scrnshots\\Final_Report.html");
	}
}

