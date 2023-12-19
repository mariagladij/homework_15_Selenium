package ua.hillel.Homework15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AddItemToCart {
    private static WebDriver driver;

    @BeforeClass
    static void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.bstackdemo.com/");
    }

    @AfterClass
    static void tearDown() {
        driver.quit();
    }

    @Test

    public void testAddTwoItemsToCart() throws InterruptedException {
        // Додаємо 2 товари в корзину
        addItemToCart("iPhone 12 Pro");
        addItemToCart("iPhone 12 Mini");

        Thread.sleep(4000);
        assertTrue(isItemInCart());
        assertEquals(getCartTotal(), "$ 1698.00");

        WebElement checkoutButton = driver.findElement(By.className("shelf-item__buy-btn"));
        assertTrue(checkoutButton.isDisplayed());
    }

    private void addItemToCart(String productName) {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[text()='"+productName+"']/following-sibling::*[@class='shelf-item__buy-btn']"));
        addToCartButton.click();
    }

    private boolean isItemInCart() {
        try {
            WebElement cartItem = driver.findElement(By.className("float-cart__shelf-container"));
            return cartItem.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    private String getCartTotal() {
        WebElement totalElement = driver.findElement(By.className("sub-price__val"));
        return totalElement.getText();
    }
}


