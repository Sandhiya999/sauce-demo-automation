package Assignments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AssignOne {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
        driver.manage().window().maximize();
        System.out.println(driver.getTitle());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        long start = System.nanoTime();
        WebElement userID = driver.findElement(By.id("user-name"));
        long end = System.nanoTime();
        System.out.println("Wait time " + (end-start));
        System.out.println(userID.getText());
        List<WebElement> users = driver.findElements(By.cssSelector("#login_credentials > br"));
        boolean count = users.size() > 0;
        if(!count)
        {
            System.out.println("false");
        }
        else {
            System.out.println(count);
        }

       // WebElement userName = driver.findElement(By.name("user-name"));
       // WebElement userClass = driver.findElement(By.className("input_error form_input"));
       // WebElement userCSS = driver.findElement(By.cssSelector("input#user-name"));
      // WebElement userxpath = driver.findElement(By.xpath("//input[@id='user-name']"));
        driver.quit();
    }
}
