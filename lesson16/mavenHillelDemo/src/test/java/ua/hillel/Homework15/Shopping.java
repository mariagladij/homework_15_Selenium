package ua.hillel.Homework15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


import static org.testng.Assert.*;

public class Shopping {
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

    public void testAddTwoItemsToCart() {
        // Додаємо 2 товари в корзину
        addItemToCart("Product_1");
        addItemToCart2();

        assertTrue(isItemInCart());
        assertEquals(getCartTotal(), "$ 1498.00");

        WebElement checkoutButton = driver.findElement(By.className("shelf-item__buy-btn"));
        assertTrue(checkoutButton.isDisplayed());
    }

    @Test

    public void testRemoveItemFromCart() {
        // Додаємо 1 товар в корзину
        addItemToCart("Product 1");

        // Відкриваємо корзину та видаляємо товар
        openCart();
        removeItemFromCart();
        removeItemFromCart();

        // Перевіряємо, що корзина порожня
        Assert.assertTrue(isCartEmpty());
    }


    private void addItemToCart(String Product_1) {
        WebElement addToCartButton = driver.findElement(By.className("shelf-item__buy-btn"));
        addToCartButton.click();
    }

    private void addItemToCart2() {
        WebElement addToCardButton2 = driver.findElement(By.xpath("//*[@id=\"2\"]/div[4]"));
        addToCardButton2.click();
    }

    private void addItemToCartTwo(String Product_2) {
        WebElement addToCartButton = driver.findElement(By.className("shelf-item__buy-btn"));
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

    private void openCart() {
        WebElement cartIcon = driver.findElement(By.className("float-cart"));
        cartIcon.click();
    }

    private void removeItemFromCart() {
        WebElement removeButton = driver.findElement(By.className("shelf-item__del"));
        removeButton.click();
    }

    private boolean isCartEmpty() {
        try {
            WebElement emptyCartMessage = driver.findElement(By.className("shelf-empty"));
            return emptyCartMessage.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
