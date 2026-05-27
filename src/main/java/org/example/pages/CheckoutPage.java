package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    @FindBy(id = "billing_first_name")
    private WebElement firstNameInput;

    @FindBy(id = "billing_last_name")
    private WebElement lastNameInput;

    @FindBy(id = "billing_address_1")
    private WebElement addressInput;

    @FindBy(id = "billing_postcode")
    private WebElement postcodeInput;

    @FindBy(id = "billing_city")
    private WebElement cityInput;

    @FindBy(id = "billing_phone")
    private WebElement phoneInput;

    @FindBy(id = "billing_email")
    private WebElement emailInput;

    @FindBy(id = "terms")
    private WebElement termsCheckbox;

    @FindBy(css = "label[for='terms']")
    private WebElement termsCheckboxLabel;

    @FindBy(id = "place_order")
    private WebElement placeOrderButton;

    @FindBy(css = "iframe[title*='Secure payment'], iframe[name^='__privateStripeFrame']")
    private WebElement stripeIframe;

    @FindBy(id = "payment-numberInput")
    private WebElement cardNumberInput;

    @FindBy(css = "input[id*='expiry'], input[placeholder*='MM']")
    private WebElement cardExpiryInput;

    @FindBy(css = "input[id*='cvc'], input[placeholder*='CVC']")
    private WebElement cardCvcInput;

    @FindBy(css = ".woocommerce-order-overview__order.order")
    private WebElement orderReceivedConfirmation;

    @FindBy(css="div.blockUI.blockOverlay")
    private WebElement blockOverlay;

    public CheckoutPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void fillBillingDetails(String fName, String lName, String street, String zip, String city, String phone, String email) {
        webDriverWait.until(ExpectedConditions.visibilityOf(firstNameInput)).clear();
        firstNameInput.sendKeys(fName);

        lastNameInput.clear();
        lastNameInput.sendKeys(lName);

        addressInput.clear();
        addressInput.sendKeys(street);

        postcodeInput.clear();
        postcodeInput.sendKeys(zip);

        cityInput.clear();
        cityInput.sendKeys(city);

        phoneInput.clear();
        phoneInput.sendKeys(phone);

        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void fillCardDetails(String cardNumber, String expiry, String cvc) {
        webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeIframe));

        webDriverWait.until(ExpectedConditions.visibilityOf(cardNumberInput)).sendKeys(cardNumber);
        cardExpiryInput.sendKeys(expiry);
        cardCvcInput.sendKeys(cvc);

        webDriver.switchTo().defaultContent();
    }

    public void acceptTerms() {

        webDriverWait.until(
                ExpectedConditions.elementToBeClickable(termsCheckbox)
        );

        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
    }

    public void placeOrder() {

        webDriverWait.until(
                ExpectedConditions.invisibilityOf(
                        blockOverlay
                )
        );

        webDriverWait.until(
                ExpectedConditions.elementToBeClickable(placeOrderButton)
        );

        Actions actions = new Actions(webDriver);

        actions.moveToElement(placeOrderButton).perform();

        placeOrderButton.click();
    }

    public boolean isOrderSuccessfullyPlaced() {
        return webDriverWait.until(ExpectedConditions.visibilityOf(orderReceivedConfirmation)).isDisplayed();
    }
}