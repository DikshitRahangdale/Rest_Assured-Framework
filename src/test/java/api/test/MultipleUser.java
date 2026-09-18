package api.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class MultipleUser {
	public UserEndPoints userEndPoints; 
	
	@BeforeClass
	public void classSetup() throws IOException
	{
		userEndPoints=new UserEndPoints();
	}
	
	@Test(priority = 1, dataProvider = "allData", dataProviderClass = DataProviders.class)
	public void createsMultipleUsers(String id, String userName, String firstName, String lastname, String email,
			String password, String phonenumber, String status) throws IOException {

		User userPayload = new User();
		userPayload.setId(Integer.parseInt(id));
		userPayload.setUsername(userName);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastname);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phonenumber);
		userPayload.setUserStatus(Integer.parseInt(status));

		Response reseponse = userEndPoints.createUser(userPayload);
		Assert.assertEquals(reseponse.getStatusCode(), 200);

	}

	@Test(priority = 2, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void getAlluser(String username) {
		Response response = userEndPoints.getUser(username);
		Assert.assertEquals(response.getStatusCode(), 200);
		String body = response.getBody().asPrettyString();
		System.out.println("Response Body-->" + body);
	}

	@Test(priority = 3, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void deleteAllUser(String username) {
		Response response = userEndPoints.deleteUser(username);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
