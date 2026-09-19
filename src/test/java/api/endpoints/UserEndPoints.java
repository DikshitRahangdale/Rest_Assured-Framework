package api.endpoints;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.*;
import java.io.IOException;
import api.payload.User;
import io.restassured.http.ContentType;

public class UserEndPoints {

	public Response createUser(User payload, String url) throws IOException {

		Response response = given().when().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
				.post(url);
		return response;
	}

	public Response createUserWithArray(List<User> payload, String url) throws IOException {

		Response response = given().when().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
				.post(url);
		return response;
	}

	public Response getUser(String userName, String url) {

		Response response = given().pathParam("username", userName).when().get(url);
		return response;

	}

	public Response updateUser(User payload, String userName, String url) {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", userName).body(payload).when().put(url);
		return response;
	}

	public Response deleteUser(String userName, String url) {

		Response response = given().pathParam("username", userName).when().delete(url);
		return response;
	}

	public Response loginUser(String username, String password, String url) {
		Response response = given().auth().basic(username, password).when().get(url);
		return response;
	}
	
	public Response logOutUser(String url) {

		Response response = given().when().get(url);
		return response;

	}


}
