package RahulshettyAccademyJavaCourse.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RahulshettyAccademyJavaCourse.abstractCommponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent {

	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver; 
		PageFactory.initElements(driver, this);

	}

	/*
	 * Actions a = new Actions(driver);
	 * a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']"
	 * )), "india").build().perform();
	 * 
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(
	 * ".ta-results")));
	 * 
	 * driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).
	 * click(); driver.findElement(By.cssSelector(".action__submit")).click();
	 * 
	 */

	@FindBy(css = ".action__submit")
	private WebElement submit;

	@FindBy(css = "[placeholder='Select Country']")
	private WebElement country;

	@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	private WebElement selectCountry;

	By Result = By.cssSelector(".ta-results");

	public void SelectCountry(String countryName) {
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(Result);
		selectCountry.click();
	}

	public ConfirmationPage submitOrder() {
		submit.click();
		return new ConfirmationPage(driver);

	}

}
