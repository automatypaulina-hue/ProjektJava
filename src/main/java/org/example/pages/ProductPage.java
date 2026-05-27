package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    @FindBy(css = "button[name='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "div.woocommerce-message")
    private WebElement alertNotice;

    @FindBy(css = ".woocommerce-message a.wc-forward")
    private WebElement viewCartButtonInAlert;

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void addToCart() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String getAlertText() {
        return webDriverWait.until(ExpectedConditions.visibilityOf(alertNotice)).getText();
    }

    public void clickViewCartFromAlert() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(viewCartButtonInAlert)).click();
    }
}