package adactin.Hotel.test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import adactin.Hotel.PageObject.LoginPage;
import adactin.Hotel.PageObject.SearchHotelPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;

public class Adactin_SearchHotelTest extends BaseClass {

    @Test(dataProvider = "searchData")
    public void searchHotelTest(HashMap<String, String> input) {

        // ---------------- LOGIN ----------------
        LoginPage lp = new LoginPage(getDriver());
        lp.loginApplication(
                input.get("username"),
                input.get("password")
        );

        // wait for page load after login
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Adactin"));

        // ---------------- SEARCH HOTEL ----------------
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

        // ---------------- VALIDATION ----------------
        switch (type) {

            case "valid":
                Assert.assertTrue(getDriver().getTitle()
                        .contains("Adactin"),
                        "Search Hotel page not loaded");
                break;

            case "invaliddate":
                Assert.assertEquals(sp.getCheckInError(),
                        "Check-In Date shall be before than Check-Out Date");
                break;

            case "missinglocation":
                Assert.assertEquals(sp.getLocationError(),
                        "Please Select a Location");
                break;

            default:
                Assert.fail("Invalid test type: " + type);
        }
    }

    // ---------------- DATA PROVIDER ----------------
    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws IOException {

        DataReader reader = new DataReader();

        List<HashMap<String, String>> data = reader.getJsonDataToMap(
                System.getProperty("user.dir")
                        + "/src/test/java/adactin/data/SearchHotel.json"
        );

        Object[][] arr = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            arr[i][0] = data.get(i);
        }

        return arr;
    }
}