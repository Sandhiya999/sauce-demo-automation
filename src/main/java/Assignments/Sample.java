package Assignments;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sample {
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("input#login-button")).click();
        //List<WebElement> prodList = driver.findElements(By.cssSelector("div[data-test='inventory-item-name']"));
        List<WebElement> cartList = driver.findElements(By.cssSelector("div.cart_item"));
        List<Map<String,String>> cartContainer = new ArrayList<>();
        for (WebElement item : cartList)
        {
            String name = item.findElement(By.cssSelector("div.-item-name")).getText().trim();
            String priceTxt = item.findElement(By.cssSelector("div._item_price")).getText().trim().replace("$","");
            cartContainer.add(Map.of("name",name,"price",priceTxt));
        }
        for(Map<String,String> prod :cartContainer)
        {
            System.out.println(prod);
        }

      /*  int count=0;
        for(WebElement el: prodList)
        {
            System.out.println(el.getText());
            count++;
        }
        if(count>0) {
            System.out.println("NO. of Items " + count);
            driver.quit();
        }*/

    }

}
