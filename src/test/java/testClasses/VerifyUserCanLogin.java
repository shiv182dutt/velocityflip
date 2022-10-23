package testClasses;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import UtilClasses.Util1;
import baseClasses.Base1;
import pomClasses.HomePage;
import pomClasses.LoginPage;

public class VerifyUserCanLogin {
	
WebDriver driver;

	ExtentHtmlReporter htmlReporter;
	ExtentReports reports;
	ExtentTest test;
	
	LoginPage lp;
	HomePage hp;
	
	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser) {
		htmlReporter = new ExtentHtmlReporter("ExtentReports.html");
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);
		test = reports.createTest("VerifyUserCanLogin");
		
		driver = Base1.getDriver(browser);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		lp = new LoginPage(driver);
		hp = new HomePage(driver);
	}
	
	@Test
	public void VerifyUserLogin() throws IOException {
		lp.enterEmailID();
		lp.enterPassword();
		lp.clickOnLoginBtn();
		boolean isProfileNameVisible = hp.checkProfileNameVisible();
		
		Assert.assertTrue(isProfileNameVisible);
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		
		if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, result.getName() +" is Passed");
		}
		
		
		
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			String path = Util1.getScreenShotPath(driver, result.getName());
			
			test.log(Status.FAIL, result.getName() +" is Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		
		else if(result.getStatus() == ITestResult.SKIP){
			
			test.log(Status.FAIL, result.getName() +" is Skipped");
		}
		
	}
	
	@AfterClass
	public void afterClass() {
		
		reports.flush();
	
	}

}
