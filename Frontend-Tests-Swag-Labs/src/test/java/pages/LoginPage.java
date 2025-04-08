package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	protected WebDriver driver;
	
	private By usernameBy = By.id("user-name");
	private By passwordBy = By.id("password");
	private By loginButtonBy = By.id("login-button");
	private By errorContainerBy = By.className("error-message-container");
	private By errorMessageBy = By.className("error-button");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// Logging in with given credentials, supports clicking or pressing enter
	public void login(String username, String password, Boolean click) {
		try {
			driver.findElement(usernameBy).sendKeys(username);
			driver.findElement(passwordBy).sendKeys(password);
		} catch (IllegalArgumentException e) {
			System.out.println("Testing without all fields.");
		}
		WebElement login=driver.findElement(loginButtonBy);

		if(click) {
			login.click();
		} else {
			login.sendKeys(Keys.RETURN);
		}
	}
	
	
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
	
	public void clickErrorButton() {
		try {
			WebElement error = driver.findElement(errorMessageBy);
			error.click();
		}
		catch(NoSuchElementException e) {
			System.out.println("No error button found");
		}
	}
	
	public String getErrorMessage() {
		WebElement error = getErrorElement();
		return error.getText();
	}
	
	
}