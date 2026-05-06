package adactin.Hotel.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.*;

import adactin.Hotel.PageObject.LoginPage;
import adactin.data.DataReader;
import adactin.testComponents.BaseClass;

public class Adactin_LoginTest extends BaseClass {

    @Test(dataProvider = "loginData")
    public void loginTest(HashMap<String, String> input) {

        // ✅ Use getDriver() (Thread-safe)
        LoginPage lp = new LoginPage(getDriver());

        lp.loginApplication(
                input.get("username"),
                input.get("password")
        );

        String type = input.get("type").toLowerCase();

        switch (type) {

        case "valid":
            Assert.assertEquals(lp.getPageTitle(),
                    "Adactin.com - Search Hotel");
            break;

        case "invalid":
            Assert.assertTrue(lp.getInvalidLoginError().contains("Invalid Login"));
            break;

        case "emptyuser":
            Assert.assertEquals(lp.getUsernameError(), "Enter Username");
            break;

        case "emptypass":
            Assert.assertEquals(lp.getPasswordError(), "Enter Password");
            break;

        case "emptyboth":
            Assert.assertEquals(lp.getUsernameError(), "Enter Username");
            Assert.assertEquals(lp.getPasswordError(), "");
            break;
        }
    }

    // ✅ Enable parallel execution for data
    @DataProvider(name = "loginData", parallel = false)
    public Object[][] getData() throws IOException {

        DataReader reader = new DataReader();

        List<HashMap<String, String>> data = reader.getJsonDataToMap(
                System.getProperty("user.dir") +
                "/src/test/java/adactin/data/LoginTest.json"
        );

        Object[][] dataProvider = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            dataProvider[i][0] = data.get(i);
        }

        return dataProvider;
    }
}