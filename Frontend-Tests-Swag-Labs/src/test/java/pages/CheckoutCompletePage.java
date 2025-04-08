package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {
	protected WebDriver driver;
	protected By backHomeBy = By.id("back-to-products");
	
	public CheckoutCompletePage(WebDriver driver) {
		this.driver = driver;
	}
	
	// back to products button
	public void pressBackToProductsButton() {
		driver.findElement(backHomeBy).click();
	}
	
}
