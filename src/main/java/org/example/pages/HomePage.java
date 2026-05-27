package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = ".site-search input.search-field")
    private WebElement searchField;

    @FindBy(css = "li.product a.woocommerce-LoopProduct-link")
    private WebElement firstProductLink;

    @FindBy(css = "li.product")
    private List<WebElement> searchResultProducts;

    @FindBy(css = "a.add_to_cart_button")
    private WebElement addToCartButton;

    @FindBy(css = "a.woocommerce-store-notice__dismiss-link")
    private WebElement dismissNoticeLink;

    @FindBy(css = "a.added_to_cart")
    private WebElement viewCartLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        webDriver.get("https://fakestore.testelka.pl/");
        try {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(dismissNoticeLink)).click();
        } catch (Exception e) {
        }
    }

    public void searchForProduct(String keyword) {
        webDriverWait.until(ExpectedConditions.visibilityOf(searchField)).clear();
        searchField.sendKeys(keyword);
        searchField.sendKeys(Keys.ENTER);
    }

    public int getSearchResultsCount() {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElements(searchResultProducts)).size();
    }

    public void clickFirstProduct() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(firstProductLink)).click();
    }

    public void addFirstProductToCartDirectly() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        webDriverWait.until(ExpectedConditions.visibilityOf(viewCartLink));
    }
}