package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;  //czeka az np przycisk bedzie widoczny

import java.time.Duration; //okreslenie czasu

public class BasePage {
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        PageFactory.initElements(webDriver, this);  // pomaga znalezc elementy na stronie
    }
}