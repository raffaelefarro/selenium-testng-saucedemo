package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstName = By.cssSelector("[data-test='firstName']");
    private final By lastName = By.cssSelector("[data-test='lastName']");
    private final By postalCode = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By error = By.cssSelector("[data-test='error']");
    private final By completeHeader = By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public CheckoutPage fillForm(String first, String last, String zip) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys(first);
        driver.findElement(lastName).sendKeys(last);
        driver.findElement(postalCode).sendKeys(zip);
        driver.findElement(continueButton).click();
        return this;
    }

    public CheckoutPage clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return this;
    }

    public CheckoutPage finish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        return this;
    }

    public String errorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(error)).getText();
    }

    public String confirmation() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }
}
