package api.endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import api.payload.User;
import io.restassured.http.ContentType;

public class UserEndPoints extends PropertiesFileReader {

	public UserEndPoints() throws IOException {
		super();
	}

	public Response createUser(User payload) throws IOException {

		Response response = given().when().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
				.post(pr.getProperty("postUrl"));
		return response;
	}

	public Response getUser(String userName) {

		Response response = given().pathParam("username", userName).when().get(pr.getProperty("getUrl"));
		return response;

	}

	public Response updateUser(User payload, String userName) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", userName).body(payload).when().put(pr.getProperty("putUrl"));
		return response;
	}

	public Response deleteUser(String userName) {

		Response response = given().pathParam("username", userName).when().delete(pr.getProperty("deleteUrl"));
		return response;
	}

}
