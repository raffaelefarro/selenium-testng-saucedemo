package qa.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.base.BaseTest;
import qa.pages.InventoryPage;
import qa.pages.LoginPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutTest extends BaseTest {

    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Acquisto completo: un prodotto dal carrello alla conferma")
    public void acquistoCompleto() {
        inventory.addToCart("sauce-labs-backpack");

        var cart = inventory.openCart();
        assertEquals(cart.itemCount(), 1);

        String conferma = cart.checkout()
                .fillForm("Mario", "Rossi", "00100")
                .finish()
                .confirmation();

        assertEquals(conferma, "Thank you for your order!");
    }

    @Test(description = "Senza nome il checkout non va avanti")
    public void checkoutSenzaNome() {
        inventory.addToCart("sauce-labs-backpack");

        String errore = inventory.openCart().checkout()
                .clickContinue()
                .errorMessage();

        assertTrue(errore.contains("First Name is required"));
    }
}
