package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;

public class ProductTests extends BaseTests {
	
	
	// Constructor allowing for testing with different users
	public ProductTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public ProductTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;
	}
	
	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		cartPage = new CartPage(driver);
		
		// log in a user
		loginPage.login(username, password, true);
		inventoryPage = new InventoryPage(driver);
		productPage = new ProductPage(driver);
	}
	
	@BeforeMethod()
	public void beforeEach() {
		driver.get(url + inventory_ext);
	}
	
	// Navigate back to product page
	@Test(description = "Verify user can return to inventory page.",
			dataProvider = "supportedProducts")
	public void userCanNavigateBackToInventory(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			// navigate to each product and go back
			inventoryPage.clickOnProductName(products[i]);
			productPage.backToProductsButton();
			String expectedUrl = url + inventory_ext;
			String actualUrl = driver.getCurrentUrl();
			Assert.assertTrue(actualUrl.contains(expectedUrl));
		}
		
	}
	
	
	// Add item to cart
	@Test(description = "Verify user can add item to cart when not already added.",
			dataProvider = "supportedProducts")
	public void userCanAddToCart(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			// navigate to each product and add it
			inventoryPage.clickOnProductName(products[i]);
			Assert.assertTrue(productPage.getAddToCartButton().isDisplayed());
			productPage.clickAddButton();
			Assert.assertEquals(productPage.numberOfItemsInCart(), i+1);
			
			// navigate back to inventory page before continuing
			productPage.backToProductsButton();
		}
		
		inventoryPage.resetAppState();
		
	}
	
	// Displays as added if already added prior and removed if not in cart
	@Test(description = "Verify product page reflects whether added to cart",
			dataProvider = "supportedProducts")
	public void productDisplaysIfAdded(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			// navigate to each product and add it
			inventoryPage.clickOnProductName(products[i]);
			productPage.clickAddButton();
			
			// navigate back to inventory
			productPage.backToProductsButton();
			
			// go back to product page and check if displayed as added already
			inventoryPage.clickOnProductName(products[i]);
			Assert.assertTrue(productPage.getRemoveButton().isDisplayed());
			
			// now remove it, go back to inventory, and navigate back to see if displayed as removed
			productPage.clickRemoveButton();
			productPage.backToProductsButton();
			inventoryPage.clickOnProductName(products[i]);
			Assert.assertTrue(productPage.getAddToCartButton().isDisplayed());
			productPage.backToProductsButton();
			
		}
		
		inventoryPage.resetAppState();
		
	}
	
	// Remove item from cart
	@Test(description = "Verify user can remove item from cart when already added.",
			dataProvider = "supportedProducts")
	public void userCanRemoveFromCart(String[] products) {
		// add all the items first
		for(int i = 0; i < products.length; ++i) {
			// navigate to each product and add it
			inventoryPage.clickOnProductName(products[i]);
			productPage.clickAddButton();
			// navigate back to inventory page
			productPage.backToProductsButton();
		}
		
		// remove all the items
		for(int i = products.length - 1; i >= 0; --i) {
			// navigate to each product and remove it
			inventoryPage.clickOnProductName(products[i]);
			Assert.assertTrue(productPage.getRemoveButton().isDisplayed());
			productPage.clickRemoveButton();
			Assert.assertEquals(productPage.numberOfItemsInCart(), i);
			// navigate back to inventory page
			productPage.backToProductsButton();
		}
		inventoryPage.resetAppState();
		
	}
		
	@Test(description = "Verify user can navigate to cart.",
			dataProvider = "supportedProducts")
	public void canGoToCart(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			inventoryPage.clickOnProductName(products[i]);
			productPage.clickOnCart();
			String expectedUrl=url+cart_ext;
			String actualUrl=driver.getCurrentUrl();
			Assert.assertEquals(actualUrl, expectedUrl);
			cartPage.clickContinueShopping();
		}
		
	}
	
	
}
