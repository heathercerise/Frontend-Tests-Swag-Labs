package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutOnePage;
import pages.CheckoutTwoPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;

public class CheckoutTwoTests extends BaseTests {


	
	// Constructor allowing for testing with different users
	public CheckoutTwoTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public CheckoutTwoTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;
	}

	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		inventoryPage = new InventoryPage(driver);
		productPage = new ProductPage(driver);
		checkoutOnePage = new CheckoutOnePage(driver);
		checkoutTwoPage = new CheckoutTwoPage(driver);
		cartPage = new CartPage(driver);
		
		// log in a user
		loginPage.login(username, password, true);
	
	}
	
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
	
	// Cancel button goes back to inventory
	@Test(description = "Verify user can cancel order and go back to inventory page.")
	public void userCanCancelOrder() {
		// navigate to cart
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		checkoutTwoPage.pressCancelButton();
		
		String expectedUrl = url + inventory_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
	
	// Finish button goes to order finished page
	@Test(description = "Verify user can finish order and go back to order complete page.")
	public void userCanfinishOrder() {
		// navigate to cart
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		checkoutTwoPage.pressFinishButton();
		
		String expectedUrl = url + checkout_complete_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
		
	}
	
	// Clicking on product name goes to correct product page
	@Test(description = "Verify can go to product page by clicking on name.",
			dataProvider = "supportedProducts")
	public void goToProductPage(String[] products) {
		inventoryPage.resetAppState();
		driver.get(url + inventory_ext);
		for(int i = 0; i < products.length; ++i) {
			if(i % 3 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		
		checkoutTwoPage.clickOnProductName(products[0]);
		String expectedUrl=url+product_ext;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedUrl));
		// inventory details name
		Assert.assertTrue(productPage.validateCorrectItem(products[0]));
		
	}
	
	
	// items displaying correctly
		// price is right
		// correct items
		// correct order
	@Test(description = "Verify product summary displays correctly.",
			dataProvider = "supportedProducts")
	public void cartDisplaysCorrectly(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		checkoutOnePage.fillInFields(FAKE_FIRST_NAME, FAKE_LAST_NAME, FAKE_ZIP_CODE, true);
		
		// checks if correct items are in cart
		for(int i = products.length-1; i >= 0; --i) {
			if(i % 2 == 0) {
				Assert.assertTrue(checkoutTwoPage.checkItemInCart(products[i]));
			} else {
				Assert.assertFalse(checkoutTwoPage.checkItemInCart(products[i]));
			}
			
		}
		
		// checks if price is displaying correctly
		Assert.assertTrue(checkoutTwoPage.checkTotalPrice());
		
		// check if items are displayed in right order
		int index = 0;
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				Assert.assertTrue(checkoutTwoPage.verifyItemInOrder(products[i], index++));
			}
		}
		
		
	}
		
}
