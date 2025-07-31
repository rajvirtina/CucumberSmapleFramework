package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilis.Constants;
import utilis.ElementUtilis;

public class CheckoutPage {

	WebDriver driver;
	Actions action;
	private ElementUtilis elementutilis;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementutilis = new ElementUtilis(driver);
	}

	@FindBy(xpath = "//button[text()='Checkout']")
	private WebElement checkoutverifytext;

	@FindBy(id = "first-name")
	private WebElement Firstname;

	@FindBy(id = "last-name")
	private WebElement Lastname;

	@FindBy(id = "postal-code")
	private WebElement postcode;

	@FindBy(id = "continue")
	private WebElement continueButton;

	public void checkoutClick() {
		elementutilis.scrollToElementAndClick(checkoutverifytext, Constants.EXPLICIT_WAIT_TIME);
	}

	public WebElement Firstname() {
		return Firstname;
	}

	public WebElement Lastname() {
		return Lastname;
	}

	public WebElement postcode() {
		return postcode;
	}

	public void continuebutton() {

		elementutilis.mousehoverandclick(continueButton, Constants.EXPLICIT_WAIT_TIME);
	}
}