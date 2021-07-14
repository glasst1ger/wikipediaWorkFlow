package com.codecool.wikipediatest;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class WikipediaTest {
    private WebDriver webDriver;
    private By usernameField = By.id("wpName1");
    private By passwordField = By.id("wpPassword1");
    private By searchField = By.id("searchInput");
    private By searchButton = By.id("searchButton");
    private By seleniumId = By.id("firstHeading");

    private WebDriverWait getWebDriverWait() {
        return new WebDriverWait(webDriver, 10);
    }

    @BeforeAll
    static void setDriverProperty() {
        System.setProperty("webdriver.chrome.driver", "D:/!CodeCool Automation Testing Course/chromedriver.exe");
    }

    @BeforeEach
    public void setWebDriver() {
        webDriver = new ChromeDriver();
    }

    @Test
    public void testLoginWiki() {
        webDriver.get("https://wikipedia.org");
        webDriver.manage().window().maximize();
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.id("js-link-box-en"))).click();
        webDriver.findElement(By.id("pt-login")).click();
        webDriver.findElement(usernameField).sendKeys("ViktorNemIsVagyVicces");
        webDriver.findElement(passwordField).sendKeys("ÓDehogyisNem");
        webDriver.findElement(By.id("wpLoginAttempt")).click();
    }

    @Test
    public void testWikipedia() {
        testLoginWiki();

        webDriver.findElement(searchField).sendKeys("Selenium");
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[6]/div/a[3]/div")));

        WebElement seleniumSoftwarePage = webDriver.findElement(By.xpath("/html/body/div[6]/div/a[3]/div"));
        seleniumSoftwarePage.click();

        String seleniumHeader = webDriver.findElement(seleniumId).getText();
        assertEquals("Selenium (software)", seleniumHeader);


        webDriver.navigate().back();

    }

    @Test
    public void testFrameworkSearch() {
        testLoginWiki();
        webDriver.findElement(searchField).sendKeys("Framework");
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[6]/div/a[1]/div")));
        webDriver.findElement(searchButton).click();
        String frameworkHeader = webDriver.findElement(seleniumId).getText();

        assertEquals("Framework", frameworkHeader);

    }

    @Test
    public void testChangeFontSettings() {
        testLoginWiki();
        webDriver.findElement(By.xpath("//*[@id=\"p-lang\"]/button")).click();

        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"uls-display-settings-fonts-tab\"]")));
        webDriver.findElement(By.xpath("//*[@id=\"uls-display-settings-fonts-tab\"]")).click();

        new Select(webDriver.findElement(By.xpath("//*[@id=\"content-font-selector\"]"))).selectByVisibleText("ComicNeue");
        webDriver.findElement(By.xpath("//*[@id=\"language-settings-dialog\"]/div[3]/div/button[2]")).click();

        String expected = "ComicNeue";
        String actual = webDriver.findElement(By.xpath("//*[@id=\"mp-welcome\"]/a")).getCssValue("font-family").split(",")[0];

        assertEquals(expected, actual);
    }

    @Test
    public void testSeleniumGridParagraph() {
        boolean isSeleniumGridParagraphPresent = false;
        testLoginWiki();
        webDriver.findElement(By.xpath("//*[@id=\"searchInput\"]")).sendKeys("Selenium");
        getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[6]/div/a[3]/div/span"))).click();
        List<WebElement> allElements = webDriver.findElements(By.xpath("//*"));
        for (WebElement allElement : allElements) {
            if (allElement.getText().contains("Selenium Grid")) {
                isSeleniumGridParagraphPresent = true;
            }
        }
        assertTrue(isSeleniumGridParagraphPresent);

    }


    @AfterEach
    public void close() {
        webDriver.close();

    }

}
