package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {
	protected WebDriver driver;
	protected By backToProductsBy = By.id("back-to-products");
	protected By removeBy = By.id("remove");
	protected By addToCartBy = By.id("add-to-cart");
	protected By goToCartBy = By.className("shopping_cart_link");
	protected By cartBadgeBy = By.className("shopping_cart_badge");
	protected By cartLinkBy = By.className("shopping_cart_link");
	protected By productNameBy = By.className("inventory_details_name");
	protected By inventoryDescBy = By.className("inventory_details_desc");
	private final String RENDER_ERROR_MESSAGE = "A description should be here, but it failed to render!";
	
	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	// Navigate back to products
	public void backToProductsButton() {
		driver.findElement(backToProductsBy).click();
	}
	
	// Get add to cart button
	public WebElement getAddToCartButton() {
		return driver.findElement(addToCartBy);
	}
	
	// Get remove from cart button
	public WebElement getRemoveButton() {
		return driver.findElement(removeBy);
	}
	
	// Add Item
	public void clickAddButton() {
		getAddToCartButton().click();
	}
	
	
	// Remove Item
	public void clickRemoveButton() {
		getRemoveButton().click();
	}
	
	// click on cart
	public void clickOnCart() {
		driver.findElement(cartLinkBy).click();
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

	// get product page title and see if matches expected
	public boolean validateCorrectItem(String product) {
		product = product.replaceAll("-", " ");
		WebElement productName = driver.findElement(productNameBy);
		
		return product.equalsIgnoreCase(productName.getText().replaceAll("-", " "));
	}
	
	public boolean isDescriptionRenderError() {
		return driver.findElement(inventoryDescBy).getText().contains(RENDER_ERROR_MESSAGE);
	}
	
}
