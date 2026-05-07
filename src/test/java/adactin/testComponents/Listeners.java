package adactin.testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;


import adacin.resources.ExtentReporterNG;


public class Listeners extends BaseClass implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        WebDriver driver = getDriver(); // from BaseClass ThreadLocal driver

        String methodName = result.getMethod().getMethodName();

        try {
            String path = getScreenshot(methodName, driver);
            extentTest.get().addScreenCaptureFromPath(path, methodName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        extentTest.get().log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}