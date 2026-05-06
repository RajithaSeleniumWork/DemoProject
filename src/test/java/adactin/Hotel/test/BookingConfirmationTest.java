package adactin.Hotel.test;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.BookHotelPage;

import adactin.Hotel.PageObject.ItineraryPage;
import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.Hotel.PageObject.SelectHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;

public class BookingConfirmationTest extends BaseClass {

	 @Test(dataProvider = "bookData")
	    public void bookingConfirmationTest(HashMap<String, String> input) {

	        // Login
		 LoginPage lp = new LoginPage(getDriver());
		    lp.loginApplication(
		            input.get("username"),
		            input.get("password")
		    );


	        // Search Hotel
	        SearchHotelPage sp = new SearchHotelPage(getDriver());

	        SelectHotelPage shp = sp.searchHotel(
	                input.get("location"),
	                Integer.parseInt(input.get("hotelIndex")),
	                input.get("roomType"),
	                input.get("rooms"),
	                input.get("checkIn"),
	                input.get("checkOut"),
	                input.get("adults"),
	                input.get("children")
	        );

	        shp.selectFirstHotel();

	        // Continue to booking page
	        BookHotelPage bp = shp.clickContinue();

	        // Book Hotel
	        ItineraryPage ip = bp.bookHotel(
	                input.get("firstName"),
	                input.get("lastName"),
	                input.get("address"),
	                input.get("cardNo"),
	                input.get("cardType"),
	                input.get("expiryMonth"),
	                input.get("expiryYear"),
	                input.get("cvv")
	        );

	        String type = input.get("testType");

	        // Verify Order ID
	        if (type.equalsIgnoreCase("verifyOrderID")) {

	            String order = bp.getOrderNumber();

	            Assert.assertTrue(order != null && order.length() > 0,
	                    "Order ID not generated");

	        }

	        // Navigate to Itinerary
	        else if (type.equalsIgnoreCase("navigateToItinerary")) {

	            ip.clickItinerary();

	            Assert.assertTrue(
	            		getDriver().getCurrentUrl().contains("BookedItinerary"),
	                    "Navigation failed"
	            );
	        }
	    }
	 @DataProvider(name = "bookData")
	 public Object[][] getData() throws Exception {

	     DataReader reader = new DataReader();

	     List<HashMap<String, String>> data =
	             reader.getJsonDataToMap(
	                     System.getProperty("user.dir")
	                     + "//src//test//java//adactin//data//BookingConfirmationData.json"
	             );

	     Object[][] arr = new Object[data.size()][1];

	     for (int i = 0; i < data.size(); i++) {
	         arr[i][0] = data.get(i);
	     }

	     return arr;
	 }
	 }
