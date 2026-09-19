package api.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.PropertiesFileReader;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import net.bytebuddy.asm.Advice.This;

public class UserTest {
	Faker faker;
	User userPayload;
	public UserEndPoints userEndPoints;
	public PropertiesFileReader config;

	@BeforeClass
	public void setUpData() throws IOException {
		faker = new Faker();
		userPayload = new User(faker.idNumber().hashCode(), faker.name().username(), faker.name().firstName(),
				faker.name().lastName(), faker.internet().safeEmailAddress(), faker.internet().password(5, 10),
				faker.phoneNumber().cellPhone(), 0);
		userEndPoints=new UserEndPoints();
		config=new PropertiesFileReader();
		

	}

	@Test(priority = 1)
	public void testUserCreation() throws IOException {
		String url=config.getUrl("postUrl");
		System.out.println("=========Post Requests=======");
		Response response = userEndPoints.createUser(userPayload,url); 
		response.then().statusCode(200).log().all();
	}

	@Test(priority = 2)
	public void testGetUser() {
		String url=config.getUrl("getUrl");
		System.out.println("=========Get Request=======");
		Response response = userEndPoints.getUser(userPayload.getUsername(),url);
		Assert.assertEquals(response.getStatusCode(), 200);
		String userbody = response.getBody().asPrettyString();
		System.out.println("Response Data-->" + userbody);

	}

	@Test(priority = 3)
	public void updateUserInfo() {
		String url=config.getUrl("putUrl");
		System.out.println("=========Put Request=======");
		userPayload.setFirstName(faker.name().firstName());
		
		Response response = userEndPoints.updateUser(userPayload, this.userPayload.getUsername(),url);
		response.then().statusCode(200);
		String body = response.getBody().asPrettyString();
		System.out.println("Body:-->" + body);
		
		String url2=config.getUrl("getUrl");

		Response response2 = userEndPoints.getUser(userPayload.getUsername(),url2);
		Assert.assertEquals(response2.getStatusCode(), 200);
		String userbody = response2.getBody().asPrettyString();
		System.out.println("Response Data-->" + userbody);
		String userFirstnameString = response2.jsonPath().getString("firstName");
		Assert.assertEquals(userFirstnameString, userPayload.getFirstName(), "User First name Does not match");

	}

	@Test(priority = 4)
	public void deleteUserinfo() {
		String url=config.getUrl("deleteUrl");
		System.out.println("=========Delete Request=======");
		Response response = userEndPoints.deleteUser(this.userPayload.getUsername(),url);
		response.then().statusCode(200);
         
		String url2=config.getUrl("getUrl");
		Response response2 = userEndPoints.getUser(userPayload.getUsername(),url2);
		String userBody = response2.getBody().asPrettyString();
		System.out.println("user Body-->" + userBody);
		Assert.assertEquals(response2.getStatusCode(), 404);
	}
}
