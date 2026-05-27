package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = "article#post-6 div.entry-content input[id^='quantity']")
    private WebElement quantityInput;

    @FindBy(name = "update_cart")
    private WebElement updateCartButton;

    @FindBy(css = "article#post-6 div.entry-content a.remove")
    private WebElement removeProductButton;

    @FindBy(css = ".woocommerce-message")
    private WebElement removalSuccessMessage;

    @FindBy(css = ".cart-empty")
    private WebElement emptyCartMessage;

    @FindBy(id = "coupon_code")
    private WebElement couponCodeInput;

    @FindBy(name = "apply_coupon")
    private WebElement applyCouponButton;

    @FindBy(id = "coupon-error-notice")
    private WebElement errorMessage;

    @FindBy(css = ".woocommerce-error")
    private WebElement woocommerceError;

    @FindBy(css = ".checkout-button")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "div.blockUI.blockOverlay")
    private WebElement loaderOverlay;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open() {
        webDriver.get("https://fakestore.testelka.pl/koszyk/");
    }

    public void updateQuantity(String qty) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(quantityInput)).clear();
        quantityInput.sendKeys(qty);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(updateCartButton)).click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(loaderOverlay));
    }

    public String getProductQuantity() {
        return webDriverWait.until(ExpectedConditions.visibilityOf(quantityInput)).getAttribute("value");
    }

    public void applyCoupon(String code) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(couponCodeInput)).clear();
        couponCodeInput.sendKeys(code);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(applyCouponButton)).click();
    }

    public String getErrorMessage() {
        try {
            return webDriverWait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
        } catch (Exception e) {
            return webDriverWait.until(ExpectedConditions.visibilityOf(woocommerceError)).getText();
        }
    }

    public void proceedToCheckout() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
    }
}