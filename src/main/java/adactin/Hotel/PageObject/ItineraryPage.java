package adactin.Hotel.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adactin.abstractComponent.AbstractClass;

public class ItineraryPage extends AbstractClass {

    WebDriver driver;

    public ItineraryPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(xpath = "(//input[@name='ids[]'])[1]")
    WebElement firstCheckbox;

    @FindBy(name = "cancelall")
    WebElement cancelAllBtn;
    
    @FindBy(id = "my_itinerary")
    WebElement itineraryBtn;
   
    

    // Actions
    public void selectFirstBooking() {
        firstCheckbox.click();
    }

    public void clickCancelAll() {
        cancelAllBtn.click();
    }
    

    // ✅ Combined Business Method (BEST PRACTICE)
    public void cancelSelectedBooking() {
        selectFirstBooking();
        clickCancelAll();
        acceptAlert();   // from AbstractClass
    }

    public void cancelWithoutSelection() {
        clickCancelAll();
        acceptAlert();   // from AbstractClass
    }

    // Validation
    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
    


    public ItineraryPage clickItinerary() {
        itineraryBtn.click();
        return new ItineraryPage(driver);
    }
}