package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutOnePage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutOneTests extends BaseTests {
	
	
	private final String FIRST_NAME_ERROR = "Error: First Name is required";
	private final String LAST_NAME_ERROR = "Error: Last Name is required";
	private final String ZIP_CODE_ERROR = "Error: Postal Code is required";
	
	
	// Constructor allowing for testing with different users
	public CheckoutOneTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public CheckoutOneTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;
	}

	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		checkoutOnePage = new CheckoutOnePage(driver);
		cartPage = new CartPage(driver);
		
		// log in a user
		loginPage.login(username, password, true);
		
		
	}
	
	// Navigate to checkout page
	@BeforeMethod()
	public void beforeEach() {
		driver.get(url + inventory_ext);
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
	}
	
	// no first
	@Test(description = "Verify user prompted to add a first name.")
	public void loginNoFirstName() {
		checkoutOnePage.fillInFields(null, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		String error = checkoutOnePage.getErrorMessage();
		Assert.assertEquals(error, FIRST_NAME_ERROR);
		
	}
	
	// no last
	@Test(description = "Verify user prompted to add a last name.")
	public void loginNoLastName() {
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, null, FAKE_ZIP_CODE, true);
		String error = checkoutOnePage.getErrorMessage();
		Assert.assertEquals(error, LAST_NAME_ERROR);
		
	}
	
	// no zip
	@Test(description = "Verify user prompted to add a zipcode.")
	public void loginNoZipCode() {
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, null, true);
		String error = checkoutOnePage.getErrorMessage();
		Assert.assertEquals(error, ZIP_CODE_ERROR);
		
	}
	
	// cancel
	@Test(description = "Verify user can cancel and go back to cart page.")
	public void userCanCancel() {
		checkoutOnePage.pressCancelButton();
		String expectedUrl = url + cart_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
	}
	
	// continue with all fields
	@Test(description = "Verify user can go to next checkout page with all fields completed and click button.")
	public void loginSuccessfullyWithButton() {
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		String expectedUrl = url + checkout_two_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
	// continue with all fields
	@Test(description = "Verify user can go to next checkout page with all fields completed and press enter.")
	public void loginSuccessfullyWithEnter() {
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, false);
		String expectedUrl = url + checkout_two_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
}
