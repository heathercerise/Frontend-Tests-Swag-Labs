package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutOnePage;
import pages.CheckoutTwoPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutCompleteTests extends BaseTests {

	CheckoutCompletePage checkoutCompletePage;
	
	public CheckoutCompleteTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public CheckoutCompleteTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;
	}
	
	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		checkoutCompletePage = new CheckoutCompletePage(driver);
		checkoutOnePage = new CheckoutOnePage(driver);
		checkoutTwoPage = new CheckoutTwoPage(driver);
		cartPage = new CartPage(driver);
		// log in a user
		loginPage.login(username, password, true);
	
	}
	
	// go back to inventory page
	@BeforeMethod()
	public void beforeEach() {
		driver.get(url + inventory_ext);
	}
	
	// reset app state after the class
	@AfterClass()
	public void afterClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		loginPage.login(username, password, true);
		inventoryPage.resetAppState();
	}
	
	// Back home button goes back to inventory
	@Test(description = "Verify user can go back to home page.")
	public void userCanGoBackHome() {
		
		// navigate to complete page
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		checkoutTwoPage.pressFinishButton();
		checkoutCompletePage.pressBackToProductsButton();
		
		String expectedUrl = url + inventory_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
	// Verify all inventory items reset after purchase
	@Test(description = "Verify all items reset after purchase.",
			dataProvider = "supportedProducts")
	public void itemsResetAfterPurchase(String[] products) {
		
		// add some products
		for(int i = 0; i < products.length; ++i) {
			if(i % 3 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		
		// navigate to complete page
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		checkoutTwoPage.pressFinishButton();
		checkoutCompletePage.pressBackToProductsButton();
		
		String expectedUrl = url + inventory_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
		//check that items were reset
		for(int i = 0; i < products.length; ++i) {
			Assert.assertTrue(inventoryPage.getAddToCart(products[i]).isDisplayed());
		}
		
		Assert.assertEquals(inventoryPage.numberOfItemsInCart(), 0);
		
	}
	
	
}
