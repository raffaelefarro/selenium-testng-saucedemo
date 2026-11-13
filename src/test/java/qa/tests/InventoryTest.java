package qa.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.base.BaseTest;
import qa.pages.InventoryPage;
import qa.pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class InventoryTest extends BaseTest {

    private InventoryPage inventory;

    @BeforeMethod
    public void login() {
        inventory = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Il catalogo mostra sei prodotti")
    public void sonoSeiProdotti() {
        assertEquals(inventory.productCount(), 6);
    }

    @Test(description = "Ordinando per prezzo crescente i prezzi sono in ordine")
    public void ordinaPerPrezzoCrescente() {
        inventory.sortBy("Price (low to high)");

        List<Double> prezzi = inventory.prices();
        List<Double> attesi = new ArrayList<>(prezzi);
        Collections.sort(attesi);
        assertEquals(prezzi, attesi);
    }

    @Test(description = "Ordinando per prezzo decrescente i prezzi sono in ordine inverso")
    public void ordinaPerPrezzoDecrescente() {
        inventory.sortBy("Price (high to low)");

        List<Double> prezzi = inventory.prices();
        List<Double> attesi = new ArrayList<>(prezzi);
        attesi.sort(Collections.reverseOrder());
        assertEquals(prezzi, attesi);
    }

    @Test(description = "Il numero sul carrello cresce a ogni prodotto aggiunto")
    public void contatoreCarrello() {
        inventory.addToCart("sauce-labs-backpack");
        assertEquals(inventory.cartCount(), 1);

        inventory.addToCart("sauce-labs-bike-light");
        assertEquals(inventory.cartCount(), 2);
    }
}
