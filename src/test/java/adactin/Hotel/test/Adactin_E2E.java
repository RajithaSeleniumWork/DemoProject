package adactin.Hotel.test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.BookHotelPage;
import adactin.Hotel.PageObject.ItineraryPage;
import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.Hotel.PageObject.SelectHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Adactin_E2E extends BaseClass{
	   @Test(dataProvider = "eneToEnd")
	    public void bookAndCancelHotel(HashMap<String, String> input) {

	        // LOGIN
		    // Login
			 LoginPage lp = new LoginPage(driver);
			    lp.loginApplication(
			            input.get("username"),
			            input.get("password")
			    );

	

	        // SEARCH HOTEL
	        SearchHotelPage sp = new SearchHotelPage(driver);

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


	        // SELECT HOTEL
	        shp.selectFirstHotel();

	        // BOOK HOTEL
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

	        // ORDER ID
	        String orderId = bp.getOrderNumber();
	        System.out.println("Order ID: " + orderId);

	        

	        // ITINERARY
	        ip.clickItinerary();

	        
	    }
	   
	   
	   @DataProvider(name = "eneToEnd")
		 public Object[][] getData() throws Exception {

		     DataReader reader = new DataReader();

		     List<HashMap<String, String>> data =
		             reader.getJsonDataToMap(
		                     System.getProperty("user.dir")
		                     + "//src//test//java//adactin//data//Adactin_E2E.json"
		             );

		     Object[][] arr = new Object[data.size()][1];

		     for (int i = 0; i < data.size(); i++) {
		         arr[i][0] = data.get(i);
		     }

		     return arr;
		 }
}
