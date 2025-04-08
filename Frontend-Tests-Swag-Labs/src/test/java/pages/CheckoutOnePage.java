package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutOnePage {
	protected WebDriver driver;
	protected By firstNameBy = By.id("first-name");
	protected By lastNameBy = By.id("last-name");
	protected By postalCodeBy = By.id("postal-code");
	protected By errorContainerBy = By.className("error-message-container");
	protected By errorMessageBy = By.className("error-button");
	protected By cancelBy = By.id("cancel");
	protected By continueBy = By.id("continue");

	public CheckoutOnePage(WebDriver driver) {
		this.driver = driver;
	}
	
	// fill in fields
	public void fillInFields(String first, String last, String zipcode, Boolean click) {
		try {
			driver.findElement(firstNameBy).sendKeys(first);
			driver.findElement(lastNameBy).sendKeys(last);
			driver.findElement(postalCodeBy).sendKeys(zipcode);
		} catch (IllegalArgumentException e) {
			System.out.println("Testing without all fields.");
		}
		WebElement continueButton=driver.findElement(continueBy);

		if(click) {
			continueButton.click();
		} else {
			continueButton.sendKeys(Keys.RETURN);
		}
	}
	
	
	// press cancel button
	public void pressCancelButton() {
		driver.findElement(cancelBy).click();
	}
	
	// press continue button
	public void pressContinueButton() {
		driver.findElement(continueBy).click();
	}
	
	// Get error message container if exists
	public WebElement getErrorElement() {
		try {
			WebElement error = driver.findElement(errorContainerBy);
			return error;
		}
		catch(NoSuchElementException e) {
			System.out.println("Looked for error message and none found.");
			return null;
		}
	}
	
	// Click to get rid of the error message
	public void clickErrorButton() {
		try {
			WebElement error = driver.findElement(errorMessageBy);
			error.click();
		}
		catch(NoSuchElementException e) {
			System.out.println("No error button found");
		}
	}
	
	// get contents of the error message
	public String getErrorMessage() {
		WebElement error = getErrorElement();
		return error.getText();
	}
}
