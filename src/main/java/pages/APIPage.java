package pages;

import io.restassured.response.Response;
import java.util.*;
import static io.restassured.RestAssured.*;

public class APIPage {

	// private static final String BASE_URL = "https://reqres.in/api";
	private final String baseUrl;

	public APIPage(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Response getUsersByPage(int page) {
		return given().baseUri(baseUrl).queryParam("page", page).when().get("/users");
	}

	/// new method
	/*
	 * public List<Integer> getAllUserIds() { List<Integer> allIds = new
	 * ArrayList(); int currentPage = 1; int totalPages; do { Response res =
	 * getUsersByPage(currentPage); totalPages =
	 * res.jsonPath().getInt("total_pages");
	 * allIds.addAll(res.jsonPath().getList("data")); currentPage++;
	 * System.out.println(res.getBody().asString()); } while (currentPage <=
	 * totalPages); return allIds; }
	 */
	public List<Integer> getAllUserIds() {
		List<Integer> allIds = new ArrayList<>();
		int currentPage = 1;
		int totalPages = 1;

		do {
			Response res = getUsersByPage(currentPage);
			System.out.println("Page: " + currentPage);
			System.out.println("Status Code: " + res.getStatusCode());
			System.out.println("Response: " + res.getBody().asString());
			if (res.getStatusCode() != 200) {
				throw new RuntimeException("API failed with status code: " + res.getStatusCode());
			}
			Integer pages = res.jsonPath().get("total_pages");
			if (pages == null) {
				throw new RuntimeException("Missing 'total_pages' in API response");
			}
			totalPages = pages;
			List<Map<String, Object>> users = res.jsonPath().getList("data");
			if (users != null) {
				for (Map<String, Object> user : users) {
					Object id = user.get("id");
					if (id instanceof Integer) {
						allIds.add((Integer) id);
					} else if (id != null) {
						allIds.add(Integer.parseInt(id.toString()));
					}
				}
			}
			currentPage++;
		} while (currentPage <= totalPages);
		return allIds;
	}

	public int getTotalUserCount() {
		Response response = getUsersByPage(2);
		return response.jsonPath().getInt("total");
	}

	public Response getSingleUser(int userId) {
		return given().baseUri(baseUrl).when().get("/users/" + userId);
	}

	/// new method
	public Response getSingleUserUnknown(int userId) {
		System.out.println(baseUrl + "/unknown/" + userId);
		return given().baseUri(baseUrl).when().get("/unknown/" + userId);
	}

	public Response createUser(String name, String job) {
		Map<String, String> userData = new HashMap<>();
		userData.put("name", name);
		userData.put("job", job);
		return given().baseUri(baseUrl).body(userData).header("Content-Type", "application/json").post("/users");
	}

	public Response login(String email, String password) {
		Map<String, String> loginData = new HashMap<>();
		loginData.put("email", email);
		if (password != null) {
			loginData.put("password", password);
		}
		return given().baseUri(baseUrl).header("Content-Type", "application/json").body(loginData).post("/login");

		// return given().baseUri(baseUrl).body(loginData).header("Content-Type",
		// "application/json").post("/login");
	}

	public Response getDelayedUsers() {
		return given().baseUri(baseUrl).queryParam("delay", 12).when().get("/users");
	}
}
