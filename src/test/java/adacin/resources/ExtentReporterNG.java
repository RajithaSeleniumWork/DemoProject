package adacin.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	 public static ExtentReports getReportObject() {

	        String path = System.getProperty("user.dir")
	                + "\\src\\test\\java\\adactin\\reports\\index.html";

	        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

	        reporter.config().setReportName("Adactin Automation Results");
	        reporter.config().setDocumentTitle("Test Execution Report");

	        ExtentReports extent = new ExtentReports();
	        extent.attachReporter(reporter);

	        extent.setSystemInfo("Tester", "Rajitha");
	        extent.setSystemInfo("Framework", "Selenium Hybrid");

	        return extent;
	    }

}
