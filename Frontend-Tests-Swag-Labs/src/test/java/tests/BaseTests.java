package tests;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import config.PropertiesFile;
import pages.CartPage;
import pages.CheckoutOnePage;
import pages.CheckoutTwoPage;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductPage;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseTests {
	protected WebDriver driver;
	protected Properties properties;
	
	// base_url that is set from properties file
	protected String url;
	protected String inventory_ext = "/inventory.html";
	protected String product_ext = "/inventory-item.html?id=";
	protected String cart_ext = "/cart.html";
	protected String checkout_one_ext = "/checkout-step-one.html";
	protected String checkout_two_ext = "/checkout-step-two.html";
	protected String checkout_complete_ext = "/checkout-complete.html";
	
	// default url if none given
	protected final String DEFAULT_URL = "https://www.saucedemo.com";
	
	// default first, last, zip
	protected final String FAKE_FIRST_NAME = "Sally";
	protected final String FAKE_LAST_NAME = "Smith";
	protected final String FAKE_ZIP_CODE = "11111";
	
	// default log in variables
	protected final String DEFAULT_USERNAME = "standard_user";
	protected final String DEFAULT_PASSWORD = "secret_sauce";
	
	// pages
	protected ProductPage productPage;
	protected InventoryPage inventoryPage;
	protected CartPage cartPage;
	protected CheckoutOnePage checkoutOnePage;
	protected CheckoutTwoPage checkoutTwoPage;
	protected LoginPage loginPage;

	// login variables
	protected String username;
	protected String password;
	
	
	// Initial valid usernames we want to test login page with
	@DataProvider(name="validCredentials")
	public Object[][] validCredentials(){
		return new Object[][] {
			{"standard_user", "secret_sauce"},
			{"problem_user", "secret_sauce"},
			{"performance_glitch_user", "secret_sauce"},
			{"error_user", "secret_sauce"}
		};
	}
	
	// Products we are currently testing with
	@DataProvider(name="supportedProducts")
	public Object[][] supportedProducts(){
		return new Object[][] {
			{"sauce-labs-backpack", "sauce-labs-bike-light",
				"sauce-labs-bolt-t-shirt", "sauce-labs-fleece-jacket",
				"sauce-labs-onesie", "test.allthethings()-t-shirt-(red)"}
		};
	}
	

	@BeforeClass
	public void driverSetUp() throws IOException {
		
		properties = PropertiesFile.getProperties("config");
		url = properties.getProperty("base_url");
		if(url == null) {
			System.out.println("Using default url: " + url);
			url = DEFAULT_URL;
		} else {
			System.out.println("Using url: " + url);
		}
		
		
		// Chrome password pop ups are creating failures so change settings
		final Map<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.password_manager_leak_detection", false);
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("profile.password_manager_enabled", false);

		final ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		
		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		// navigate to our URL
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void closeDriver() {
		driver.quit();
	}
}
