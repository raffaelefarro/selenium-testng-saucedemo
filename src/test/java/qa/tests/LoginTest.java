package qa.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.base.BaseTest;
import qa.pages.LoginPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(description = "Con credenziali corrette si arriva al catalogo")
    public void loginConCredenzialiValide() {
        var inventory = new LoginPage(driver).loginAs("standard_user", "secret_sauce");

        assertEquals(inventory.pageTitle(), "Products");
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test(description = "Un utente bloccato non può entrare")
    public void utenteBloccato() {
        var login = new LoginPage(driver).submit("locked_out_user", "secret_sauce");

        assertTrue(login.errorMessage().contains("this user has been locked out"));
    }

    @DataProvider(name = "credenzialiNonValide")
    public Object[][] credenzialiNonValide() {
        return new Object[][]{
                {"standard_user", "password_sbagliata", "do not match any user"},
                {"utente_che_non_esiste", "secret_sauce", "do not match any user"},
                {"", "secret_sauce", "Username is required"},
                {"standard_user", "", "Password is required"},
        };
    }

    @Test(dataProvider = "credenzialiNonValide", description = "Credenziali sbagliate o mancanti mostrano l'errore giusto")
    public void credenzialiNonValide(String user, String pass, String expected) {
        var login = new LoginPage(driver).submit(user, pass);

        assertTrue(login.errorMessage().contains(expected));
    }
}
