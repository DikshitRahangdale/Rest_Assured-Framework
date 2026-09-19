package api.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.PropertiesFileReader;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class MultipleUser {
	public UserEndPoints userEndPoints; 
	public PropertiesFileReader config;
	
	@BeforeClass
	public void classSetup() throws IOException
	{
		
		userEndPoints=new UserEndPoints();
		config=new PropertiesFileReader();
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
		
		String url=config.getUrl("postUrl");

		Response reseponse = userEndPoints.createUser(userPayload,url);
		Assert.assertEquals(reseponse.getStatusCode(), 200);

	}

	@Test(priority = 2, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void getAlluser(String username) {

		String url=config.getUrl("getUrl");
		Response response = userEndPoints.getUser(username,url);
		Assert.assertEquals(response.getStatusCode(), 200);
		String body = response.getBody().asPrettyString();
		System.out.println("Response Body-->" + body);
	}

	@Test(priority = 3, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void deleteAllUser(String username) {
		String url=config.getUrl("deleteUrl");
		Response response = userEndPoints.deleteUser(username,url);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
