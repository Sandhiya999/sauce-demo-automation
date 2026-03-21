package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CheckoutPreview extends BasePage{
    public CheckoutPreview()
    {
        super();
    }
    private By previewTitle = By.cssSelector("span[data-test='title']");
    private By finishBtn = By.id("finish" );
    private By itemUITotal = By.cssSelector("div[data-test='subtotal-label']");
    private By tax = By.cssSelector("div[data-test='tax-label']");
    private By total = By.cssSelector("div[data-test='total-label']");
    List<Map<String, String>> previewContainer = new ArrayList<>();
    public String verifyPreviewPage()
    {
        return getTextValue(previewTitle);
    }
    public List<Map<String,String>> getPreviewItems()
    {
        previewContainer.clear();
        List<WebElement> previewList = driver.findElements(By.cssSelector("div.cart_item"));
        for(WebElement item :previewList)

        {
            String name = item.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().trim();
            String priceTxt = item.findElement(By.cssSelector("div[data-test='inventory-item-price']")).getText().trim().replace("$", "");
            previewContainer.add(Map.of("name", name, "price", priceTxt));
        }
        previewContainer.sort(Comparator.comparing(m -> m.get("name")));
        return previewContainer;

    }
    public Double getCalculatedItemTotal()
    {
        Double itemTotal =0.0;
        for(Map<String,String> one: previewContainer)
        {
            itemTotal  = itemTotal + (Double.parseDouble(one.get("price")));
        }
        return itemTotal;
    }
    public Double getTotalFromUI()
    {
         return Double.parseDouble(getTextValue(itemUITotal).trim().replace("Item total: $",""));

    }
    public Double getTaxFromUI()
    {
        Double taxAmount=0.0;
        taxAmount = Double.parseDouble(getTextValue(tax).trim().replace("Tax: $",""));
        return taxAmount;
    }
    public Double totalOfUI()
    {
        return Double.parseDouble(getTextValue(total).trim().replace("Total: $",""));
    }
    public Double totalOfCalculated()
    {
        return (getCalculatedItemTotal())+ (getTaxFromUI());
    }
    public OrderCompletionPage placeOrder()
    {
       click(finishBtn);
        log.info("Placing order by clicking finish button");
        log.info("Navigating to Order Completion Page");
       return new OrderCompletionPage();
    }
}
