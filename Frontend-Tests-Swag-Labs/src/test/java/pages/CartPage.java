package pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;

public class CartPage {
	protected WebDriver driver;
	protected By continueShoppingBy = By.id("continue-shopping");
	protected By checkoutBy = By.id("checkout");
	protected By inventoryNameBy = By.className("inventory_item_name");
	protected By burgerMenuBy = By.id("react-burger-menu-btn");
	protected By inventoryNavBarBy = By.id("inventory_sidebar_link");
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// Click Continue Shopping
	public void clickContinueShopping() {
		driver.findElement(continueShoppingBy).click();
	}
	
	// Click Checkout Button
	public void clickCheckout() {
		driver.findElement(checkoutBy).click();
	}
	
	// Get items in cart
	public List<WebElement> getItemsInCart() {
		List<WebElement> items = driver.findElements(By.className("inventory_item_name"));
		return items;
	}
	
	//get number of items in cart icon
	public int numberOfItemsInCartIcon() {
		try {
			return Integer.parseInt(driver.findElement(By.className("shopping_cart_badge")).getText());
		} catch (NoSuchElementException e) {
			// No cart item is displayed so showing empty cart to user
			return 0;
		}
	}
	
	// Verify item in cart by name
	public boolean verifyItemInCart(String productName) {
		productName = productName.replaceAll("-", " ");
		
		List<WebElement> items = getItemsInCart();
		for(WebElement item : items) {
			if(productName.equalsIgnoreCase(item.getText().replaceAll("-", " "))){
				return true;
			}
		}
		return false;
	}
	
	
	
	// Remove item by name
	public void clickRemoveItem(String productName) {
		WebElement productRemove = driver.findElement(By.id("remove-"+ productName));
		productRemove.click();
	}
	

	// Click on product name
	public void clickOnProductName(String productName) {
		productName = productName.replaceAll("-", " ");
		List<WebElement> items = getItemsInCart();
		for(WebElement item : items) {
			if(productName.equalsIgnoreCase(item.getText().replaceAll("-", " "))){
				item.click();
				return;
			}
		}
		
	}
	

	public boolean verifyItemInOrder(String productName, int index) {
		// get all products and see if the index is correct aka order maintained
		productName = productName.replaceAll("-", " ");
		
		List<WebElement> items = getItemsInCart();
		for(int i = 0; i < items.size(); ++i) {
			if(productName.equalsIgnoreCase(items.get(i).getText().replaceAll("-", " "))){
				// System.out.println(index + ": " + productName + " " + i + ": " + items.get(i).getText().replaceAll("-", " "));
				return(index == i);
			}
		}
		return false;
	}
	
	public void clickOnInventoryPageFromNavBar() {
		driver.findElement(burgerMenuBy).click();
		WebElement resetApp = driver.findElement(inventoryNavBarBy);
		resetApp.click();
	}
	
	

}
