package adactin.abstractComponent;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractClass {
WebDriver driver;
	
	public AbstractClass(WebDriver driver) {
		this.driver=driver;
	}

	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
	}
	
	@FindBy(xpath="(//td[@class='welcome_menu'])[2]")
	WebElement header;
	
	public void headerPart() {
		String text=header.getText();
		System.out.println(text);
	}
	
	 // ✅ Select by Visible Text
    public void selectByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    // ✅ Select by Index
    public void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    // ✅ Select by Value
    public void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }
    
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

}
