package adactin.testComponents;

import java.io.IOException;
import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


import adacin.resources.ExtentReporterNG;

public class Listeners extends BaseClass implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // already initialized in ExtentReporterNG
    }

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

        WebDriver driver = null;
        String methodName = result.getMethod().getMethodName();

        try {
            Field field = result.getTestClass()
                    .getRealClass()
                    .getDeclaredField("driver");

            driver = (WebDriver) field.get(result.getInstance());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (driver != null) {
                String path = getScreenshot(methodName, driver);
                extentTest.get().addScreenCaptureFromPath(path, methodName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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