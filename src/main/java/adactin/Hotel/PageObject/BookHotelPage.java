package adactin.Hotel.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adactin.abstractComponent.AbstractClass;

public class BookHotelPage extends AbstractClass{
	
	  WebDriver driver;

	    public BookHotelPage(WebDriver driver) {
	        super(driver);
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    // ===== Locators =====

	    @FindBy(id = "first_name")
	    WebElement firstName;

	    @FindBy(id = "last_name")
	    WebElement lastName;

	    @FindBy(id = "address")
	    WebElement address;

	    @FindBy(id = "cc_num")
	    WebElement creditCard;

	    @FindBy(id = "cc_type")
	    WebElement cardType;

	    @FindBy(id = "cc_exp_month")
	    WebElement expMonth;

	    @FindBy(id = "cc_exp_year")
	    WebElement expYear;

	    @FindBy(id = "cc_cvv")
	    WebElement cvv;

	    @FindBy(id = "book_now")
	    WebElement bookNowBtn;

	    @FindBy(id = "order_no")
	    WebElement orderNumber;

	    @FindBy(id = "cc_num_span")
	    WebElement cardError;

	    @FindBy(id = "cc_cvv_span")
	    WebElement cvvError;

	    // ===== Actions =====

	    public void enterFirstName(String fname) {
	        firstName.sendKeys(fname);
	    }

	    public void enterLastName(String lname) {
	        lastName.sendKeys(lname);
	    }

	    public void enterAddress(String addr) {
	        address.sendKeys(addr);
	    }

	    public void enterCreditCard(String card) {
	        creditCard.sendKeys(card);
	    }

	    public void selectCardType(String type) {
	        selectByVisibleText(cardType, type);
	    }

	    public void selectExpMonth(String month) {
	        selectByVisibleText(expMonth, month);
	    }

	    public void selectExpYear(String year) {
	        selectByVisibleText(expYear, year);
	    }

	    public void enterCVV(String code) {
	        cvv.sendKeys(code);
	    }

	    public void clickBookNow() {
	        bookNowBtn.click();
	    }

	    // ===== Combined Action =====

	    public ItineraryPage bookHotel(String fname, String lname, String addr,
	                          String card, String type, String month,
	                          String year, String cvvCode) {

	        enterFirstName(fname);
	        enterLastName(lname);
	        enterAddress(addr);
	        enterCreditCard(card);
	        selectCardType(type);
	        selectExpMonth(month);
	        selectExpYear(year);
	        enterCVV(cvvCode);
	        clickBookNow();
	        return new ItineraryPage(driver);
	    }

	    // ===== Validations =====
	    
	  
	   

	    public String getOrderNumber() {
	        return orderNumber.getAttribute("value");
	    }

	    public String getCardError() {
	        return cardError.getText();
	    }

	    public String getCVVError() {
	        return cvvError.getText();
	    }

}
