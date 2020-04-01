package Demo;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListners implements ITestListener {

	
	public void onFinish(ITestContext result) {
		 
		
	}

	
	public void onStart(ITestContext result)
	{
		 
		
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		 
		
	}

	
	public void onTestFailure(ITestResult result) 
	{
		 System.out.println("Test Case are Fails = " +result.getName());
		
	}

	public void onTestSkipped(ITestResult result)
	{
		 System.out.println("Test Case are Skip = "+result.getName());
	
	}

	public void onTestStart(ITestResult result)
	{
		 System.out.println("Test Case are Start = "+result.getName());

	}

	
	public void onTestSuccess(ITestResult result)
	{
		 System.out.println("Test Case are Success ="+result.getName());

	}

}
