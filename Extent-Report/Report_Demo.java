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




//################################## pom.xml #################################
<dependency>
	<groupId>com.relevantcodes</groupId>
	<artifactId>extentreports</artifactId>
	version>2.41.2</version>
</dependency>
<dependency>
	<groupId>org.freemarker</groupId>
	<artifactId>freemarker</artifactId>
	<version>2.3.23</version>
</dependency>

//####################################### testng.xml #################################
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite">

  <listeners>
  	<listener class-name="com.TEA.Framework.Claims.ExtentReportListener.ExtentReportListener"></listener>
  </listeners>
  
  <test name="Test">
    <classes>
      <class name="com.TEA.Framework.RiskTrac.JBehave.StoryConfig"/>
    </classes>
  </test> <!-- Test -->
</suite> <!-- Suite -->
	      
//######################################## ExtentReportListener.java ###################################
package com.TEA.Framework.Claims.ExtentReportListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportListener implements IReporter {
	private ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator
				+ "Extent.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase()
							+ "ed");
				}

				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
}
