package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItems = By.cssSelector(".cart_item");
    private final By checkout = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int itemCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart_list")));
        return driver.findElements(cartItems).size();
    }

    public CheckoutPage checkout() {
        driver.findElement(checkout).click();
        return new CheckoutPage(driver);
    }
}
