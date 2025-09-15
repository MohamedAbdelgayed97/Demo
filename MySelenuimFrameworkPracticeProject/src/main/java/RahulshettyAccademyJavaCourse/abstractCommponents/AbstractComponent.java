package RahulshettyAccademyJavaCourse.abstractCommponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import RahulshettyAccademyJavaCourse.PageObjects.CartPage;
import RahulshettyAccademyJavaCourse.PageObjects.OrderPage;

public class AbstractComponent {

    WebDriver driver;
    WebDriverWait wait;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.wait.pollingEvery(Duration.ofMillis(500));
    }

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;

    public void waitForElementToAppear(By findBy) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public CartPage goToCartPage() {
        scrollToTop();
        waitForElementToBeClickable(cartHeader);
        clickElement(cartHeader);
        return new CartPage(driver);
    }

    public OrderPage goToOrdersPage() {
        scrollToTop();
        waitForElementToBeClickable(orderHeader);
        clickElement(orderHeader);
        return new OrderPage(driver);
    }

    private void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    private void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void waitForElementToDisappear(WebElement element) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        shortWait.until(ExpectedConditions.invisibilityOf(element));
    }
}
