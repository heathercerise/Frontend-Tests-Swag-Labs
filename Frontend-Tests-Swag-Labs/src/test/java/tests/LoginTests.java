package tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LoginPage;

public class LoginTests extends BaseTests{
	
	// error message constants
	private final String USER_DOES_NOT_EXIST_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
	private final String MUST_HAVE_USERNAME_MESSAGE = "Epic sadface: Username is required";
	private final String MUST_HAVE_PASSWORD_MESSAGE = "Epic sadface: Password is required";
	private final String LOCKED_OUT_MESSAGE = "Epic sadface: Sorry, this user has been locked out.";
	
	// value for testing locked out user
	private final String LOCKED_OUT_USERNAME = "locked_out_user";
	private final String LOCKED_OUT_PASSWORD = "secret_sauce";
	
	// fake username and passwords that don't exist
	private final String FAKE_USERNAME = "fake_user";
	private final String FAKE_PASSWORD= "fake_password";
	
	@BeforeMethod
	public void beforeEach() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		
	}
	
	
	@Test(description = "Verify user can login with correct credentials and button click.",
			dataProvider = "validCredentials")
	public void loginValidUserClick(String username, String password) {
		// login with correct credentials and press button
		loginPage.login(username, password, true);
		
		String expectedUrl=url+inventory_ext;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
		
	}
	
	@Test(description = "Verify user can login with correct credentials and enter button.",
			dataProvider = "validCredentials")
	public void loginValidUserEnter(String username, String password) {
		// login with correct credentials and press the enter key
		loginPage.login(username, password, false);
		
		String expectedUrl=url+inventory_ext;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
		
	}
	
	
	@Test(description = "Verify user prompted to add a username.")
	public void loginNoUsername() {
		// login with no username set and check error message
		loginPage.login(null, FAKE_PASSWORD, true);
		String error = loginPage.getErrorMessage();
		
		Assert.assertEquals(error, MUST_HAVE_USERNAME_MESSAGE);
		
	}
	
	@Test(description = "Verify user prompted to add a password.",
			dataProvider = "validCredentials")
	public void loginNoPassword(String username, String password) {
		// login with no password set and check error message
		loginPage.login(username, null, true);
		String error = loginPage.getErrorMessage();
		
		Assert.assertEquals(error, MUST_HAVE_PASSWORD_MESSAGE);
		
	}
	
	@Test(description = "Verify user can't login with unregistered username.")
	public void loginWrongUsername() {
		// log in with wrong username and password and check error message
		loginPage.login(FAKE_USERNAME, FAKE_PASSWORD, true);
		String error = loginPage.getErrorMessage();
		
		Assert.assertEquals(error, USER_DOES_NOT_EXIST_MESSAGE);
		
	}
	
	@Test(description = "Verify user can't login with incorrect password.",
			dataProvider = "validCredentials")
	public void loginWrongPassword(String username, String password) {
		// login with wrong password and check error message
		loginPage.login(username, FAKE_PASSWORD, true);
		String error = loginPage.getErrorMessage();
		
		Assert.assertEquals(error, USER_DOES_NOT_EXIST_MESSAGE);
		
	}
	
	
	@Test(description = "Locked out user cannot login and displays error.")
	public void loginLockedOut() {
		// log in with locked out user and compare error message
		loginPage.login(LOCKED_OUT_USERNAME, LOCKED_OUT_PASSWORD, true);
		String error = loginPage.getErrorMessage();
		
		Assert.assertEquals(error, LOCKED_OUT_MESSAGE);
		
	}
	
	@Test(description = "Can remove error message after displayed.")
	public void removeErrorMessage() {
		// Login with locked out user, click on error message X and see if disappears
		loginPage.login(LOCKED_OUT_USERNAME, LOCKED_OUT_PASSWORD, true);
		loginPage.clickErrorButton();
		String error = loginPage.getErrorMessage();
		Assert.assertEquals(error, "");
		
		
	}
	
	
}
