package utilis;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtilis {

	WebDriver driver;

	public ElementUtilis(WebDriver driver) {
		this.driver = driver;
	}

	public void clickonElement(WebElement element, long durationInSeconds) {

		WebElement webelement = waitforelement(element, durationInSeconds);
		webelement.click();
	}

	public void typetextIntoElement(WebElement element, String texttobetyped, long durationInSeconds) {

		WebElement webelement = waitforelement(element, durationInSeconds);
		webelement.click();
		webelement.clear();
		webelement.sendKeys(texttobetyped);
	}

	public WebElement waitforelement(WebElement element, long durationInSeconds) {
		WebElement webelement = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, durationInSeconds);
			webelement = wait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (Exception e) {
			e.printStackTrace();

		}
		return webelement;
	}

	public void mousehoverandclick(WebElement element, long durationInSeconds) {

		WebElement webelement = waitforvisibilityofelement(element, durationInSeconds);
		Actions action = new Actions(driver);
		action.moveToElement(webelement).click().build().perform();

	}

	public void mousehoverandclickOnString(String element, long durationInSeconds) {
		WebElement ele = driver.findElement(By.xpath(element));
		WebElement webelement = waitforvisibilityofelement(ele, durationInSeconds);
		Actions action = new Actions(driver);
		action.moveToElement(webelement).click().build().perform();

	}

	public WebElement waitforvisibilityofelement(WebElement element, long durationInSeconds) {
		WebElement webelement = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, durationInSeconds);
			webelement = wait.until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			e.printStackTrace();

		}
		return webelement;
	}

	public String gettingthetext(WebElement element, long durationInSeconds) {
		WebElement webelement = waitforvisibilityofelement(element, durationInSeconds);
		String text = null;
		try {
			text = webelement.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public String gettingthetextString(String element, int durationInSeconds) {
		WebElement ele = driver.findElement(By.xpath(element));
		WebElement webelement = waitforvisibilityofelement(ele, durationInSeconds);
		String text = null;
		try {
			text = webelement.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return text;
	}

	public void scrollToElementAndClick(WebElement element, int durationInSeconds) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
		WebElement webelement = waitforvisibilityofelement(element, durationInSeconds);
		webelement.click();
	}
}
