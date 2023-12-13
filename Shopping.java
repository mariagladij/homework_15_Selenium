package ua.hillel.Homework15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Shopping {
    private static WebDriver driver;


    @BeforeAll
    static void setUp() {

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://www.bstackdemo.com/");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
    @Test
    @Order(1)
    public void testAddTwoItemsToCart() {
        // Додаємо 2 товари в корзину
        addItemToCart("Product 1");
        addItemToCart("Product 2");

        Assert.assertTrue(isItemInCart("Product 1"));
        Assert.assertEquals(getCartTotal(), "$ 1598.00");

        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[3]/div[3]"));
        Assert.assertTrue(checkoutButton.isDisplayed());
    }

    @Test
    @Order(2)
    public void testRemoveItemFromCart() {
        // Додаємо 1 товар в корзину
        addItemToCart("Product 1");

        // Відкриваємо корзину та видаляємо товар
        openCart();
        removeItemFromCart("Product 1");

        // Перевіряємо, що корзина порожня
        Assert.assertTrue(isCartEmpty());
    }


    private void addItemToCart(String itemName) {
        WebElement addToCartButton = driver.findElement(By.className("shelf-item__buy-btn"));
        //WebElement addToCartButton = driver.findElement(By.xpath("//*[@id=\"1\"]/div[4]"));
        addToCartButton.click();
    }

    private boolean isItemInCart(String itemName) {
        try {
            WebElement cartItem = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]"));
            return cartItem.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    private String getCartTotal() {
        WebElement totalElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[3]/div[2]/p"));
        return totalElement.getText();
    }

    private void openCart() {
        WebElement cartIcon = driver.findElement(By.className("float-cart"));
        cartIcon.click();
    }

    private void removeItemFromCart(String itemName) {
        WebElement removeButton = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[1]"));
        removeButton.click();
    }

    private boolean isCartEmpty() {
        try {
            WebElement emptyCartMessage = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[3]/div[3]"));
            return emptyCartMessage.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
