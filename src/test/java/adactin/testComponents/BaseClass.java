package adactin.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    // ThreadLocal for parallel execution
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties prop;

    // Getter method
    public WebDriver getDriver() {
        return driver.get();
    }

    // Initialize Driver based on browser
    public WebDriver initializeDriver(String browserName) throws IOException {

        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\test\\java\\adacin\\resources\\GlobalData.properties");
        prop.load(fis);

        WebDriver driverInstance = null;

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driverInstance = new ChromeDriver();

        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driverInstance = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driverInstance = new EdgeDriver();

        } else {
            throw new RuntimeException("Browser not supported");
        }

        driver.set(driverInstance);

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        return getDriver();
    }

    // Launch Application
    @Parameters("browser")
    @BeforeMethod
    public void launchApplication(@Optional String browser) throws IOException {

        initializeDriver(browser);

        String url = prop.getProperty("URL");
        getDriver().get(url);
    }

    // Screenshot method
    public String getScreenshot(String testCaseName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        File file = new File(System.getProperty("user.dir") +
                "/reports/" + testCaseName + ".png");

        FileUtils.copyFile(source, file);

        return file.getAbsolutePath();
    }

    // Close browser
    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // ✅ VERY IMPORTANT for parallel
        }
    }
}