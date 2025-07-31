package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilis.Constants;
import utilis.ElementUtilis;

public class LoginPage {

	WebDriver driver;
	private ElementUtilis elementutilis;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementutilis = new ElementUtilis(driver);
	}

	@FindBy(id = "user-name")
	private WebElement emailAddressoption;

	@FindBy(id = "password")
	private WebElement Passwordoption;

	@FindBy(id = "login-button")
	private WebElement loginbuttonoption;

	public void emailAddress(String emailaddress) {
		elementutilis.typetextIntoElement(emailAddressoption, emailaddress, Constants.EXPLICIT_WAIT_TIME);
	}

	public void password(String password) {
		elementutilis.typetextIntoElement(Passwordoption, password, Constants.EXPLICIT_WAIT_TIME);
	}

	public AccountPage loginbutton() {
		elementutilis.clickonElement(loginbuttonoption, Constants.EXPLICIT_WAIT_TIME);
		return new AccountPage(driver);
	}
}
