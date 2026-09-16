package api.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import net.bytebuddy.asm.Advice.This;

public class UserTest {
	Faker faker;
	User userPayload;

	@BeforeClass
	public void setUpData() {
		faker = new Faker();
		userPayload = new User(faker.idNumber().hashCode(), faker.name().username(), faker.name().firstName(),
				faker.name().lastName(), faker.internet().safeEmailAddress(), faker.internet().password(5, 10),
				faker.phoneNumber().cellPhone(), 0);

	}

	@Test(priority = 1)
	public void testUserCreation() throws IOException {
		System.out.println("=========Post Requests=======");
		Response response = UserEndPoints.createUser(userPayload); 
		response.then().statusCode(200).log().all();
	}

	@Test(priority = 2)
	public void testGetUser() {
		System.out.println("=========Get Request=======");
		Response response = UserEndPoints.getUser(userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		String userbody = response.getBody().asPrettyString();
		System.out.println("Response Data-->" + userbody);

	}

	@Test(priority = 3)
	public void updateUserInfo() {
		System.out.println("=========Put Request=======");
		userPayload.setFirstName(faker.name().firstName());
		Response response = UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
		response.then().statusCode(200);
		String body = response.getBody().asPrettyString();
		System.out.println("Body:-->" + body);

		Response response2 = UserEndPoints.getUser(userPayload.getUsername());
		Assert.assertEquals(response2.getStatusCode(), 200);
		String userbody = response2.getBody().asPrettyString();
		System.out.println("Response Data-->" + userbody);
		String userFirstnameString = response2.jsonPath().getString("firstName");
		Assert.assertEquals(userFirstnameString, userPayload.getFirstName(), "User First name Does not match");

	}

	@Test(priority = 4)
	public void deleteUserinfo() {
		System.out.println("=========Delete Request=======");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().statusCode(200);

		Response response2 = UserEndPoints.getUser(userPayload.getUsername());
		String userBody = response2.getBody().asPrettyString();
		System.out.println("user Body-->" + userBody);
		Assert.assertEquals(response2.getStatusCode(), 404);
	}
}
