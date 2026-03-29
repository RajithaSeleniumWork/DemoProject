package adactin.Hotel.test;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;


import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.Hotel.PageObject.SelectHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;


public class Adactin_SelectHotelTest extends BaseClass{
	
	@Test(dataProvider = "selectHotelData")
	public void selectHotelTest(HashMap<String, String> input) {

	    LoginPage lp = new LoginPage(driver);
	    lp.loginApplication(
	            input.get("username"),
	            input.get("password")
	    );

	    SearchHotelPage sp = new SearchHotelPage(driver);
	    sp.searchHotel(
	            input.get("location"),
	            2,
	            input.get("roomType"),
	            input.get("rooms"),
	            input.get("checkIn"),
	            input.get("checkOut"),
	            input.get("adults"),
	            input.get("children")
	    );

	    SelectHotelPage shp = new SelectHotelPage(driver);

	    String type = input.get("type").toLowerCase();

	    if (type.equals("validselect")) {

	        shp.selectFirstHotel();
	        shp.clickContinue();

	        Assert.assertEquals(
	                shp.getTitle(),
	                "Adactin.com - Book A Hotel"
	        );

	    } else if (type.equals("noradioselection")) {

	        shp.clickContinue();

	        Assert.assertEquals(
	                shp.getRadioError(),
	                "Please Select a Hotel"
	        );
	    }
	}
	
	@DataProvider(name = "selectHotelData")
	public Object[][] getData() throws IOException {

	    DataReader reader = new DataReader();

	    List<HashMap<String, String>> data =
	            reader.getJsonDataToMap(
	                    System.getProperty("user.dir")
	                    + "//src//test//java//adactin//data//SelectHotelData.json"
	            );

	    Object[][] arr = new Object[data.size()][1];

	    for (int i = 0; i < data.size(); i++) {
	        arr[i][0] = data.get(i);
	    }

	    return arr;
	}

	}


