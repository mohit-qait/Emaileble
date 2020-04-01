package Report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

public class CustomTestNGReporter implements IReporter
{
	String []classN=new String[20];
	int []pass=new int[20];
	int []fail=new int[20];
	int counter=-1;
	
	
	//This is the customize emailabel report template file path.
	private static final String emailableReportTemplateFile = "C:\\Users\\91975\\TestngListeners\\email\\src\\test\\java\\Report\\Custom.html";
	
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		
		try
		{
			// Get content data in TestNG report template file.
			String customReportTemplateStr = this.readEmailabelReportTemplate();
			
			// Create custom report title.
			String customReportTitle = this.getCustomReportTitle("Custom TestNG Report");
			
			// Create test suite summary data.
			String customSuiteSummary = this.getTestSuiteSummary(suites);
			
			// Create test methods summary data.
			String customTestMethodSummary = this.getTestMehodSummary(suites);
			
			// Replace report title place holder with custom title.
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$", customReportTitle);
			
			// Replace test suite place holder with custom test suite summary.
			//customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\$", customSuiteSummary);
			
			// Replace test methods place holder with custom test method summary.
			customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Detail\\$", customTestMethodSummary);
			
			// Write replaced test report content to custom-emailable-report.html.
			File targetFile = new File(outputDirectory + "/custom-emailable-report.html");
			FileWriter fw = new FileWriter(targetFile);
			fw.write(customReportTemplateStr);
			fw.flush();
			fw.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/* Read template content. */
	private String readEmailabelReportTemplate()
	{
		StringBuffer retBuf = new StringBuffer();
		
		try {
		
			File file = new File(this.emailableReportTemplateFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}finally
		{
			return retBuf.toString();
		}
	}
	
	/* Build custom report title. */
	private String getCustomReportTitle(String title)
	{
		StringBuffer retBuf = new StringBuffer();
		retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
		return retBuf.toString();
	}
	
	/* Build test suite summary data. */
	private String getTestSuiteSummary(List<ISuite> suites)
	{
		StringBuffer retBuf = new StringBuffer();
		
		try
		{	
			for(ISuite tempSuite: suites)
			{				
				Map<String, ISuiteResult> testResults = tempSuite.getResults();
				
				for (ISuiteResult result : testResults.values()) {
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			return retBuf.toString();
		}
	}

	/* Get date string format value. */
	private String getDateInStringFormat(Date date)
	{
		StringBuffer retBuf = new StringBuffer();
		if(date==null)
		{
			date = new Date();
		}
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		retBuf.append(df.format(date));
		return retBuf.toString();
	}
	
	private String getTestMehodSummary(List<ISuite> suites)
	{
		StringBuffer retBuf = new StringBuffer();
		try
		{
		
			for(ISuite tempSuite: suites)
			{
				
				Map<String, ISuiteResult> testResults = tempSuite.getResults();
				
				for (ISuiteResult result : testResults.values()) {
					
					ITestContext testObj = result.getTestContext();

					String testName = testObj.getName();
					
					/* Get failed test method related data. */
					IResultMap testFailedResult = testObj.getFailedTests();
					String failedTestMethodInfo = this.getTestMethodReport(testName, testFailedResult, false, false);
					retBuf.append(failedTestMethodInfo);
					/* Get passed test method related data. */
					IResultMap testPassedResult = testObj.getPassedTests();
					String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);
					retBuf.append(passedTestMethodInfo);
				}
			}
			for(int i=0;i<classN.length;i++)
			{
				classN[i]=classN[i].replaceAll("Demo.", "");
			retBuf.append("<tr>");
		
			retBuf.append("<td width='20%'>");
			retBuf.append(classN[i]);
			retBuf.append("</td>");

			retBuf.append("<td width='20%'>");
			retBuf.append(pass[i]);
			retBuf.append("</td>");
		
			retBuf.append("<td width='20%'>");
			retBuf.append(fail[i]);
			retBuf.append("</td>");
			retBuf.append("<tr>");
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			return retBuf.toString();
		}
		
	}
	
	
	/* Get failed, passed or skipped test methods report. */
	private String getTestMethodReport(String testName, IResultMap testResultMap, boolean passedReault, boolean skippedResult)
	{

		StringBuffer retStrBuf = new StringBuffer();
		
		String resultTitle = testName;
		
		String color = "green";
		
		if(skippedResult)
		{
			resultTitle += " - Skipped ";
			color = "yellow";
		}else
		{
			if(!passedReault)
			{
				resultTitle += " - Failed ";
				color = "red";
			}else
			{
				resultTitle += " - Passed ";
				color = "green";
			}
		}
		
		
			Set<ITestResult> testResultSet = testResultMap.getAllResults();
			
			for(ITestResult testResult : testResultSet)
			{
				String testClassName = "";
				
				
				//Get testClassName
				testClassName = testResult.getTestClass().getName();
					
				//Get testMethodName
		//		testMethodName = testResult.getMethod().getMethodName();
				
					
				//Get parameter list.
				
				List valid=Arrays.asList(classN);
				if(valid.contains(testClassName))
				{
					counter=getindex(testClassName);
				}
				else
				{
					counter++;
					classN[counter]=testClassName;
				}
					
				if(resultTitle.contains("Passed"))	
				{
					pass[counter]++;
					
				}
				else if(resultTitle.contains("Failed"))	
				{
					fail[counter]++;
					
				}
				
				/* Add test class name. */
				retStrBuf.append("<td width='20%'>");
				testClassName=testClassName.replaceAll("Demo.", "");
				//retStrBuf.append(testClassName);
				retStrBuf.append("</td>");
				
			}
		
		return retStrBuf.toString();
	}
	
	/* Convert a string array elements to a string. */
	private String stringArrayToString(String strArr[])
	{
		StringBuffer retStrBuf = new StringBuffer();
		if(strArr!=null)
		{
			for(String str : strArr)
			{
				retStrBuf.append(str);
				retStrBuf.append(" ");
			}
		}
		return retStrBuf.toString();
	}

	public int getindex(String testClassName)
	{
		for(int i=0;i<classN.length;i++)
		{
			if(classN[i].contentEquals(testClassName))
			{
				return i;
			}
		}
		return 0;
	}
}
