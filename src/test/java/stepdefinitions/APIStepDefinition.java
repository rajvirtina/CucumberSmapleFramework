package stepdefinitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pages.APIPage;
import utilis.ConfigReader;

public class APIStepDefinition {

	APIPage apiPage;
	List<Integer> allUserIds;
	int expectedTotalUsers;
	Response response;

	public APIStepDefinition() {
		String baseUrl = ConfigReader.getBaseApiURI("baseUrl");
		apiPage = new APIPage(baseUrl);
	}

	@Given("^I get the default list of users for on 1st page$")
	public void iGetTheDefaultListofusers() {
		expectedTotalUsers = apiPage.getTotalUserCount();

	}

	@When("I get the list of all users within every page")
	public void iGetTheListOfAllUsers() {
		allUserIds = apiPage.getAllUserIds();
		System.out.println(expectedTotalUsers);
		System.out.println(allUserIds.size());
	}

	@Then("I should see total users count equals the number of user ids")
	public void iShouldMatchTotalCount() {
		assertEquals(expectedTotalUsers, allUserIds.size());
	}

	@Given("I make a search for user {int}")
	public void iMakeASearchForUser(int sUserID) {
		response = apiPage.getSingleUser(sUserID);	
	}

	@Then("I should see the following user data")
	public void IShouldSeeFollowingUserData(DataTable dt) {
		assertEquals(response.statusCode(), 200, "Expected 200 OK but got: " + response.statusCode());
		List<Map<String, String>> expectedList = dt.asMaps(String.class, String.class);
		Map<String, String> expected = expectedList.get(0);
		Map<String, ?> actual = response.jsonPath().getMap("data");
		assertNotNull(actual, "User data is missing in the response");
		assertEquals(expected.get("first_name"), actual.get("first_name"));
		assertEquals(expected.get("email"), actual.get("email"));
	}

	@Then("I receive error code {int} in response")
	public void iReceiveErrorCodeInResponse(int responseCode) {
		assertEquals(responseCode, response.getStatusCode());
	}

	@Given("I create a user with following {word} {word}")
	public void iCreateUserWithFollowing(String sUsername, String sJob) {
		response = apiPage.createUser(sUsername, sJob);
	}

	@Then("response should contain the following data")
	public void iReceiveErrorCodeInResponse(DataTable dt) {
		List<String> expected = dt.row(0);
		for (String field : expected) {
			assertNotNull("Missing field" + field, response.jsonPath().getString(field));
		}
	}

	@Given("I login unsuccessfully with the following data")
	public void iLoginUnsuccessfullyWithFollowingData(DataTable dt) {
		Map<String, String> data = dt.asMap();
		String email = data.get("Email");
		String password = data.get("Password");
		response = apiPage.login(email, (password == null || password.trim().isEmpty()) ? null : password);
	}

	@Given("^I wait for the user list to load$")
	public void iWaitForUserListToLoad() {
		response = apiPage.getDelayedUsers();
	}

	@Then("I should see that every user has a unique id")
	public void iShouldSeeThatEveryUserHasAUniqueID() {
		List<Integer> ids = response.jsonPath().getList("data.id");
		assertNotNull(ids, "User IDs list is null");
		Set<Integer> uniqueIds = new HashSet<>(ids);
		assertEquals(ids.size(), uniqueIds.size(), "Duplicate user IDs found");
	}

	@Then("^I should get a response code of (\\d+)$")
	public void iShouldGetAResponseCodeOf(int responseCode) {
		assertEquals(responseCode, response.statusCode());
	}

	@And("^I should see the following response message:$")
	public void iShouldSeeTheFollowingResponseMessage(DataTable dt) {
		String expected = dt.asLists().get(0).get(0);
		assertTrue(response.asString().contains(expected));
	}
}
