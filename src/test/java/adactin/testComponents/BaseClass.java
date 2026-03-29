package adactin.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import adactin.Hotel.PageObject.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	public LoginPage loginlocators;
	

	    public WebDriver initializeDriver() throws IOException {

			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					"C:\\Rajitha\\TestNGFramework\\DemoProject\\src\\test\\java\\adacin\\resources\\GlobalData.properties");
			prop.load(fis);
			String browserName =System.getProperty("browser")!=null ? System.getProperty("browser"): prop.getProperty("browser");
			 //prop.getProperty("browser");
			
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();

			} else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			} else if (browserName.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();

			}

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			
			driver.manage().window().maximize();
			return driver;

		}


		 public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {
		        // Read the JSON file content as a string
		        String jsonContent = FileUtils.readFileToString(
		                new File(filepath),
		                StandardCharsets.UTF_8);

		        // Create an ObjectMapper instance
		        ObjectMapper mapper = new ObjectMapper();

		        // Convert the JSON string to a list of hash maps
		        List<HashMap<String, String>> data = mapper.readValue(jsonContent,
		                new TypeReference<List<HashMap<String, String>>>() {
		                });

		        return data;
		    }
		 
		 public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
			 TakesScreenshot ts=(TakesScreenshot)driver;
			 File source=ts.getScreenshotAs(OutputType.FILE);
			 File file=new File("C:\\Rajitha\\TestNGFramework\\DemoProject\\src\\test\\java\\adactin\\reports\\"+testCaseName+".png");
			 FileUtils.copyFile(source, file);
			 return System.getProperty("user.dir")+"\\src\\test\\java\\adactin\\reports\\"+ testCaseName+".png";
		 }
		 

			@BeforeMethod
			public LoginPage launchApplication() throws IOException 
			{
				driver=initializeDriver();
				loginlocators = new LoginPage(driver);
				loginlocators.goTo();
				
				
		        
				return  new LoginPage(driver);
			}
			
			@AfterMethod
			public void tearDown()
			{
				driver.close();
			}
			

}
