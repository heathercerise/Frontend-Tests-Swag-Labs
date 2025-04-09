package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage {
	protected WebDriver driver;
	protected By sortBy = By.className("product_sort_container");
	protected By inventoryItemsBy = By.className("inventory_item_name");
	protected By inventoryItemPriceBy = By.className("inventory_item_price");
	protected By cartBadgeBy = By.className("shopping_cart_badge");
	protected By cartLinkBy = By.className("shopping_cart_link");
	protected By burgerMenuBy = By.id("react-burger-menu-btn");
	protected By resetLinkBy = By.id("reset_sidebar_link");
	protected By logoutLinkBy = By.id("logout_sidebar_link");
	protected By aboutPageLinkBy = By.id("about_sidebar_link");
	
	public InventoryPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	// Sort inventory by chosen method
	public boolean sortProductsByValue(String val) {
		WebElement selectElement = driver.findElement(sortBy);
		Select dropdown = new Select(selectElement);
		dropdown.selectByValue(val);
		
		if(isAlertPresent()) {
			System.out.println("Handled alert for sorting.");
			return false; // Sorting is alerted as broken, doesn't work as expected.
		}
		
		return verifyProductsSorted(val);
		
	}
	
	// Check if alert was thrown
	public boolean isAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertMessage = driver.switchTo().alert().getText();
			System.out.println("Alert given to user: " + alertMessage);
			alert.accept();
			
			return true;
		} catch (NoAlertPresentException e){
			// no alert
			return false;
		} 
	}
	
	// Method for verifying whether products are sorted as expected
	public boolean verifyProductsSorted(String val) {

		
		if(val == "az" || val == "za") {
			List<WebElement> items = driver.findElements(inventoryItemsBy);
			List<String> names = new ArrayList<>();
			for(WebElement item : items) {
				names.add(item.getText());
			}
			
			List<String> sortedNames = new ArrayList<>(names);
			
			if(val == "az") {
				Collections.sort(sortedNames);
			} else if (val == "za") {
				Collections.sort(sortedNames, Collections.reverseOrder());
			}
			
			return names.equals(sortedNames);


		}
		else if(val == "lohi" || val == "hilo") {
			List<WebElement> items = driver.findElements(inventoryItemPriceBy);
			List<Float> prices = new ArrayList<>();
			
			// parse the price value from the text and add to list
			for(WebElement item : items) {
				String priceText = item.getText();
				if (priceText.length() > 0) {
		            priceText = priceText.substring(1);
		        }
				float price = Float.parseFloat(priceText);
				prices.add(price);
			}

			// compare sorted prices to our price order
			List<Float> sortedPrices = new ArrayList<>(prices);
			if(val == "lohi") {
				Collections.sort(sortedPrices);
			} else if (val == "hilo") {
				Collections.sort(sortedPrices, Collections.reverseOrder());
			}
	
			return prices.equals(sortedPrices);
		}
		else {
			System.out.println("Sort method not yet supported for testing.");
			return false;
		}
		
	}
	
	// find add to cart button
	public WebElement getAddToCart(String name) {
		return driver.findElement(By.id("add-to-cart-" + name));
	}
	
	
	// find remove from cart button
	public WebElement getRemoveFromCart(String name) {
		return driver.findElement(By.id("remove-" + name));
	}
	
	
	// add to cart
	public void addProductToCart(String name) {
		WebElement addButton = getAddToCart(name);
		addButton.click();
	}
	

	
	// remove from cart
	public void removeProductFromCart(String name) {
		WebElement removeButton = getRemoveFromCart(name);
		removeButton.click();
	}
	
	//get number of items in cart
	public int numberOfItemsInCart() {
		try {
			return Integer.parseInt(driver.findElement(cartBadgeBy).getText());
		} catch (NoSuchElementException e) {
			// No cart item is displayed so showing empty cart to user
			return 0;
		}
	}
	
	// click on cart
	public void clickOnCart() {
		driver.findElement(cartLinkBy).click();
	}
	 
	
	// Click on product name
	public void clickOnProductName(String productName) {
		productName = productName.replaceAll("-", " ");
		List<WebElement> items = driver.findElements(inventoryItemsBy);
		for(WebElement item : items) {
			if(productName.equalsIgnoreCase(item.getText().replaceAll("-", " "))){
				item.click();
				return;
			}
		}
		
	}
	
	// reset app state
	public void resetAppState() {
		driver.findElement(burgerMenuBy).click();
		WebElement resetApp = driver.findElement(resetLinkBy);
		resetApp.click();
	}



	public void clickLogout() {
		driver.findElement(burgerMenuBy).click();
		WebElement resetApp = driver.findElement(logoutLinkBy);
		resetApp.click();
		
	}
	
	public void clickAboutPage() {
		driver.findElement(burgerMenuBy).click();
		WebElement resetApp = driver.findElement(aboutPageLinkBy);
		resetApp.click();
		
	}
	
}
