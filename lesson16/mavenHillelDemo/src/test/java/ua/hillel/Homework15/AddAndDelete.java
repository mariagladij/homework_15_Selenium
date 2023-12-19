package ua.hillel.Homework15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class AddAndDelete {

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

    public void testRemoveItemFromCart() throws InterruptedException {

        String url = "https://www.bstackdemo.com/";
        driver.get(url);

        Thread.sleep(1000);

        // Додаємо 1 товар в корзину
        addItemToCart("iPhone 12 Pro");

        // Відкриваємо корзину та видаляємо товар
        //openCart();
        removeItemFromCart();

        // Перевіряємо, що корзина порожня
        Assert.assertTrue(isCartEmpty());
    }

    private void addItemToCart(String productName) {
        WebElement addToCartButton = driver.findElement(By.xpath("//*[text()='"+productName+"']/following-sibling::*[@class='shelf-item__buy-btn']"));
        addToCartButton.click();
    }

    private void removeItemFromCart() throws InterruptedException {
        WebElement removeButton = driver.findElement(By.xpath("//*[@class = 'shelf-item__del']"));
        Thread.sleep(1000);
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
