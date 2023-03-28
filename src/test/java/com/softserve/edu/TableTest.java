package com.softserve.edu;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class TableTest extends BaseTest{

    @Test
    public void checkCityPresence(){
        //Click on the Understand button
        driver.findElement(By.xpath("//button[contains(text(), 'Understand')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//footer[contains(@class, 'pJvQ8')]")));
        driver.switchTo().frame(driver.findElement(By.xpath("//div[contains(@data-options, '/filter-row')]//iframe")));
        //Scroll to the Uncontrolled Mode item table
        Actions actions = new Actions(driver);
        actions.moveToElement(
                driver.findElement(By.xpath("//div[contains(@data-options, '/filter-row')]//th[3]//input[contains(@placeholder, 'Filter')]")))
                .perform();
        //Enter the letter L in the City filter
        List<WebElement>initialElements = driver.findElements(By.xpath("//div[contains(@data-options, '/filter-row')]//tbody//td"));
        driver.findElement(By
                .xpath("//div[contains(@data-options, '/filter-row')]//th[3]//input[contains(@placeholder, 'Filter')]"))
                .sendKeys("L", Keys.ENTER);
        waitForTableSizeChanging(initialElements);
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@data-options, '/filter-row')]//tbody//td"));
        //Check the presence in the column of the cities of Las Vegas and London
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(elements.stream().anyMatch(city -> city.getText().contains("Las Vegas")));
        softAssert.assertTrue(elements.stream().anyMatch(city -> city.getText().contains("London")));
        softAssert.assertAll();
    }

    public void waitForTableSizeChanging(List<WebElement>initialElements){
        new WebDriverWait(driver, Duration.ofSeconds(60)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver){
                return initialElements.size() !=
                        driver.findElements(By.xpath("//div[contains(@data-options, '/filter-row')]//tbody//td")).size();
            }
        });
    }
}
