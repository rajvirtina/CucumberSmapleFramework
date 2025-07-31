package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilis.Constants;
import utilis.ElementUtilis;

public class CheckoutSummaryPage {
	WebDriver driver;
	private ElementUtilis elementutilis;

	public CheckoutSummaryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementutilis = new ElementUtilis(driver);
	}

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	private List<WebElement> itemPrices;

	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	private WebElement itemTotal;

	@FindBy(xpath = "//div[@class='summary_tax_label']")
	private WebElement taxAmount;

	@FindBy(xpath = "//div[@class='summary_total_label']")
	private WebElement totalAmount;

	public double calculateExpectedItemTotal() {
		double sum = 0.0;
		for (WebElement price : itemPrices) {
			String priceText = elementutilis.gettingthetext(price, Constants.EXPLICIT_WAIT_TIME);
			sum += Double.parseDouble(priceText.replace("$", ""));
		}
		return Math.round(sum * 100.0) / 100.0;
	}

	public double getDisplayedItemTotal() {
		String itemTotalText = elementutilis.gettingthetext(itemTotal, Constants.EXPLICIT_WAIT_TIME);
		return Double.parseDouble(itemTotalText.replace("Item total: $", ""));
	}

	public double getDisplayedTax() {
		String taxText = elementutilis.gettingthetext(taxAmount, Constants.EXPLICIT_WAIT_TIME);
		return Double.parseDouble(taxText.replace("Tax: $", ""));
	}

	public double getDisplayedTotal() {
		String totalText = elementutilis.gettingthetext(totalAmount, Constants.EXPLICIT_WAIT_TIME);
		return Double.parseDouble(totalText.replace("Total: $", ""));
	}
}
