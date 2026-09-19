package api.test;

import java.io.IOException;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.PropertiesFileReader;
import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.ExcelData;
import io.restassured.response.Response;

public class MultipleUserArray {

	public UserEndPoints userEndPoints;
	public PropertiesFileReader config;
	public ExcelData excelData;

	@BeforeClass
	public void classSetup() throws IOException {
		userEndPoints = new UserEndPoints();
		config = new PropertiesFileReader();
		String path = System.getProperty("user.dir") + "//testData//apiUserData.xlsx";
		excelData = new ExcelData(path); 
	}

	@Test(priority = 1)
	public void createsMultipleUserswithArray() throws IOException {
		List<User> users = new ArrayList<>();
		int lastRowIndex = excelData.getRowCount("Users");

		for (int i = 1; i <= lastRowIndex; i++) {

			User userPayload = new User();

			userPayload.setId(Integer.parseInt(excelData.getCellData("Users", i, 0)));

			userPayload.setUsername(excelData.getCellData("Users", i, 1));

			userPayload.setFirstName(excelData.getCellData("Users", i, 2));

			userPayload.setLastName(excelData.getCellData("Users", i, 3));

			userPayload.setEmail(excelData.getCellData("Users", i, 4));

			userPayload.setPassword(excelData.getCellData("Users", i, 5));

			userPayload.setPhone(excelData.getCellData("Users", i, 6));

			userPayload.setUserStatus(Integer.parseInt(excelData.getCellData("Users", i, 7)));

			users.add(userPayload);
		}

		String url = config.getUrl("postUrlwithAraays");

		Response reseponse = userEndPoints.createUserWithArray(users, url);
		Assert.assertEquals(reseponse.getStatusCode(), 200);

	}
	
	@Test(priority = 2)
	public void loginUsers() throws IOException
	{
		User userPayload = new User();
		userPayload.setPassword(excelData.getCellData("Users", 1, 5));
		userPayload.setUsername(excelData.getCellData("Users", 1, 1));
		String url = config.getUrl("userLoginUrl");
		
		Response response=userEndPoints.loginUser(userPayload.getUsername(), userPayload.getPassword(), url);
		Assert.assertEquals(response.getStatusCode(), 200,"Status Code does not match");
		
		String url2 = config.getUrl("userLogout");
		Response response2=userEndPoints.logOutUser(url2);
		Assert.assertEquals(response.getStatusCode(), 200,"Status Code does not match");
		

	}
	

	@Test(priority = 3, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void getAlluser(String username) {

		String url = config.getUrl("getUrl");
		Response response = userEndPoints.getUser(username, url);
		Assert.assertEquals(response.getStatusCode(), 200);
		String body = response.getBody().asPrettyString();
		System.out.println("Response Body-->" + body);
	}

	@Test(priority = 4, dataProvider = "userName", dataProviderClass = DataProviders.class)
	public void deleteAllUser(String username) {
		String url = config.getUrl("deleteUrl");
		Response response = userEndPoints.deleteUser(username, url);
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
