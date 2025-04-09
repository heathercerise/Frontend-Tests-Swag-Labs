package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;

import org.testng.annotations.Test;

// Note that you can also run these tests using UserFactory
public class InventoryTests extends BaseTests {
	
	// Currently supported sorting techniques
	@DataProvider(name="supportedSortingValues")
	public Object[][] supportedSortingValues(){
		return new Object[][] {
			{"za"},
			{"lohi"},
			{"hilo"},
			{"az"}
		};
	}
	
	// Constructor allowing for testing with different users
	public InventoryTests(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	// Default constructor running with standard user
	public InventoryTests() {
		this.username = DEFAULT_USERNAME;
		this.password= DEFAULT_PASSWORD;
	}

	// Before implementing tests, logs in the user
	@BeforeClass()
	public void beforeClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		// log in a user
		loginPage.login(username, password, true);
		inventoryPage = new InventoryPage(driver);
	}
	
	@AfterClass()
	public void afterClass() {
		driver.get(url);
		loginPage = new LoginPage(driver);
		loginPage.login(username, password, true);
		inventoryPage.resetAppState();
	}
	
	@AfterMethod()
	public void afterEach() {
		// Ensure back on inventory page
		driver.get(url + inventory_ext);
	}
	
	@Test(description = "Verify clicking on product name brings to product page.",
			dataProvider = "supportedProducts",
			enabled=true)
	public void inventoryProductPageOnClick(String[] products) {
		
		for(int i = 0; i < products.length; ++i) {
			inventoryPage.clickOnProductName(products[i]);
			
			Assert.assertTrue(productPage.validateCorrectItem(products[i]));
			
			
			String expectedUrl=url+product_ext;
			String actualUrl=driver.getCurrentUrl();
			Assert.assertTrue(actualUrl.contains(expectedUrl));
			
			productPage.backToProductsButton();
		}
		
		
	}
	
	@Test(description = "Verify user can sort products.",
			dataProvider = "supportedSortingValues",
			enabled=true)
	public void inventorySort(String sortValue) {
		System.out.println("Running with user: " + username);
		Boolean result = inventoryPage.sortProductsByValue(sortValue);
		Assert.assertTrue(result);
	}
	
	
	@Test(description = "Verify user can add products to cart.",
			dataProvider = "supportedProducts",
			enabled=true)
	public void addItemsToCart(String[] products) {
		
		for(int i = 0; i < products.length; ++i) {
			inventoryPage.addProductToCart(products[i]);
			Assert.assertTrue(inventoryPage.getRemoveFromCart(products[i]).isDisplayed());
			Assert.assertEquals(inventoryPage.numberOfItemsInCart(), i+1);
		}
		
		inventoryPage.resetAppState();
	}
	
	@Test(description = "Verify user can remove products from cart.",
			dataProvider = "supportedProducts",
			enabled=true)
	public void removeItemsFromCart(String[] products) {
		
		for(int i = 0; i < products.length; ++i) {
			//Assert.assertTrue(inventoryPage.getAddToCart(products[i]).isDisplayed());
			inventoryPage.addProductToCart(products[i]);
		}
		
		for(int i = products.length-1; i >= 0; --i) {
			inventoryPage.removeProductFromCart(products[i]);
			Assert.assertTrue(inventoryPage.getAddToCart(products[i]).isDisplayed());
			Assert.assertEquals(inventoryPage.numberOfItemsInCart(), i);
		}
		
		inventoryPage.resetAppState();
	}
	
	@Test(description = "Verify user can navigate to cart.",
			enabled=true)
	public void canGoToCart() {
		inventoryPage.clickOnCart();
		String expectedUrl=url+cart_ext;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl);
	}
	
	
	@Test(description = "Verify user can logout.",
			enabled=true)
	public void userCanLogout() {
		inventoryPage.clickLogout();
		String expectedUrl=url;
		String actualUrl=driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedUrl));
		
		// see if actually logged out aka can't access other pages
		driver.get(url+inventory_ext);
		actualUrl=driver.getCurrentUrl();
		Assert.assertTrue(actualUrl.contains(expectedUrl));
	
		
		String error = loginPage.getErrorMessage();
		Assert.assertEquals(error, "Epic sadface: You can only access '/inventory.html' when you are logged in.");
		
	}
	
	@Test(description = "Verify user state is maintained after logging out and back in.",
			dataProvider = "supportedProducts",
			enabled=true)
	public void userStateMaintained(String[] products) {
		driver.get(url);
		loginPage.login(username, password, true);
		inventoryPage.resetAppState();
		driver.get(url + inventory_ext);
		
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				inventoryPage.addProductToCart(products[i]);
			}
		}
		
		inventoryPage.clickLogout();
		
		//log back in
		loginPage.login(username, password, true);
		
		// see if added items stayed added
		for(int i = 0; i < products.length; ++i) {
			if(i % 2 == 0) {
				Assert.assertTrue(inventoryPage.getRemoveFromCart(products[i]).isDisplayed());
			}
		}
	}
	
	
	
}
