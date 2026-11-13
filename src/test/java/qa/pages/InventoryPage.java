package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector(".title");
    private final By items = By.cssSelector(".inventory_item");
    private final By prices = By.cssSelector(".inventory_item_price");
    private final By sortSelect = By.cssSelector("[data-test='product-sort-container']");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String pageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public int productCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(items));
        return driver.findElements(items).size();
    }

    public InventoryPage addToCart(String productId) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='add-to-cart-" + productId + "']"))).click();
        return this;
    }

    public int cartCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText());
    }

    public InventoryPage sortBy(String visibleText) {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(sortSelect)))
                .selectByVisibleText(visibleText);
        return this;
    }

    public List<Double> prices() {
        return driver.findElements(prices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    public CartPage openCart() {
        driver.findElement(cartLink).click();
        return new CartPage(driver);
    }
}
