package RahulshettyAccademyJavaCourse.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RahulshettyAccademyJavaCourse.abstractCommponents.AbstractComponent;

public class LandingPageByGedo extends AbstractComponent{
	
	WebDriver driver;
	
	public LandingPageByGedo(WebDriver driver) {
	    super(driver);
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}

	
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	//PageFactory
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;


	public ProductCatalogue Login(String UserEmail,String Password) {
		userEmail.sendKeys(UserEmail);
		passwordEle.sendKeys(Password);
	    waitForElementToBeClickable(submit);

		submit.click();
		 ProductCatalogue prCatalogue = new ProductCatalogue(driver);
		 return prCatalogue;
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}

	
}
