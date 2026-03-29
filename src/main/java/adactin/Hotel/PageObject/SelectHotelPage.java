package adactin.Hotel.PageObject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adactin.abstractComponent.AbstractClass;

public class SelectHotelPage extends AbstractClass{
	
	  WebDriver driver;

	    public SelectHotelPage(WebDriver driver) {
	        super(driver);
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    // ===== Locators =====

	    @FindBy(xpath = "//form[@id='select_form']//table//tr[1]//td")
	    List<WebElement> hotelHeadings;

	    @FindBy(xpath = "//form[@id='select_form']//table//table//tr[2]//td//input")
	    List<WebElement> hotelDetails;

	    @FindBy(id = "radiobutton_0")
	    WebElement firstRadioBtn;

	    @FindBy(id = "continue")
	    WebElement continueBtn;

	    @FindBy(id = "radiobutton_span")
	    WebElement radioError;

	    // ===== Actions =====

	    public void printHotelHeadings() {
	        for (WebElement ele : hotelHeadings) {
	            System.out.println(ele.getText());
	        }
	    }

	    public void printHotelDetails() {
	        for (WebElement ele : hotelDetails) {
	            System.out.println(ele.getAttribute("value"));
	        }
	    }

	    public void selectFirstHotel() {
	        firstRadioBtn.click();
	    }

	    public BookHotelPage clickContinue() {
	        continueBtn.click();
	        return new BookHotelPage(driver);
	    }

	    // ===== Validation =====

	    public String getTitle() {
	        return driver.getTitle();
	    }

	    public String getRadioError() {
	        return radioError.getText();
	    }
	    
	  

}
