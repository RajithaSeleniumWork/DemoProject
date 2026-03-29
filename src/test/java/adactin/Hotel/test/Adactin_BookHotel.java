package adactin.Hotel.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import adactin.Hotel.PageObject.BookHotelPage;
import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.Hotel.PageObject.SelectHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;


public class Adactin_BookHotel extends BaseClass{
	

	
	@Test(dataProvider = "bookHotelData")
	public void bookHotelTest(HashMap<String, String> input) {

	    LoginPage lp = new LoginPage(driver);
	    lp.loginApplication(
	            input.get("username"),
	            input.get("password")
	    );

	    SearchHotelPage sp = new SearchHotelPage(driver);
	    sp.searchHotel(
	            input.get("location"),
	            Integer.parseInt(input.get("hotelIndex")),
	            input.get("roomType"),
	            input.get("rooms"),
	            input.get("checkIn"),
	            input.get("checkOut"),
	            input.get("adults"),
	            input.get("children")
	    );

	    SelectHotelPage shp = new SelectHotelPage(driver);
	    shp.selectFirstHotel();
	    shp.clickContinue();

	    BookHotelPage bp = new BookHotelPage(driver);

	    String type = input.get("type").toLowerCase();

	    if (type.equals("validbooking")) {

	        bp.bookHotel(
	                input.get("firstName"),
	                input.get("lastName"),
	                input.get("address"),
	                input.get("cardNo"),
	                input.get("cardType"),
	                input.get("expiryMonth"),
	                input.get("expiryYear"),
	                input.get("cvv")
	        );

	        Assert.assertTrue(
	                bp.getOrderNumber() != null &&
	                !bp.getOrderNumber().isEmpty(),
	                "Order not generated"
	        );

	    } else if (type.equals("invalidcard")) {

	        bp.bookHotel(
	                input.get("firstName"),
	                input.get("lastName"),
	                input.get("address"),
	                input.get("cardNo"),
	                input.get("cardType"),
	                input.get("expiryMonth"),
	                input.get("expiryYear"),
	                input.get("cvv")
	        );

	        Assert.assertEquals(
	                bp.getCardError(),
	                "Please Enter your 16 Digit Credit Card Number"
	        );

	    } else if (type.equals("emptycvv")) {

	        bp.bookHotel(
	                input.get("firstName"),
	                input.get("lastName"),
	                input.get("address"),
	                input.get("cardNo"),
	                input.get("cardType"),
	                input.get("expiryMonth"),
	                input.get("expiryYear"),
	                input.get("cvv")
	        );

	        Assert.assertEquals(
	                bp.getCVVError(),
	                "Please Enter your Credit Card CVV Number"
	        );
	    }
	}
	
	@DataProvider(name = "bookHotelData")
	public Object[][] getData() throws IOException {

	    DataReader reader = new DataReader();

	    List<HashMap<String, String>> data =
	            reader.getJsonDataToMap(
	                    System.getProperty("user.dir")
	                    + "//src//test//java//adactin//data//BookHotelData.json"
	            );

	    Object[][] arr = new Object[data.size()][1];

	    for (int i = 0; i < data.size(); i++) {
	        arr[i][0] = data.get(i);
	    }

	    return arr;
	}
		


}
