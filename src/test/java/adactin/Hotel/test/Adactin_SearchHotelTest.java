package adactin.Hotel.test;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;


public class Adactin_SearchHotelTest extends BaseClass{
	@Test(dataProvider = "searchData")
	public void searchHotelTest(HashMap<String, String> input) {

	    LoginPage lp = new LoginPage(getDriver());
	    lp.loginApplication(
	            input.get("username"),
	            input.get("password")
	    );

	    SearchHotelPage sp = new SearchHotelPage(getDriver());

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

	    String type = input.get("type").toLowerCase();

	    if (type.equals("valid")) {

	        Assert.assertTrue(getDriver().getCurrentUrl().contains("SelectHotel"));

	    } else if (type.equals("invaliddate")) {

	        Assert.assertEquals(sp.getCheckInError(),
	                "Check-In Date shall be before than Check-Out Date");

	    } else if (type.equals("missinglocation")) {

	        Assert.assertEquals(sp.getLocationError(),
	                "Please Select a Location");
	    }
	}
	
	
	
	@DataProvider(name = "searchData")
	public Object[][] getSearchData() throws IOException {

	    DataReader reader = new DataReader();

	    List<HashMap<String, String>> data = reader.getJsonDataToMap(
	            System.getProperty("user.dir") +
	            "//src//test//java//adactin//data//SearchHotel.json"
	    );

	    Object[][] arr = new Object[data.size()][1];

	    for (int i = 0; i < data.size(); i++) {
	        arr[i][0] = data.get(i);
	    }

	    return arr;
	}

}
