package pages;
import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class BasePage extends BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected By cartBtn = By.cssSelector("span[data-test='shopping-cart-badge']");
    protected By hamburgerMenu = By.cssSelector("button#react-burger-menu-btn");
    protected By logoutBtn = By.id("logout_sidebar_link");
    protected By emptyCart = By.cssSelector("a[data-test='shopping-cart-link']");

    public BasePage ()
    {
        this.driver=getDriver();
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    /*------Wait Helpers--------------------*/
    protected WebElement waitVisible(By locator)
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    protected WebElement waitClickable(By locator)
    {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    /*------Actions-------------------*/
    protected void click(By locator)
    {   log.info("Clicking Element: "+ locator);
        waitClickable(locator).click();
    }
    protected String getTextValue(By locator)
    {
       // scrollIntoView(locator);
        return waitVisible(locator).getText();
    }
    protected void type(By locator, String text)
    {
        WebElement element = waitVisible(locator);
        element.clear();
        log.info("Typing " + text + "into element: " + locator);
       element.sendKeys(text);

    }
    /*------OTHERS______*/
    protected boolean displayed(By locator)
    {
        waitVisible(locator);
        return driver.findElement(locator).isDisplayed();
    }
    protected void highlight(By locator)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].style.border='3px solid red'",element);
    }
    /*------Safe Checks-------------------*/
    public boolean isElementPresent(By locator)
    {
        waitVisible(locator);
        boolean present = driver.findElements(locator).size()>0;
        if(!present)
        {
            log.warn("Element" + locator + "Not present");
        }
        else {
            log.info("Element" + locator + "present in DOM");

        }
        return present;
    }
    protected boolean isElementVisible(By locator)
    {
        try
        {
            return waitVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    /*------UTIL-------------------*/
    protected void scrollIntoView(By locator)
    {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",element);
    }
    public int cartCount( )
    {
        if(driver.findElements(cartBtn).size()>0) {
            return Integer.parseInt(getTextValue(cartBtn));
        }
        return 0;
    }
    public LoginPage logout()
    {
        click(hamburgerMenu);
        click(logoutBtn);
        log.info("Logged Out Successfully");
        return new LoginPage();
    }
    public void pageRefresh()
    {
        driver.navigate().refresh();
    }

}
