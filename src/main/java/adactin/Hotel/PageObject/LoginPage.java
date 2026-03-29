package adactin.Hotel.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adactin.abstractComponent.AbstractClass;

public class LoginPage extends AbstractClass{
	
	 WebDriver driver;

	    // Constructor
	    public LoginPage(WebDriver driver) {
	        super(driver);  // call parent class
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    // ===== Locators =====

	    @FindBy(id = "username")
	    WebElement username;

	    @FindBy(id = "password")
	    WebElement password;

	    @FindBy(id = "login")
	    WebElement loginBtn;

	    @FindBy(className = "auth_error")
	    WebElement invalidErrorMsg;

	    @FindBy(id = "username_span")
	    WebElement usernameErrorMsg;

	    @FindBy(id = "password_span")
	    WebElement passwordErrorMsg;

	    // ===== Actions =====

	    public void enterUsername(String user) {
	        username.sendKeys(user);
	    }

	    public void enterPassword(String pass) {
	        password.sendKeys(pass);
	    }

	    public void clickLogin() {
	        loginBtn.click();
	    }

	    public void loginApplication(String user, String pass) {
	        username.sendKeys(user);
	        password.sendKeys(pass);
	        loginBtn.click();
	    }

	    // ===== Validations =====

	    public String getPageTitle() {
	        return driver.getTitle();
	    }

	    public String getInvalidLoginError() {
	        return invalidErrorMsg.getText();
	    }

	    public String getUsernameError() {
	        return usernameErrorMsg.getText();
	    }

	    public String getPasswordError() {
	        return passwordErrorMsg.getText();
	    }

	    // ===== Navigation =====

	    public void goTo() {
	        driver.get("https://adactinhotelapp.com/");
	    }

}
