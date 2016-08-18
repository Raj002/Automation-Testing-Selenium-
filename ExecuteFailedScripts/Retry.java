package Execute_Failed_Test_Cases;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer 
{
	int minRetryCount = 0;
	int maxRetryCount = 1;
	
	public boolean retry(ITestResult result)
	{
		if(minRetryCount<=maxRetryCount)
		{
			System.out.println("Following Text case if failing : " + result.getName());
			
			System.out.println("Retrying the test count is : " + (minRetryCount + 1));
			
			minRetryCount++;
			
			return true;
		}
		
		return false;
	}
}
