package tests;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import RahulshettyAccademyJavaCourse.PageObjects.CartPage;
import RahulshettyAccademyJavaCourse.PageObjects.CheckOutPage;
import RahulshettyAccademyJavaCourse.PageObjects.ConfirmationPage;
import RahulshettyAccademyJavaCourse.PageObjects.OrderPage;
import RahulshettyAccademyJavaCourse.PageObjects.ProductCatalogue;
import TestComponent.BaseTest;

public class SubmitOrderTest extends BaseTest {
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData")
	public void submitOrder(HashMap<String, String> input) throws InterruptedException {

		ProductCatalogue productCatalogue = landingPage.Login(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkoutPage = cartPage.GoToCheckOut();
		checkoutPage.SelectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Current working directory: " + System.getProperty("user.dir"));


		// driver.quit(); // optional to close browser
	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		// "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.Login("anshika@gmail.com", "Iamking@000");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

	}
	
	/*@Test()
	public void testExtentDemo() {
	    Assert.assertTrue(false); // عشان نتاكد ان التقرير هيتولد
	}*/


	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "//src//test//java//data//purchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

}
