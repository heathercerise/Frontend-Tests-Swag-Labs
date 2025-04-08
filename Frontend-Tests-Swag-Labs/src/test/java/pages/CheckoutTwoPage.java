package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutTwoPage {

	protected WebDriver driver;

	protected By productNamesBy = By.className("inventory_item_name");
	protected By totalPriceBy = By.className("summary_subtotal_label");
	protected By itemPricesBy = By.className("inventory_item_price");
	protected By cancelBy = By.id("cancel");
	protected By finishBy = By.id("finish");

	public CheckoutTwoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// press cancel button
	public void pressCancelButton() {
		driver.findElement(cancelBy).click();
	}
	
	// click on finish button
	public void pressFinishButton() {
		driver.findElement(finishBy).click();
	}
	
	// check total price is correct
	public boolean checkTotalPrice() {
		List<WebElement> priceList = driver.findElements(itemPricesBy);
		float total_price = 0;
		
		// parse the price value from the text and add to list
		for(WebElement price : priceList) {
			String priceText = price.getText();
			if (priceText.length() > 0) {
	            priceText = priceText.substring(1);
	        }
			float priceToAdd = Float.parseFloat(priceText);
			total_price += priceToAdd;
		}
		
		WebElement subtotal = driver.findElement(totalPriceBy);
		String subtotalText = subtotal.getText();
		subtotalText = subtotalText.replace("Item total: $", "");
		float subtotal_price = Float.parseFloat(subtotalText);
		float epsilon = 0.01f;

		if (Math.abs(subtotal_price- total_price) < epsilon) {
			return true;
		}else {
			return false;
		}
	}
	
	// Click on product name
	public void clickOnProductName(String productName) {
		productName = productName.replaceAll("-", " ");
		List<WebElement> items = getProductNames();
		for(WebElement item : items) {
			if(productName.equalsIgnoreCase(item.getText().replaceAll("-", " "))){
				item.click();
				return;
			}
		}
		
	}
	
	// get products in the summary of products
	public List<WebElement> getProductNames() {
		return driver.findElements(productNamesBy);
	}
	
	// check item in cart
	public boolean checkItemInCart(String productName) {
		productName = productName.replaceAll("-", " ");
		
		List<WebElement> items = getProductNames();
		for(WebElement item : items) {
			if(productName.equalsIgnoreCase(item.getText().replaceAll("-", " "))){
				return true;
			}
		}
		return false;
		
	}
	
	// check items displayed in order
	public boolean verifyItemInOrder(String productName, int index) {
		productName = productName.replaceAll("-", " ");
		
		List<WebElement> items = getProductNames();
		for(int i = 0; i < items.size(); ++i) {
			if(productName.equalsIgnoreCase(items.get(i).getText().replaceAll("-", " "))){
				return(index == i);
			}
		}
		return false;
	}

	

	
	
	
}
