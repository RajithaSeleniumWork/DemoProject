package adactin.testComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    // Thread-safe driver for parallel execution
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties prop;

    public WebDriver getDriver() {
        return driver.get();
    }

    // ---------------- DRIVER INITIALIZATION ----------------
    public WebDriver initializeDriver(String browserName) throws IOException {

        // Load properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\test\\resources\\GlobalData.properties");
        prop.load(fis);

        if (browserName == null || browserName.isEmpty()) {
            browserName = prop.getProperty("browser");
        }

        String headless = System.getProperty("headless");

        WebDriver driverInstance;

        // ---------------- CHROME ----------------
        if (browserName.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if ("true".equalsIgnoreCase(headless)) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--window-size=1920,1080");
            }

            driverInstance = new ChromeDriver(options);

        }

        // ---------------- FIREFOX ----------------
        else if (browserName.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if ("true".equalsIgnoreCase(headless)) {
                options.addArguments("--headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
            }

            driverInstance = new FirefoxDriver(options);
        }

        // ---------------- EDGE ----------------
        else if (browserName.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();

            if ("true".equalsIgnoreCase(headless)) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--remote-allow-origins=*");
            }

            driverInstance = new EdgeDriver(options);
        }

        else {
            throw new RuntimeException("Browser not supported: " + browserName);
        }

        driver.set(driverInstance);

        // ---------------- GLOBAL SETTINGS ----------------
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();

        return getDriver();
    }

    // ---------------- BEFORE METHOD ----------------
    @Parameters("browser")
    @BeforeMethod
    public void launchApplication(@Optional String browser) throws IOException {

        initializeDriver(browser);

        String url = prop.getProperty("URL");
        getDriver().get(url);
    }

    // ---------------- SCREENSHOT ----------------
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        java.io.File source = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir")
                + "/reports/screenshots/"
                + testCaseName + "_" + System.currentTimeMillis() + ".png";

        java.io.File file = new java.io.File(path);
        org.apache.commons.io.FileUtils.copyFile(source, file);

        return path;
    }

    // ---------------- TEARDOWN ----------------
    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}