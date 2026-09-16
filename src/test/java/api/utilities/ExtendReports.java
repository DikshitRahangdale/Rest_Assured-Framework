package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtendReports implements ITestListener {
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;

	@Override
	public void onStart(ITestContext context) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportPath = System.getProperty("user.dir") + "//reports//Extentresprts-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(reportPath);
		sparkReporter.config().setDocumentTitle("Automation APT Testing");
		sparkReporter.config().setReportName("Automation APT Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("TimeStamp", timeStamp);
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Device", "Laptop");
		extent.setSystemInfo("Server", "QA Testing Server");
		extent.setSystemInfo("QA Name", "Dikshti Rahangdale");
		extent.setSystemInfo("API Testing Toll", "Rest Assured");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.pass("Test Case Passed:->" + result.getName());
		test.assignAuthor("Diskhit Rahangdale Automatin Engineer");
		test.assignCategory("Pass Test Cases");
		test.assignDevice("lenovo laptop");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.fail("Test Case is Failed:->" + result.getName());
		test.info("Test Case failed exception:->" + result.getThrowable().getMessage());
		test.assignAuthor("Diskhit Rahangdale Automation Engineer");
		test.assignCategory("Failed Test cases");
		test.assignDevice("Lenovo Laptop");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.skip("Test Case Skipped:->" + result.getName());
		test.info("Test case Skipped:->" + result.getThrowable().getMessage());
		test.assignAuthor("Dikshit Rahangdale Automation Engineer");
		test.assignCategory("Skipped Test cases");
		test.assignDevice("Lenovo Laptop");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
