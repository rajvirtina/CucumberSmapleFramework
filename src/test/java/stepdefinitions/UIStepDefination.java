package stepdefinitions;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import Hooks.Hooks;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AccountPage;
import pages.CheckoutPage;
import pages.CheckoutSummaryPage;
import pages.LoginPage;
import pages.SearchPage;
import utilis.ConfigReader;

public class UIStepDefination extends DriverFactory {
	LoginPage LogPage = new LoginPage(driver);
	SearchPage sp = new SearchPage(driver);
	Logger logs = LogManager.getLogger(Hooks.class.getName());
	AccountPage APage = new AccountPage(driver);
	CheckoutPage checkPage = new CheckoutPage(driver);
	CheckoutSummaryPage checkoutSummaryPage = new CheckoutSummaryPage(driver);

	@Given("I am on the home page")
	public void i_am_on_the_home_page() {
		String url = ConfigReader.intializeProperties().getProperty("url");
		driver.get(url);
		System.out.println("Navigated to home page: " + url);
	}

	@And("I login in with the following details")
	public void i_login_in_with_the_following_details(DataTable dataTable) {
		List<Map<String, String>> credentialsList = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> credentials : credentialsList) {
			String username = credentials.get("userName");
			String password = credentials.get("Password");
			if (!credentials.containsKey("userName") || !credentials.containsKey("Password")) {
				throw new IllegalArgumentException("Missing required keys in DataTable");
			}
			LogPage.emailAddress(username);
			logs.debug("entered emailaddress" + username);
			LogPage.password(password);
			logs.debug("entered password" + password);
			LogPage.loginbutton();
			logs.debug("clicked on loginbutton");
			Assert.assertEquals("Products", APage.confirmlogin());
			logs.info("login successfully");
		}
	}

	@And("I add the following items to the basket")
	public void i_add_the_following_items_to_the_basket(DataTable dataTable) throws InterruptedException {
		List<String> productNames = dataTable.asList(String.class);
		for (String itemName : productNames) {
			sp.AddtocartButton(itemName);
			logs.info("Successfully added: " + itemName + "Product to cart");
		}
	}

	@And("I  should see {int} items added to the shopping cart")
	public void i_should_see_items_added_to_the_shopping_cart(int count) {
		Assert.assertEquals(count, Integer.parseInt(sp.Shoppingcartsuccesslink()),"Count Not Matched " +  Integer.parseInt(sp.Shoppingcartsuccesslink()));
		logs.info("Count Matched");
	}

	@And("I click on the shopping cart")
	public void i_click_on_the_shopping_cart() {
		sp.ShoppingCartLink();
		logs.info("Clicked and Navigated to Shopping Cart Page");
	}

	@And("I verify that the QTY count for each item should be {int}")
	public void i_verify_that_the_qty_count_for_each_item_should_be(int itemCount) {
		sp.productNamesCount(itemCount);
		logs.info(" QTY count for each item Matched");
	}

	@And("I remove the following item:")
	public void i_remove_the_following_item(DataTable dataTable) {
		List<String> items = dataTable.asList(String.class);
		for (String itemName : items) {
			sp.RemoveButton(itemName);
			logs.info("Item Removed from Cart" + itemName);
		}
	}

	@And("I click on the CHECKOUT button")
	public void i_click_on_the_checkout_button() {
		checkPage.checkoutClick();
		logs.info("Clicked on Checkout Button");
	}

	@And("I type {string} for First Name")
	public void i_type_for_first_name(String firstName) {
		checkPage.Firstname().sendKeys(firstName);
		logs.debug("entered valid firstname : " + firstName);
	}

	@And("I type {string} for Last Name")
	public void i_type_for_last_name(String lastName) {
		checkPage.Lastname().sendKeys(lastName);
		logs.debug("entered valid last name : " + lastName);

	}

	@And("I type {string} for ZIP\\/Postal Code")
	public void i_type_for_zip_postal_code(String zipcode) {
		checkPage.postcode().sendKeys(zipcode);
		logs.debug("entered valid zipcode : " + zipcode);
	}

	@When("I click on the CONTINUE button")
	public void i_click_on_the_continue_button() throws InterruptedException {
		checkPage.continuebutton();
		logs.debug("Clicked On Continue button");
	}

	@Then("Item total will be equal to the total of items on the list")
	public void item_total_will_be_equal_to_the_total_of_items_on_the_list() {
		double expected = checkoutSummaryPage.calculateExpectedItemTotal();
		double actual = checkoutSummaryPage.getDisplayedItemTotal();
		Assert.assertEquals(actual, expected, 0.01, "Item total mismatch");
		logs.info("Item total matches: $" + actual);
	}

	@Then("a Tax rate of {int} % is applied to the total")
	public void a_tax_rate_of_is_applied_to_the_total(Integer int1) {
		double itemTotal = checkoutSummaryPage.getDisplayedItemTotal();
		double tax = checkoutSummaryPage.getDisplayedTax();
		double expectedTax = Math.round(itemTotal * 0.08 * 100.0) / 100.0;
		Assert.assertEquals(tax, expectedTax, 0.01, "Tax calculation mismatch");
		logs.info("Tax is correctly applied: $" + tax);
	}
}
