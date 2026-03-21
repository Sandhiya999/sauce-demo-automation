package pages;
import org.openqa.selenium.By;

public class OrderCompletionPage extends BasePage{
    public OrderCompletionPage()
    {
        super();
    }
    private By thankYouHeader = By.cssSelector("h2[data-test='complete-header']");
    private By completionText = By.cssSelector("div[data-test='complete-text']");
    public String getthankYouHeader()
    {
               log.info("Fetching Thankyou message");
               //log.info(getTextValue(thankYouHeader));
               return getTextValue(thankYouHeader);
    }
    public String getCompletionText()
    {
       log.info("Please wait Fetching your Order Completion Message");
       log.info(getTextValue(completionText));
       return getTextValue(completionText);
}    }

