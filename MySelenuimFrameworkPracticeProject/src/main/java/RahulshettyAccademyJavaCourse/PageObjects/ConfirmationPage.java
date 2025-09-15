package RahulshettyAccademyJavaCourse.PageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import RahulshettyAccademyJavaCourse.abstractCommponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	//	String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();


	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;
	
	public String getConfirmationMessage()
	{
		CheckOutPage cp = new CheckOutPage(driver);	
		return confirmationMessage.getText();
	}
	
}
