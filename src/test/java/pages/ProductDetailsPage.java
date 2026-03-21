package pages;

import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage{

    public ProductDetailsPage()
    {
        super();
    }
    private By prod_name = By.cssSelector("div[data-test='inventory-item-name']");
    private By price = By.cssSelector("div[data-test='inventory-item-price']");
    private By backPage = By.cssSelector("button#back-to-products");


    public String prod_name() {
        waitVisible(prod_name);
        highlight(prod_name);
        return getTextValue(prod_name);
    }
    public String prod_price()
    {
        waitVisible(price);
        highlight(price);
        return getTextValue(price);
    }

    public InventoryPage goback(){
        waitClickable(backPage);
        click(backPage);
        return new InventoryPage();
    }


}
