package RahulshettyAccademyJavaCourse.PageObjects;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import RahulshettyAccademyJavaCourse.abstractCommponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
    WebDriver driver;
    WebDriverWait wait;
    
    public ProductCatalogue(WebDriver driver) {
        // initialization
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(css = ".mb-3")
    List<WebElement> products;
    
    @FindBy(css = ".ng-animating")
    WebElement spinner;
    
    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");
    
    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }
    
    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(product->
        product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return prod;
    }
    
    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        WebElement addToCartButton = prod.findElement(addToCart);
        
        // Strategy 1: Wait for element to be clickable
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCartButton.click();
        } catch (Exception e1) {
            System.out.println("Normal click failed, trying alternative methods...");
            
            // Strategy 2: Scroll element into view and try again
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
                Thread.sleep(500); // Small pause after scrolling
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
                addToCartButton.click();
            } catch (Exception e2) {
                System.out.println("Scroll and click failed, trying Actions class...");
                
                // Strategy 3: Use Actions class
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(addToCartButton).click().perform();
                } catch (Exception e3) {
                    System.out.println("Actions click failed, trying JavaScript click...");
                    
                    // Strategy 4: JavaScript click as last resort
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                }
            }
        }
        
        // Wait for toast message and spinner
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
    }
    
    // Alternative method with more robust waiting
    public void addProductToCartRobust(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        
        // Wait for any overlapping elements to disappear
        waitForSpinnerToDisappear();
        
        // Find the add to cart button
        WebElement addToCartButton = prod.findElement(addToCart);
        
        // Wait for button to be both visible and clickable
        wait.until(ExpectedConditions.and(
            ExpectedConditions.visibilityOf(addToCartButton),
            ExpectedConditions.elementToBeClickable(addToCartButton)
        ));
        
        // Scroll button into view
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", 
            addToCartButton
        );
        
        // Small pause to ensure any animations complete
        Thread.sleep(300);
        
        // Try multiple click strategies
        boolean clicked = false;
        
        // Strategy 1: Normal click
        if (!clicked) {
            try {
                addToCartButton.click();
                clicked = true;
            } catch (Exception e) {
                System.out.println("Normal click failed: " + e.getMessage());
            }
        }
        
        // Strategy 2: Actions click
        if (!clicked) {
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(addToCartButton).pause(Duration.ofMillis(100)).click().perform();
                clicked = true;
            } catch (Exception e) {
                System.out.println("Actions click failed: " + e.getMessage());
            }
        }
        
        // Strategy 3: JavaScript click
        if (!clicked) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                clicked = true;
            } catch (Exception e) {
                System.out.println("JavaScript click failed: " + e.getMessage());
            }
        }
        
        if (!clicked) {
            throw new RuntimeException("Unable to click Add to Cart button after trying all strategies");
        }
        
        // Wait for toast message and spinner
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
    }
    
    // Helper method to wait for spinner to disappear
    private void waitForSpinnerToDisappear() {
        try {
            // Wait for spinner to appear first (if it's going to)
            wait.until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")),
                ExpectedConditions.invisibilityOf(spinner)
            ));
        } catch (Exception e) {
            // Spinner might not appear, which is fine
        }
    }
    
    // Method to check if element is truly clickable (not just visible)
    private boolean isElementClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}