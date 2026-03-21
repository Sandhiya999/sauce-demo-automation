package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    public CartPage() {
        super();
    }

    private By cart = By.cssSelector("span[data-test='title']");
    private By checkOutBtn = By.id("checkout");

    public void removeFromCart(String product) {
        By prod = By.xpath("//div[contains(@class,'inventory_item_name') and contains(text(),'" + product + "')]" +
                "/ancestor::div[contains(@class,'cart_item_label')]//button");
        click(prod);
        log.info("Removing product from cart: "+ product);
    }

    public List<Map<String, String>> getProducts() {

    List<WebElement> cartList = driver.findElements(By.cssSelector("div.cart_item"));
    List<Map<String, String>> cartContainer = new ArrayList<>();
        for(WebElement item :cartList)

    {
        String name = item.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText().trim();
        String priceTxt = item.findElement(By.cssSelector("div[data-test='inventory-item-price']")).getText().trim().replace("$", "");
        cartContainer.add(Map.of("name", name, "price", priceTxt));
    }
        cartContainer.sort(Comparator.comparing(m -> m.get("name")));
        return cartContainer;
    }
    public CheckoutInfoPage checkout()
    {
        click(checkOutBtn);
        log.info("Navigating to Checkout User Info page");
        return new CheckoutInfoPage();
    }

}
