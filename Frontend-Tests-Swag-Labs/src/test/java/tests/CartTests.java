package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;

public class CartTests extends BaseTests {


	// Constructor allowing for testing with different users
	public CartTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public CartTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;

	}

	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		
		// log in a user
		loginPage.login(username, password, true);
		inventoryPage = new InventoryPage(driver);
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
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
	
	
	// Continue shopping goes back to inventory
	@Test(description = "Verify user can return to inventory page.")
	public void userCanContinueShopping() {
		// navigate to cart
		inventoryPage.clickOnCart();
		cartPage.clickContinueShopping();
		
		String expectedUrl = url + inventory_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
	// Cart displays the correct items
	@Test(description = "Verify cart displays correct items.",
			dataProvider = "supportedProducts")
	public void cartDisplaysCorrectItems(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		inventoryPage.clickOnCart();
		
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				Assert.assertTrue(cartPage.verifyItemInCart(products[i]));
			} else {
				Assert.assertFalse(cartPage.verifyItemInCart(products[i]));
			}
			
		}
		cartPage.clickContinueShopping();
		inventoryPage.resetAppState();
	}
	
	// Checkout goes to checkout page
	@Test(description = "Verify user can go to checkout page.")
	public void userCanCheckout() {
		// navigate to cart
		inventoryPage.clickOnCart();
		cartPage.clickCheckout();
		
		String expectedUrl = url + checkout_one_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
		
	}
	
	// Remove from cart works as expected
	@Test(description = "Verify remove from cart functions.",
			dataProvider = "supportedProducts")
	public void removeFromCartFunctions(String[] products) {
		inventoryPage.resetAppState();
		driver.get(url + inventory_ext);
		for(int i = 0; i < products.length; ++i) {
			inventoryPage.addProductToCart(products[i]);
		}
		inventoryPage.clickOnCart();
		
		for(int i = 0; i < products.length; ++i) {
			// Assert.assertTrue(cartPage.verifyItemInCart(products[i]));
			cartPage.clickRemoveItem(products[i]);
			Assert.assertFalse(cartPage.verifyItemInCart(products[i]));
			Assert.assertEquals(cartPage.numberOfItemsInCartIcon(), products.length - 1 - i);
		}
	}
	
	// Check cart is displaying correctly with the right items after adding from product page
	@Test(description = "Cart shows item after adding from product page",
			dataProvider = "supportedProducts")
	public void productPageAddDisplaysInCart(String[] products) {
		for(int i = 0; i < products.length; ++i) {
			if(i % 3 == 0) {
				inventoryPage.resetAppState();
				driver.get(url + inventory_ext);
				
				inventoryPage.clickOnProductName(products[i]);
				productPage.clickAddButton();
				productPage.clickOnCart();
				Assert.assertTrue(cartPage.verifyItemInCart(products[i]));
				cartPage.clickContinueShopping();
			}
			
		}
		
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
		
		cartPage.clickOnProductName(products[0]);
		String expectedUrl=url+product_ext;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedUrl));
		// inventory details name
		Assert.assertTrue(productPage.validateCorrectItem(products[0]));
	
		
	}
	
	// Order is maintained by cart
	// Cart displays the correct items
	@Test(description = "Verify cart displays items in order they were added.",
			dataProvider = "supportedProducts")
	public void cartDisplaysitemsInOrder(String[] products) {
		
		// Add even first, then odd and check if that's maintained in cart
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 1) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		inventoryPage.clickOnCart();
		
		int index = 0;
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				Assert.assertTrue(cartPage.verifyItemInOrder(products[i], index++));
			}
		}
		for(int i = 0; i <products.length; ++i) {
			if(i % 2 == 1) {
				Assert.assertTrue(cartPage.verifyItemInOrder(products[i], index++));
			}
		}
		cartPage.clickContinueShopping();
		inventoryPage.resetAppState();
	}
	
	
	// Clicking on All Items in Nav Bar navigates to inventory page
	@Test(description = "Verify can go to inventory page from nav bar all items button.",
			dataProvider = "supportedProducts")
	public void goToInventoryWithNavBar(String[] products) {
		
		inventoryPage.clickOnCart();
		cartPage.clickOnInventoryPageFromNavBar();
		String expectedUrl = url + inventory_ext;
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl,  expectedUrl);
	
		
	}
	
}
