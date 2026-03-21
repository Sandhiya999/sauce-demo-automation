package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class CheckoutInfoPage extends BasePage{
    public CheckoutInfoPage()
    {
        super();
    }
    private By pageTitle = By.cssSelector("span[data-test='title']");
    private By userFirstName = By.id("first-name");
    private By userLastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By checkerr = By.cssSelector("h3[data-test='error']");
    public String verifyCheckoutPage()
    {
        return getTextValue(pageTitle);
    }
    public void getUserInfo(String first,String last,String post)
    {
        type(userFirstName,first);
        type(userLastName,last);
        type(postalCode,post);
        log.info("Filling user info: " + first + " " + last + ", postal code: " + post);
        click(continueBtn);
    }
    public boolean isPreviewpage()
    {
        return driver.getCurrentUrl().contains("checkout-step-two");

    }
    public String getCheckOutErr()
    {
        return getTextValue(checkerr);
    }
    public CheckoutPreview returnPreviewpage()
    {
        if(isElementPresent(pageTitle))
        {
            log.info("Navigating to checkout preview page");
            return new CheckoutPreview();
        }
        else
        {
            throw new NoSuchElementException("Page not in preview page");
        }
    }

}
