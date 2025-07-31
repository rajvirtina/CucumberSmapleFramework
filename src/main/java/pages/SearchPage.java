package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilis.Constants;
import utilis.ElementUtilis;

public class SearchPage {

	WebDriver driver;
	Actions action;
	private ElementUtilis elementutilis;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementutilis = new ElementUtilis(driver);
	}

	String productList = "//div[normalize-space(text())='$text']/ancestor::div[@class='inventory_item']//button";

	@FindBy(xpath = "//span[@class='shopping_cart_badge']")
	private WebElement shoppingcartBadge;

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	private WebElement shoppingcartlink;

	String quantityLocator = "//div[normalize-space(text())='$text']/ancestor::div[@class='cart_item']//div[@class='cart_quantity']";
	
	String removeProductButton = "//div[normalize-space(text())='$text']/ancestor::div[@class='cart_item']//button";


	@FindBy(xpath = "//div[@class='inventory_item_name']")
	private List<WebElement> cartProductNames;

	@FindBy(xpath = "//div[@class='cart_quantity']")
	private List<WebElement> cartQuantities;

	public void AddtocartButton(String itemName) throws InterruptedException {
		elementutilis.mousehoverandclickOnString(productList.replace("$text", itemName), Constants.EXPLICIT_WAIT_TIME);
	}

	public String Shoppingcartsuccesslink() {
		return elementutilis.gettingthetext(shoppingcartBadge, Constants.EXPLICIT_WAIT_TIME);
	}

	public void ShoppingCartLink() {
		elementutilis.scrollToElementAndClick(shoppingcartlink, Constants.EXPLICIT_WAIT_TIME);
	}

	public String ShoppingcartItemQtyLink(String itemName) {
		return elementutilis.gettingthetextString(quantityLocator.replace("$text", itemName),
				Constants.EXPLICIT_WAIT_TIME);
	}

	public void productNamesCount(int count) {
		int productCount = cartProductNames.size();
		int quantityCount = cartQuantities.size();
		if (productCount != quantityCount) {
			throw new AssertionError(
					"Mismatch in count: Products(" + productCount + ") vs Quantities(" + quantityCount + ")");
		}
		for (int i = 0; i < productCount; i++) {
			String productName = cartProductNames.get(i).getText().trim();
			String quantity = cartQuantities.get(i).getText().trim();
			int quantityInt = Integer.parseInt(quantity);
			if (quantityInt != count) {
				throw new AssertionError(
						"Quantity for product '" + productName + "' is not '" + count + "', but: " + quantity);
			}
		}
	}
	
	public void RemoveButton(String itemName) {
		elementutilis.mousehoverandclickOnString(removeProductButton.replace("$text", itemName),  Constants.EXPLICIT_WAIT_TIME);
	}

}
