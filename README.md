# Test automatici su SauceDemo (Selenium, TestNG, Java)

Un piccolo framework di test automatici che ho scritto per mostrare come lavoro. Gira su [saucedemo.com](https://www.saucedemo.com/), un negozio finto fatto apposta per esercitarsi, quindi si può provare senza chiedere il permesso a nessuno.

I test sono 12 e coprono login, catalogo e acquisto.

## Come è fatto

Uso il **Page Object**: ogni pagina del sito ha una sua classe (`LoginPage`, `InventoryPage`, `CartPage`, `CheckoutPage`) che sa come cliccare e leggere quella pagina. I test non contengono selettori, raccontano solo cosa succede. Se il sito cambia un bottone, si corregge in un posto solo.

```
src/test/java/qa
├── base     BaseTest (apre e chiude Chrome) e ScreenshotListener
├── pages    una classe per pagina
└── tests    LoginTest, InventoryTest, CheckoutTest
```

Cose che ho curato:

- **Attese esplicite** (`WebDriverWait`) e niente `Thread.sleep`, così i test non falliscono a caso
- **Screenshot quando un test fallisce**, salvato in `target/screenshots`
- **Data provider** per provare più login sbagliati con lo stesso test
- **GitHub Actions**: a ogni push i test girano da soli con Chrome senza finestra, e se qualcosa fallisce gli screenshot si scaricano dalla pagina dell'esecuzione

## Cosa viene testato

| Area | Cosa controllo |
|---|---|
| Login | accesso corretto, utente bloccato, password sbagliata, utente inesistente, campi vuoti |
| Catalogo | sei prodotti, ordinamento per prezzo crescente e decrescente, contatore del carrello |
| Checkout | acquisto completo fino alla conferma, errore se manca il nome |

## Come lanciarli

Servono Java 17, Maven e Chrome. Il driver lo scarica Selenium da solo.

```bash
mvn test                     # si vede il browser
mvn test -Dheadless=true     # senza finestra
```

Il report arriva in `target/surefire-reports`.

## Cose che aggiungerei

- Test in parallelo
- Un report più bello (Allure)
- Gli stessi test anche su Firefox
