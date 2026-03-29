package adactin.Hotel.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adactin.abstractComponent.AbstractClass;

public class SearchHotelPage extends AbstractClass{
	
	 WebDriver driver;

	    public SearchHotelPage(WebDriver driver) {
	        super(driver);
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }

	    // ===== Locators =====

	    @FindBy(id = "location")
	    WebElement location;

	    @FindBy(id = "hotels")
	    WebElement hotel;

	    @FindBy(id = "room_type")
	    WebElement roomType;

	    @FindBy(id = "room_nos")
	    WebElement numberOfRooms;

	    @FindBy(id = "datepick_in")
	    WebElement checkInDate;

	    @FindBy(id = "datepick_out")
	    WebElement checkOutDate;

	    @FindBy(id = "adult_room")
	    WebElement adults;

	    @FindBy(id = "child_room")
	    WebElement children;

	    @FindBy(id = "Submit")
	    WebElement searchBtn;

	    // ===== Error Messages =====

	    @FindBy(id = "checkin_span")
	    WebElement checkInError;

	    @FindBy(id = "checkout_span")
	    WebElement checkOutError;

	    @FindBy(id = "location_span")
	    WebElement locationError;

	    // ===== Actions =====

	    public void selectLocation(String value) {
	        selectByVisibleText(location, value);
	    }

	    public void selectHotel(int index) {
	        selectByIndex(hotel, index);
	    }

	    public void selectRoomType(String value) {
	        selectByValue(roomType, value);
	    }

	    public void selectRooms(String value) {
	        selectByValue(numberOfRooms, value);
	    }

	    public void enterCheckInDate(String date) {
	        checkInDate.clear();
	        checkInDate.sendKeys(date);
	    }

	    public void enterCheckOutDate(String date) {
	        checkOutDate.clear();
	        checkOutDate.sendKeys(date);
	    }

	    public void selectAdults(String value) {
	        selectByValue(adults, value);
	    }

	    public void selectChildren(String value) {
	        selectByValue(children, value);
	    }

	    public void clickSearch() {
	        searchBtn.click();
	    }

	    // ===== Combined Action =====

	    public SelectHotelPage searchHotel(String locationVal, int hotelIndex, String roomTypeVal,
	                           String rooms, String checkIn, String checkOut,
	                           String adult, String child) {

	        selectLocation(locationVal);
	        selectHotel(hotelIndex);
	        selectRoomType(roomTypeVal);
	        selectRooms(rooms);
	        enterCheckInDate(checkIn);
	        enterCheckOutDate(checkOut);
	        selectAdults(adult);
	        selectChildren(child);
	        clickSearch();
	        
	        return new SelectHotelPage(driver);
	    }

	    // ===== Validations =====

	    public String getCheckInError() {
	        return checkInError.getText();
	    }

	    public String getCheckOutError() {
	        return checkOutError.getText();
	    }

	    public String getLocationError() {
	        return locationError.getText();
	    }

}
