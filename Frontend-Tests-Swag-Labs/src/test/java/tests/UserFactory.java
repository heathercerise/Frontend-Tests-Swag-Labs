package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class UserFactory {
	
	@DataProvider(name="validCredentials")
	public Object[][] validCredentials(){
		return new Object[][] {
			{"standard_user", "secret_sauce"},
			{"problem_user", "secret_sauce"},
			{"performance_glitch_user", "secret_sauce"},
			{"error_user", "secret_sauce"}
		};
	}

	@Factory(dataProvider = "validCredentials")
	public Object[] InventoryTestFactory(String username, String password) {
		System.out.println("Inventory factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new InventoryTests(username, password);
		return tests;
		
	}
	
	@Factory(dataProvider = "validCredentials")
	public Object[] CartTestFactory(String username, String password) {
		System.out.println("Cart factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new CartTests(username, password);
		return tests;
		
	}
	
	@Factory(dataProvider = "validCredentials")
	public Object[] ProductTestFactory(String username, String password) {
		System.out.println("Product factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new ProductTests(username, password);
		return tests;
		
	}
	
	@Factory(dataProvider = "validCredentials")
	public Object[] CheckoutOneTestFactory(String username, String password) {
		System.out.println("Checkout One factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new CheckoutOneTests(username, password);
		return tests;
		
	}
	
	@Factory(dataProvider = "validCredentials")
	public Object[] CheckoutTwoTestFactory(String username, String password) {
		System.out.println("Checkout Two factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new CheckoutTwoTests(username, password);
		return tests;
		
	}
	
	@Factory(dataProvider = "validCredentials")
	public Object[] CheckoutCompleteTestFactory(String username, String password) {
		System.out.println("Checkout complete factory being run.");
		Object[] tests = new Object[1];
		tests[0] = new CheckoutCompleteTests(username, password);
		return tests;
		
	}
	
}
