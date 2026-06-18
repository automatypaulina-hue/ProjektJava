import net.datafaker.Faker;
import org.junit.jupiter.api.Test;  // import adnotacji testu junit @Test
import org.junit.jupiter.params.ParameterizedTest;  // jeden test wykona sie pare razy
import org.junit.jupiter.params.provider.MethodSource;  // zrodlo danych
import org.junit.jupiter.params.provider.ValueSource;  // zrodlo danych junit 2 razy sie wykona (joga,pilates
import org.example.pages.*;  // import page objectow

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoreTests extends BaseTest {
// extends dziedziczy z BaseTest czyli driver SetUp TearDown
    private static final Faker faker = new Faker(new Locale("pl"));

    static Stream<Object[]> checkoutDataProvider() { //dostarcza dane DDT
        return Stream.generate(() -> new Object[]{
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().streetAddress(),
                "00-000",
                faker.address().city(),
                faker.phoneNumber().cellPhone(),
                faker.internet().emailAddress()
        }).limit(2);  // generuje 2 zestawy danych
    }


    // PRZYPADEK 1
    @ParameterizedTest
    @ValueSource(strings = {"Yoga", "Pilates"})  //DDT
    public void testSearchProduct(String productKeyword) {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.searchForProduct(productKeyword);

        int resultsCount = homePage.getSearchResultsCount();
        assertTrue(resultsCount > 0, "Brak wyników wyszukiwania dla słowa: " + productKeyword);
    }


    // PRZYPADEK 2
    @Test
    public void testAddProductToCartFromProductPage() {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.open();
        homePage.clickFirstProduct();
        productPage.addToCart();

        String alertText = productPage.getAlertText().toLowerCase();
        assertTrue(alertText.contains("dodany do koszyka"),
                alertText);
    }

    // PRZYPADEK 3
    @Test
    public void testUpdateCartQuantity() {
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.open();
        homePage.addFirstProductToCartDirectly();

        cartPage.open();
        String targetQuantity = "4";
        cartPage.updateQuantity(targetQuantity);

        String currentQuantity = cartPage.getProductQuantity();
        assertEquals(targetQuantity, currentQuantity);
    }

    // PRZYPADEK 4
    @ParameterizedTest
    @ValueSource(strings = {"NIEISTNIEJE1", "ZLYKOD2026"})  //DDT
    public void testInvalidCouponCode(String invalidCode) {
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.open();
        homePage.addFirstProductToCartDirectly();

        cartPage.open();
        cartPage.applyCoupon(invalidCode);

        assertTrue(cartPage.getErrorMessage().toLowerCase().contains("nie istnieje"));
    }

    // PRZYPADEK 5
    @ParameterizedTest(name = "Złożenie zamówienia przez: {0} {1}")
    @MethodSource("checkoutDataProvider")
    public void testCheckoutFormWithFakerData(String fName, String lName, String street, String zip, String city, String phone, String email) {
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        homePage.open();
        homePage.addFirstProductToCartDirectly();

        cartPage.open();
        cartPage.proceedToCheckout();

        checkoutPage.fillBillingDetails(fName, lName, street, zip, city, phone, email);
        checkoutPage.fillCardDetails("4242424242424242", "1229", "123");
        checkoutPage.acceptTerms();
        checkoutPage.placeOrder();

        assertTrue(checkoutPage.isOrderSuccessfullyPlaced());
    }

    // PRZYPADEK 6
    @Test
    public void testViewCartFromProductPageNotification() {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.open();
        homePage.clickFirstProduct();
        productPage.addToCart();

        productPage.clickViewCartFromAlert();

        assertTrue(driver.getCurrentUrl().contains("/koszyk/"), "Nie przekierowano pomyślnie do koszyka");
    }
}