package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class InventoryPage extends BasePage{
    public InventoryPage()
    {
        super();
    }
    private By home = By.cssSelector("span[data-test='title']");
    List<Map<String,String>> expectedProdList = new ArrayList<>();
    public String getHomeTitle()
    {
        return getTextValue(home);
    }
    public CartPage goToCart()
    {
       if(isElementPresent(cartBtn)) {
           click(cartBtn);
           log.info("Navigating to Cart page");
           return new CartPage();
       }
       else {
           throw new NoSuchElementException("Cart button not found");
       }
    }
    public void addToCart(String prodn)
    {
        By prod= By.xpath(
                "//div[contains(@class,'inventory_item_name') and contains(text(),'" + prodn + "')]" +
                        "/ancestor::div[contains(@class,'inventory_item')]//button"
        );
        By prodcontainer = By.xpath(
                "//div[contains(@class,'inventory_item_name') and contains(text(),'" + prodn + "')]" +
                        "/ancestor::div[contains(@class,'inventory_item')]"
        );
        WebElement prod1 = driver.findElement(prodcontainer);
        String name1 = prod1.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().trim();
        String pricetxt1 = prod1.findElement(By.cssSelector("div[data-test='inventory-item-price']")).getText().trim().replace("$", "");
        expectedProdList.add(Map.of("name",name1,"price",pricetxt1));
        click(prod);
        log.info("Adding product to cart: "+prodn);
    }
    public boolean removeProduct(String prodremove)
    {
        String prodId=prodremove.toLowerCase().replace( " ","-");
        By removeBtn = By.id("remove-"+prodId);
        if(driver.findElements(removeBtn).size()>0)
        {
            click(removeBtn);
            return true;
        }
        return false;
    }
    public List<Map<String,String>> getSelectedProducts()
    {
        expectedProdList.sort(Comparator.comparing(m -> m.get("name")));
        return expectedProdList;
    }
    public boolean isInventoryPageLoaded()
    {
        return isElementVisible(By.id("inventory_container"));
    }

    }
