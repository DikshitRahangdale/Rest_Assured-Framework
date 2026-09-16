package api.endpoints;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import api.payload.User;
import io.restassured.http.ContentType;


public class UserEndPoints {
	

	public static Response createUser(User payload) throws IOException {

		FileReader reader = new FileReader("src/test/resources/urls.properties");
		Properties pr = new Properties();
		pr.load(reader);
		
		
		Response response = given().when().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
				.post(pr.getProperty("postUrl"));
		return response;
	}

	public static Response getUser(String userName) {
		
		Response response = given().pathParam("username", userName).when().get(Routes.getUrl);
		return response;

	}

	public static Response updateUser(User payload, String userName) {
		
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", userName).body(payload).when().put(Routes.putUrl);
		return response;
	}

	public static Response deleteUser(String userName) {
		
		Response response = given().pathParam("username", userName).when().delete(Routes.deleteUrl);
		return response;
	}

}
