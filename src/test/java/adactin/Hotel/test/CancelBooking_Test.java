package adactin.Hotel.test;


import java.util.HashMap;
import java.util.List;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.BookHotelPage;
import adactin.Hotel.PageObject.ItineraryPage;
import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.Hotel.PageObject.SelectHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;


public class CancelBooking_Test extends BaseClass{
	
	 @Test(dataProvider = "cancelData")
	    public void cancelBookingTest(HashMap<String, String> input) {

		 LoginPage lp = new LoginPage(getDriver());
		    lp.loginApplication(
		            input.get("username"),
		            input.get("password")
		    );

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

			        BookHotelPage bp = shp.clickContinue();

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

			        ItineraryPage itinerary = ip.clickItinerary();

			        if (input.get("testType").equals("cancelSelected")) {
			            itinerary.cancelSelectedBooking();
			        } else {
			            itinerary.cancelWithoutSelection();
			        }
			    }
	 
	 @DataProvider(name = "cancelData")
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

